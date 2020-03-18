package com.technicalinterest.group.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dao.UserRole;
import com.technicalinterest.group.dto.BlogUserDTO;
import com.technicalinterest.group.dto.RoleAuthDTO;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.mapper.RoleAuthMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.mapper.UserRoleMapper;
import com.technicalinterest.group.service.MailService;
import com.technicalinterest.group.service.VSystemService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.*;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.JWTUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @package: com.shuyu.blog.service.impl
 * @className: UserServiceImpl
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:37
 * @since: 0.1
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailService mailService;
    @Autowired
    private VSystemService systemService;
    @Autowired
    private DefaultKaptcha producer;

    @Value("${server}")
    private String SERVER;

    @Value("${activation_web_url}")
    private String ACTIVATION_WEB_URL;

    @Value("${reset_web_url}")
    private String REST_WEB_URL;
    /**
     *
     */
    @Value("${activation_time}")
    private Long ACTIVATION_TIME;
    /**
     * 登录过期时间
     */
    @Value("${login_time}")
    private Long LOGIN_TIME;
    /**
     * 验证码过期时间
     */
    @Value("${img_time}")
    private Long IMG_TIME;
    /**
     * 登录错误次数
     */
    @Value("${login_error_times}")
    private Integer LOGIN_ERROR_TIMES;
    /**
     * 用户锁住时间
     */
    @Value("${lock_user_time}")
    private Long LOCK_USER_TIME;

    private static final String PASS_SALT = "3edc4rfv!@#";

    private static final String LOGIN_ERROR_KEY = "login_error_";

    private String LOCK_USER_KEY = "lock_user_";


    /**
     * 登录
     *
     * @param userDTO
     * @return ReturnClass<UserDTO>
     * @author: shuyu.wang
     * @date: 2019-07-14 18:48
     */
    @Override
    public ReturnClass login(EditUserDTO userDTO) {
        //验证码校验
        ReturnClass returnClass = validImg(userDTO.getToken(), userDTO.getImg());
        if (!returnClass.isSuccess()) {
            return returnClass;
        }
        //判断登录错误次数
        ReturnClass loginErrorTimes = loginErrorTimes(userDTO.getUserName());
        if (!loginErrorTimes.isSuccess()) {
            return loginErrorTimes;
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        //用户名判断
        UserRoleDTO userRoleDTO1 = userMapper.queryUserRoleDTO(user);
        if (Objects.isNull(userRoleDTO1)) {
            return ReturnClass.fail(UserConstant.NO_USER);
        }
        if (userRoleDTO1.getState() == 0) {
            return ReturnClass.fail(UserConstant.NO_ACTIVATION);
        }
        if (userRoleDTO1.getIsDel() == 1) {
            return ReturnClass.fail(UserConstant.USER_DISABLE);
        }
        //密码判断
        user.setPassWord(userDTO.getPassWord() + PASS_SALT);
        UserRoleDTO userRoleDTO = userMapper.queryUserRoleDTO(user);
        if (Objects.isNull(userRoleDTO)) {
            //累加错误次数
            ReturnClass returnClass1 = addErrorSum(userDTO.getUserName());
            if (!returnClass1.isSuccess()) {
                return returnClass1;
            }
            return ReturnClass.fail(UserConstant.PASSWORD_ERROR);
        }
        //清除错误登录次数
        redisUtil.del(LOGIN_ERROR_KEY + userDTO.getUserName());
        //获取权限列表
        List<RoleAuthDTO> roleAuthDTOS = roleAuthMapper.queryAuthByRole(userRoleDTO.getRoleId(), (short) 1);
        if (userRoleDTO.getRoleType() == 1) {
            if (!redisUtil.hasKey(UserConstant.ADMIN_AUTH_URL)) {
                List<RoleAuthDTO> roleAuth = roleAuthMapper.queryAuthByRole(userRoleDTO.getRoleId(), (short) 2);
                StringBuffer stringBuffer = new StringBuffer();
                for (RoleAuthDTO entity : roleAuth) {
                    stringBuffer.append(entity.getUrl());
                    stringBuffer.append(",");
                }
                redisUtil.set(UserConstant.ADMIN_AUTH_URL, stringBuffer.toString());
            }
        }
        //生成token
        UserJWTDTO userVO = new UserJWTDTO();
        userVO.setUserName(userRoleDTO.getUserName());
        userVO.setNickName(userRoleDTO.getNickName());
        userVO.setRoleType(userRoleDTO.getRoleType());
        userVO.setPhoto(userRoleDTO.getPhoto());
        userVO.setAuthList(roleAuthDTOS);
        userVO.setUserToken(setToken(userRoleDTO.getUserName()));
        return ReturnClass.success(UserConstant.LOGIN_SUCCESS, userVO);
    }

    private String setToken(UserJWTDTO userVO, UserRoleDTO userRoleDTO) {
        String token = JWTUtil.generateToken(userVO);
        redisUtil.set(token, JSONObject.toJSONString(userRoleDTO), LOGIN_TIME);
        return token;
    }

    private String setToken(String userName) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        if (redisUtil.hasKey(userName)) {
            String o = (String) redisUtil.get(userName);
            redisUtil.del(o);
        }
        redisUtil.set(userName, token, LOGIN_TIME);
        redisUtil.set(token, userName, LOGIN_TIME);
        return token;
    }

    /**
     * 注册新用户
     *
     * @param newUserDTO
     * @return null
     * @author: shuyu.wang
     * @date: 2019-07-21 21:32
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnClass addUser(EditUserDTO newUserDTO) {
        //验证码校验
        ReturnClass returnClass = validImg(newUserDTO.getToken(), newUserDTO.getImg());
        if (!returnClass.isSuccess()) {
            return returnClass;
        }
        User user = User.builder().userName(newUserDTO.getUserName()).build();
        User userByUser = userMapper.getUserByUser(user);
        if (!Objects.isNull(userByUser)) {
            return ReturnClass.fail(UserConstant.DUPLICATE_USER_NAME);
        }
        User email = User.builder().email(newUserDTO.getEmail()).build();
        User emailResult = userMapper.getUserByUser(email);
        if (!Objects.isNull(emailResult)) {
            return ReturnClass.fail(UserConstant.DUPLICATE_USER_EMAIL);
        }
        BeanUtils.copyProperties(newUserDTO, user);
        user.setNickName("小小程序员");
        user.setPassWord(newUserDTO.getPassWord() + PASS_SALT);
        int i = userMapper.insertSelective(user);
        if (i != 1) {
            return ReturnClass.fail(UserConstant.ADD_USER_ERROR);
        } else {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId((long) 2);
            int i1 = userRoleMapper.insertSelective(userRole);
            if (i1 < 1) {
                throw new VLogException(UserConstant.ADD_USER_ERROR);
            }
            VSystemDTO vSystemDTO = VSystemDTO.builder().userName(newUserDTO.getUserName()).vStart(new Date()).build();
            systemService.insertSelective(vSystemDTO);
//			String key = newUserDTO.getUserName() + "_" + UUID.randomUUID().toString();
//			redisUtil.set(key, String.valueOf(user.getId()), ACTIVATION_TIME);
//			//发送邮件
//			//点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
//			mailService.sendHtmlMail(newUserDTO.getEmail(), UserConstant.MAIL_TITLE,
//					"<p>欢迎!&nbsp" + newUserDTO.getUserName() + "<br>" + "感谢您在技术兴趣博客网站的注册，请点击这里激活您的账号:<br></p>" + "<a href=\"" + ACTIVATION_WEB_URL + UserConstant.ACTIVATION_URL
//							+ key + "\">" + SERVER + UserConstant.ACTIVATION_URL + key + "</a><br>" + "<p>祝您使用愉快，使用过程中您有任何问题请及时联系我们。</p>");

            return ReturnClass.success(UserConstant.ADD_EMAIL_SEND);
        }
    }

    /**
     * 更新用户信息
     *
     * @param editUserDTO
     * @return null
     * @author: shuyu.wang
     * @date: 2019-07-21 22:11
     */
    @Override
    public ReturnClass updateUser(EditUserDTO editUserDTO) {
        User user = new User();
        BeanUtils.copyProperties(editUserDTO, user);
        user.setUserName(getUserNameByLoginToken());
        int update = userMapper.updateByUserName(user);
        if (update != 1) {
            return ReturnClass.fail(UserConstant.EDIT_USER_ERROR);
        } else {
            return ReturnClass.success(UserConstant.EDIT_USER_SUS);
        }
    }

    /**
     * 修改密码
     *
     * @param editUserDTO
     * @return null
     * @author: shuyu.wang
     * @date: 2019-07-21 22:11
     */
    @Override
    public ReturnClass updateUserPassWord(EditUserDTO editUserDTO) {
        String accessToken = RequestHeaderContext.getInstance().getAccessToken();
        if (!redisUtil.hasKey(accessToken)) {
            throw new VLogException(ResultEnum.TIME_OUT);
        }
        String userName = (String) redisUtil.get(accessToken);
        User user = User.builder().userName(userName).passWord(editUserDTO.getNewPassWord() + PASS_SALT).build();
        User userByUser = userMapper.getUserByUser(user);
        if (Objects.isNull(userByUser)) {
            return ReturnClass.fail("旧密码错误");
        }
        user.setPassWord(editUserDTO.getNewPassWord() + PASS_SALT);
        int update = userMapper.updateByUserName(user);
        if (update != 1) {
            return ReturnClass.fail(UserConstant.EDIT_USER_ERROR);
        } else {
            return ReturnClass.success(UserConstant.EDIT_USER_SUS);
        }
    }

    /**
     * @param token
     * @return null
     * @Description: 退出登录
     * @author: shuyu.wang
     * @date: 2019-07-21 22:22
     */
    @Override
    public ReturnClass logout(String token) {
        if (redisUtil.hasKey(token)) {
            Object o = redisUtil.get(token);
            redisUtil.del(token);
            redisUtil.del(o.toString());
        }
        return ReturnClass.success();
    }

    /**
     * @param key
     * @return null
     * @Description: 账号激活
     * @author: shuyu.wang
     * @date: 2019/8/5 21:24
     */
    @Override
    public ReturnClass activationUser(String key) {
        String id = (String) redisUtil.get(key);
        if (!StringUtils.isEmpty(id)) {
            User user = new User();
            user.setId(Long.parseLong(id));
            user.setState((short) 1);
            int update = userMapper.update(user);
            if (update < 1) {
                throw new VLogException(UserConstant.ACTIVATION_FAIL);
            }
            redisUtil.del(key);
            return ReturnClass.success(UserConstant.ACTIVATION_SUC);
        } else {
            return ReturnClass.fail(UserConstant.MAIL_OUTTIME);
        }
    }

    /**
     * @return null
     * @Description: 根据toke获取用户信息
     * @author: shuyu.wang
     * @date: 2019-07-28 19:43
     */
    @Override
    public ReturnClass getUserByToken() {
        String accessToken = RequestHeaderContext.getInstance().getAccessToken();
        if (!redisUtil.hasKey(accessToken)) {
            throw new VLogException(ResultEnum.TIME_OUT);
        }
        String userName = (String) redisUtil.get(accessToken);
        User user = User.builder().userName(userName).build();
        UserRoleDTO userRoleDTO = userMapper.queryUserRoleDTO(user);
        log.info("getUserByToken>>>  token={},userInfo={}", accessToken, userRoleDTO);
        if (Objects.isNull(userRoleDTO)) {
            throw new VLogException(ResultEnum.USERINFO_ERROR);
        }
        return ReturnClass.success(userRoleDTO);
    }

    /**
     * @param userName
     * @return null
     * @Description:根据用户名查询用户信息
     * @author: shuyu.wang
     * @date: 2019-08-08 13:08
     */
    @Override
    public ReturnClass getUserByuserName(Boolean authCheck, String userName) {
        //获取数据是否是当前用户校验
        if (authCheck) {
            ReturnClass returnClass = userNameIsLoginUser(userName);
            if (!returnClass.isSuccess()) {
                throw new VLogException(ResultEnum.NO_AUTH);
            } else {
                return returnClass;
            }
        }
        User user = User.builder().userName(userName).build();
        User userResult = userMapper.getUserByUser(user);
        if (Objects.nonNull(userResult)) {
            return ReturnClass.success(userResult);
        }
        return ReturnClass.fail();
    }

    /**
     * @return null
     * @Description:查询登陆者用户信息
     * @author: shuyu.wang
     * @date: 2019-08-08 13:08
     */
    @Override
    public ReturnClass getUserDetail() {
        User user = User.builder().userName(getUserNameByLoginToken()).build();
        User userResult = userMapper.getUserByUser(user);
        if (Objects.nonNull(userResult)) {
            return ReturnClass.success(userResult);
        }
        return ReturnClass.fail();
    }

    /**
     * @param userName
     * @return null
     * @Description: 判断用户名是否是当前操作登录的用户
     * @author: shuyu.wang
     * @date: 2019-08-08 13:12
     */
    @Override
    public ReturnClass userNameIsLoginUser(String userName) {
        String accessToken = RequestHeaderContext.getInstance().getAccessToken();
        if (!redisUtil.hasKey(accessToken)) {
            throw new VLogException(ResultEnum.TIME_OUT);
        }
        String useName = (String) redisUtil.get(accessToken);
        User query = new User();
        query.setUserName(useName);
        User result = userMapper.getUserByUser(query);
//		UserDTO userDTO=JSONObject.parseObject(userInfo,UserDTO.class);
        if (Objects.nonNull(result) && StringUtils.equals(userName, useName)) {
            return ReturnClass.success(result);
        }
        return ReturnClass.fail();
    }

    /**
     * @param userName
     * @return null
     * @Description:获取博客用户信息
     * @author: shuyu.wang
     * @date: 2019-08-19 12:56
     */
    @Override
    public ReturnClass getBlogUserInfo(String userName) {
        List<BlogUserDTO> blogUserDTOS = userMapper.queryUserBlog(userName);
        if (blogUserDTOS.isEmpty()) {
            return ReturnClass.fail(UserConstant.NO_USER_INFO);
        }
        return ReturnClass.success(blogUserDTOS);
    }

    /**
     * @param userName
     * @return null
     * @Description: 发送忘记密码邮件
     * @author: shuyu.wang
     * @date: 2019-10-08 17:04
     */
    @Override
    public ReturnClass sendForgetPassMail(String userName, String email, String img, String token) {
        //验证码校验
        ReturnClass returnClass = validImg(token, img);
        if (!returnClass.isSuccess()) {
            return returnClass;
        }
        User user = new User();
        user.setUserName(userName);
        //用户名判断
        User userByUser = userMapper.getUserByUser(user);
        if (Objects.isNull(userByUser)) {
            return ReturnClass.fail(UserConstant.NO_USER);
        }
        if (userByUser.getState() == 0) {
            return ReturnClass.fail(UserConstant.NO_ACTIVATION);
        }
        if (!StringUtils.equals(email, userByUser.getEmail())) {
            return ReturnClass.fail(UserConstant.MAIL_FAIL);
        }
        String key = userName + "_" + UUID.randomUUID().toString();
        redisUtil.set(key, String.valueOf(userByUser.getId()), ACTIVATION_TIME);
        //发送邮件
        //点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
        mailService.sendHtmlMail(userByUser.getEmail(), UserConstant.FORGET_PASS_MAIL_TITLE,
                "<p>您好!&nbsp" + userName + "<br>" + "如果您确认忘记登录密码，请点击这里重置您的登录密码:<br></p>" + "<a href=\"" + REST_WEB_URL + UserConstant.FORGET_PASS_URL + key + "\">" + SERVER
                        + UserConstant.FORGET_PASS_URL + key + "</a><br>" + "<p>如果操作过程中遇到其他问题请及时联系我们。</p>");

        return ReturnClass.success(UserConstant.FORGET_PASS_MAIL_SEND);
    }

    /**
     * @param editUserDTO
     * @return null
     * @Description: 修改密码
     * @author: shuyu.wang
     * @date: 2019-10-08 17:23
     */
    @Override
    public ReturnClass resetPassWord(String key, EditUserDTO editUserDTO) {

        String id = (String) redisUtil.get(key);
        if (!StringUtils.isEmpty(id)) {
            User user = new User();
            user.setUserName(editUserDTO.getUserName());
            //用户名判断
            User userByUser = userMapper.getUserByUser(user);
            if (Objects.isNull(userByUser)) {
                return ReturnClass.fail(UserConstant.NO_USER);
            }
            if (Long.parseLong(id) != userByUser.getId()) {
                return ReturnClass.fail(UserConstant.KEY_ERROR);
            }
            user.setId(Long.parseLong(id));
            user.setPassWord(editUserDTO.getPassWord());
            int update = userMapper.update(user);
            if (update < 1) {
                throw new VLogException(UserConstant.CHANGE_PASS_FAIL);
            }
            redisUtil.del(key);
            return ReturnClass.success(UserConstant.CHANGE_PASS_SUC);
        } else {
            return ReturnClass.fail(UserConstant.MAIL_OUTTIME);
        }
    }

    @Override
    public ReturnClass createImage() {
        /**
         * 前后端分离 登录验证码 方案
         * 后端生成图片 验证码字符串 uuid
         * uuid为key  验证码字符串为value
         * 传递bs64图片 和uuid给前端
         * 前端在登录的时候 传递 账号 密码 验证码 uuid
         * 通过uuid获取 验证码 验证
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //获取验证码
        String text = producer.createText();
        try {
            BufferedImage image = producer.createImage(text);
            ImageIO.write(image, "png", out);
            String base64bytes = Base64.encodeBase64String(out.toByteArray());
            //该字符串传输至前端放入src即可显示图片，安卓可以去掉data:image/png;base64,
            String src = "data:image/png;base64," + base64bytes;
            String token ="imagVerifi_"+UUID.randomUUID().toString().replace("-","");
            redisUtil.set(token, text, IMG_TIME);
            ImagVerifi imagVerifi = ImagVerifi.builder().img(src.replaceAll("[\\s*\t\n\r]", "")).token(token).build();
            return ReturnClass.success(imagVerifi);
        } catch (IOException e) {
            throw new VLogException(UserConstant.CREAT_IMG_FAIL);
        }
    }

    /**
     * 查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public ReturnClass getUserInfo(String userName) {
        User user = User.builder().userName(userName).build();
        User userByUser = userMapper.getUserByUser(user);
        if (Objects.isNull(userByUser)) {
            throw new VLogException(ResultEnum.NO_URL);
        }
        return ReturnClass.success(userByUser);
    }

    /**
     * @param token
     * @return null
     * @Description: 校验验证码
     * @author: shuyu.wang
     * @date: 2019-10-10 17:07
     */
    private ReturnClass validImg(String token, String img) {
        if (redisUtil.hasKey(token)) {
            String result = (String) redisUtil.get(token);
            if (StringUtils.equals(result, img)) {
                redisUtil.del(token);
                return ReturnClass.success(UserConstant.IMG_SUC);
            }
            redisUtil.del(token);
            return ReturnClass.fail(UserConstant.IMG_FAIL);
        }
        return ReturnClass.fail(UserConstant.IMG_TIME_OUT);
    }

    /**
     * @param userName
     * @return null
     * @Description: 累加错误次数
     * @author: shuyu.wang
     * @date: 2019-10-11 18:27
     */
    private ReturnClass addErrorSum(String userName) {
        String key = LOGIN_ERROR_KEY + userName;
        if (redisUtil.hasKey(key)) {
            long incr = redisUtil.incr(key, (long) 1);
            if (incr >= LOGIN_ERROR_TIMES) {
                redisUtil.set(LOCK_USER_KEY + userName, userName, LOCK_USER_TIME);
                return ReturnClass.fail(UserConstant.LOGIN_ERROR_OVER_TIMES);
            }
        } else {
            redisUtil.set(key, (long) 1, LOCK_USER_TIME);
        }
        return ReturnClass.success();
    }

    /**
     * @param userName
     * @return com.technicalinterest.group.service.dto.ReturnClass
     * @Description: 判断账号是否被锁定
     * @author: shuyu.wang
     * @date: 2019/10/11 21:45
     */
    private ReturnClass loginErrorTimes(String userName) {
        String key = LOCK_USER_KEY + userName;
        if (redisUtil.hasKey(key)) {
            return ReturnClass.fail(UserConstant.LOGIN_ERROR_OVER_TIMES);
        }
        return ReturnClass.success();
    }

    /**
     * 根据登录token获取用户名
     *
     * @return
     */
    @Override
    public String getUserNameByLoginToken() {
        String accessToken = RequestHeaderContext.getInstance().getAccessToken();
        if (!redisUtil.hasKey(accessToken)) {
            throw new VLogException(ResultEnum.TIME_OUT);
        }
        String userName = (String) redisUtil.get(accessToken);
        return userName;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        User user = User.builder().userName(userName).build();
        User result = userMapper.getUserByUser(user);
        if (Objects.isNull(result)) {
            throw new VLogException(ResultEnum.USERINFO_ERROR);
        }
        return result;
    }
}

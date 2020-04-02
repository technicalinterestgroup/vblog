package com.technicalinterest.group.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.*;
import com.technicalinterest.group.api.util.IndexOrderByUtil;
import com.technicalinterest.group.api.vo.*;
import com.technicalinterest.group.api.vo.websitenotice.WebsiteNoticeDetailVO;
import com.technicalinterest.group.api.vo.websitenotice.WebsiteNoticeVO;
import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.dao.Reply;
import com.technicalinterest.group.dto.*;
import com.technicalinterest.group.service.*;
import com.technicalinterest.group.service.Enum.ArticleOrderEnum;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.annotation.VBlogReadCount;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.dto.*;
import com.technicalinterest.group.service.dto.AskDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.ListBeanUtils;
import com.technicalinterest.group.service.util.WebSocketUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: ViewController
 * @description: 前端显示接口
 * @author: Shuyu.Wang
 * @date: 2019-08-12 13:07
 * @since: 0.1
 **/
@Api(tags = "前端系统接口")
@RestController
@RequestMapping("view")
@Slf4j
public class ViewController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	private VSystemService vSystemService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TagService tagService;
	@Autowired
	private WebsiteNoticeService websiteNoticeService;
	@Autowired
	private AskService askService;
	@Autowired
	private ReplayService replayService;

	private static final Boolean authCheck = false;

	/**
	 * 登录接口
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "登录", notes = "用户登录")
	@GetMapping(value = "/user/login")
	@BlogOperation(value = "登录")
	public ApiResult<UserVO> login(@Valid UserParam userParam) {
		ApiResult apiResult = new ApiResult();
		EditUserDTO userDTO = new EditUserDTO();
		BeanUtils.copyProperties(userParam, userDTO);
		ReturnClass login = userService.login(userDTO,(short)2);
		if (login.isSuccess()) {
			UserVO userVO = new UserVO();
			UserJWTDTO resultUser = (UserJWTDTO) login.getData();
			BeanUtils.copyProperties(resultUser, userVO);
			List<RoleAuthVO> result=new ArrayList<>();
			for (RoleAuthDTO entity: resultUser.getAuthList()) {
				RoleAuthVO parent=new RoleAuthVO();
				BeanUtils.copyProperties(entity,parent);
				if(Objects.nonNull(entity)){
					List list = ListBeanUtils.copyProperties(entity.getChildren(), RoleAuthVO.class);
					parent.setChildren(list);
				}
				result.add(parent);
			}
			userVO.setAuthList(result);
			apiResult.success(userVO);
			apiResult.setMsg(login.getMsg());
		} else {
			apiResult.fail(login.getMsg());
		}
		return apiResult;
	}

	/**
	 * 注册新用户
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:50
	 * @param newUserParam
	 * @return com.technicalinterest.group.api.vo.ApiResult<String>
	 */
	@ApiOperation(value = "新用户注册", notes = "新用户注册")
	@PostMapping(value = "/user/new")
	@BlogOperation(value = "新用户注册")
	@DistributeLock(key = "#newUserParam.userName", timeout = 1, expire = 1, errMsg = "00000")
	public ApiResult<String> saveUser( @RequestBody NewUserParam newUserParam) {
		log.info("注册用户数据：{}", JSONObject.toJSON(newUserParam));
		ApiResult apiResult = new ApiResult();
		EditUserDTO newUserDTO = new EditUserDTO();
		BeanUtils.copyProperties(newUserParam, newUserDTO);
		ReturnClass addUser = userService.addUser(newUserDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getMsg(), null);
		} else {
			apiResult.fail(addUser.getMsg());
		}
		return apiResult;
	}

	/**
	 * 账号激活
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:27
	 * @return null
	 */
	@ApiOperation(value = "账号激活", notes = "激活")
	@GetMapping(value = "/user/activation/{key}")
	@BlogOperation(value = "账号激活")
	public ApiResult<String> activationUser(@PathVariable("key") String key) {
		ApiResult apiResult = new ApiResult();
		ReturnClass activationUser = userService.activationUser(key);
		if (activationUser.isSuccess()) {
			apiResult.success(activationUser.getMsg(), null);
		} else {
			apiResult.fail(activationUser.getMsg());
		}
		return apiResult;
	}

	/**
	 * 发送重置密码邮件
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:27
	 * @return null
	 */
	@ApiOperation(value = "发送重置密码邮件")
	@GetMapping(value = "/user/forget/{userName}")
	@BlogOperation(value = "发送重置密码邮件")
	public ApiResult<String> forgetPassWord(@PathVariable("userName") String userName,@Validated ResetEmailParam resetEmailParam) {
		ApiResult apiResult = new ApiResult();
		ReturnClass activationUser = userService.sendForgetPassMail(userName,resetEmailParam.getEmail(),resetEmailParam.getImg(),resetEmailParam.getToken());
		if (activationUser.isSuccess()) {
			apiResult.success(activationUser.getMsg(), null);
		} else {
			apiResult.fail(activationUser.getMsg());
		}
		return apiResult;
	}

	/**
	 * 重置密码
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:27
	 * @return null
	 */
	@ApiOperation(value = "重置密码")
	@PostMapping(value = "/user/reset/{key}")
	@BlogOperation(value = "重置密码")
	@DistributeLock( key = "#resetPassParam.userName", timeout = 2, expire = 1, errMsg = "00000")
	public ApiResult<String> resetPassWord(@PathVariable("key") String key, @Validated @RequestBody ResetPassParam resetPassParam) {
		ApiResult apiResult = new ApiResult();
		EditUserDTO editUserDTO = new EditUserDTO();
		BeanUtils.copyProperties(resetPassParam, editUserDTO);
		ReturnClass activationUser = userService.resetPassWord(key, editUserDTO);
		if (activationUser.isSuccess()) {
			apiResult.success(activationUser.getMsg(), null);
		} else {
			apiResult.fail(activationUser.getMsg());
		}
		return apiResult;
	}

	
	/**
	 * @Description: 获取验证码
	 * @author: shuyu.wang
	 * @date: 2019-10-11 11:24
	 * @return null
	*/
	@ApiOperation(value = "获取验证码")
	@GetMapping(value = "/captcha")
	public ApiResult<ImagVerifi> captcha() {
		ApiResult apiResult = new ApiResult();
		ReturnClass img = userService.createImage();
		if (img.isSuccess()) {
			apiResult.success(img.getData());
		} else {
			apiResult.setMsg(img.getMsg());
		}
		return apiResult;
	}

	/**
	 * 用户信息接口
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "会员用户信息", notes = "用户信息")
	@GetMapping(value = "/user/info/{userName}")
	public ApiResult<UserBlogInfoVO> userInfo(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass userInfo = userService.getUserInfo(userName);
		if (userInfo.isSuccess()) {
			ReturnClass blogUserInfo = articleService.getBlogInfoByUser(userName);
			UserBlogInfoVO userVO = new UserBlogInfoVO();
			BeanUtils.copyProperties(userInfo.getData(), userVO);
			BeanUtils.copyProperties(blogUserInfo.getData(), userVO);
			apiResult.success(userVO);
		} else {
			throw new VLogException(ResultEnum.NO_URL);
		}
		return apiResult;
	}

	/**
	 * @Description: 会员文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param queryArticleParam
	 * @return null
	 */
	@ApiOperation(value = "会员文章列表", notes = "文章列表")
	@GetMapping(value = "/article/list/{userName}")
	public ApiResult<PageBean<ArticlesVO>> listUserArticle(@PathVariable("userName") String userName, @Valid QueryArticleParam queryArticleParam) {
		ApiResult apiResult = new ApiResult();
		QueryArticleDTO queryArticleDTO = new QueryArticleDTO();
		BeanUtils.copyProperties(queryArticleParam, queryArticleDTO);
		queryArticleDTO.setOrderBy(IndexOrderByUtil.getOrderByFlage(queryArticleParam));
		queryArticleDTO.setUserName(userName);
		queryArticleDTO.setState((short) 1);
		ReturnClass listArticle = articleService.listArticle(authCheck, userName, queryArticleDTO);
		if (listArticle.isSuccess()) {
			PageBean<ArticlesDTO> pageBean = (PageBean<ArticlesDTO>) listArticle.getData();
			List<ArticlesVO> list = new ArrayList<>();
			for (ArticlesDTO entity : pageBean.getPageData()) {
				ArticlesVO articlesVO = new ArticlesVO();
				BeanUtils.copyProperties(entity, articlesVO);
				list.add(articlesVO);
			}
			PageBean<ArticlesVO> pageInfo = new PageBean<ArticlesVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);

		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 会员最新文章
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param userName
	 * @return null
	 */
	@ApiOperation(value = "会员最新文章列表", notes = "文章列表")
	@GetMapping(value = "/article/new/{userName}")
	public ApiResult<ArticleTitleVO> listArticleHotByUser(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(userName, ArticleOrderEnum.NEW.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 会员热门文章
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param userName
	 * @return null
	 */
	@ApiOperation(value = "会员热门文章列表")
	@GetMapping(value = "/article/hot/{userName}")
	public ApiResult<ArticleTitleVO> listArticleNewByUser(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(userName, ArticleOrderEnum.HOT.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 用户推荐文章 按点赞数量排序
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @return null
	 */
	@ApiOperation(value = "用户推荐阅读文章列表")
	@GetMapping(value = "/article/recommend/{userName}")
	public ApiResult<ArticleTitleVO> listArticleRecommendByUser(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(userName, ArticleOrderEnum.Recommend.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 文章归档
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param userName
	 * @return null
	 */
	@ApiOperation(value = "会员文章归档", notes = "文章归档")
	@GetMapping(value = "/article/archive/{userName}")
	public ApiResult<List<ArticleArchiveVO>> listArticleArchive(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticleArchive = articleService.listArticleArchive(userName);
		if (listArticleArchive.isSuccess()) {
			List<ArticleArchiveVO> list = new ArrayList<ArticleArchiveVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticleArchive.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleArchiveVO articleArchiveVO = new ArticleArchiveVO();
				BeanUtils.copyProperties(entity, articleArchiveVO);
				list.add(articleArchiveVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticleArchive.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "会员文章分类", notes = "文章分类")
	@GetMapping(value = "/article/category/{userName}")
	public ApiResult<ArticleTitleVO> listArticleCategory(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();

		ReturnClass listCategory = categoryService.listCategoryByUser(userName);
		if (listCategory.isSuccess()) {
			List<CategoryVO> list = new ArrayList<CategoryVO>();
			List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) listCategory.getData();
			for (CategoryDTO entity : categoryDTOList) {
				CategoryVO categoryVO = new CategoryVO();
				BeanUtils.copyProperties(entity, categoryVO);
				list.add(categoryVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listCategory.getMsg());
		}
		return apiResult;
	}
	@ApiOperation(value = "用户文章标签")
	@GetMapping(value = "/article/tags/{userName}")
	public ApiResult<List<TagVO>> allTagListByUser(@PathVariable("userName") String userName){
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = tagService.allTagList(userName);
		if (returnClass.isSuccess()) {
			List<TagVO> list = new ArrayList<>();
			List<TagDTO> articlesDTOS = (List<TagDTO>) returnClass.getData();
			for (TagDTO entity : articlesDTOS) {
				TagVO tagVO = new TagVO();
				BeanUtils.copyProperties(entity, tagVO);
				list.add(tagVO);
			}
			apiResult.success(list);
		}
		return apiResult;
	}

	/**
	 * @Description: 文章详情
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param id
	 * @return null
	 */
	@ApiOperation(value = "文章详情", notes = "文章详情")
	@GetMapping(value = "/article/detail/{id}")
	@VBlogReadCount(type = "1")
	public ApiResult<ArticleContentVO> articleDetail(@PathVariable("id") Long id,
			@RequestParam(name = "userName",required = false)String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass articleDetail = articleService.articleDetailView(id,userName);
		ArticleContentVO articleContentVO = new ArticleContentVO();
		if (articleDetail.isSuccess()) {
			BeanUtils.copyProperties(articleDetail.getData(), articleContentVO);
			apiResult.success(articleContentVO);

		} else {
			apiResult.setMsg(articleDetail.getMsg());
		}
		return apiResult;
	}
	/**
	 * 查询参数详情
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "博客主题渲染查询接口", notes = "详情")
	@GetMapping(value = "/blog/info/{userName}")
	public ApiResult<VSystemVO> systemDetail(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass getSystemByUser = vSystemService.getSystemByUser(userName);
		if (getSystemByUser.isSuccess()) {
			VSystemVO vSystemVO = new VSystemVO();
			BeanUtils.copyProperties(getSystemByUser.getData(), vSystemVO);
			apiResult.success(vSystemVO);

		} else {
			apiResult.fail(getSystemByUser.getMsg());
		}
		return apiResult;
	}
//-------------------------------------------------------------------
	/**
	 * @Description: 网站文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @param queryArticleParam
	 * @return null
	 */
	@ApiOperation(value = "整站文章列表", notes = "文章列表")
	@GetMapping(value = "/article/list")
	public ApiResult<PageBean<ArticlesVO>> listArticle(@Valid QueryArticleParam queryArticleParam) {
		ApiResult apiResult = new ApiResult();
		QueryArticleDTO queryArticleDTO = new QueryArticleDTO();
		BeanUtils.copyProperties(queryArticleParam, queryArticleDTO);
		queryArticleDTO.setOrderBy(IndexOrderByUtil.getOrderByFlage(queryArticleParam));
		ReturnClass listArticle = articleService.allListArticle(queryArticleDTO);
		if (listArticle.isSuccess()) {
			PageBean<ArticlesDTO> pageBean = (PageBean<ArticlesDTO>) listArticle.getData();
			List<ArticlesVO> list = new ArrayList<>();
			for (ArticlesDTO entity : pageBean.getPageData()) {
				ArticlesVO articlesVO = new ArticlesVO();
				BeanUtils.copyProperties(entity, articlesVO);
				list.add(articlesVO);
			}
			PageBean<ArticlesVO> pageInfo = new PageBean<ArticlesVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);

		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 网站最新文章 按时间排序
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @return null
	 */
	@ApiOperation(value = "最新文章列表")
	@GetMapping(value = "/article/new")
	public ApiResult<ArticleTitleVO> listArticleHot() {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(null, ArticleOrderEnum.NEW.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}
	//最新发布

	/**
	 * @Description: 热门文章 按阅读量排序
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @return null
	 */
	@ApiOperation(value = "网站热门文章列表")
	@GetMapping(value = "/article/hot")
	public ApiResult<ArticleTitleVO> listArticleNew() {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(null, ArticleOrderEnum.HOT.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 推荐文章 按点赞数量排序
	 * @author: shuyu.wang
	 * @date: 2019-08-13 12:37
	 * @return null
	 */
	@ApiOperation(value = "推荐阅读文章列表")
	@GetMapping(value = "/article/recommend")
	public ApiResult<ArticleTitleVO> listArticleRecommend() {
		ApiResult apiResult = new ApiResult();
		ReturnClass listArticle = articleService.listArticleOrderBy(null, ArticleOrderEnum.Recommend.getOrderByFlag());
		if (listArticle.isSuccess()) {
			List<ArticleTitleVO> list = new ArrayList<ArticleTitleVO>();
			List<ArticlesDTO> articlesDTOS = (List<ArticlesDTO>) listArticle.getData();
			for (ArticlesDTO entity : articlesDTOS) {
				ArticleTitleVO articleTitleVO = new ArticleTitleVO();
				BeanUtils.copyProperties(entity, articleTitleVO);
				list.add(articleTitleVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}
	@ApiOperation(value = "文章标签集合")
	@GetMapping(value = "/article/tags")
	public ApiResult<List<TagVO>> allTagList(){
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = tagService.allTagList(null);
		if (returnClass.isSuccess()) {
			List<TagVO> list = new ArrayList<>();
			List<TagDTO> articlesDTOS = (List<TagDTO>) returnClass.getData();
			for (TagDTO entity : articlesDTOS) {
				TagVO tagVO = new TagVO();
				BeanUtils.copyProperties(entity, tagVO);
				list.add(tagVO);
			}
			apiResult.success(list);
		}
		return apiResult;
	}



	@ApiOperation(value = "博客评论", notes = "评论")
	@GetMapping(value = "/comment/list/{articleId}")
	public ApiResult<CommentResultDTO> listCommet1(@PathVariable("articleId") Long articleId) {
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = commentService.getArticleComment(articleId);
		if (returnClass.isSuccess()) {
			apiResult.success(returnClass.getData());
		} else {
			apiResult.fail(returnClass.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "获取网站排名前4位的博主信息", notes = "博主信息")
	@GetMapping(value = "/bloguser")
	public ApiResult<List<BlogUserVO>> listBlogUser() {
		ApiResult apiResult = new ApiResult();
		ReturnClass blogUserInfo = userService.getBlogUserInfo(null);
		if (blogUserInfo.isSuccess()) {
			List list1 = ListBeanUtils.copyProperties(blogUserInfo.getData(), BlogUserVO.class);
			apiResult.success(list1);
		} else {
			apiResult.setMsg(blogUserInfo.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "查询轮播图")
	@GetMapping(value = "/carousels")
	public ApiResult<List<WebsiteNoticeVO>> getCarousels(@RequestParam("type")Short type) {
		ApiResult apiResult = new ApiResult();
		ReturnClass carousels = websiteNoticeService.getIndexWebsiteNotice(type);
		if (carousels.isSuccess()) {
			List result = ListBeanUtils.copyProperties(carousels.getData(), WebsiteNoticeVO.class);
			apiResult.success(result);
		} else {
			apiResult.setMsg(carousels.getMsg());
		}
		return apiResult;
	}
	@ApiOperation(value = "通告详情查询")
	@GetMapping(value = "/notice/{id}")
	@VBlogReadCount(type = "3")
	public ApiResult<WebsiteNoticeDetailVO> getNoticeDetail(@PathVariable long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass carousels = websiteNoticeService.getWebsiteNoticeDetail(id);
		if (carousels.isSuccess()) {
			WebsiteNoticeDetailVO websiteNoticeDetailVO=new WebsiteNoticeDetailVO();
			BeanUtils.copyProperties(carousels.getData(),websiteNoticeDetailVO);
			apiResult.success(websiteNoticeDetailVO);
		} else {
			apiResult.setMsg(carousels.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "问题列表")
	@GetMapping(value = "/ask/list")
	public ApiResult<PageBean<AskListVO>> getAskList(QueryAskParam queryAskParam) {
		log.info("问题发布 参数{}", JSONObject.toJSON(queryAskParam));
		ApiResult apiResult = new ApiResult();
		AskDTO ask=new AskDTO();
		BeanUtils.copyProperties(queryAskParam, ask);
		ReturnClass<PageBean<com.technicalinterest.group.dto.AskDTO>> saveArticle = askService.getAskPage(ask);
		if (saveArticle.isSuccess()) {
			PageBean<AskListVO> result=new  PageBean<AskListVO>();
			PageBean<com.technicalinterest.group.dto.AskDTO> pageBean = saveArticle.getData();
			List list = ListBeanUtils.copyProperties(pageBean.getPageData(), AskListVO.class);
			BeanUtils.copyProperties(pageBean,result);
			result.setPageData(list);
			apiResult.success(result);
		} else {
			apiResult.fail(saveArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "问题详情")
	@GetMapping(value = "/ask/detail/{id}")
	@VBlogReadCount(type = "2")
	public ApiResult<AskVO> getAskDetail(@PathVariable Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass<Ask> saveArticle = askService.getAskDetailById(id);
		if (saveArticle.isSuccess()) {
			AskVO askVO=new AskVO();
			BeanUtils.copyProperties(saveArticle.getData(),askVO);
			apiResult.success(askVO);
		} else {
			apiResult.fail(saveArticle.getMsg());
		}
		return apiResult;
	}
	@ApiOperation(value = "问题回答列表")
	@PostMapping(value = "/{id}/replys")
	public ApiResult<List<ReplyVO>> getReplyList(@PathVariable Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass<List<Reply>> result=replayService.getReplyList(id);
		if (result.isSuccess()) {
			List<ReplyVO> replyVOS = ListBeanUtils.copyProperties(result.getData(), ReplyVO.class);
			apiResult.success(replyVOS);
		} else {
			apiResult.fail(result.getMsg());
		}
		return apiResult;
	}
	@ApiOperation(value = "测试ws")
	@GetMapping(value = "/socket")
	public ApiResult<List<BlogUserVO>> test(@RequestParam("userName")String userName,@RequestParam(value = "msg")String msg) {
		WebSocketUtils.sendToUser(userName,WebSocketMessage.builder().message(msg).build());
		ApiResult apiResult = new ApiResult();
		return apiResult;
	}
	

	//站点统计

}

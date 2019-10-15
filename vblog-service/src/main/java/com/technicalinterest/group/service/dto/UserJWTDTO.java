package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dto.RoleAuthDTO;
import lombok.Data;

import java.util.List;

/**
 * @package: com.shuyu.blog.vo
 * @className: UserVO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 19:00
 * @since: 0.1
 **/
@Data
public class UserJWTDTO {

    private String userToken;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String photo;
    /**
     * 角色等级 1;管理员 2：普通用户
     */
    private Short roleType;

    /**
     * 权限列表
     */
    private List<RoleAuthDTO> authList;
}

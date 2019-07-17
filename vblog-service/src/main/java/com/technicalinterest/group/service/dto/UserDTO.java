package com.technicalinterest.group.service.dto;

import lombok.Data;

/**
 * @package: com.shuyu.blog.dto
 * @className: UserDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:46
 * @since: 0.1
 **/
@Data
public class UserDTO extends Base{
    private Long id;

    private String userName;

    private String userToken;
}

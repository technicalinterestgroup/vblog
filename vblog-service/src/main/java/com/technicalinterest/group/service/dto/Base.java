package com.technicalinterest.group.service.dto;

import lombok.Data;

/**
 * @package: com.shuyu.blog.dto
 * @className: Base
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 19:15
 * @since: 0.1
 **/
@Data
public class Base {
    private int code;

    /**
     * 提示msg
     */
    private String msg;

    public Base() {
        this.code = -1;
    }

    public Base success(){
        this.code =0;
        return this;
    }
}

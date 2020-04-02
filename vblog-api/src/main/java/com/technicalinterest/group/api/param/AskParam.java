package com.technicalinterest.group.api.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: AskParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 17:25
 * @Version: 1.0
 */
@Data
public class AskParam {

    private Long id;
    @NotBlank
    private String title;
    /**
     * 标签id
     */
    private Long  tagId;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章内容html格式
     */
    private String contentFormat;

    /**
     * 文章内容md格式
     */
    private String content;
}

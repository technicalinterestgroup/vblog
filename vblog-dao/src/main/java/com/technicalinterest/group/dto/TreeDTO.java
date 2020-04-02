package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: TreeDTO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/26 9:42
 * @Version: 1.0
 */
@Data
public class TreeDTO {

    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否展开直子节点
     */
    private Boolean expand=true;

    private Boolean indeterminate;
    /**
     * 禁掉响应
     */
    private Boolean disabled;
    /**
     * 是否选中子节点
     */
    private Boolean selected;
    /**
     * 是否勾选(如果勾选，子节点也会全部勾选)
     */
    private Boolean checked;
    /**
     * 子节点属性数组
     */
    private List<TreeDTO> children;
}

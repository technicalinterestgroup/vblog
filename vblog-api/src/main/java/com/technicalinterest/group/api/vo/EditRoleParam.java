package com.technicalinterest.group.api.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: QueryRoleParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/25 14:02
 * @Version: 1.0
 */
@Data
public class EditRoleParam {

    private Long id;
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotNull
    private Short type;
}

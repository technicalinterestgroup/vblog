package com.technicalinterest.group.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: ArticleContentDTO
 * @description: 编辑文章
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:08
 * @since: 0.1
 **/
@Data
@ApiModel(description = "新增文章参数")
public class EditArticleContentParam {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "文章列表id")
	@NotNull(message = "文章id不能为空！")
	private Long id;

	@ApiModelProperty(value = "是否为系统推荐文章",allowableValues = "1:是,0:",example = "1")
	private Short recommend;

	/**
	 * 文章状态 0：草稿，1：发布
	 */
	@ApiModelProperty(value = "文章状态",allowableValues = "0：草稿，1：发布")
	private Short state;

}

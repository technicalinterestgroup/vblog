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

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "文章标题")
	@NotBlank(message = "文章标题不能为空！")
	private String title;
	/**
	 * 是否置顶
	 */
	@ApiModelProperty(value = "是否置顶",allowableValues = "1:置顶,0:不置顶",example = "1")
	private Short isTop;
	/**
	 * 分类id
	 */
	@ApiModelProperty(value = "文章关联分类id")
	private Long categoryId;
	/**
	 * 标签id
	 */
	@ApiModelProperty(value = "文章关联标签id")
	private Long  tagId;

	/**
	 * 文章内容
	 */
	@ApiModelProperty(value = "文章内容html格式")
	private String content;

	/**
	 * 文章内容markdown格式
	 */
	@ApiModelProperty(value = "文章内容markdown格式")
	@JsonProperty("contentMD")
	private String contentMD;
	/**
	 * 文章状态 0：草稿，1：发布
	 */
//	@ApiModelProperty(value = "文章状态",allowableValues = "0：草稿，1：发布")
//	private Short state;

	public String getContentMD() {
		return contentMD;
	}

	public void setContentMD(String contentMD) {
		this.contentMD = contentMD;
	}
}

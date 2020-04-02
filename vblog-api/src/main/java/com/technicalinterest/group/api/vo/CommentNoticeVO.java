package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CommentNoticeDTO
 * @description: 消息通知VO
 * @author: Shuyu.Wang
 * @date: 2019-08-20 12:38
 * @since: 0.1
 **/
@Data
@ApiModel(description = "评论通知")
public class CommentNoticeVO {

	@ApiModelProperty(value = "评论id")
	private Long id;

	@ApiModelProperty(value = "用户名")
	private String userName;

	private String sourceUserName;
	/**
	 * id
	 */
	private Long sourceId;

	@ApiModelProperty(value = "博客标题")
	private String title;

	@ApiModelProperty(value = "是否查看过",allowableValues = "0:未查看，1:已经查看")
	private Short isView;

	@ApiModelProperty(value = "评论时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@ApiModelProperty(value = "点赞类型",allowableValues = "1:博客，2:评论")
	private Short type;

	private String typeCN;

	public void setTypeCN() {
		if (this.type==null){
			this.typeCN="博客";
			return;
		}
		if (this.type==1){
			this.typeCN="博客";
		}else if(this.type==2){
			this.typeCN="评论";
		}
	}


}

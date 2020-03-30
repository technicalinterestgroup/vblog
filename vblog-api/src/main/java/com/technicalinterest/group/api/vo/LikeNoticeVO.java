package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CommentNoticeDTO
 * @description: 点赞消息通知DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-20 12:38
 * @since: 0.1
 **/
@Data
@ApiModel(description = "点赞列表")
public class LikeNoticeVO {
	/**
	 * 点赞id
	 */
	@ApiModelProperty(value = "记录id")
	private Long id;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "点赞用户")
	private String userName;
	@ApiModelProperty(value = "源数据作者")
	private String sourceUserName;
	/**
	 * id
	 */
	@ApiModelProperty(value = "元数据id")
	private Long sourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "名称")
	private String title;
	/**
	 * 是否查看过
	 */
	@ApiModelProperty(value = "是否查看过",allowableValues = "0:未查看，1:已经查看")
	private Short isView;

	@ApiModelProperty(value = "点赞类型",allowableValues = "1:博客，2:评论")
	private Short type;

	@ApiModelProperty(value = "点赞时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	private String typeCN;

	public String getTypeCN() {
		this.setTypeCN();
		return typeCN;
	}

	public void setTypeCN() {
		if (this.type==null){
			return;
		}
		if (this.type==1){
			this.typeCN="博客";
		}else if(this.type==2){
			this.typeCN="评论";
		}
	}
}

package com.ssafy.vue.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "CommentDto : 댓글 정보", description = "댓글 정보(articleNo, userId, userName, registerTime, content)를 나타낸다.")
public class CommentDto {

	@ApiModelProperty(value = "댓글번호")
	private int commentNo;
	@ApiModelProperty(value = "글번호")
	private int articleNo;
	@ApiModelProperty(value = "작성자 아이디")
	private String userId;
	@ApiModelProperty(value = "작성자 이름")
	private String userName;
	@ApiModelProperty(value = "글내용")
	private String content;
	@ApiModelProperty(value = "작성일")	
	private String registerTime;


	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}

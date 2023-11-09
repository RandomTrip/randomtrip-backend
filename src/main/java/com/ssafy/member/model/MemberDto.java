package com.ssafy.member.model;


public class MemberDto {

	private String userId;
	private String userPass;
	private String userName;

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

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userPass=" + userPass + ", userName=" + userName + "]";
	}
	

}

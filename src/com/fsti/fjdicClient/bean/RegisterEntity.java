package com.fsti.fjdicClient.bean;

/**
 * 注册实体
 * @author 王久叶
 *
 */
public class RegisterEntity {
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 电话号码
	 */
	private String telephone;
	
	/**
	 * 电子邮件
	 */
	private String email;
	
	/**
	 * 密码
	 */
	private String password;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

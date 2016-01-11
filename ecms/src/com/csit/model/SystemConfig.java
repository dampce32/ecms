package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_SystemConfig")
public class SystemConfig extends BaseModel {
	
	private static final long serialVersionUID = -7550834187930679983L;
	
	/**
	 * 配置Id
	 */
	private Integer configId;
	/**
	 * SMTP地址
	 */
	private String emailHost;
	/**
	 * 服务器邮箱
	 */
	private String emailCode;
	/**
	 * 邮箱密码
	 */
	private String emailPwd;
	/**
	 * 邮件过期时间（分钟）
	 */
	private Integer emailExpireTime;
	/**
	 * 注册邮件过期时间（分钟）
	 */
	private Integer registerEmailExpireTime;

	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司图片
	 */
	private Picture picture;
	/**
	 * 公司简介
	 */
	private String companyProfiles;
	/**
	 * 咨询热线
	 */
	private String hotline;
	@Id
	@Column(name = "configId", unique = true, nullable = false)
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
	@Column(name = "emailHost")
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	
	@Column(name = "emailCode")
	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	
	@Column(name = "emailPwd")
	public String getEmailPwd() {
		return emailPwd;
	}
	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}
	
	@Column(name = "emailExpireTime")
	public Integer getEmailExpireTime() {
		return emailExpireTime;
	}
	public void setEmailExpireTime(Integer emailExpireTime) {
		this.emailExpireTime = emailExpireTime;
	}
	
	@Column(name = "registerEmailExpireTime")
	public Integer getRegisterEmailExpireTime() {
		return registerEmailExpireTime;
	}
	public void setRegisterEmailExpireTime(Integer registerEmailExpireTime) {
		this.registerEmailExpireTime = registerEmailExpireTime;
	}
	
	@Column(name = "companyName")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "companyProfiles")
	public String getCompanyProfiles() {
		return companyProfiles;
	}
	public void setCompanyProfiles(String companyProfiles) {
		this.companyProfiles = companyProfiles;
	}
	@Column(name = "hotline")
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pictureId")
	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}

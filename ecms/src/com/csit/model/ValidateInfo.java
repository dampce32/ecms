package com.csit.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @Description:验证码信息类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-21
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name = "T_ValidateInfo")
public class ValidateInfo extends BaseModel {
	private static final long serialVersionUID = 683482418293724591L;

	/**
	 * id用uuid生成
	 * 验证码信息类Id
	 */
	private String validateInfoId;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 验证对象Id
	 */
	private Integer userId;
	
	
	@Id
	@Column(name = "validateInfoId", unique = true, nullable = false)
	public String getValidateInfoId() {
		return validateInfoId;
	}
	public void setValidateInfoId(String validateInfoId) {
		this.validateInfoId = validateInfoId;
	}
	
	@Column(name = "createTime", length = 23)
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

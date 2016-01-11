package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @Description: 短信配置
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-17
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_MessageConfig")
public class MessageConfig extends BaseModel {
	
	private static final long serialVersionUID = 3819519547382977483L;
	/**
	 * 开关Id
	 */
	private Integer switchId;
	/**
	 * 开关名称
	 */
	private String switchName;
	/**
	 * 开关状态
	 * 0 - 关闭
	 * 1 - 打开
	 */
	private Integer status;
	/**
	 * 短信自定义头
	 */
	private String head;
	/**
	 * 短信固定部分例子
	 */
	private String fixedPart;
	/**
	 * 短信自定义尾
	 */
	private String tail;
	/**
	 * 是否总开关
	 * 0 - 不是
	 * 1 - 是
	 */
	private Integer isMainSwitch;
	/**
	 * 备注
	 */
	private String note;
	
	@Column(name = "switchName", unique = true, nullable = false)
	public String getSwitchName() {
		return switchName;
	}
	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
	
	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "head")
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	@Column(name = "fixedPart")
	public String getFixedPart() {
		return fixedPart;
	}
	public void setFixedPart(String fixedPart) {
		this.fixedPart = fixedPart;
	}
	
	@Column(name = "tail")
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	
	@Column(name = "isMainSwitch", nullable = false)
	public Integer getIsMainSwitch() {
		return isMainSwitch;
	}
	public void setIsMainSwitch(Integer isMainSwitch) {
		this.isMainSwitch = isMainSwitch;
	}
	@Id
	@Column(name = "switchId", unique = true, nullable = false)
	public Integer getSwitchId() {
		return switchId;
	}
	public void setSwitchId(Integer switchId) {
		this.switchId = switchId;
	}
	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}

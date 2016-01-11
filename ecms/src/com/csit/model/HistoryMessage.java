package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @Description: 发送历史记录
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name="T_HistoryMessage")
public class HistoryMessage extends BaseModel {

	private static final long serialVersionUID = 4907510701407379100L;
	
	/**
	 * ID主键
	 */
	private Integer historyId;
	
	/**
	 * 消息内容
	 */
	private String messageContent;
	
	/**
	 * 接收人号码，使用","隔开
	 */
	private String receiveIDs;
	
	/**
	 * 发送时间
	 */
	private Date sendTime;
	
	/**
	 * 消息类型 
	 * 1 - 普通短信
	 * 2 - 注册验证短信
	 * 3 - 报名通知短信
	 */
	private Integer messageType;

	/**
	 * 发送信息人
	 */
	private Teacher teacher;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "historyId", unique = true, nullable = false)
	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	@Column(name = "messageContent")
	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	@Column(name = "receiveIDs", nullable = false)
	public String getReceiveIDs() {
		return receiveIDs;
	}

	public void setReceiveIDs(String receiveIDs) {
		this.receiveIDs = receiveIDs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sendTime",updatable=false)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getMessageType() {
		return messageType;
	}

	@Column(name = "messageType", nullable = false)
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId",updatable=false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}

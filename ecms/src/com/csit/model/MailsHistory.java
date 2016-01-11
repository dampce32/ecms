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
 * @Description: 邮件发送历史记录
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-9
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_MailsHistory")
public class MailsHistory extends BaseModel {
	
	private static final long serialVersionUID = -8272116573947251078L;
	/**
	 * Id
	 */
	private Integer mailsHistoryId;
	/**
	 * 发件人
	 */
	private Teacher teacher;
	/**
	 * 收件人Id 使用","隔开
	 */
	private String receiveIDs;
	/**
	 * 附件
	 */
	private String attachment;   
	/**
	 * 主题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 邮件类型
	 * 1 - 普通邮件
	 * 2 - 注册邮件
	 * 3 - 报名报告邮件
	 */
	private Integer mailType;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mailsHistoryId", unique = true, nullable = false)
	public Integer getMailsHistoryId() {
		return mailsHistoryId;
	}
	public void setMailsHistoryId(Integer mailsHistoryId) {
		this.mailsHistoryId = mailsHistoryId;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "attachment")
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sendTime")
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId",updatable=false)
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Column(name = "receiveIDs")
	public String getReceiveIDs() {
		return receiveIDs;
	}
	public void setReceiveIDs(String receiveIDs) {
		this.receiveIDs = receiveIDs;
	}
	
	@Column(name = "mailType", nullable = false)
	public Integer getMailType() {
		return mailType;
	}
	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}
	
}

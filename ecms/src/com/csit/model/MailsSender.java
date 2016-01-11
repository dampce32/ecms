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
 * @Description: 邮件发送
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-8
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_MailsSender")
public class MailsSender extends BaseModel {
	
	private static final long serialVersionUID = -7969128390292384511L;
	
	/**
	 * Id
	 */
	private Integer mailsSenderId;
	/**
	 * 发件人
	 */
	private Teacher teacher;
    /**
     * 收件人Id,用","隔开
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
	 * 发送错误次数
	 */
	private Integer errorCount;
	
	/**
	 * 邮件类型
	 * 1 - 普通邮件
	 * 2 - 注册邮件
	 * 3 - 报名报告邮件
	 */
	private Integer mailType;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mailsSenderId", unique = true, nullable = false)
	public Integer getMailsSenderId() {
		return mailsSenderId;
	}
	public void setMailsSenderId(Integer mailsSenderId) {
		this.mailsSenderId = mailsSenderId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", updatable=false)
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
	
	@Column(name = "errorCount")
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	
	@Column(name = "mailType", nullable = false)
	public Integer getMailType() {
		return mailType;
	}
	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}
}

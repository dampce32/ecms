package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @Description:教师通讯录
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-24
 * @author longweier
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Contacts")
public class Contacts extends BaseModel{
	
	/**   
	  * serialVersionUID:TODO
	  *   
	  * @since v1.0  
	 **/   
	
	private static final long serialVersionUID = 1441540472281855252L;

	/**
	 * ID
	 */
	private Integer contactsId;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 电话号码
	 */
	private String mobilePhone;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * QQ号码
	 */
	private String qqNumber;
	
	/**
	 * MSN
	 */
	private String msn;
	
	/**
	 * 是否可见
	 */
	private Integer visible;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 教师
	 */
	private Teacher teacher;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "contactsId", unique = true, nullable = false)
	public Integer getContactsId() {
		return contactsId;
	}

	public void setContactsId(Integer contactsId) {
		this.contactsId = contactsId;
	}

	@Column(name="name",length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="mobilePhone",length=50)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="email",length=100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="qqNumber",length=20)
	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	@Column(name="msn",length=50)
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name="visible")
	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}
	
	@Column(name="note",length=2000)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="teacherId",nullable = false,updatable=false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	
}

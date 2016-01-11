package com.csit.model;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Description:教师角色
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Entity
@Table(name = "T_TeacherRole")
public class TeacherRole extends BaseModel {

	private static final long serialVersionUID = -2175830298308903580L;
	// Fields
	/**
	 * 教师角色Id
	 */
	private TeacherRoleId id;
	/**
	 * 角色
	 */
	private Role role;
	/**
	 * 教师
	 */
	private Teacher teacher;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 最后操作时间
	 */
	private Timestamp operateTime;

	// Constructors

	/** default constructor */
	public TeacherRole() {
	}

	/** minimal constructor */
	public TeacherRole(TeacherRoleId id, Role role, Teacher teacher) {
		this.id = id;
		this.role = role;
		this.teacher = teacher;
	}

	/** full constructor */
	public TeacherRole(TeacherRoleId id, Role role, Teacher teacher,
			String note, Timestamp operateTime) {
		this.id = id;
		this.role = role;
		this.teacher = teacher;
		this.note = note;
		this.operateTime = operateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teacherId", column = @Column(name = "TeacherID", nullable = false)),
			@AttributeOverride(name = "roleId", column = @Column(name = "RoleID", nullable = false)) })
	public TeacherRoleId getId() {
		return this.id;
	}

	public void setId(TeacherRoleId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RoleID", nullable = false, insertable = false, updatable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID", nullable = false, insertable = false, updatable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "OperateTime", length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

}
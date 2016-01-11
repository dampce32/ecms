package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Description:教师
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Entity
@Table(name = "T_Teacher", uniqueConstraints = @UniqueConstraint(columnNames = "TeacherCode"))
public class Teacher extends BaseModel {

	// Fields

	private static final long serialVersionUID = 3267764931478494603L;
	public static final String LOGIN_TEACHERID = "loginTeacherID";
	public static final String LOGIN_TEACHERName = "loginTeacherName";
	/**
	 * 教师ID
	 */
	private Integer teacherId;
	/**
	 * 教师编号
	 */
	private String teacherCode;
	/**
	 * 教师名称
	 */
	private String teacherName;
	/**
	 * 密码
	 */
	private String passwords;
	/**
	 * 状态
	 */
	private Boolean state;
	/**
	 * 备注
	 */
	private String note;

	// private Set<RoleRight> roleRights = new HashSet<RoleRight>(0);
	// private Set<Role> roles = new HashSet<Role>(0);
	 private Set<TeacherRole> teacherRoles = new HashSet<TeacherRole>(0);
	// private Set<Right> rights = new HashSet<Right>(0);

	// Constructors

	/** default constructor */
	public Teacher() {
	}

	/** minimal constructor */
	public Teacher(Integer teacherId, String teacherName, String passwords,
			Boolean state) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.passwords = passwords;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "TeacherID", unique = true, nullable = false)
	@GeneratedValue(strategy=IDENTITY)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "TeacherCode", unique = true)
	public String getTeacherCode() {
		return this.teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	@Column(name = "TeacherName", nullable = false)
	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Column(name = "Passwords", nullable = false)
	public String getPasswords() {
		return this.passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	@Column(name = "State", nullable = false)
	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "teacher")
	public Set<TeacherRole> getTeacherRoles() {
		return teacherRoles;
	}

	public void setTeacherRoles(Set<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}

}
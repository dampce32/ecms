package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:角色
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Entity
@Table(name = "T_Role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseModel {

	// Fields

	private static final long serialVersionUID = -3678249081105297223L;
	/**
	 * 角色Id
	 */
	private Integer roleId;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 角色编号
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 状态
	 */
	private Boolean state;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 最后操作时间
	 */
	private Timestamp operateTime;
	/**
	 * 角色权限
	 */
	private Set<RoleRight> roleRights = new HashSet<RoleRight>(0);
	/**
	 * 教师角色
	 */
	private Set<TeacherRole> teacherRoles = new HashSet<TeacherRole>(0);
	
	
	/**
	 * 该角色是否被选中,主要是用作教师角色
	 */
	private String checked;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Integer roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public Role(Integer roleId, Teacher teacher, String roleCode,
			String roleName, Boolean state, String note, Timestamp operateTime,
			Set<RoleRight> roleRights, Set<TeacherRole> teacherRoles) {
		this.roleId = roleId;
		this.teacher = teacher;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.state = state;
		this.note = note;
		this.operateTime = operateTime;
		this.roleRights = roleRights;
		this.teacherRoles = teacherRoles;
	}

	// Property accessors
	@Id
	@Column(name = "RoleID", unique = true, nullable = false)
	@GeneratedValue(strategy=IDENTITY)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "RoleCode",unique=true)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "RoleName")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "State")
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

	@Column(name = "OperateTime", length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RoleRight> getRoleRights() {
		return this.roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<TeacherRole> getTeacherRoles() {
		return this.teacherRoles;
	}

	public void setTeacherRoles(Set<TeacherRole> teacherRoles) {
		this.teacherRoles = teacherRoles;
	}
	@Transient
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}
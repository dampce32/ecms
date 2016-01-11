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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:角色权限
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Entity
@Table(name = "T_RoleRight")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RoleRight extends BaseModel {

	// Fields
	private static final long serialVersionUID = 8081775363857970356L;
	/**
	 * 角色权限Id
	 */
	private RoleRightId id;
	/**
	 * 权限
	 */
	private Right right;
	/**
	 * 角色
	 */
	private Role role;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
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

	// Constructors

	/** default constructor */
	public RoleRight() {
	}

	/** minimal constructor */
	public RoleRight(RoleRightId id, Right right, Role role) {
		this.id = id;
		this.right = right;
		this.role = role;
	}

	/** full constructor */
	public RoleRight(RoleRightId id, Right right, Role role, Teacher teacher,
			Boolean state, String note, Timestamp operateTime) {
		this.id = id;
		this.right = right;
		this.role = role;
		this.teacher = teacher;
		this.state = state;
		this.note = note;
		this.operateTime = operateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "roleId", column = @Column(name = "RoleID", nullable = false)),
			@AttributeOverride(name = "rightId", column = @Column(name = "RightID", nullable = false)) })
	public RoleRightId getId() {
		return this.id;
	}

	public void setId(RoleRightId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RightID", nullable = false, insertable = false, updatable = false)
	public Right getRight() {
		return this.right;
	}

	public void setRight(Right right) {
		this.right = right;
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
	@JoinColumn(name = "TeacherID")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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

	/**
	 * @Description: 用于接收树节点展开不明原因发出的值
	 * @Create: 2012-10-27 下午11:39:53
	 * @author lys
	 * @update logs
	 * @param id
	 * @throws Exception
	 */
	public void setId(String id) {}
}
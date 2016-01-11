package com.csit.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:系统权限
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-16
 * @Author lys
 */
@Entity
@Table(name = "T_Right", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "ParentRightID", "Array" }),
		@UniqueConstraint(columnNames = { "ParentRightID", "RightCode" }) })
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Right extends BaseModel {
	// Fields
	private static final long serialVersionUID = 8000895058757180805L;
	/**
	 * 权限ID
	 */
	private String rightId;
	/**
	 * 父权限ID
	 */
	private Right parentRight;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 权限编号
	 */
	private String rightCode;
	/**
	 * 权限名称
	 */
	private String rightName;
	/**
	 * 顺序
	 */
	private Integer array;
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
	 * 是否为叶子节点
	 */
	private Boolean isLeaf;
	/**
	 * 权限Url
	 */
	private String rightUrl;
	/**
	 * 权限类型 1--Url权限 2--界面按钮 3--数据显示
	 */
	private Integer kind;
	/**
	 * 子权限
	 */
	private Set<Right> childrenRights = new HashSet<Right>(0);
	
	/**
	 * 角色权限
	 */
	private Set<RoleRight> roleRights = new HashSet<RoleRight>(0);
	/**
	 * 子权限List
	 */
	private List<Right> childrenRightList = new ArrayList<Right>();

	// Constructors

	/** default constructor */
	public Right() {
	}

	/** minimal constructor */
	public Right(String rightId) {
		this.rightId = rightId;
	}

	// Property accessors
	@Id
	@Column(name = "RightID", unique = true, nullable = false)
	public String getRightId() {
		return this.rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "RightCode")
	public String getRightCode() {
		return this.rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	@Column(name = "RightName")
	public String getRightName() {
		return this.rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	@Column(name = "Array")
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
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

	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "right")
	public Set<RoleRight> getRoleRights() {
		return this.roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ParentRightID")
	public Right getParentRight() {
		return parentRight;
	}

	public void setParentRight(Right parentRight) {
		this.parentRight = parentRight;
	}

	@Column(name = "IsLeaf")
	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "RightUrl", length = 100)
	public String getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}

	@Column(name = "Kind", length = 100, nullable = false)
	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	@Transient
	public List<Right> getChildrenRightList() {
		return childrenRightList;
	}

	public void setChildrenRightList(List<Right> childrenRightList) {
		this.childrenRightList = childrenRightList;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentRight")
	public Set<Right> getChildrenRights() {
		return childrenRights;
	}

	public void setChildrenRights(Set<Right> childrenRights) {
		this.childrenRights = childrenRights;
	}

}
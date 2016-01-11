package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:教师角色Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Embeddable
public class TeacherRoleId extends BaseModel {

	private static final long serialVersionUID = 909698468854014907L;
	// Fields
	/**
	 * 教师Id
	 */
	private Integer teacherId;
	/**
	 * 角色Id
	 */
	private Integer roleId;

	// Constructors

	/** default constructor */
	public TeacherRoleId() {
	}

	/** full constructor */
	public TeacherRoleId(Integer teacherId, Integer roleId) {
		this.teacherId = teacherId;
		this.roleId = roleId;
	}

	// Property accessors

	@Column(name = "TeacherID", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "RoleID", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TeacherRoleId))
			return false;
		TeacherRoleId castOther = (TeacherRoleId) other;

		return ((this.getTeacherId() == castOther.getTeacherId()) || (this
				.getTeacherId() != null && castOther.getTeacherId() != null && this
				.getTeacherId().equals(castOther.getTeacherId())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this
						.getRoleId() != null && castOther.getRoleId() != null && this
						.getRoleId().equals(castOther.getRoleId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTeacherId() == null ? 0 : this.getTeacherId().hashCode());
		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}
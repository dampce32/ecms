package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:角色权限Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
@Embeddable
public class RoleRightId extends BaseModel {

	private static final long serialVersionUID = 4684233439010503392L;
	// Fields
	/**
	 * 角色Id
	 */
	private Integer roleId;
	/**
	 * 权限Id
	 */
	private String rightId;

	// Constructors

	/** default constructor */
	public RoleRightId() {
	}

	/** full constructor */
	public RoleRightId(Integer roleId, String rightId) {
		this.roleId = roleId;
		this.rightId = rightId;
	}

	// Property accessors

	@Column(name = "RoleID", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RightID", nullable = false)
	public String getRightId() {
		return this.rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleRightId))
			return false;
		RoleRightId castOther = (RoleRightId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this
				.getRoleId() != null && castOther.getRoleId() != null && this
				.getRoleId().equals(castOther.getRoleId())))
				&& ((this.getRightId() == castOther.getRightId()) || (this
						.getRightId() != null && castOther.getRightId() != null && this
						.getRightId().equals(castOther.getRightId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result
				+ (getRightId() == null ? 0 : this.getRightId().hashCode());
		return result;
	}

}
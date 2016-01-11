package com.csit.model;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TempArrangeStudentId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TempArrangeStudentId extends BaseModel {

	private static final long serialVersionUID = 801939713506791577L;
	// Fields

	private Integer teacherId;
	private Timestamp operateTime;
	private Integer arrangeId;
	private Integer studentId;

	// Constructors

	/** default constructor */
	public TempArrangeStudentId() {
	}

	/** full constructor */
	public TempArrangeStudentId(Integer teacherId, Timestamp operateTime,
			Integer arrangeId, Integer studentId) {
		this.teacherId = teacherId;
		this.operateTime = operateTime;
		this.arrangeId = arrangeId;
		this.studentId = studentId;
	}

	// Property accessors

	@Column(name = "TeacherID", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "OperateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "ArrangeID", nullable = false)
	public Integer getArrangeId() {
		return this.arrangeId;
	}

	public void setArrangeId(Integer arrangeId) {
		this.arrangeId = arrangeId;
	}

	@Column(name = "StudentID", nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TempArrangeStudentId))
			return false;
		TempArrangeStudentId castOther = (TempArrangeStudentId) other;

		return ((this.getTeacherId() == castOther.getTeacherId()) || (this
				.getTeacherId() != null
				&& castOther.getTeacherId() != null && this.getTeacherId()
				.equals(castOther.getTeacherId())))
				&& ((this.getOperateTime() == castOther.getOperateTime()) || (this
						.getOperateTime() != null
						&& castOther.getOperateTime() != null && this
						.getOperateTime().equals(castOther.getOperateTime())))
				&& ((this.getArrangeId() == castOther.getArrangeId()) || (this
						.getArrangeId() != null
						&& castOther.getArrangeId() != null && this
						.getArrangeId().equals(castOther.getArrangeId())))
				&& ((this.getStudentId() == castOther.getStudentId()) || (this
						.getStudentId() != null
						&& castOther.getStudentId() != null && this
						.getStudentId().equals(castOther.getStudentId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTeacherId() == null ? 0 : this.getTeacherId().hashCode());
		result = 37
				* result
				+ (getOperateTime() == null ? 0 : this.getOperateTime()
						.hashCode());
		result = 37 * result
				+ (getArrangeId() == null ? 0 : this.getArrangeId().hashCode());
		result = 37 * result
				+ (getStudentId() == null ? 0 : this.getStudentId().hashCode());
		return result;
	}

}
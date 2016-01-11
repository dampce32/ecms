package com.csit.model;

// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TempArrangeStudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Temp_ArrangeStudent")
public class TempArrangeStudent extends BaseModel {

	private static final long serialVersionUID = 707031789442292495L;

	private TempArrangeStudentId id;
	private TempArrange tempArrange;
	private String note;

	// Constructors

	/** default constructor */
	public TempArrangeStudent() {
	}

	/** minimal constructor */
	public TempArrangeStudent(TempArrangeStudentId id, TempArrange tempArrange) {
		this.id = id;
		this.tempArrange = tempArrange;
	}

	/** full constructor */
	public TempArrangeStudent(TempArrangeStudentId id, TempArrange tempArrange,
			String note) {
		this.id = id;
		this.tempArrange = tempArrange;
		this.note = note;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "teacherId", column = @Column(name = "TeacherID", nullable = false)),
			@AttributeOverride(name = "operateTime", column = @Column(name = "OperateTime", nullable = false, length = 23)),
			@AttributeOverride(name = "arrangeId", column = @Column(name = "ArrangeID", nullable = false)),
			@AttributeOverride(name = "studentId", column = @Column(name = "StudentID", nullable = false)) })
	public TempArrangeStudentId getId() {
		return this.id;
	}

	public void setId(TempArrangeStudentId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
			@JoinColumn(name = "TeacherID", referencedColumnName = "TeacherID", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "OperateTime", referencedColumnName = "OperateTime", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "ArrangeID", referencedColumnName = "ArrangeID", nullable = false, insertable = false, updatable = false) })
	public TempArrange getTempArrange() {
		return this.tempArrange;
	}

	public void setTempArrange(TempArrange tempArrange) {
		this.tempArrange = tempArrange;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
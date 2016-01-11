package com.csit.model;

// default package

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
 * @Description:培训班级学生
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_TrainingClassStudent")
public class TrainingClassStudent implements java.io.Serializable {

	private static final long serialVersionUID = 8285833209402014258L;
	// Fields

	private Integer trainingClassStudentId;
	private Student student;
	private TrainingClass trainingClass;
	private Integer status;

	// Constructors

	/** default constructor */
	public TrainingClassStudent() {
	}

	/** minimal constructor */
	public TrainingClassStudent(Integer trainingClassStudentId) {
		this.trainingClassStudentId = trainingClassStudentId;
	}

	/** full constructor */
	public TrainingClassStudent(Integer trainingClassStudentId,
			Student student, TrainingClass trainingClass, Integer status) {
		this.trainingClassStudentId = trainingClassStudentId;
		this.student = student;
		this.trainingClass = trainingClass;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "trainingClassStudentId", unique = true, nullable = false)
	public Integer getTrainingClassStudentId() {
		return this.trainingClassStudentId;
	}

	public void setTrainingClassStudentId(Integer trainingClassStudentId) {
		this.trainingClassStudentId = trainingClassStudentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainingClassId")
	public TrainingClass getTrainingClass() {
		return this.trainingClass;
	}

	public void setTrainingClass(TrainingClass trainingClass) {
		this.trainingClass = trainingClass;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
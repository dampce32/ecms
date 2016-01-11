package com.csit.model;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Description:缴费
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Pay")
public class Pay extends BaseModel {

	private static final long serialVersionUID = 7543688465942170849L;
	// Fields

	private Integer payId;
	private PayType payType;
	private Teacher teacher;
	private Competition competition;
	private Timestamp payDate;
	private Double fee;
	private Integer status;
	private Student student;
	private String note;

	// Constructors

	/** default constructor */
	public Pay() {
	}

	/** minimal constructor */
	public Pay(Integer payId, PayType payType, Timestamp payDate, Double fee,
			Integer status) {
		this.payId = payId;
		this.payType = payType;
		this.payDate = payDate;
		this.fee = fee;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "payId", unique = true, nullable = false)
	public Integer getPayId() {
		return this.payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payTypeId", nullable = false)
	public PayType getPayType() {
		return this.payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionId", nullable = false)
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Column(name = "payDate", nullable = false, length = 23)
	public Timestamp getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	@Column(name = "fee", nullable = false, precision = 53, scale = 0)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId", nullable = false)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
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
 * @Description:支出
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Expend")
public class Expend extends BaseModel {

	private static final long serialVersionUID = 990286831346414993L;
	// Fields

	private Integer expendId;
	private ExpendType expendType;
	private Competition competition;
	private Timestamp expendDate;
	private String expendName;
	private String expendCorporation;
	private String handler;
	private String note;
	private Integer status;
	private Double fee;

	// Constructors

	/** default constructor */
	public Expend() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "expendId", unique = true, nullable = false)
	public Integer getExpendId() {
		return this.expendId;
	}

	public void setExpendId(Integer expendId) {
		this.expendId = expendId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expendTypeId", nullable = false)
	public ExpendType getExpendType() {
		return this.expendType;
	}

	public void setExpendType(ExpendType expendType) {
		this.expendType = expendType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionId", nullable = false)
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Column(name = "expendDate", nullable = false, length = 23)
	public Timestamp getExpendDate() {
		return this.expendDate;
	}

	public void setExpendDate(Timestamp expendDate) {
		this.expendDate = expendDate;
	}

	@Column(name = "expendName", nullable = false, length = 100)
	public String getExpendName() {
		return this.expendName;
	}

	public void setExpendName(String expendName) {
		this.expendName = expendName;
	}

	@Column(name = "expendCorporation", length = 100)
	public String getExpendCorporation() {
		return this.expendCorporation;
	}

	public void setExpendCorporation(String expendCorporation) {
		this.expendCorporation = expendCorporation;
	}

	@Column(name = "handler", length = 100)
	public String getHandler() {
		return this.handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "fee", nullable = false, precision = 53, scale = 0)
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

}
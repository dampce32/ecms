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

/**
 * 
 * @Description: 试题子表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-6
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name = "T_SubjectSub")
public class SubjectSub extends BaseModel {

	private static final long serialVersionUID = -6647225542239777494L;

	private SubjectSubId id;
	private Subject subject;
	private Teacher teacher;
	private String descript;
	private String answer;
	private String option0;
	private String option1;
	private String option2;
	private String option3;
	private String note;
	private Timestamp operateTime;

	// Constructors

	/** default constructor */
	public SubjectSub() {
	}

	/** minimal constructor */
	public SubjectSub(SubjectSubId id, Subject subject, Teacher teacher,
			Timestamp operateTime) {
		this.id = id;
		this.subject = subject;
		this.teacher = teacher;
		this.operateTime = operateTime;
	}

	/** full constructor */
	public SubjectSub(SubjectSubId id, Subject subject, Teacher teacher,
			String descript, String answer, String option0, String option1,
			String option2, String option3, String note, Timestamp operateTime) {
		this.id = id;
		this.subject = subject;
		this.teacher = teacher;
		this.descript = descript;
		this.answer = answer;
		this.option0 = option0;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.note = note;
		this.operateTime = operateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "subjectId", column = @Column(name = "SubjectID", nullable = false)),
			@AttributeOverride(name = "subId", column = @Column(name = "SubID", nullable = false)) })
	public SubjectSubId getId() {
		return this.id;
	}

	public void setId(SubjectSubId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SubjectID", nullable = false, insertable = false, updatable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "Descript")
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "Answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "Option0")
	public String getOption0() {
		return this.option0;
	}

	public void setOption0(String option0) {
		this.option0 = option0;
	}

	@Column(name = "Option1")
	public String getOption1() {
		return this.option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	@Column(name = "Option2")
	public String getOption2() {
		return this.option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	@Column(name = "Option3")
	public String getOption3() {
		return this.option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "OperateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

}
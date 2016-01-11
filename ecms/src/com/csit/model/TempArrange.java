package com.csit.model;

// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TempArrange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Temp_Arrange")
public class TempArrange extends BaseModel {

	private static final long serialVersionUID = 1L;

	private TempArrangeId id;
	private Teacher teacher;
	private String examType;
	private String resultType;
	private Integer paperId;
	private String groupId;
	private String groupFullName;
	private Timestamp beginDateTime;
	private Timestamp endDateTime;
	private Integer monitorId;
	private Integer correctId;
	private Integer status;
	private String note;
	private Integer roomid;
	private Integer gradeid;
	private Integer majorid;
	private Integer classid;
	private Set<TempArrangeStudent> tempArrangeStudents = new HashSet<TempArrangeStudent>(
			0);

	// Constructors

	/** default constructor */
	public TempArrange() {
	}

	/** minimal constructor */
	public TempArrange(TempArrangeId id, Teacher teacher) {
		this.id = id;
		this.teacher = teacher;
	}

	/** full constructor */
	public TempArrange(TempArrangeId id, Teacher teacher, String examType,
			String resultType, Integer paperId, String groupId,
			String groupFullName, Timestamp beginDateTime,
			Timestamp endDateTime, Integer monitorId, Integer correctId,
			Integer status, String note, Integer roomid, Integer gradeid,
			Integer majorid, Integer classid,
			Set<TempArrangeStudent> tempArrangeStudents) {
		this.id = id;
		this.teacher = teacher;
		this.examType = examType;
		this.resultType = resultType;
		this.paperId = paperId;
		this.groupId = groupId;
		this.groupFullName = groupFullName;
		this.beginDateTime = beginDateTime;
		this.endDateTime = endDateTime;
		this.monitorId = monitorId;
		this.correctId = correctId;
		this.status = status;
		this.note = note;
		this.roomid = roomid;
		this.gradeid = gradeid;
		this.majorid = majorid;
		this.classid = classid;
		this.tempArrangeStudents = tempArrangeStudents;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "teacherId", column = @Column(name = "TeacherID", nullable = false)),
			@AttributeOverride(name = "operateTime", column = @Column(name = "OperateTime", nullable = false, length = 23)),
			@AttributeOverride(name = "arrangeId", column = @Column(name = "ArrangeID", nullable = false)) })
	public TempArrangeId getId() {
		return this.id;
	}

	public void setId(TempArrangeId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID", nullable = false, insertable = false, updatable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "ExamType")
	public String getExamType() {
		return this.examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	@Column(name = "ResultType")
	public String getResultType() {
		return this.resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Column(name = "PaperID")
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Column(name = "GroupID")
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GroupFullName")
	public String getGroupFullName() {
		return this.groupFullName;
	}

	public void setGroupFullName(String groupFullName) {
		this.groupFullName = groupFullName;
	}

	@Column(name = "BeginDateTime", length = 23)
	public Timestamp getBeginDateTime() {
		return this.beginDateTime;
	}

	public void setBeginDateTime(Timestamp beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	@Column(name = "EndDateTime", length = 23)
	public Timestamp getEndDateTime() {
		return this.endDateTime;
	}

	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}

	@Column(name = "MonitorID")
	public Integer getMonitorId() {
		return this.monitorId;
	}

	public void setMonitorId(Integer monitorId) {
		this.monitorId = monitorId;
	}

	@Column(name = "CorrectID")
	public Integer getCorrectId() {
		return this.correctId;
	}

	public void setCorrectId(Integer correctId) {
		this.correctId = correctId;
	}

	@Column(name = "Status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "roomid")
	public Integer getRoomid() {
		return this.roomid;
	}

	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}

	@Column(name = "gradeid")
	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	@Column(name = "majorid")
	public Integer getMajorid() {
		return this.majorid;
	}

	public void setMajorid(Integer majorid) {
		this.majorid = majorid;
	}

	@Column(name = "classid")
	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempArrange")
	public Set<TempArrangeStudent> getTempArrangeStudents() {
		return this.tempArrangeStudents;
	}

	public void setTempArrangeStudents(
			Set<TempArrangeStudent> tempArrangeStudents) {
		this.tempArrangeStudents = tempArrangeStudents;
	}

}
package com.csit.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:基础字典
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-18
 * @Author lys
 */
@Entity
@Table(name = "T_DataDictionary", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "DataDictionaryType",
				"DataDictionaryCode" }),
		@UniqueConstraint(columnNames = { "DataDictionaryType", "Array" }) })
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DataDictionary extends BaseModel {

	// Fields
	private static final long serialVersionUID = 3048246605375153402L;
	/**
	 * 数据字典ID
	 */
	private Integer dataDictionaryId;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 数据类型
	 	Grade 年级
		Lesson 课程
		Major 专业
		Room 考场
		Class 班级
	 */
	private String dataDictionaryType;
	/**
	 * 代码
	 */
	private String dataDictionaryCode;
	/**
	 * 名称
	 */
	private String dataDictionaryName;
	/**
	 * 机房机器数
	 */
	private Integer roomComputerCount;
	/**
	 * 排序
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

	// Constructors

	/** default constructor */
	public DataDictionary() {
	}

	/** minimal constructor */
	public DataDictionary(Integer dataDictionaryId, Teacher teacher,
			String dataDictionaryType, String dataDictionaryCode,
			String dataDictionaryName, Integer array, Boolean state,
			Timestamp operateTime) {
		this.dataDictionaryId = dataDictionaryId;
		this.teacher = teacher;
		this.dataDictionaryType = dataDictionaryType;
		this.dataDictionaryCode = dataDictionaryCode;
		this.dataDictionaryName = dataDictionaryName;
		this.array = array;
		this.state = state;
		this.operateTime = operateTime;
	}

	// Property accessors
	@Id
	@Column(name = "DataDictionaryID", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	public Integer getDataDictionaryId() {
		return this.dataDictionaryId;
	}

	public void setDataDictionaryId(Integer dataDictionaryId) {
		this.dataDictionaryId = dataDictionaryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "DataDictionaryType", nullable = false)
	public String getDataDictionaryType() {
		return this.dataDictionaryType;
	}

	public void setDataDictionaryType(String dataDictionaryType) {
		this.dataDictionaryType = dataDictionaryType;
	}

	@Column(name = "DataDictionaryCode", nullable = false)
	public String getDataDictionaryCode() {
		return this.dataDictionaryCode;
	}

	public void setDataDictionaryCode(String dataDictionaryCode) {
		this.dataDictionaryCode = dataDictionaryCode;
	}

	@Column(name = "DataDictionaryName", nullable = false)
	public String getDataDictionaryName() {
		return this.dataDictionaryName;
	}

	public void setDataDictionaryName(String dataDictionaryName) {
		this.dataDictionaryName = dataDictionaryName;
	}

	@Column(name = "Room_ComputerCount")
	public Integer getRoomComputerCount() {
		return this.roomComputerCount;
	}

	public void setRoomComputerCount(Integer roomComputerCount) {
		this.roomComputerCount = roomComputerCount;
	}

	@Column(name = "Array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@Column(name = "State", nullable = false)
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

	@Column(name = "OperateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

}
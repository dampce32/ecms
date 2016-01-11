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

/**
 * @Description: 资讯
 * @Copyright: 福州骏资讯华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
@Entity
@Table(name = "T_Information")
public class Information extends BaseModel {

	private static final long serialVersionUID = 5849785205823313833L;
	// Fields
	/**
	 * 资讯Id
	 */
	private Integer informationId;
	/**
	 * 资讯录入教师
	 */
	private Teacher teacher;
	/**
	 * 赛事
	 */
	private Competition competition;
	/**
	 * 资讯类型
	 * 大赛章程
	 * 大赛公告
	 * 我要报名
	 * 赛前培训
	 */
	private String category;
	/**
	 * 资讯标题
	 */
	private String informationTitle;
	/**
	 * 资讯内容
	 */
	private String content;
	/**
	 * 发布日期
	 */
	private Timestamp publishDate;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 排序
	 */
	private Integer array;

	// Constructors

	/** default constructor */
	public Information() {
	}

	// Property accessors
	@Id
	@Column(name = "informationId", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	public Integer getInformationId() {
		return this.informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionId")
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Column(name = "category", length = 20)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "informationTitle", nullable = false, length = 200)
	public String getInformationTitle() {
		return this.informationTitle;
	}

	public void setInformationTitle(String informationTitle) {
		this.informationTitle = informationTitle;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "publishDate", nullable = false, length = 23)
	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

}
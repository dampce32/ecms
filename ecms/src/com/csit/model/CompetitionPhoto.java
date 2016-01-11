package com.csit.model;

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
 * @Description: 赛事风采
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
@Entity
@Table(name = "T_CompetitionPhoto")
public class CompetitionPhoto extends BaseModel {

	// Fields

	private static final long serialVersionUID = 1791476816865347515L;
	/**
	 * 赛事风采Id
	 */
	private Integer competitionPhotoId;
	/**
	 * 图片
	 */
	private Picture picture;
	/**
	 * 赛事
	 */
	private Competition competition;
	/**
	 * 图片类型 选手风采 主持人风采
	 */
	private String photoType;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 排序
	 */
	private Integer array;

	// Constructors

	/** default constructor */
	public CompetitionPhoto() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "competitionPhotoId", unique = true, nullable = false)
	public Integer getCompetitionPhotoId() {
		return this.competitionPhotoId;
	}

	public void setCompetitionPhotoId(Integer competitionPhotoId) {
		this.competitionPhotoId = competitionPhotoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pictureId")
	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	@Column(name = "photoType", length = 20)
	public String getPhotoType() {
		return this.photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionId")
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

}
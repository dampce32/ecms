package com.csit.model;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description:培训班级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_TrainingClass")
public class TrainingClass extends BaseModel {

	private static final long serialVersionUID = -5579460517311649774L;
	// Fields

	private Integer trainingClassId;
	private Goods goods;
	private CompetitionGroup competitionGroup;
	private String trainingClassName;
	private String address;
	private String classDate;
	private Integer limit;

	private Double goodsFee;
	private Double fee;
	private String note;
	private Integer stuCount;
	private String classTeacher;
	private Integer status;
	private Set<TrainingClassStudent> trainingClassStudents = new HashSet<TrainingClassStudent>(0);

	// Constructors

	/** default constructor */
	public TrainingClass() {
	}

	/** minimal constructor */
	public TrainingClass(Integer trainingClassId, String trainingClassName,
			Integer stuCount) {
		this.trainingClassId = trainingClassId;
		this.trainingClassName = trainingClassName;
		this.stuCount = stuCount;
	}

	/** full constructor */
	public TrainingClass(Integer trainingClassId, Goods goods,
			CompetitionGroup competitionGroup, String trainingClassName, String address,
			String classDate, Integer limit, Double fee, String note,
			Integer stuCount, Set<TrainingClassStudent> trainingClassStudents) {
		this.trainingClassId = trainingClassId;
		this.goods = goods;
		this.competitionGroup = competitionGroup;
		this.trainingClassName = trainingClassName;
		this.address = address;
		this.classDate = classDate;
		this.limit = limit;
		this.fee = fee;
		this.note = note;
		this.stuCount = stuCount;
		this.trainingClassStudents = trainingClassStudents;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "trainingClassId", unique = true, nullable = false)
	public Integer getTrainingClassId() {
		return this.trainingClassId;
	}

	public void setTrainingClassId(Integer trainingClassId) {
		this.trainingClassId = trainingClassId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goodsId")
	public Goods getGoods() {
		return this.goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionGroupId")
	public CompetitionGroup getCompetitionGroup() {
		return this.competitionGroup;
	}

	public void setCompetitionGroup(CompetitionGroup competitionGroup) {
		this.competitionGroup = competitionGroup;
	}

	@Column(name = "trainingClassName", nullable = false, length = 100)
	public String getTrainingClassName() {
		return this.trainingClassName;
	}

	public void setTrainingClassName(String trainingClassName) {
		this.trainingClassName = trainingClassName;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "limit")
	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Column(name = "fee", precision = 53, scale = 0)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "note", length = 500)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "stuCount", nullable = false)
	public Integer getStuCount() {
		return this.stuCount;
	}
	
	@Column(name = "classDate", length = 23)
	public String getClassDate() {
		return classDate;
	}

	public void setClassDate(String classDate) {
		this.classDate = classDate;
	}

	@Column(name = "classTeacher", length = 23)
	public String getClassTeacher() {
		return classTeacher;
	}

	
	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStuCount(Integer stuCount) {
		this.stuCount = stuCount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingClass")
	public Set<TrainingClassStudent> getTrainingClassStudents() {
		return this.trainingClassStudents;
	}

	public void setTrainingClassStudents(
			Set<TrainingClassStudent> trainingClassStudents) {
		this.trainingClassStudents = trainingClassStudents;
	}

	@Transient
	public Double getTotal() {
		if(goodsFee!=null){
			return fee+goodsFee;
		}else {
			return fee;
		}
		
	}

	@Column(name = "goodsFee", precision = 53, scale = 0)
	public Double getGoodsFee() {
		return goodsFee;
	}

	public void setGoodsFee(Double goodsFee) {
		this.goodsFee = goodsFee;
	}
}
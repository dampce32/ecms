package com.csit.model;

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

/**
 * @Description:教材
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Goods")
public class Goods extends BaseModel {

	private static final long serialVersionUID = 7011902086089145871L;
	// Fields

	private Integer goodsId;
	private Teacher teacher;
	private String goodsName;
	private Double purchasePrice;
	private Double sellingPrice;
	private Integer store;
	private Double price;
	private Double amount;
	private String pressName;
	private Set<Recrej> recrejs = new HashSet<Recrej>(0);
	private Set<TrainingClass> trainingClasses = new HashSet<TrainingClass>(0);

	// Constructors

	/** default constructor */
	public Goods() {
	}

	/** minimal constructor */
	public Goods(Integer goodsId, String goodsName, Integer store) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.store = store;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "goodsId", unique = true, nullable = false)
	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "goodsName", nullable = false, length = 100)
	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "purchasePrice", precision = 53, scale = 0)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column(name = "sellingPrice", precision = 53, scale = 0)
	public Double getSellingPrice() {
		return this.sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	@Column(name = "store", nullable = false)
	public Integer getStore() {
		return this.store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@Column(name = "pressName", length = 100)
	public String getPressName() {
		return this.pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "goods")
	public Set<Recrej> getRecrejs() {
		return this.recrejs;
	}

	public void setRecrejs(Set<Recrej> recrejs) {
		this.recrejs = recrejs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "goods")
	public Set<TrainingClass> getTrainingClasses() {
		return this.trainingClasses;
	}

	public void setTrainingClasses(Set<TrainingClass> trainingClasses) {
		this.trainingClasses = trainingClasses;
	}

	@Column(name = "price", precision = 53, scale = 0)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "amount", precision = 53, scale = 0)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
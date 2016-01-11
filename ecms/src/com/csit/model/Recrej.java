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
import javax.persistence.Transient;
/**
 * @Description:入库出库单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Recrej")
public class Recrej extends BaseModel {

	private static final long serialVersionUID = 4306468206387269981L;
	// Fields

	private Integer recrejId;
	private Goods goods;
	private Teacher teacher;
	private Integer qty;
	private Double price;
	private Timestamp recrejDate;
	private Integer recrejType;

	// Constructors

	/** default constructor */
	public Recrej() {
	}

	/** minimal constructor */
	public Recrej(Integer recrejId, Integer qty, Double price,
			Timestamp recrejDate, Integer recrejType) {
		this.recrejId = recrejId;
		this.qty = qty;
		this.price = price;
		this.recrejDate = recrejDate;
		this.recrejType = recrejType;
	}

	/** full constructor */
	public Recrej(Integer recrejId, Goods goods, Integer qty, Double price,
			Timestamp recrejDate, Integer recrejType) {
		this.recrejId = recrejId;
		this.goods = goods;
		this.qty = qty;
		this.price = price;
		this.recrejDate = recrejDate;
		this.recrejType = recrejType;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "recrejId", unique = true, nullable = false)
	public Integer getRecrejId() {
		return this.recrejId;
	}

	public void setRecrejId(Integer recrejId) {
		this.recrejId = recrejId;
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
	@JoinColumn(name = "teacherId")
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Column(name = "qty", nullable = false)
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name = "price", nullable = false, precision = 53, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "recrejDate", nullable = false, length = 23)
	public Timestamp getRecrejDate() {
		return this.recrejDate;
	}

	public void setRecrejDate(Timestamp recrejDate) {
		this.recrejDate = recrejDate;
	}

	@Column(name = "recrejType", nullable = false)
	public Integer getRecrejType() {
		return this.recrejType;
	}

	public void setRecrejType(Integer recrejType) {
		this.recrejType = recrejType;
	}
	@Transient
	public Double getTotalPrice() {
		return price*qty;
	}

}
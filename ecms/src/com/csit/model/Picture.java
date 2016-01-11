package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @Description: 系统图片
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_Picture")

public class Picture  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = 5585076991034634139L;
	/**
	 * 图片Id
	 */
	private Integer pictureId;
	/**
	 * 图片路径
	 */
    private String picturePath;
    /**
     * 图片名称
     */
    private String pictureName;


    // Constructors

    /** default constructor */
    public Picture() {
    }

	/** minimal constructor */
    public Picture(Integer pictureId, String picturePath) {
        this.pictureId = pictureId;
        this.picturePath = picturePath;
    }
    
   
    // Property accessors
    @Id 
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="pictureId", unique=true, nullable=false)

    public Integer getPictureId() {
        return this.pictureId;
    }
    
    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }
    
    @Column(name="picturePath", nullable=false, length=50)

    public String getPicturePath() {
        return this.picturePath;
    }
    
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    @Column(name="pictureName", length=50)

    public String getPictureName() {
        return this.pictureName;
    }
    
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
   








}
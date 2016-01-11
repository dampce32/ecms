package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Description:短信黑字典
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author lys
 * @vesion 1.0
 */
@Entity
@Table(name="T_MsgBlackList")
public class MsgBlackList  extends BaseModel {
	
	private static final long serialVersionUID = -1369971921958806344L;
	// Fields    
	/**
	 * 短信黑字典Id
	 */
     private Integer msgBlackListId;
     /**
      * 黑字
      */
     private String blackCode;


    // Constructors
    /** default constructor */
    public MsgBlackList() {
    }
    
    @Column(name="blackCode", nullable=false, length=50)
    public String getBlackCode() {
		return blackCode;
	}
	public void setBlackCode(String blackCode) {
		this.blackCode = blackCode;
	}

	// Property accessors
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="msgBlackListId", unique=true, nullable=false)
    public Integer getMsgBlackListId() {
        return this.msgBlackListId;
    }
    
    public void setMsgBlackListId(Integer msgBlackListId) {
        this.msgBlackListId = msgBlackListId;
    }
}
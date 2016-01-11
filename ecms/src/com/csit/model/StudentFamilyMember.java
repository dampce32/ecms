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
 * 
 * @Description: 家庭成员
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_StudentFamilyMember")
public class StudentFamilyMember  implements java.io.Serializable {


    // Fields   
	
	private static final long serialVersionUID = 1721232703747190607L;
	/**
	 * 家庭成员Id
	 */
	private Integer competitionFamilyMemberId;
	/**
	 * 学生
	 */
    private Student student;
	/**
	 * 家庭成员
	 */
    private String familyMember;
	/**
	 * 姓名
	 */
    private String familyMemberName;
	/**
	 * 年龄
	 */
    private Integer familyMemberAge;
	/**
	 * 工作单位及职务
	 */
    private String workUnitsAndPosition;
	/**
	 * 联系电话
	 */
    private String phone;
	/**
	 * E-mail
	 */
    private String email;


    // Constructors

    /** default constructor */
    public StudentFamilyMember() {
    }

	/** minimal constructor */
    public StudentFamilyMember(Integer competitionFamilyMemberId, String familyMember, String familyMemberName, String phone) {
        this.competitionFamilyMemberId = competitionFamilyMemberId;
        this.familyMember = familyMember;
        this.familyMemberName = familyMemberName;
        this.phone = phone;
    }
    
   
    // Property accessors
    @Id 
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="competitionFamilyMemberId", unique=true, nullable=false)
    public Integer getCompetitionFamilyMemberId() {
        return this.competitionFamilyMemberId;
    }
    
    public void setCompetitionFamilyMemberId(Integer competitionFamilyMemberId) {
        this.competitionFamilyMemberId = competitionFamilyMemberId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="studentId")

    public Student getStudent() {
        return this.student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    @Column(name="familyMember", nullable=false, length=20)

    public String getFamilyMember() {
        return this.familyMember;
    }
    
    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }
    
    @Column(name="familyMemberName", nullable=false, length=30)

    public String getFamilyMemberName() {
        return this.familyMemberName;
    }
    
    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }
    
    @Column(name="familyMemberAge")

    public Integer getFamilyMemberAge() {
        return this.familyMemberAge;
    }
    
    public void setFamilyMemberAge(Integer familyMemberAge) {
        this.familyMemberAge = familyMemberAge;
    }
    
    @Column(name="workUnitsAndPosition", length=50)

    public String getWorkUnitsAndPosition() {
        return this.workUnitsAndPosition;
    }
    
    public void setWorkUnitsAndPosition(String workUnitsAndPosition) {
        this.workUnitsAndPosition = workUnitsAndPosition;
    }
    
    @Column(name="phone", nullable=false, length=30)

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Column(name="email", length=50)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
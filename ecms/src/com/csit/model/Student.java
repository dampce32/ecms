package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @Description: 学生
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Student", uniqueConstraints = @UniqueConstraint(columnNames = "userCode"))
public class Student extends BaseModel {

	// Fields
	private static final long serialVersionUID = 3041887190886127134L;
	/**
	 * 学生登陆Id
	 */
	public static final String LOGIN_ID = "studentId";
	/**
	 * 学生登录名称
	 */
	public static final String LOGIN_NAME = "studentName";
	/**
	 * 学生正在答的答卷
	 */
	public static final String EXAMANSWERID = "examAnswerId";
	/**
	 * 答题的结束时间
	 */
	public static final String ANSWER_ENDTIME = "answer_endTime";

	/**
	 * 学生Id
	 */
	private Integer studentId;
	/**
	 * 区
	 */
	private Area area;
	/**
	 * 民族
	 */
	private Nation nation;
	/**
	 * 个人头像
	 */
	private Picture picture;
	/**
	 * 学生姓名
	 */
	private String studentName;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 家庭电话
	 */
	private String phone;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * E-mail
	 */
	private String email;
	/**
	 * 家庭地址
	 */
	private String address;
	/**
	 * 学校年级班级
	 */
	private SchoolGradeClazz schoolGradeClazz;
	/**
	 * 登录名
	 */
	private String userCode;
	/**
	 * 登录密码
	 */
	private String userPwd;
	/**
	 * 状态 0--未启用 1--启用
	 */
	private Integer status;
	
	private Set<StudentFamilyMember> studentFamilyMembers = new HashSet<StudentFamilyMember>(0);

	// Constructors

	/** default constructor */
	public Student() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "studentId", unique = true, nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId")
	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationId")
	public Nation getNation() {
		return this.nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pictureId")
	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	@Column(name = "studentName", nullable = false, length = 50)
	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", nullable = false, length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "phone", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "idNumber", length = 50)
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "mobilePhone", length = 50)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", nullable = false, length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "userCode", unique = true, nullable = false, length = 50)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "userPwd", nullable = false, length = 50)
	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolGradeClazzId")
	public SchoolGradeClazz getSchoolGradeClazz() {
		return schoolGradeClazz;
	}

	public void setSchoolGradeClazz(SchoolGradeClazz schoolGradeClazz) {
		this.schoolGradeClazz = schoolGradeClazz;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	public Set<StudentFamilyMember> getStudentFamilyMembers() {
		return studentFamilyMembers;
	}

	public void setStudentFamilyMembers(
			Set<StudentFamilyMember> studentFamilyMembers) {
		this.studentFamilyMembers = studentFamilyMembers;
	}

}
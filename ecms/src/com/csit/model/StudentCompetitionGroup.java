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
import javax.persistence.UniqueConstraint;



/**
 * 
 * @Description: 学生参赛组别
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name="T_StudentCompetitionGroup", uniqueConstraints = @UniqueConstraint(columnNames="examCode"))
public class StudentCompetitionGroup  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = -1483311931338431566L;
	/**
	 * 学生参赛组别Id
	 */
	private Integer studentCompetitionGroupId;
	/**
	 * 区
	 */
    private Area area;
    /**
     * 赛事组别
     */
    private CompetitionGroup competitionGroup;
    /**
     * 学生
     */
    private Student student;
    /**
     * 状态
     * 0 - 未审核
     * 1 - 审核通过
     * 2 - 审核不通过 
     */
    private Integer status;
    /**
     * 准考证号
     */
	private String examCode;
    // Constructors

	/** default constructor */
    public StudentCompetitionGroup() {
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="studentCompetitionGroupId", unique=true, nullable=false)

    public Integer getStudentCompetitionGroupId() {
        return this.studentCompetitionGroupId;
    }
    
    public void setStudentCompetitionGroupId(Integer studentCompetitionGroupId) {
        this.studentCompetitionGroupId = studentCompetitionGroupId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="areaId", nullable=false)

    public Area getArea() {
        return this.area;
    }
    
    public void setArea(Area area) {
        this.area = area;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="competitionGroupId", nullable=false)

    public CompetitionGroup getCompetitionGroup() {
        return this.competitionGroup;
    }
    
    public void setCompetitionGroup(CompetitionGroup competitionGroup) {
        this.competitionGroup = competitionGroup;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="studentId", nullable=false)

    public Student getStudent() {
        return this.student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    @Column(name="status", nullable=false)
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="examCode")
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

    
}
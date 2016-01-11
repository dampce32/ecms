package com.csit.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.CascadeType;

/**
 * 
 * @Description:试题表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-26
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Subject")
public class Subject extends BaseModel {

	private static final long serialVersionUID = 8705295735624071253L;

	/**
	 * 试题号
	 */
	private Integer subjectId;
	/**
	 * 参赛组别
	 */
	private Group group;
	/**
	 * 出题教师
	 */
	private Teacher publishTeacher;
	/**
	 * 操作员
	 */
	private Teacher teacher;
	/**
	 * 试题类型号
	 * 单项选择
		多项选择
		判断
		判断改错
		填空
		简答
		完型填空
		阅读理解
		听力打字
		打字
		操作
	 */
	private String subjectType;
	/**
	 *  试题描述(RTF/HTML)
	 */
	private String descript;
	/**
	 * 试题描述(纯文本，不含样式)
	 */
	private String descriptPlain;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 难度
	 * 易
	 * 中
	 * 难
	 */
	private String difficulty;
	/**
	 * 默认分数
	 */
	private Double score;
	/**
	 * 出题日期
	 */
	private Timestamp publishDate;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 选项/填空个数（如果含有小小题，这里将小小题的数目放在OptionCount下，便于考试界面加载）
	 */
	private Integer optionCount;
	private String option0;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	private String option6;
	private String option7;
	private String option8;
	private String option9;
	/**
	 * 状态
	 * --0：未确定
	   --1：已确定
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 操作时间
	 */
	private Timestamp operateTime;
	private Set<PaperBigSmall> paperBigSmalls = new HashSet<PaperBigSmall>(0);
	private Set<SubjectSub> subjectSubs = new HashSet<SubjectSub>(0);

	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** minimal constructor */
	public Subject(Integer subjectId, Teacher teacher,
			Timestamp operateTime) {
		this.subjectId = subjectId;
		this.teacher = teacher;
		this.operateTime = operateTime;
	}

	// Property accessors
	@Id
	@Column(name = "SubjectID", unique = true, nullable = false)
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PublishTeacherID")
	public Teacher getPublishTeacher() {
		return publishTeacher;
	}

	public void setPublishTeacher(Teacher publishTeacher) {
		this.publishTeacher = publishTeacher;
	}

	@Transient
	public String getPublishTeacherName(){
		if(publishTeacher!=null){
			return publishTeacher.getTeacherName();
		}
		else {
			return null;
		}
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TeacherID", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "SubjectType")
	public String getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "Descript")
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "DescriptPlain")
	public String getDescriptPlain() {
		return this.descriptPlain;
	}

	public void setDescriptPlain(String descriptPlain) {
		this.descriptPlain = descriptPlain;
	}

	@Column(name = "Memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "Difficulty")
	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Column(name = "Score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "PublishDate", length = 23)
	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "Answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "OptionCount")
	public Integer getOptionCount() {
		return this.optionCount;
	}

	public void setOptionCount(Integer optionCount) {
		this.optionCount = optionCount;
	}

	@Column(name = "Option0")
	public String getOption0() {
		return this.option0;
	}

	public void setOption0(String option0) {
		this.option0 = option0;
	}

	@Column(name = "Option1")
	public String getOption1() {
		return this.option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	@Column(name = "Option2")
	public String getOption2() {
		return this.option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	@Column(name = "Option3")
	public String getOption3() {
		return this.option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	@Column(name = "Option4")
	public String getOption4() {
		return this.option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	@Column(name = "Option5")
	public String getOption5() {
		return this.option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	@Column(name = "Option6")
	public String getOption6() {
		return this.option6;
	}

	public void setOption6(String option6) {
		this.option6 = option6;
	}

	@Column(name = "Option7")
	public String getOption7() {
		return this.option7;
	}

	public void setOption7(String option7) {
		this.option7 = option7;
	}

	@Column(name = "Option8")
	public String getOption8() {
		return this.option8;
	}

	public void setOption8(String option8) {
		this.option8 = option8;
	}

	@Column(name = "Option9")
	public String getOption9() {
		return this.option9;
	}

	public void setOption9(String option9) {
		this.option9 = option9;
	}

	
	

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "OperateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<PaperBigSmall> getPaperBigSmalls() {
		return this.paperBigSmalls;
	}

	public void setPaperBigSmalls(Set<PaperBigSmall> paperBigSmalls) {
		this.paperBigSmalls = paperBigSmalls;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<SubjectSub> getSubjectSubs() {
		return this.subjectSubs;
	}

	public void setSubjectSubs(Set<SubjectSub> subjectSubs) {
		this.subjectSubs = subjectSubs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GroupID")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
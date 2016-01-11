package com.csit.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.csit.dao.StudentDAO;
import com.csit.model.Student;
import com.csit.util.PageUtil;
/**
 * @Description:学生DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-19
 * @Author lys
 */
@Repository
public class StudentDAOImpl extends BaseDAOImpl<Student, Integer> implements
		StudentDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StudentDAO#query(com.csit.model.Student, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> query(Student model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Student.class);
		
		criteria.createAlias("picture", "picture", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("area", "area", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("area.province", "province", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("area.city", "city", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("schoolGradeClazz", "schoolGradeClazz", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("schoolGradeClazz.schoolGrade", "schoolGrade", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("schoolGrade.school", "school", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("schoolGrade.grade", "grade", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("schoolGradeClazz.clazz", "clazz", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("nation", "nation", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&model.getUserCode()!=null){
			criteria.add(Restrictions.like("userCode",model.getUserCode(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStudentName()!=null){
			criteria.add(Restrictions.like("studentName",model.getStudentName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status",model.getStatus()));
		}
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.desc("studentId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StudentDAO#getTotalCount(com.csit.model.Student)
	 */
	@Override
	public Long getTotalCount(Student model) {
		Criteria criteria = getCurrentSession().createCriteria(Student.class);
		
		if(model!=null&&model.getUserCode()!=null){
			criteria.add(Restrictions.like("userCode",model.getUserCode(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStudentName()!=null){
			criteria.add(Restrictions.like("studentName",model.getStudentName(),MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getStatus()!=null){
			criteria.add(Restrictions.eq("status",model.getStatus()));
		}
		criteria.setProjection(Projections.rowCount());
		
		return new Long(criteria.uniqueResult().toString());
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.StudentDAO#queryCombobox()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> queryCombobox(String studentName) {
		Criteria criteria = getCurrentSession().createCriteria(Student.class);
		
		criteria.createAlias("area","area",CriteriaSpecification.LEFT_JOIN);
		
		if(studentName!=null){
			criteria.add(Restrictions.like("studentName",studentName,MatchMode.ANYWHERE));
		}
		
		criteria.add(Restrictions.eq("status", 1));
		criteria.addOrder(Order.asc("studentId"));
		return criteria.list();
	}
	
//	@Override
//	public Long getTotalCountArrange(Student model, Integer teacherId,
//			Timestamp operateTime, Integer arrangeId) {
//
//		StringBuilder sql = new StringBuilder();
//		sql.append("select count(*) from T_Student a ");
//		sql.append("left join (select tas.StudentID from T_Temp_ArrangeStudent tas where tas.ArrangeID =:arrangeId and tas.OperateTime =:operateTime and tas.TeacherID =:teacherId)b on a.StudentID = b.StudentID");
//		sql.append(" where b.StudentID is null ");
//		if(model.getGrade().getDataDictionaryId()!=-1){
//			sql.append("and a.GradeID=:gradeId ");
//		}
//		if(model.getMajor().getDataDictionaryId()!=-1){
//			sql.append("and a.MajorID=:majorId ");
//		}
//		if(model.getClazz().getDataDictionaryId()!=-1){
//			sql.append("and a.ClassID=:clazzId ");
//		}
//		if(model.getStudentCode()!=null){
//			sql.append("and a.StudentCode like :studentCode ");
//		}
//		Query query = getCurrentSession().createSQLQuery(sql.toString());
//		query.setInteger("arrangeId", arrangeId);
//		query.setInteger("teacherId", teacherId);
//		query.setTimestamp("operateTime", operateTime);
//		if(model.getGrade().getDataDictionaryId()!=-1){
//			query.setInteger("gradeId", model.getGrade().getDataDictionaryId());
//		}
//		if(model.getMajor().getDataDictionaryId()!=-1){
//			query.setInteger("majorId", model.getMajor().getDataDictionaryId());
//		}
//		if(model.getClazz().getDataDictionaryId()!=-1){
//			query.setInteger("clazzId", model.getClazz().getDataDictionaryId());
//		}
//		if(model.getStudentCode()!=null){
//			query.setString("studentCode", "%"+model.getStudentCode()+"%");
//		}
//		return Long.parseLong(query.uniqueResult().toString()) ;
//	}
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Student> queryArrange(Student model, Integer teacherId,
//			Timestamp operateTime, Integer arrangeId, Integer page, Integer rows) {
//
//		StringBuilder sql = new StringBuilder();
//		sql.append("select a.StudentID studentId,a.StudentName studentName,a.StudentCode studentCode,g.DataDictionaryName grade,m.DataDictionaryName major,c.DataDictionaryName clazz,a.Note note from T_Student a ");
//		sql.append("left join (select tas.StudentID from T_Temp_ArrangeStudent tas where tas.ArrangeID =:arrangeId and tas.OperateTime =:operateTime and tas.TeacherID =:teacherId)b on a.StudentID = b.StudentID ");
//		sql.append("left join T_DataDictionary g on g.DataDictionaryID=a.GradeID ");
//		sql.append("left join T_DataDictionary m on m.DataDictionaryID=a.MajorID ");
//		sql.append("left join T_DataDictionary c on c.DataDictionaryID=a.ClassID ");
// 
//		sql.append("where b.StudentID is null ");
//		if(model.getGrade().getDataDictionaryId()!=-1){
//			sql.append("and a.GradeID=:gradeId ");
//		}
//		if(model.getMajor().getDataDictionaryId()!=-1){
//			sql.append("and a.MajorID=:majorId ");
//		}
//		if(model.getClazz().getDataDictionaryId()!=-1){
//			sql.append("and a.ClassID=:clazzId ");
//		}
//		if(model.getStudentCode()!=null){
//			sql.append("and a.StudentCode like :studentCode ");
//		}
//		Query query = getCurrentSession().createSQLQuery(sql.toString());
//		query.setInteger("arrangeId", arrangeId);
//		query.setInteger("teacherId", teacherId);
//		query.setTimestamp("operateTime", operateTime);
//		if(model.getGrade().getDataDictionaryId()!=-1){
//			query.setInteger("gradeId", model.getGrade().getDataDictionaryId());
//		}
//		if(model.getMajor().getDataDictionaryId()!=-1){
//			query.setInteger("majorId", model.getMajor().getDataDictionaryId());
//		}
//		if(model.getClazz().getDataDictionaryId()!=-1){
//			query.setInteger("clazzId", model.getClazz().getDataDictionaryId());
//		}
//		if(model.getStudentCode()!=null){
//			query.setString("studentCode", "%"+model.getStudentCode()+"%");
//		}
//		query.setFirstResult((page-1)*rows);
//		query.setMaxResults(rows);
//		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//	}

}

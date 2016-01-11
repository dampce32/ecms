package com.csit.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.TempArrangeStudentDAO;
import com.csit.model.TempArrangeStudent;
import com.csit.model.TempArrangeStudentId;
/**
 * @Description:考务安排学生临时DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class TempArrangeStudentDAOImpl extends BaseDAOImpl<TempArrangeStudent, TempArrangeStudentId>
		implements TempArrangeStudentDAO {

	@Override
	public Long getTotalCount(Integer arrangeId,Integer teacherId,Timestamp operateTime) {
		Criteria criteria = getCurrentSession().createCriteria(TempArrangeStudent.class);
		criteria.add(Restrictions.eq("id.arrangeId", arrangeId));
		criteria.add(Restrictions.eq("id.operateTime", operateTime));
		criteria.add(Restrictions.eq("id.teacherId", teacherId));
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TempArrangeStudent> query(Integer arrangeId,Integer teacherId,Timestamp operateTime,
			Integer page, Integer rows) {
		StringBuilder sql = new StringBuilder();
		sql.append("select tas.StudentID studentId,s.StudentName studentName,s.StudentCode studentCode,g.DataDictionaryName grade,m.DataDictionaryName major,c.DataDictionaryName clazz from T_Temp_ArrangeStudent tas ");
		sql.append("left join T_Student s on s.StudentID=tas.StudentID ");
		sql.append("left join T_DataDictionary g on g.DataDictionaryID=s.GradeID ");
		sql.append("left join T_DataDictionary m on m.DataDictionaryID=s.MajorID ");
		sql.append("left join T_DataDictionary c on c.DataDictionaryID=s.ClassID ");
 
		sql.append("where tas.ArrangeID=:arrangeId and tas.OperateTime=:operateTime and tas.TeacherId=:teacherId ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("arrangeId", arrangeId);
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setFirstResult((page-1)*rows);
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}

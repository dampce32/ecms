package com.csit.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.csit.dao.PaperDAO;
import com.csit.model.Paper;
import com.csit.util.PageUtil;
import com.csit.vo.StoreProcedureResult;
/**
 * @Description:试卷DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Repository
public class PaperDAOImpl extends BaseDAOImpl<Paper, Integer> implements
		PaperDAO {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#query(com.csit.model.Paper, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Paper> query(Paper model, Integer page, Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(Paper.class);
		criteria.createAlias("publishTeacher", "publishTeacher", CriteriaSpecification.LEFT_JOIN);
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group", model.getGroup()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher", model.getPublishTeacher()));
		}
		
		if(model.getState()!=null){
			criteria.add(Restrictions.eq("state", model.getState()));
		}
		
		criteria.setFirstResult(PageUtil.getPageBegin(page, rows));
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.desc("paperId"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#getTotalCount(com.csit.model.Paper)
	 */
	@Override
	public Long getTotalCount(Paper model) {
		Criteria criteria = getCurrentSession().createCriteria(Paper.class);
		if(model.getGroup()!=null&&model.getGroup().getGroupId()!=null){
			criteria.add(Restrictions.eq("group", model.getGroup()));
		}
		if(model.getPublishTeacher()!=null&&model.getPublishTeacher().getTeacherId()!=null){
			criteria.add(Restrictions.eq("publishTeacher", model.getPublishTeacher()));
		}
		if(model.getState()!=null){
			criteria.add(Restrictions.eq("state", model.getState()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#clearTemp(java.lang.Integer, java.sql.Timestamp, java.lang.Integer)
	 */
	@Override
	public void clearTemp(Integer teacherId, Timestamp operateTime,
			Integer paperId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from T_Temp_PaperBigSmall  where teacherId =:teacherId and operateTime = :operateTime and paperId=:paperId ");
		sql.append("delete from T_Temp_PaperBig  where teacherId =:teacherId and operateTime = :operateTime and paperId=:paperId ");
		sql.append("delete from T_Temp_Paper  where teacherId =:teacherId and operateTime = :operateTime and paperId=:paperId ");
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		query.executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#saveNew(java.lang.Integer, java.sql.Timestamp, java.lang.Integer)
	 */
	@Override
	public Integer saveNew(Integer teacherId, Timestamp operateTime,
			Integer paperId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_Paper(PaperID, PaperType, PaperName, GroupID, Limits, IsBigMix, IsSmallContinue, Score, PublishTeacherID, PublishDate, State, Note, TeacherID, OperateTime) ");
		sql.append("select PaperID, PaperType, PaperName, GroupID, Limits, IsBigMix, IsSmallContinue, Score, PublishTeacherID, PublishDate, State, Note, TeacherID, OperateTime from T_Temp_Paper where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId ");
	
		sql.append("insert into T_PaperBig(PaperID, BigID, BigName, SubjectType, IsSmallMix, Score, Array, Note, TeacherID, OperateTime) ");
		sql.append("select PaperID, BigID, BigName, SubjectType, IsSmallMix, Score, Array, Note, TeacherID, OperateTime from T_Temp_PaperBig where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId ");
		
		sql.append("insert into T_PaperBigSmall(PaperID, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, TeacherID, OperateTime) ");
		sql.append("select a.PaperID, a.BigID, a.SmallID, a.SubjectID, a.GroupID, a.Difficulty, a.Score, ");
		sql.append("	case ");
		sql.append("		when b.SubjectType in ('单项选择', '多项选择', '完型填空', '阅读理解') then a.IsOptionMix ");
		sql.append("		else 0 ");
		sql.append("	end IsOptionMix, ");
		sql.append("	a.Array, a.Note, a.TeacherID, a.OperateTime ");
		sql.append("from (select * from T_Temp_PaperBigSmall where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("left join T_Temp_PaperBig b on a.TeacherID = b.TeacherID and a.OperateTime = b.OperateTime and a.PaperID = b.PaperID and a.BigID = b.BigID ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		return query.executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#getCountSmallValid(java.lang.Integer, java.sql.Timestamp, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getCountSmallValid(Integer teacherId, Timestamp operateTime,
			Integer paperId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select 1 ");
		sql.append("from (select * from T_Temp_PaperBig "); 
		sql.append("	where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("left join T_Temp_PaperBigSmall b on a.TeacherID = b.TeacherID and a.OperateTime = b.OperateTime and a.PaperID = b.PaperID and a.BigID = b.BigID ");
		sql.append("group by a.BigID ");
		sql.append("having count(b.SmallID) = 0 ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#saveModify(java.lang.Integer, java.sql.Timestamp, java.lang.Integer)
	 */
	@Override
	public Integer saveModify(Integer teacherId, Timestamp operateTime,
			Integer paperId) {
		StringBuffer sql = new StringBuffer();
		
		//删
		sql.append("delete from T_PaperBigSmall ");
		sql.append("where PaperID = :paperId ");
		sql.append("and cast(BigID as varchar)+'-'+cast(SmallID as varchar) not in ");
		sql.append("		(select cast(BigID as varchar)+'-'+cast(SmallID as varchar) ");
		sql.append("		from T_Temp_PaperBigSmall ");
		sql.append("		where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) ");
		
		sql.append("delete from T_PaperBig ");
		sql.append("where PaperID = :paperId ");
		sql.append("and BigID not in ");
		sql.append("		(select BigID ");
		sql.append("		from T_Temp_PaperBig ");
		sql.append("		where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) ");
		//更新
		sql.append("update T_PaperBigSmall ");
		sql.append("set SubjectID = a.SubjectID, ");
		sql.append("	GroupID = a.GroupID, ");
		sql.append("	Difficulty = a.Difficulty, ");
		sql.append("	Score = a.Score, ");
		sql.append("	IsOptionMix = a.IsOptionMix, ");
		sql.append("	Array = a.Array, ");
		sql.append("	Note = a.Note, ");
		sql.append("	TeacherID = a.TeacherID, ");
		sql.append("	OperateTime = a.OperateTime ");
		sql.append("from (select a.PaperID, a.BigID, a.SmallID, a.SubjectID, a.GroupID, a.Difficulty, a.Score, ");
		sql.append("		case ");
		sql.append("			when b.SubjectType in ('单项选择', '多项选择', '完型填空', '阅读理解', '听力选择', '听力短文') then a.IsOptionMix ");
		sql.append("			else 0 ");
		sql.append("		end IsOptionMix, ");
		sql.append("		a.Array, a.Note, a.TeacherID, a.OperateTime ");
		sql.append("		from (select * from T_Temp_PaperBigSmall where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("left join T_Temp_PaperBig b on a.TeacherID = b.TeacherID and a.OperateTime = b.OperateTime and a.PaperID = b.PaperID and a.BigID = b.BigID) a ");
		sql.append("where T_PaperBigSmall.PaperID = a.PaperID ");
		sql.append("and T_PaperBigSmall.BigID = a.BigID ");
		sql.append("and T_PaperBigSmall.SmallID = a.SmallID ");
		
		sql.append("update T_PaperBig ");
		sql.append("	set BigName = a.BigName, ");
		sql.append("	SubjectType = a.SubjectType, ");
		sql.append("	IsSmallMix = a.IsSmallMix, ");
		sql.append("	Score = a.Score, ");
		sql.append("	Array = a.Array, ");
		sql.append("	Note = a.Note, ");
		sql.append("	TeacherID = a.TeacherID, ");
		sql.append("	OperateTime = a.OperateTime ");
		sql.append("from (select * from T_Temp_PaperBig where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("where T_PaperBig.PaperID = a.PaperID ");
		sql.append("and T_PaperBig.BigID = a.BigID ");
		
		sql.append("update T_Paper ");
		sql.append("	set PaperType = a.PaperType, ");
		sql.append("	PaperName = a.PaperName, ");
		sql.append("	GroupID = a.GroupID, ");
		sql.append("	Limits = a.Limits, ");
		sql.append("	IsBigMix = a.IsBigMix, ");
		sql.append("	IsSmallContinue = a.IsSmallContinue, ");
		sql.append("	Score = a.Score, ");
		sql.append("	PublishTeacherID = a.PublishTeacherID, ");
		sql.append("	PublishDate = a.PublishDate, ");
		sql.append("	State = a.State, ");
		sql.append("	Note = a.Note, ");
		sql.append("	TeacherID = a.TeacherID, ");
		sql.append("	OperateTime = a.OperateTime ");
		sql.append("from (select * from T_Temp_Paper where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("where T_Paper.PaperID = a.PaperID ");
		//新增
		sql.append("insert into T_PaperBig(PaperID, BigID, BigName, SubjectType, IsSmallMix, Score, Array, Note, TeacherID, OperateTime) ");
		sql.append("select a.PaperID, a.BigID, a.BigName, a.SubjectType, a.IsSmallMix, a.Score, a.Array, a.Note, a.TeacherID, a.OperateTime ");
		sql.append("from (select * from T_Temp_PaperBig where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("left join T_PaperBig b on a.PaperID = b.PaperID and a.BigID = b.BigID ");
		sql.append("where b.BigID is null ");
		
		sql.append("insert into T_PaperBigSmall(PaperID, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, TeacherID, OperateTime) ");
		sql.append("select a.PaperID, a.BigID, a.SmallID, a.SubjectID, a.GroupID, a.Difficulty, a.Score, ");
		sql.append("	case ");
		sql.append("		when c.SubjectType in ('单项选择', '多项选择', '完型填空', '阅读理解') then a.IsOptionMix ");
		sql.append("		else 0 ");
		sql.append("	end IsOptionMix, ");
		sql.append("	a.Array, a.Note, a.TeacherID, a.OperateTime ");
		sql.append("from (select * from T_Temp_PaperBigSmall where TeacherID = :teacherId and OperateTime = :operateTime and PaperID = :paperId) a ");
		sql.append("left join T_PaperBigSmall b on a.PaperID = b.PaperID and a.BigID = b.BigID and a.SmallID = b.SmallID ");
		sql.append("left join T_Temp_PaperBig c on a.TeacherID = c.TeacherID and a.OperateTime = c.OperateTime and a.PaperID = c.PaperID and a.BigID = c.BigID ");
		sql.append("where b.BigID is null or b.SmallID is null ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		return query.executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#view(java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public void view(Integer paperId, Integer teacherId,
			Timestamp operateTime) {
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("insert into T_Temp_Paper(PaperID, PaperType, PaperName, GroupID,Score ,Limits, IsBigMix, IsSmallContinue, PublishTeacherID, PublishDate, State, Note, TeacherID, OperateTime) ");
		sql.append("select PaperID, PaperType, PaperName, GroupID,Score,Limits, IsBigMix, IsSmallContinue, PublishTeacherID, PublishDate, State, Note, :teacherId, :operateTime from T_Paper where PaperID = :paperId ");
		
		sql.append("insert into T_Temp_PaperBig(PaperID, BigID, BigName, SubjectType, IsSmallMix,Score, Array, Note, TeacherID, OperateTime) ");
		sql.append("select PaperID, BigID, BigName, SubjectType, IsSmallMix,Score, Array, Note, :teacherId, :operateTime from T_PaperBig where PaperID = :paperId ");
		
		sql.append("insert into T_Temp_PaperBigSmall(PaperID, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, TeacherID, OperateTime) ");
		sql.append("select PaperID, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, :teacherId, :operateTime from T_PaperBigSmall where PaperID = :paperId ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		query.executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#copyAdd(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public void copyAdd(Integer paperId, Integer paperIdTemp,
			Integer teacherId, Timestamp operateTime) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into T_Temp_Paper(PaperID, PaperType, PaperName, GroupID,Score, Limits, IsBigMix, IsSmallContinue, PublishTeacherID, PublishDate, State, Note, TeacherID, OperateTime) ");
		sql.append("select :paperIdTemp, PaperType, PaperName, GroupID,Score, Limits, IsBigMix, IsSmallContinue, PublishTeacherID, PublishDate, State, Note, :teacherId, :operateTime from T_Paper where PaperID = :paperId ");
		
		sql.append("insert into T_Temp_PaperBig(PaperID, BigID, BigName, SubjectType, IsSmallMix,Score, Array, Note, TeacherID, OperateTime) ");
		sql.append("select :paperIdTemp, BigID, BigName, SubjectType, IsSmallMix,Score, Array, Note, :teacherId, :operateTime from T_PaperBig where PaperID = :paperId ");
		
		sql.append("insert into T_Temp_PaperBigSmall(PaperID, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, TeacherID, OperateTime) ");
		sql.append("select :paperIdTemp, BigID, SmallID, SubjectID, GroupID, Difficulty, Score, IsOptionMix, Array, Note, :teacherId, :operateTime from T_PaperBigSmall where PaperID = :paperId ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("teacherId", teacherId);
		query.setTimestamp("operateTime", operateTime);
		query.setInteger("paperId", paperId);
		query.setInteger("paperIdTemp", paperIdTemp);
		query.executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#loadDetail(java.lang.Integer)
	 */
	@Override
	public Paper loadDetail(Integer paperId) {
		Criteria criteria = getCurrentSession().createCriteria(Paper.class);
		criteria.createAlias("group", "group", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("publishTeacher", "publishTeacher", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("paperId", paperId));
		return (Paper) criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PaperDAO#saveSP(java.lang.Integer, java.sql.Timestamp, java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public StoreProcedureResult editPaperSP(Integer teacherId,
			Timestamp operateTime, Integer paperId, String flag,Boolean state) throws SQLException {
		  Connection conn = getCurrentSession().connection();   
	        CallableStatement call;
	               call = conn.prepareCall("{?=Call P_EditPaper(?,?,?,?,?,?)}");
	               call.registerOutParameter(1, java.sql.Types.INTEGER);
	               call.setString(2, flag);
		       		call.setInt(3, paperId);
		       		call.setBoolean(4, state);
		       		call.setInt(5, teacherId);
		       		call.setTimestamp(6, operateTime);
		       		call.registerOutParameter(7, java.sql.Types.VARCHAR);
	               call.execute();
	               StoreProcedureResult  spResult = new StoreProcedureResult();
	               spResult.setReturnInt(call.getInt(1));
	               spResult.setReturnValue(call.getString(7));
			return spResult; 
	}

}

package com.csit.dao.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.csit.dao.TempArrangeDAO;
import com.csit.model.TempArrange;
import com.csit.model.TempArrangeId;
/**
 * @Description:考务安排临时DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-14
 * @author jcf
 * @vesion 1.0
 */
@Repository
public class TempArrangeDAOImpl extends BaseDAOImpl<TempArrange, TempArrangeId>
		implements TempArrangeDAO {

	@Override
	public Integer copy(Integer arrangeId, Integer teacherId,
			Timestamp operateTime) {

		StringBuilder sql=new StringBuilder();
		sql.append("delete from T_Temp_ArrangeStudent where TeacherID = @TeacherID and OperateTime = @OperateTime and ArrangeID = @ArrangeID");
		sql.append("");
		return null;
	}

}

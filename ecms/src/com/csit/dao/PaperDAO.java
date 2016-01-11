package com.csit.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.csit.model.Paper;
import com.csit.vo.StoreProcedureResult;
/**
 * @Description:试卷DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
public interface PaperDAO extends BaseDAO<Paper,Integer>{
	/**
	 * @Description: 分页查询试卷
	 * @Created Time: 2013-4-28 上午8:44:42
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Paper> query(Paper model, Integer page, Integer rows);
	/**
	 * @Description: 统计试卷
	 * @Created Time: 2013-4-28 上午8:45:10
	 * @Author lys
	 * @param model
	 * @return
	 */
	Long getTotalCount(Paper model);
	/**
	 * @Description: 清空临时表
	 * @Created Time: 2013-4-30 下午7:57:30
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param paperId
	 */
	void clearTemp(Integer teacherId, Timestamp operateTime, Integer paperId);
	/**
	 * @Description: 试卷保存新建
	 * @Created Time: 2013-5-3 上午10:20:38
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param paperId
	 * @return
	 * @throws Exception 
	 */
	Integer saveNew(Integer teacherId, Timestamp operateTime, Integer paperId);
	/**
	 * @Description: 试卷大题下的小题是否为空
	 * @Created Time: 2013-5-3 下午2:29:41
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param paperId
	 * @return
	 */
	List<Map<String, Object>> getCountSmallValid(Integer teacherId, Timestamp operateTime,
			Integer paperId);
	/**
	 * @Description: 试卷修改保存
	 * @Created Time: 2013-5-3 下午4:47:58
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param paperId
	 * @return
	 */
	Integer saveModify(Integer teacherId, Timestamp operateTime, Integer paperId);
	/**
	 * @Description: 查看试卷（将试卷复制到临时试卷）
	 * @Created Time: 2013-5-4 上午10:54:37
	 * @Author lys
	 * @param paperId
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	void view(Integer paperId, Integer teacherId, Timestamp operateTime);
	/**
	 * @Description: 复制新建
	 * @Created Time: 2013-5-6 下午10:40:44
	 * @Author lys
	 * @param paperId
	 * @param paperIdTemp
	 * @param teacherId
	 * @param operateTime
	 */
	void copyAdd(Integer paperId, Integer paperIdTemp, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 取得试卷的详细信息
	 * @Created Time: 2013-5-8 下午5:45:37
	 * @Author lys
	 * @param paperId
	 * @return
	 */
	Paper loadDetail(Integer paperId);
	/**
	 * @Description: 存储过程保存
	 * @Created: 2013-7-3 上午11:01:43
	 * @author lys
	 * @update logs
	 * @param teacherId
	 * @param operateTime
	 * @param paperId
	 * @param flag 
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	StoreProcedureResult editPaperSP(Integer teacherId, Timestamp operateTime, Integer paperId, String flag,Boolean state) throws SQLException;
}

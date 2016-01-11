package com.csit.dao;

import java.util.List;

import com.csit.model.HistoryMessage;
import com.csit.model.Teacher;

/**
 * 
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
public interface HistoryMessageDAO extends BaseDAO<HistoryMessage, Integer> {
	
	/**
	 * 
	 * @Description: 查询发送历史记录
	 * @param
	 * @Create: 2013-6-28 上午09:13:56
	 * @author longweier
	 * @update logs
	 * @param beginDate    开始时间
	 * @param endDate	        结束时间
	 * @param teacher	        教师
	 * @param page
	 * @param rows
	 * @return
	 * @return
	 * @throws Exception
	 */
	public List<HistoryMessage> query(String beginDate,String endDate,Teacher teacher,Integer page, Integer rows);
	
	/**
	 * 
	 * @Description: 查询总数
	 * @param
	 * @Create: 2013-6-25 下午03:44:58
	 * @author longweier
	 * @update logs
	 * @param model
	 * @return
	 * @return
	 * @throws Exception
	 */
	Long getTotalCount(String beginDate,String endDate,Teacher teacher);
	
	/**
	 * 
	 * @Description: 查询
	 * @param
	 * @Create: 2013-6-26 下午02:18:26
	 * @author longweier
	 * @update logs
	 * @param historyMessage
	 * @return
	 * @return
	 * @throws Exception
	 */
	List<String> getMobiles(HistoryMessage historyMessage);
	/**
	 * 
	 * @Description: 查询提示短信发送历史
	 * @Create: 2013-7-18 下午03:16:38
	 * @author yk
	 * @update logs
	 * @param beginDate
	 * @param endDate
	 * @param teacher
	 * @param page
	 * @param rows
	 * @return
	 */
	List<HistoryMessage> queryPromptHistory(String beginDate,String endDate, Integer messageType,Integer page, Integer rows);
	/**
	 * 
	 * @Description: 统计提示短信发送历史 
	 * @Create: 2013-7-18 下午03:20:55
	 * @author yk
	 * @update logs
	 * @param beginDate
	 * @param endDate
	 * @param teacher
	 * @return
	 */
	Long getPromptTotalCount(String beginDate,String endDate, Integer messageType);
}

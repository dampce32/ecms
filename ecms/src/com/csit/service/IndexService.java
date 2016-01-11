package com.csit.service;

import java.util.Map;

import com.csit.vo.ServiceResult;

public interface IndexService {
	/**
	 * @Description: 欢迎界面表头信息
	 * @Created Time: 2013-6-2 上午9:33:40
	 * @Author lys
	 * @param competitionId
	 * @param studentId
	 * @return
	 */
	ServiceResult indexTitle(String competitionId, Integer studentId);
	/**
	 * @Description: 欢迎界面
	 * @Created Time: 2013-6-2 下午12:24:13
	 * @Author lys
	 * @param competitionId
	 * @return
	 */
	ServiceResult index(String competitionId);
	/**
	 * @Description: 首页
	 * @Created Time: 2013-6-15 下午12:14:06
	 * @Author lys
	 * @param competitionId
	 * @return
	 */
	Map<String, Object> homePage(String competitionId);

}

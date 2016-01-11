package com.csit.service;

import java.util.Map;

import com.csit.model.Information;
import com.csit.vo.ServiceResult;
/**
 * @Description:资讯Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
public interface InformationService extends BaseService<Information, Integer>{
	/**
	 * @Description: 保存资讯
	 * @Created Time: 2013-6-3 下午3:45:27
	 * @Author lys
	 * @param model
	 * @param teacherId 
	 * @return
	 */
	ServiceResult save(Information model, Integer teacherId);
	/**
	 * @Description: 分页查询资讯
	 * @Created Time: 2013-6-3 下午5:19:52
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Information model, Integer page, Integer rows);
	/**
	 * @Description: 打开初始化资讯
	 * @Created Time: 2013-6-3 下午10:48:36
	 * @Author lys
	 * @param model
	 * @return
	 */
	ServiceResult init(Information model);
	/**
	 * @Description: 批量删除资讯
	 * @Created Time: 2013-6-3 下午11:09:54
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改状态
	 * @Created Time: 2013-6-3 下午11:10:07
	 * @Author lys
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Information model);
	/**
	 * @Description: 更新资讯排序
	 * @Created Time: 2013-6-3 下午11:29:02
	 * @Author lys
	 * @param informationId
	 * @param updateInformationId
	 * @return
	 */
	ServiceResult updateArray(String informationId, String updateInformationId);
	/**
	 * 
	 * @Description: 初始化公告界面
	 * @Create: 2013-6-14 上午09:05:05
	 * @author jcf
	 * @update logs
	 * @param competitionId
	 * @param page
	 * @param rows
	 * @return
	 */
	Map<String, Object> initNotice(String competitionId, Integer page,Integer rows);
	Information getInformationById(String competitionId,Integer informationId);
	/**
	 * @Description: 初始化大赛章程
	 * @Created Time: 2013-6-18 上午11:29:42
	 * @Author lys
	 * @param competitionId
	 * @return
	 */
	String initCompetitionRule(String competitionId);

}

package com.csit.service;

import java.sql.Timestamp;
import java.util.List;

import com.csit.model.Paper;
import com.csit.vo.ServiceResult;
/**
 * @Description:试卷Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
public interface PaperService extends BaseService<Paper,Integer>{
	/**
	 * @Description: 分页查询试卷
	 * @Created Time: 2013-4-27 下午4:57:40
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(Paper model, Integer page, Integer rows);
	/**
	 * @Description: 新建
	 * @Created Time: 2013-4-28 上午10:07:22
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 */
	public ServiceResult newAdd(Integer teacherId,Timestamp operateTime);
	/**
	 * @Description: 清空临时表
	 * @Created Time: 2013-4-30 下午7:49:05
	 * @Author lys
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult clearTemp(Integer teacherId, Timestamp operateTime,
			Paper model);
	/**
	 * @Description: 保存试卷
	 * @Created Time: 2013-5-1 下午9:56:19
	 * @Author lys
	 * @param bigId
	 * @param smallIds
	 * @param isOptionMixs
	 * @param scores
	 * @param difficultys
	 * @param bigIds
	 * @param subjectTypes
	 * @param bigNames
	 * @param isSmallMixs
	 * @param scoreBigs
	 * @param saveType
	 * @param teacherId
	 * @param operateTime
	 * @param model
	 * @return
	 */
	ServiceResult save(String bigId, String smallIds, String isOptionMixs,
			String scores, String difficultys, String bigIds,
			String subjectTypes, String bigNames, String isSmallMixs,
			String scoreBigs, String saveType, Integer teacherId,
			Timestamp operateTime, Paper model);
	/**
	 * @Description: 批量删除试卷
	 * @Created Time: 2013-5-4 上午9:20:21
	 * @Author lys
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 批量修改试卷状态
	 * @Created Time: 2013-5-4 上午10:02:49
	 * @Author lys
	 * @param ids
	 * @param model 
	 * @return
	 */
	ServiceResult mulUpdateState(String ids, Paper model);
	/**
	 * @Description: 打开修改试卷
	 * @Created Time: 2013-5-4 上午10:38:50
	 * @Author lys
	 * @param model
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult modify(Paper model, Integer teacherId, Timestamp operateTime);
	/**
	 * @Description: 
	 * @Created Time: 2013-5-4 上午10:47:19
	 * @Author lys
	 * @param model
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult view(Paper model, Integer teacherId, Timestamp operateTime);
	/**
	 * @Description: 复制新建
	 * @Created Time: 2013-5-6 下午10:24:32
	 * @Author lys
	 * @param paperId
	 * @param teacherId
	 * @param operateTime
	 * @return
	 */
	ServiceResult copyAdd(Integer paperId, Integer teacherId,
			Timestamp operateTime);
	/**
	 * @Description: 查看试卷
	 * @Created Time: 2013-5-8 上午10:09:41
	 * @Author lys
	 * @param model
	 * @return
	 */
	String getViewPanel(Paper model);
	/**
	 * @Description: 查看试卷(全Html)
	 * @Created Time: 2013-5-9 上午9:09:04
	 * @Author lys
	 * @param model
	 * @return
	 */
	String getViewHtml(Paper model);
	/**
	 * @Description: 取得开放的考卷
	 * @Created Time: 2013-5-9 下午2:58:55
	 * @Author lys
	 * @return
	 */
	List<Paper> getIndexStu();
	/**
	 * @Description: 
	 * @Created Time: 2013-5-10 上午11:06:48
	 * @Author lys
	 * @param paperId
	 * @return
	 */
	Paper load(Integer paperId);
	/**
	 * @Description: 初始化小题
	 * @Created Time: 2013-5-12 下午11:18:28
	 * @Author lys
	 * @param paperId
	 * @param bigId
	 * @param smallId
	 * @param smallNo 
	 * @return
	 */
	ServiceResult initSmall(String paperId, String bigId, String smallId, String smallNo);
	/**
	 * @Description: 修改试卷状态
	 * @Created: 2013-7-13 下午7:57:48
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @return
	 */
	ServiceResult updateState(Paper model);
	/**
	 * @Description: 教师端查看学生端试卷
	 * @Created: 2013-7-14 上午10:03:23
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param model
	 * @return
	 */
	ServiceResult initExamView(Paper model);
	/**
	 * @Description: 教师端查看学生端试卷小题
	 * @Created: 2013-7-14 上午11:30:26
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @param paperId
	 * @param bigId
	 * @param smallId
	 * @param smallNo
	 * @return
	 */
	ServiceResult initSmallView(String paperId, String bigId, String smallId,
			String smallNo);

}

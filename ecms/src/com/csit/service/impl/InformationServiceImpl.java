package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.InformationDAO;
import com.csit.model.Competition;
import com.csit.model.Information;
import com.csit.model.Teacher;
import com.csit.service.InformationService;
import com.csit.util.DateUtil;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

/**
 * @Description:资讯Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-3
 * @Author lys
 */
@Service
public class InformationServiceImpl extends
		BaseServiceImpl<Information, Integer> implements InformationService {
	@Resource
	private InformationDAO informationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.InformationService#save(com.csit.model.Information,
	 * java.lang.Integer)
	 */
	@Override
	public ServiceResult save(Information model, Integer teacherId) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写资讯信息");
			return result;
		}
		if (StringUtils.isEmpty(model.getInformationTitle())) {
			result.setMessage("请填写资讯标题");
			return result;
		}
		if (StringUtils.isEmpty(model.getCategory())) {
			result.setMessage("请填写资讯类型");
			return result;
		}
		if (model.getCompetition() == null
				|| model.getCompetition().getCompetitionId() == null) {
			result.setMessage("请选择资讯");
			return result;
		}
		if (model.getStatus() == null) {
			result.setMessage("请选择状态");
			return result;
		}
		if (model.getContent() == null) {
			result.setMessage("请填写资讯内容");
			return result;
		}
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);

		if (model.getInformationId() == null) {//新增
			if("大赛章程".equals(model.getCategory())){
				//如果是赛事章程，一个赛事只能有一个赛事章程
				String[] propertyNames={"competition.competitionId","category"};
				Object[] values={model.getCompetition().getCompetitionId(),"大赛章程"};
				Information oldInformation =  informationDAO.load(propertyNames, values);
				if(oldInformation!=null){
					result.setMessage("赛事下已有大赛章程，不能新增大赛章程");
					return result;
				}
			}
			model.setPublishDate(DateUtil.getNowTimestamp());
			// 取得相同资讯类型的最大排序
			Integer maxArray = informationDAO.getMaxArray(model.getCategory());
			if (maxArray == null) {
				maxArray = 0;
			} else {
				maxArray++;
			}
			model.setArray(maxArray);
			informationDAO.save(model);
		} else {
			Information oldModel = informationDAO.load(model.getInformationId());
			if (oldModel == null) {
				result.setMessage("要修改的资讯已不存在");
				return result;
			}
			//判断赛事下是否已有 大赛章程
			//如果有，判断是否还是当前的资讯
			//		如果是，可修改
			//		如果不是，则如果当前的资讯不能修改为大赛章程
			//如果没有，则允许修改
			String[] propertyNames={"competition.competitionId","category"};
			Object[] values={model.getCompetition().getCompetitionId(),"大赛章程"};
			Information oldInformation =  informationDAO.load(propertyNames, values);
			if(oldInformation!=null){
				if(oldInformation.getInformationId().intValue()!=oldModel.getInformationId().intValue()){
					if("大赛章程".equals(model.getCategory())){
						result.setMessage("赛事下已有大赛章程，不能新增大赛章程");
						return result;
					}
				}
			}
			oldModel.setInformationTitle(model.getInformationTitle());
			oldModel.setCategory(model.getCategory());
			oldModel.setContent(model.getContent());
			oldModel.setCompetition(model.getCompetition());
			oldModel.setStatus(model.getStatus());
		}
		result.setIsSuccess(true);
		result.addData("informationId", model.getInformationId());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.csit.service.InformationService#query(com.csit.model.Information,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(Information model, Integer page, Integer rows) {
		List<Information> list = informationDAO.query(model, page, rows);
		Long total = informationDAO.getTotalCount(model);

		String[] properties = { "informationId", "informationTitle",
				"competition.competitionName", "status", "category" };
		return JSONUtil.toJson(list, properties, total);
	}

	/*
	 * 
	 */
	@Override
	public ServiceResult init(Information model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请选择要打开的资讯信息");
			return result;
		}
		Information oldModel = informationDAO.init(model);
		String[] propertiesInformation = { "informationId",
				"competition.competitionId", "category", "informationTitle",
				"content", "status", "teacher.teacherName" };
		String informationData = JSONUtil.toJson(oldModel,
				propertiesInformation);
		result.addData("information", informationData);
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.InformationService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isEmpty(ids)) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil
				.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if (idArray.length == 0) {
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			Information oldModel = informationDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			} else {
				informationDAO.delete(Integer.parseInt(id));
				haveDelete = true;
			}
		}
		if (!haveDelete) {
			result.setMessage("没有可删除的资讯");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.csit.service.InformationService#mulUpdateStatus(java.lang.String,
	 * com.csit.model.Information)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Information model) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isEmpty(ids)) {
			result.setMessage("请选择要修改状态的资讯");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		if (idArray.length == 0) {
			result.setMessage("请选择要修改状态的资讯");
			return result;
		}
		if (model == null || model.getStatus() == null) {
			result.setMessage("请选择要修改成的状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Information oldModel = informationDAO.load(Integer.parseInt(id));
			if (oldModel != null
					&& oldModel.getStatus().intValue() != model.getStatus()
							.intValue()) {
				oldModel.setStatus(model.getStatus());
				haveUpdateShzt = true;
			}
		}
		if (!haveUpdateShzt) {
			result.setMessage("没有可修改状态的资讯");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.InformationService#updateArray(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ServiceResult updateArray(String informationId,
			String updateInformationId) {
		ServiceResult result = new ServiceResult(false);
		if (informationId == null || updateInformationId == null) {
			result.setMessage("请选择要改变排序的资讯");
			return result;
		}
		informationDAO.updateArray(informationId, updateInformationId);

		result.setIsSuccess(true);
		return result;
	}

	@Override
	public Map<String, Object> initNotice(String competitionId, Integer page,
			Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		Information model = new Information();
		Competition competition = new Competition();
		competition.setCompetitionId(Integer.parseInt(competitionId));
		model.setCompetition(competition);
		model.setCategory("大赛公告");

		List<Information> list = informationDAO.query(model, page + 1, rows);
		Long total = informationDAO.getTotalCount(model);

		map.put("noticeList", list);
		map.put("total", total);
		return map;
	}

	@Override
	public Information getInformationById(String competitionId,Integer informationId) {
		Competition competition=new Competition();
		competition.setCompetitionId(Integer.parseInt(competitionId));
		String[] propertyNames={"competition","informationId"};
		Object[] values={competition,informationId};
		return informationDAO.load(propertyNames, values);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.InformationService#initCompetitionRule(java.lang.String)
	 */
	@Override
	public String initCompetitionRule(String competitionId) {
		String[] propertyNames={"competition.competitionId","category"};
		Object[] values={Integer.parseInt(competitionId),"大赛章程"};
		Information oldInformation =  informationDAO.load(propertyNames, values);
		if(oldInformation!=null){
			return  oldInformation.getContent();
		}
		return null;
	}

}

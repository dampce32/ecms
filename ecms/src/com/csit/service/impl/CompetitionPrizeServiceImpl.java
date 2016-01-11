package com.csit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionPrizeDAO;
import com.csit.model.CompetitionPrize;
import com.csit.model.Prize;
import com.csit.service.CompetitionPrizeService;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.ServiceResult;

/**
 * @Description:赛事奖项Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-4
 * @author jcf
 * @vesion 1.0
 */
@Service
public class CompetitionPrizeServiceImpl extends BaseServiceImpl<CompetitionPrize, Integer> implements CompetitionPrizeService {

	@Resource
	private CompetitionPrizeDAO competitionPrizeDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#delete(com.csit.model.Competition)
	 */
	public ServiceResult delete(CompetitionPrize model) {
		ServiceResult result = new ServiceResult(false);
		if (model == null || model.getCompetitionPrizeId()==null) {
			result.setMessage("请选择要删除的赛事奖项");
			return result;
		}
		CompetitionPrize oldModel = competitionPrizeDAO.load(model.getCompetitionPrizeId());
		if (oldModel == null) {
			result.setMessage("该赛事奖项已不存在");
			return result;
		} else {
			competitionPrizeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionService#query(com.csit.model.Competition, java.lang.Integer, java.lang.Integer)
	 */
	public String query(CompetitionPrize model) {

		List<CompetitionPrize> list = competitionPrizeDAO.query(model);

		String[] properties = { "competitionPrizeId", "prize.prizeName","array","competitionGroup.competitionGroupId","award"};
		return JSONUtil.toJson(list, properties);
	}

	public ServiceResult save(CompetitionPrize model,String prizeIds) {
		ServiceResult result = new ServiceResult(false);
		if (model == null) {
			result.setMessage("请填写赛事信息");
			return result;
		}
		if (model.getCompetitionPrizeId()==null) {// 新增
			String[] ids = StringUtil.split(prizeIds);
			if(ids.length==0){
				result.setMessage("请选择赛事分组");
				return result;
			}else {
				Integer arrayInteger=competitionPrizeDAO.getMaxArray();
				for(int i=0;i<ids.length;i++){
					CompetitionPrize competitionPrize = new CompetitionPrize();
					competitionPrize.setArray(arrayInteger+i+1);
					Prize prize=new Prize();
					prize.setPrizeId(Integer.parseInt(ids[i]));
					competitionPrize.setPrize(prize);
					competitionPrize.setCompetitionGroup(model.getCompetitionGroup());
					competitionPrizeDAO.save(competitionPrize);
				}
			}
		} else {
			CompetitionPrize oldModel = competitionPrizeDAO.load(model.getCompetitionPrizeId());
			if (oldModel == null) {
				result.setMessage("该奖项已不存在");
				return result;
			}
			oldModel.setAward(model.getAward());
		}
		result.setIsSuccess(true);
		return result;
	}

	@Override
	public ServiceResult updateArray(Integer competitionPrizeId, Integer updateCompetitionPrizeId) {
		ServiceResult result = new ServiceResult(false);
		if(competitionPrizeId==null||updateCompetitionPrizeId==null){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		competitionPrizeDAO.updateArray(competitionPrizeId, updateCompetitionPrizeId);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeService#queryCombobox(com.csit.model.CompetitionPrize)
	 */
	@Override
	public String queryCombobox(CompetitionPrize model) {
		List<CompetitionPrize> list = competitionPrizeDAO.queryCombobox(model);
		String[] properties = {"competitionPrizeId","prize.prizeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPrizeService#queryDatagrid(com.csit.model.CompetitionPrize)
	 */
	@Override
	public String queryDatagrid(CompetitionPrize model) {
		List<CompetitionPrize> list = competitionPrizeDAO.queryCombobox(model);
		String[] properties = {"competitionPrizeId","prize.prizeName"};
		String jsonString = JSONUtil.toJson(list,properties);
		return jsonString;
	}

}

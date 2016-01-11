package com.csit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionDAO;
import com.csit.dao.InformationDAO;
import com.csit.dao.SystemConfigDAO;
import com.csit.model.Competition;
import com.csit.model.Information;
import com.csit.model.SystemConfig;
import com.csit.service.IndexService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;

@Service
public class IndexServiceImpl implements IndexService {
	@Resource
	private CompetitionDAO competitionDAO;
	@Resource
	private InformationDAO informationDAO;
//	@Resource
//	private CompetitionPhotoDAO competitionPhotoDAO;
	@Resource
	SystemConfigDAO systemConfigDAO;
	@Override
	public ServiceResult indexTitle(String competitionId, Integer studentId) {
		ServiceResult result = new ServiceResult(false);
		List<Competition> competitionList = competitionDAO.queryIndexTitle();
		Competition currCompetition = null;
		//如果有选择赛事，则找出赛事名称
		if(StringUtils.isNotEmpty(competitionId)){
			for (Competition competition : competitionList) {
				if(competition.getCompetitionId().toString().equals(competitionId)){
					currCompetition = competition;
					break;
				}
			}
		}else{
			if(competitionList!=null){
				currCompetition = competitionList.get(0);
			}
		}
		
		StringBuffer dropdownMenuSb  = new StringBuffer();
		for (Competition competition :  competitionList) {
			if(competition.getCompetitionId().intValue()==currCompetition.getCompetitionId().intValue()){
				dropdownMenuSb.append("<li  class=\"active\"><a href=\"#\" competitionId=\""+competition.getCompetitionId()+"\">"+competition.getCompetitionName()+"</a></li>");
			}else{
				dropdownMenuSb.append("<li><a href=\"#\" competitionId=\""+competition.getCompetitionId()+"\">"+competition.getCompetitionName()+"</a></li>");
			}
		}
		result.addData("dropdownMenu", dropdownMenuSb.toString());
		if(currCompetition!=null){
			result.addData("currCompetitionId", currCompetition.getCompetitionId());
			result.addData("currCompetitionName", currCompetition.getCompetitionName());
		}
		
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.IndexService#index(java.lang.String)
	 */
	@Override
	public ServiceResult index(String competitionId) {
		ServiceResult result = new ServiceResult(false);
		List<Competition> competitionList = competitionDAO.queryIndexTitle();
		StringBuffer dropdownMenuSb  = new StringBuffer();
		
		for (Competition competition :  competitionList) {
			dropdownMenuSb.append("<li><a href=\"#\" competitionId=\""+competition.getCompetitionId()+"\">"+competition.getCompetitionName()+"</a></li>");
		}
		result.addData("dropdownMenu", dropdownMenuSb.toString());
		Competition currCompetition = null;
		//如果有选择赛事，则找出赛事名称
		if(StringUtils.isNotEmpty(competitionId)){
			for (Competition competition : competitionList) {
				if(competition.getCompetitionId().toString().equals(competitionId)){
					currCompetition = competition;
					break;
				}
			}
		}else{
			currCompetition = competitionList.get(0);
		}
		result.addData("currCompetitionId", currCompetition.getCompetitionId());
		result.addData("currCompetitionName", currCompetition.getCompetitionName());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.IndexService#homePage(java.lang.String)
	 */
	@Override
	public Map<String, Object> homePage(String competitionId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Competition> competitionList = competitionDAO.queryIndexTitle();
		SystemConfig systemConfig = systemConfigDAO.load(1);
		map.put("competitionList", competitionList);
		Competition currCompetition = null;
		//如果有选择赛事，则找出赛事名称
		if(StringUtils.isNotEmpty(competitionId)){
			for (Competition competition : competitionList) {
				if(competition.getCompetitionId().toString().equals(competitionId)){
					currCompetition = competition;
					break;
				}
			}
		}else{
			if(competitionList!=null&&competitionList.size()>0){
				currCompetition = competitionList.get(0);
			}
		}
		if(currCompetition!=null){
			map.put("currCompetitionId", currCompetition.getCompetitionId());
			map.put("currCompetitionName", currCompetition.getCompetitionName());
			map.put("currCompetitionNote", currCompetition.getCompetitionNote());
			map.put("currCompetitionPic", currCompetition.getPicture());
			map.put("systemConfig", systemConfig);
			//当前赛事的赛事公告
			
			Information oldInformation = new Information();
			oldInformation.setCompetition(currCompetition);
			oldInformation.setCategory("大赛公告");

			List<Information> informationList = informationDAO.query(oldInformation,1,GobelConstants.DEFAULTPAGESIZE);
			map.put("informationList", informationList);
			
//			//当前赛事的选手风采
//			CompetitionPhoto oldCompetitionPhoto = new CompetitionPhoto();
//			oldCompetitionPhoto.setCompetition(currCompetition);
//			oldCompetitionPhoto.setPhotoType("选手风采");
//			
//			List<CompetitionPhoto> competitorPhotoList = competitionPhotoDAO.query(oldCompetitionPhoto, 1, GobelConstants.COMPETITIONPHOTO_PAGESIZE);
//			map.put("competitorPhotoList", competitorPhotoList);
//			
//			oldCompetitionPhoto.setPhotoType("主持人风采");
//			List<CompetitionPhoto> comperePhotoList = competitionPhotoDAO.query(oldCompetitionPhoto, 1, GobelConstants.COMPETITIONPHOTO_PAGESIZE);
//			map.put("comperePhotoList", comperePhotoList);
		}
		return map;
	}

}

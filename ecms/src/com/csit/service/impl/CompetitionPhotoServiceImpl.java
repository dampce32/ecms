package com.csit.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.csit.dao.CompetitionPhotoDAO;
import com.csit.dao.PictureDAO;
import com.csit.model.Competition;
import com.csit.model.CompetitionPhoto;
import com.csit.model.Picture;
import com.csit.service.CompetitionPhotoService;
import com.csit.util.FileUtil;
import com.csit.util.JSONUtil;
import com.csit.util.StringUtil;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事风采Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
@Service
public class CompetitionPhotoServiceImpl extends
		BaseServiceImpl<CompetitionPhoto, Integer> implements
		CompetitionPhotoService {
	@Resource
	PictureDAO pictureDAO;
	@Resource
	private CompetitionPhotoDAO competitionPhotoDAO;
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#uploadPhoto(java.io.File, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult uploadPhoto(File file, String fileFileName,
			String fileContentType, String pictureBasePath) {
		ServiceResult result = new ServiceResult(false);
		if(file==null){
			result.setMessage("上传文件格式错误");
			return result;
		}
		//扩展名
		String extention = FilenameUtils.getExtension(fileFileName);	
		if(!"jpg".equalsIgnoreCase(extention)||"png".equalsIgnoreCase(extention)||"jpeg".equalsIgnoreCase(extention)){
			result.setMessage("上传文件格式错误");
			return result;
		}
		//创建文件夹
		File dirs = new File(pictureBasePath);
		if(!dirs.exists()){
			dirs.mkdirs();
		}
		Picture picture = new Picture();
		picture.setPicturePath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
		pictureDAO.save(picture);
		//文件名
		//文件路径
		String targetPath=pictureBasePath+File.separator+picture.getPictureId()+".png";
		String targetThumbPath=pictureBasePath+File.separator+"t_"+picture.getPictureId()+".png";
		//创建文件
		File imageFile = new File(targetPath);
		//保存文件
		FileUtil.saveFile(file, imageFile);
		
		try {
			Thumbnails.of(new File(targetPath))
			.size(160, 160).toFile(new File(targetThumbPath));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		result.addData("pictureId", picture.getPictureId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#save(com.csit.model.CompetitionPhoto, java.lang.String)
	 */
	@Override
	public ServiceResult save(CompetitionPhoto model, String pictureBasePath) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写赛事风采信息");
			return result;
		}
		if(model.getCompetition()==null||model.getCompetition().getCompetitionId()==null){
			result.setMessage("请选择赛事风采所属的赛事");
			return result;
		}
		if(StringUtils.isEmpty(model.getPhotoType())){
			result.setMessage("请选择赛事风采所属的类型");
			return result;
		}
		if(model.getPicture()==null|model.getPicture().getPictureId()==null){
			result.setMessage("请选择要上传的图片");
			return result;
		}
		if(model.getCompetitionPhotoId()==null){
			Integer array = competitionPhotoDAO.getMaxArray(model.getCompetition());
			model.setArray(array+1);
			competitionPhotoDAO.save(model);
		}else{
			CompetitionPhoto oldModel = competitionPhotoDAO.load(model.getCompetitionPhotoId());
			oldModel.setCompetition(model.getCompetition());
			oldModel.setPhotoType(model.getPhotoType());
			oldModel.setNote(model.getNote());
			if(model.getPicture().getPictureId().intValue()!=oldModel.getPicture().getPictureId().intValue()){
				//移除原来的图片
//				String filePath = pictureBasePath +File.separator+oldModel.getPicture().getPictureId()+".png";
//				FileUtil.deleteFile(filePath);
//				pictureDAO.delete(oldModel.getPicture());
				
				oldModel.setPicture(model.getPicture());
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#query(com.csit.model.CompetitionPhoto, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String query(CompetitionPhoto model, Integer page, Integer rows) {
		List<CompetitionPhoto> list = competitionPhotoDAO.query(model, page, rows);
		Long total = competitionPhotoDAO.getTotalCount(model);

		String[] properties = { "competitionPhotoId", "photoType","picture.pictureId","competition.competitionId","competition.competitionName","note"};
		return JSONUtil.toJson(list, properties, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#mulDelete(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids, String pictureBasePath) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		boolean haveDelete = false;
		for (String id : idArray) {
			CompetitionPhoto oldModel = competitionPhotoDAO.load(Integer.parseInt(id));
			if (oldModel == null) {
				continue;
			}else{
				//移除原来的图片
//				String filePath = pictureBasePath +File.separator+oldModel.getPicture().getPictureId()+".png";
//				FileUtil.deleteFile(filePath);
//				pictureDAO.delete(oldModel.getPicture());
				
				competitionPhotoDAO.delete(oldModel);
				haveDelete = true;
			}
		}
		if(!haveDelete){
			result.setMessage("没有可删除的资讯");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult updateArray(String competitionPhotoId,
			String updateCompetitionPhotoId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(competitionPhotoId)||StringUtils.isEmpty(updateCompetitionPhotoId)){
			result.setMessage("请选择要改变排序的赛事风采");
			return result;
		}
		competitionPhotoDAO.updateArray(Integer.parseInt(competitionPhotoId),Integer.parseInt(updateCompetitionPhotoId));
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#init(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String, Object> init(String competitionId, String photoType, Integer page,
			Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		CompetitionPhoto model = new CompetitionPhoto();
		Competition competition = new Competition();
		competition.setCompetitionId(Integer.parseInt(competitionId));
		model.setCompetition(competition);
		if("competitor".equals(photoType)){
			model.setPhotoType("选手风采");
		}else if("compere".equals(photoType)){
			model.setPhotoType("主持人风采");
		}
		
		
		List<CompetitionPhoto> list = competitionPhotoDAO.query(model, page+1, rows);
		Long total = competitionPhotoDAO.getTotalCount(model);
		
		map.put("photoList", list);
		map.put("total", total);
		return map;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#mulUploadPhoto(java.util.List, java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	public String mulUploadPhoto(List<File> filedata,
			List<String> filedataFileName, List<String> filedataContentType,
			String pictureBasePath) {
		String msg ="";
		int fileSize = filedata.size();
		if(filedata!=null&&fileSize!=0){
			for (int i = 0; i < fileSize; i++) {
				File file = filedata.get(i);
				//创建文件夹
				File dirs = new File(pictureBasePath);
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				Picture picture = new Picture();
				picture.setPicturePath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
				pictureDAO.save(picture);
				//文件名
				//文件路径
				String targetPath=pictureBasePath+File.separator+picture.getPictureId()+".png";
				String targetThumbPath=pictureBasePath+File.separator+"t_"+picture.getPictureId()+".png";
				//创建文件
				File imageFile = new File(targetPath);
				//保存文件
				FileUtil.saveFile(file, imageFile);
				if(StringUtils.isEmpty(msg)){
					msg+= picture.getPictureId();
				}else{
					msg+= ","+picture.getPictureId();
				}
				try {
					Thumbnails.of(new File(targetPath))
					.size(160, 160).toFile(new File(targetThumbPath));
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		}
		return msg;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.CompetitionPhotoService#mulSave(com.csit.model.CompetitionPhoto, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult mulSave(CompetitionPhoto model, String pictureIds,
			String pictureBasePath) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写赛事风采信息");
			return result;
		}
		if(model.getCompetition()==null||model.getCompetition().getCompetitionId()==null){
			result.setMessage("请选择赛事风采所属的赛事");
			return result;
		}
		if(StringUtils.isEmpty(model.getPhotoType())){
			result.setMessage("请选择赛事风采所属的类型");
			return result;
		}
		if(StringUtils.isEmpty(pictureIds)){
			result.setMessage("请选择要上传的图片");
			return result;
		}
		Integer[] pictureIdArray = StringUtil.splitToInteger(pictureIds);
		if(pictureIdArray.length==0){
			result.setMessage("请选择要上传的图片");
			return result;
		}
		for (int i = 0; i < pictureIdArray.length; i++) {
			CompetitionPhoto competitionPhoto = new CompetitionPhoto();
			competitionPhoto.setCompetition(model.getCompetition());
			competitionPhoto.setPhotoType(model.getPhotoType());
			
			Picture picture = new Picture();
			picture.setPictureId(pictureIdArray[i]);
			competitionPhoto.setPicture(picture);
			
			Integer array = competitionPhotoDAO.getMaxArray(model.getCompetition());
			competitionPhoto.setArray(array+1);
			competitionPhotoDAO.save(competitionPhoto);
		}
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public ServiceResult deletePhoto(Integer pictureId, String pictureBasePath) {

		ServiceResult result = new ServiceResult(false);
		String filePath1 = pictureBasePath +File.separator+pictureId+".png";
		String filePath2 = pictureBasePath +File.separator+"t_"+pictureId+".png";
		FileUtil.deleteFile(filePath1);
		FileUtil.deleteFile(filePath2);
		result.setIsSuccess(true);
		return result;
	}

}

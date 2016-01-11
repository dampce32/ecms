package com.csit.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.csit.model.CompetitionPhoto;
import com.csit.vo.ServiceResult;
/**
 * @Description:赛事风采Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
public interface CompetitionPhotoService extends BaseService<CompetitionPhoto, Integer>{
	/**
	 * @Description: 上传照片
	 * @Created Time: 2013-6-11 下午6:56:09
	 * @Author lys
	 * @param file
	 * @param fileFileName
	 * @param fileContentType
	 * @param pictureBasePath
	 * @return
	 */
	ServiceResult uploadPhoto(File file, String fileFileName,
			String fileContentType, String pictureBasePath);
	/**
	 * @Description: 保存赛事风采
	 * @Created Time: 2013-6-11 下午9:29:06
	 * @Author lys
	 * @param model
	 * @param pictureBasePath 
	 * @return
	 */
	ServiceResult save(CompetitionPhoto model, String pictureBasePath);
	/**
	 * @Description: 分页查询赛事风采
	 * @Created Time: 2013-6-12 上午10:16:37
	 * @Author lys
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	String query(CompetitionPhoto model, Integer page, Integer rows);
	/**
	 * @Description: 批量删除赛事风采
	 * @Created Time: 2013-6-12 下午5:11:34
	 * @Author lys
	 * @param ids
	 * @param pictureBasePath 
	 * @return
	 */
	ServiceResult mulDelete(String ids, String pictureBasePath);
	/**
	 * @Description: 更新赛事风采排序
	 * @Created Time: 2013-6-12 下午5:11:47
	 * @Author lys
	 * @param competitionPhotoId
	 * @param updateCompetitionPhotoId
	 * @return
	 */
	ServiceResult updateArray(String competitionPhotoId,
			String updateCompetitionPhotoId);
	/**
	 * @Description: 初始化赛事风采界面
	 * @Created Time: 2013-6-13 下午7:24:03
	 * @Author lys
	 * @param competitionId
	 * @param photoType 
	 * @param page
	 * @param competitionphotoPagesize
	 * @return
	 */
	Map<String, Object> init(String competitionId, String photoType, Integer page,
			Integer rows);
	/**
	 * @Description: 批量上传赛事图片
	 * @Created Time: 2013-6-30 上午9:21:49
	 * @Author lys
	 * @param filedata
	 * @param filedataFileName
	 * @param filedataContentType
	 * @param pictureBasePath
	 * @return
	 */
	String mulUploadPhoto(List<File> filedata, List<String> filedataFileName,
			List<String> filedataContentType, String pictureBasePath);
	/**
	 * @Description: 批量保存赛事风采
	 * @Created Time: 2013-6-30 下午8:33:34
	 * @Author lys
	 * @param model
	 * @param pictureIds
	 * @param pictureBasePath
	 * @return
	 */
	ServiceResult mulSave(CompetitionPhoto model, String pictureIds,
			String pictureBasePath);
	/**
	 * 
	 * @Description: 删除服务器上照片
	 * @Create: 2013-7-16 下午03:17:28
	 * @author jcf
	 * @update logs
	 * @param pictureId
	 * @param pictureBasePath
	 */
	ServiceResult deletePhoto(Integer pictureId,String pictureBasePath);

}

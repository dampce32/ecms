package com.csit.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csit.dao.SystemConfigDAO;
import com.csit.model.CompetitionPhoto;
import com.csit.model.SystemConfig;
import com.csit.service.CompetitionPhotoService;
import com.csit.vo.GobelConstants;
import com.csit.vo.ServiceResult;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:赛事风采Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-11
 * @Author lys
 */
@Controller
@Scope("prototype")
public class CompetitionPhotoAction extends BaseAction implements
		ModelDriven<CompetitionPhoto> {

	private static final long serialVersionUID = 6168356261586763235L;
	private static final Logger logger = Logger
			.getLogger(CompetitionPhotoAction.class);
	private CompetitionPhoto model = new CompetitionPhoto();
	@Resource
	private CompetitionPhotoService competitionPhotoService;

	@Resource
	SystemConfigDAO systemConfigDAO;
	/** 文件对象 */
	private List<File> Filedata;
	/** 文件名 */
	private List<String> FiledataFileName;
	/** 文件内容类型 */
	private List<String> FiledataContentType;
	/** 返回字符串 */
	private String returnValue = null;

	@Override
	public CompetitionPhoto getModel() {
		return model;
	}

	/**
	 * @Description: 上传赛事风采图片
	 * @Created Time: 2013-6-11 下午9:25:49
	 * @Author lys
	 */
	public void uploadPhoto() {
		ServiceResult result = new ServiceResult(false);
		try {
			String pictureBasePath = getRequest().getSession()
					.getServletContext()
					.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
			result = competitionPhotoService.uploadPhoto(file, fileFileName,
					fileContentType, pictureBasePath);
		} catch (Exception e) {
			result.setMessage("上传赛事风采图片失败");
			logger.error("上传赛事风采图片失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}

	/**
	 * @Description: 批量上传赛事图片
	 * @Created Time: 2013-6-28 下午6:01:52
	 * @Author lys
	 */
	public void mulUploadPhoto() {
		String pictureBasePath = getRequest().getSession().getServletContext()
				.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
		String msg = competitionPhotoService.mulUploadPhoto(Filedata,FiledataFileName,FiledataContentType, pictureBasePath);;
		ajaxJson(msg);
	}

	/**
	 * @Description: 保存赛事风采
	 * @Created Time: 2013-6-3 下午3:44:52
	 * @Author lys
	 */
	public void save() {
		ServiceResult result = new ServiceResult(false);
		try {
			String pictureBasePath = getRequest().getSession()
					.getServletContext()
					.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
			result = competitionPhotoService.save(model, pictureBasePath);
		} catch (Exception e) {
			result.setMessage("保存赛事风采失败");
			logger.error("保存赛事风采失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量保存赛事风采
	 * @Created Time: 2013-6-30 下午8:28:04
	 * @Author lys
	 */
	public void mulSave() {
		ServiceResult result = new ServiceResult(false);
		try {
			String pictureBasePath = getRequest().getSession()
					.getServletContext()
					.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
			String pictureIds = getParameter("pictureIds");
			result = competitionPhotoService.mulSave(model,pictureIds, pictureBasePath);
		} catch (Exception e) {
			result.setMessage("批量保存赛事风采失败");
			logger.error("批量保存赛事风采失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 分页查询赛事风采
	 * @Created Time: 2013-6-12 上午10:16:01
	 * @Author lys
	 */
	public void query() {
		String jsonArray = competitionPhotoService.query(model, page, rows);
		ajaxJson(jsonArray);
	}

	/**
	 * @Description: 批量删除赛事风采
	 * @Created Time: 2013-6-12 下午5:08:24
	 * @Author lys
	 */
	public void mulDelete() {
		ServiceResult result = new ServiceResult(false);
		try {
			String pictureBasePath = getRequest().getSession()
					.getServletContext()
					.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
			result = competitionPhotoService.mulDelete(ids, pictureBasePath);
		} catch (Throwable e) {
			result.setMessage("批量删除赛事风采失败");
			logger.error("批量删除赛事风采失败", e);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * @Description: 更新赛事风采排序
	 * @Created Time: 2013-6-3 下午11:27:06
	 * @Author lys
	 */
	public void updateArray() {
		String competitionPhotoId = getParameter("competitionPhotoId");
		String updateCompetitionPhotoId = getParameter("updateCompetitionPhotoId");
		ServiceResult result = new ServiceResult(false);
		try {
			result = competitionPhotoService.updateArray(competitionPhotoId,
					updateCompetitionPhotoId);
		} catch (Exception e) {
			result.setMessage("更新赛事风采排序失败");
			logger.error("更新赛事风采排序失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

	/**
	 * @Description: 取得赛事风采的缩略图
	 * @Created Time: 2013-6-13 上午10:10:28
	 * @Author lys
	 */
	public void getPhoto() {
		String pictureId = getParameter("pictureId");
		String pictureBasePath = getRequest().getSession().getServletContext()
				.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
		String path = pictureBasePath + File.separator + pictureId + ".png";
		try {
			Thumbnails.of(new File(path)).size(160, 160)
					.toOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 初始化赛事风采界面
	 * @Created Time: 2013-6-13 上午10:17:48
	 * @Author lys
	 * @return
	 */
	public String init() {
		String competitionId = getParameter("competitionId");
		String photoType = getParameter("photoType");
		Map<String, Object> map = competitionPhotoService.init(competitionId,
				photoType, page, GobelConstants.COMPETITIONPHOTO_PAGESIZE);
		request.setAttribute("photoList", map.get("photoList"));
		request.setAttribute("total", map.get("total"));
		request.setAttribute("currPage", page);
		request.setAttribute("pictureBasePath",
				GobelConstants.COMPETITIONPHOTO_BASEPATH);
		SystemConfig systemConfig = systemConfigDAO.load(1);
		request.setAttribute("systemConfig", systemConfig);
		return SUCCESS;
	}
	
	public void deletePic() {
		ServiceResult result = new ServiceResult(false);
		try {
			String pictureBasePath = getRequest().getSession()
					.getServletContext()
					.getRealPath(GobelConstants.COMPETITIONPHOTO_BASEPATH);
			result = competitionPhotoService.deletePhoto(model.getPicture().getPictureId(), pictureBasePath);
		} catch (Throwable e) {
			result.setMessage("删除照片失败");
			logger.error("删除照片失败", e);
		}
		ajaxJson(result.toJSON());
	}

	public List<File> getFiledata() {
		return Filedata;
	}

	public void setFiledata(List<File> filedata) {
		Filedata = filedata;
	}

	public List<String> getFiledataFileName() {
		return FiledataFileName;
	}

	public void setFiledataFileName(List<String> filedataFileName) {
		FiledataFileName = filedataFileName;
	}

	public List<String> getFiledataContentType() {
		return FiledataContentType;
	}

	public void setFiledataContentType(List<String> filedataContentType) {
		FiledataContentType = filedataContentType;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
}

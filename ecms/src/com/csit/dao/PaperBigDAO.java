package com.csit.dao;

import java.util.List;

import com.csit.model.Paper;
import com.csit.model.PaperBig;
import com.csit.model.PaperBigId;
/**
 * @Description:试卷大题DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-8
 * @Author lys
 */
public interface PaperBigDAO extends BaseDAO<PaperBig,PaperBigId>{
	/**
	 * @Description: 查询试卷下的大题
	 * @Created Time: 2013-5-8 下午5:56:26
	 * @Author lys
	 * @param model
	 * @return
	 */
	List<PaperBig> query(Paper model);
	

}

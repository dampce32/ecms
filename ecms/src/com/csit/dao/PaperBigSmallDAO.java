package com.csit.dao;

import java.util.List;

import com.csit.model.PaperBig;
import com.csit.model.PaperBigSmall;
import com.csit.model.PaperBigSmallId;
/**
 * @Description: 试卷小题DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-8
 * @Author lys
 */
public interface PaperBigSmallDAO extends BaseDAO<PaperBigSmall,PaperBigSmallId>{
	/**
	 * @Description: 大题下的小题列表
	 * @Created Time: 2013-5-8 下午8:24:06
	 * @Author lys
	 * @param paperBig
	 * @return
	 */
	List<PaperBigSmall> query(PaperBig paperBig);

}

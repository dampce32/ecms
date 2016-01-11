package com.csit.vo;
/**
 * @Description:系统有用到的全局变量
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-27
 * @author lys
 * @vesion 1.0
 */
public interface GobelConstants {
	
	public static int DEFAULTPAGESIZE = 10;
	
	public static String SPLIT=",";
	public static String SPLIT_SEPARATOR ="^";
	/**
	 * 默认教师密码
	 */
	public static String DEFAULT_TEACHCER_PWD ="123456";
	/**
	 * 赛事风采的根路径
	 */
	public static String COMPETITIONPHOTO_BASEPATH ="upload/image/competitionPhoto";
	/**
	 * 赛事风采的每页的相片数
	 */
	public static Integer COMPETITIONPHOTO_PAGESIZE = 9;
	/**
	 * 邮件发送的附件路径
	 */
	public static String MailAttachment_BASEPATH = "upload/file/mailAttachment";
	
}

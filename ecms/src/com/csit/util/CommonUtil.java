package com.csit.util;

import org.apache.commons.lang.StringUtils;


/**
 * @Description:共用的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-13
 * @author lys
 * @vesion 1.0
 */
public class CommonUtil {
	
	/**
	 * @Description: 取得大题题号
	 * @Created Time: 2013-5-9 上午10:15:40
	 * @Author lys
	 * @param no
	 * @return
	 */
	public static String getBigNo(Integer no){
		String result ="";
		switch (no) {
		case 1:
			result ="I";
			break;
		case 2:
			result ="II";
			break;
		case 3:
			result ="III";
			break;
		case 4:
			result ="IV";
			break;
		case 5:
			result ="V";
			break;
		case 6:
			result ="VI";
			break;
		case 7:
			result ="VII";
			break;
		case 8:
			result ="VIII";
			break;
		case 9:
			result ="IX";
			break;
		case 10:
			result ="X";
			break;
		case 11:
			result ="XI";
			break;
		case 12:
			result ="XXII";
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * @Description: 取得选项编号
	 * @Created Time: 2013-5-15 下午1:48:27
	 * @Author lys
	 * @param optionIndex
	 * @return
	 */
	public static char getOptionChar(int optionIndex){
		return (char)('A'+optionIndex);
	}
	/**
	 * @Description: 是否取得分数
	 * @Created Time: 2013-5-15 下午3:36:41
	 * @Author lys
	 * @param standardOption
	 * @param answer
	 * @return
	 */
	public static boolean isGoalScore(String standardOption,String answer){
		boolean isGoalScore = false;
		if(StringUtils.isEmpty(answer)){
			isGoalScore = false;
		}else{
			String[] answerArray = StringUtil.split(standardOption, "!@#");
			for (int i = 0; i < answerArray.length; i++) {
				String answerStr = answerArray[i];
				if (StringUtils.isNotEmpty(answerStr)&&answerStr.trim().equals(answer.trim())) {
					isGoalScore = true;
					break;
				}
			}
		}
		return isGoalScore;
	}
	
}

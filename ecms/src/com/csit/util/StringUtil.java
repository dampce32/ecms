package com.csit.util;

import org.apache.commons.lang.StringUtils;

import com.csit.vo.GobelConstants;

public class StringUtil {
	/**
	 * @Description: 将字符串source根据separator分割成字符串数组
	 * @Create: 2013-1-8 下午2:28:10
	 * @author lys
	 * @update logs
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String[] split(String source,String separator){
		String[] distArray ={};
		if(source==null){
			return distArray;
		}
		int i = 0;
		distArray = new String[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			distArray[i++] = StringUtils.isEmpty(value)?null:value;
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成字符串数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static String[] split(String source){
		return split(source,GobelConstants.SPLIT_SEPARATOR);
	}
	
	/**
	 * @Description: 将字符串source根据separator分割成Integer数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Integer[] splitToInteger(String source,String separator){
		Integer[] distArray ={};
		if(source==null){
			return distArray;
		}
		int i = 0;
		distArray = new Integer[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			distArray[i++] = StringUtils.isEmpty(value)?null:Integer.parseInt(value);
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Integer数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Integer[] splitToInteger(String source){
		return splitToInteger(source,GobelConstants.SPLIT_SEPARATOR);
	}
	
	/**
	 * @Description: 将字符串source根据separator分割成Integer数组，并提出其中的Null值
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Integer[] splitToIntegerTrimNull(String source,String separator){
		Integer[] distArray ={};
		if(source==null||source.length()==0){
			return distArray;
		}
		int i = 0;
		distArray = new Integer[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			if(StringUtils.isNotEmpty(value)){
				distArray[i++] = Integer.parseInt(value);
			}
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Integer数组，并剔除Null值
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Integer[] splitToIntegerTrimNull(String source){
		return splitToIntegerTrimNull(source,GobelConstants.SPLIT_SEPARATOR);
	}
	
	/**
	 * @Description: 将字符串source根据separator分割成Boolean数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Boolean[] splitToBoolean(String source,String separator){
		Boolean[] distArray ={};
		if(source==null){
			return distArray;
		}
		int i = 0;
		distArray = new Boolean[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			distArray[i++] = StringUtils.isEmpty(value)?null:Boolean.parseBoolean(value);
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Boolean数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Boolean[] splitToBoolean(String source){
		return splitToBoolean(source,GobelConstants.SPLIT_SEPARATOR);
	}
	
	/**
	 * @Description: 将字符串source根据separator分割成Boolean数组
	 * @Created Time: 2013-3-4 下午10:15:04
	 * @Author lys
	 * @param source
	 * @param separator
	 * @return
	 */
	public static Double[] splitToDouble(String source,String separator){
		Double[] distArray ={};
		if(source==null){
			return distArray;
		}
		int i = 0;
		distArray = new Double[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			distArray[i++] = StringUtils.isEmpty(value)?null:Double.parseDouble(value);
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成Double数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static Double[] splitToDouble(String source){
		return splitToDouble(source,GobelConstants.SPLIT_SEPARATOR);
	}
	/**
	 * @Description: 将null等空Empty情况变为空字符串
	 * @Created Time: 2013-5-9 上午9:20:22
	 * @Author lys
	 * @param note
	 * @return
	 */
	public static String getEmptyToBlank(String source) {
		if(StringUtils.isEmpty(source)){
			return "";
		}
		return source;
	}
	
	
}

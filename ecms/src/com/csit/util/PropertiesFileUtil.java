package com.csit.util;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @Description: 读取properties文件工具类
 * @Copyright: 福州骏华科技信息有限公司 (c)2012
 * @Created Date : 2012-3-27
 * @author longweier
 * @vesion 1.0
 */
public class PropertiesFileUtil {

	private Properties properties;
	
	private String propFilePath;
	
	/**
	 * 
	 * @Description: 加载文件
	 * @param
	 * @Create: 2012-3-27 下午12:40:30
	 * @author longweier
	 * @update logs
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public void instancePropertiesFile(String filePath)
	{
		propFilePath = filePath;
		
		properties = new Properties();
		
		InputStream inputStream;
		
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFilePath);
			
			properties.load(inputStream);
			
			inputStream.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			
			inputStream = null;
		}
	}
	
	/**
	 * 
	 * @Description: 通过key获取value值
	 * @param
	 * @Create: 2012-3-27 下午12:40:43
	 * @author longweier
	 * @update logs
	 * @param key
	 * @return
	 * @return
	 * @throws Exception
	 */
	public String getValue(String key)
	{
		if(properties == null){
			
			return null;
		}
		
		if(properties.containsKey(key))
		{
			return properties.getProperty(key);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @Description: 获取通过","隔开的一些只，返回一个List
	 * @param
	 * @Create: 2012-3-27 下午12:41:01
	 * @author longweier
	 * @update logs
	 * @param key
	 * @return
	 * @return
	 * @throws Exception
	 */
	public List<String> getValueList(String key)
	{
		List<String> list = null;
		
		if(properties == null){
			
			return null;
		}
		
		if(properties.containsKey(key))
		{
			String value = properties.getProperty(key);
			
			String[] values = value.split(",");
			
			list = Arrays.asList(values);
		}
		
		return list;
	}
	
	public void setValue(String key,String value){
		if(properties==null){
			return;
		}
		try {
			String path = getClass().getClassLoader().getResource(propFilePath).getPath();
			OutputStream out = new FileOutputStream(path);
			properties.setProperty(key, value);
			properties.store(out,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

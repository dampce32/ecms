package com.csit.thread;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import cn.emay.sdk.client.api.Client;

import com.csit.util.PropertiesFileUtil;

/**
 * 
 * @Description: 短信消息发送Client
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-25
 * @author longweier
 * @vesion 1.0
 */
public class MessageClient {
	
	private static String softwareSerialNo = null;
	
	private static  String key = null;
	
	private static Client client =null;
	
	private MessageClient(){
		
	}
	/**
	 * 
	 * @Description: 短信发送端注册
	 * @param
	 * @Create: 2013-6-25 下午04:05:08
	 * @author longweier
	 * @update logs
	 * @param softwareSerialNo
	 * @param key
	 * @return    0	注册成功
				  911005	客户端注册失败(请检查序列号、密码、key值是否配置正确)
				  911003	该序列号已经使用其它key值注册过了。（若无法找回key值请联系销售注销，然后重新注册使用。）
				  303	网络超时或者网络故障
				  305	服务器端返回错误，错误的返回值（返回值不是数字字符串）
				  999	操作频繁
	 * @throws Exception
	 */
	public static Integer registClient(String softwareSerialNo,String key){
		if(StringUtils.isEmpty(softwareSerialNo) || StringUtils.isEmpty(key)){
			return 0;
		}
		int result = -1;
		try {
			client = new Client(softwareSerialNo, key);
			result = client.registEx(key);
			
//			int a = client.registDetailInfo("福州一飞教育培训学校", "林以宋", "059123320529","13950222605", "linyisong032@gmail.com", "059123320524", "照屿路10号", "350000");
			
			if(result==0){
				PropertiesFileUtil properties = new PropertiesFileUtil();
				
				properties.instancePropertiesFile("systemConfig.properties");
				
				properties.setValue("softwareSerialNo", softwareSerialNo);
				properties.setValue("key", key);
				properties.setValue("regist", "1");
				
				MessageClient.softwareSerialNo = softwareSerialNo;
				MessageClient.key = key;
				
				return 1;
			}
			client = null;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 
	 * @Description: 注销
	 * @param
	 * @Create: 2013-6-26 下午05:12:08
	 * @author longweier
	 * @update logs
	 * @param softwareSerialNo
	 * @param key
	 * @return
	 * @return
	 * @throws Exception
	 */
	public static Integer logout(String softwareSerialNo,String key){
		if(StringUtils.isEmpty(softwareSerialNo) || StringUtils.isEmpty(key)){
			return 0;
		}
		int result = -1;
		try {
			client = new Client(softwareSerialNo, key);
			result = client.logout();
			if(result==0){
				client = null;
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} 
	
	public synchronized static Client getClient(){
		if(client==null){
			PropertiesFileUtil properties = new PropertiesFileUtil();
			
			properties.instancePropertiesFile("systemConfig.properties");
			softwareSerialNo = properties.getValue("softwareSerialNo");
			key = properties.getValue("key");
			
			try {
				client = new Client(softwareSerialNo, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	/**
	 * 
	 * @Description: 短信发送函数,调用该函数会即时的下发短信，支持单发和群发
	 * @param
	 * @Create: 2013-6-25 下午05:49:05
	 * @author longweier
	 * @update logs
	 * @param mobiles
	 * @param smsContent
	 * @param smsPriority
	 * @return   -1	发送信息失败（短信内容长度越界）
			     0	短信发送成功
				 17	发送信息失败（未激活序列号或序列号和KEY值不对，或账户没有余额等）
				 101	客户端网络故障
				 305	服务器端返回错误，错误的返回值（返回值不是数字字符串）
				 306	客户端发送队列满
				 307	目标电话号码不符合规则，电话号码必须是以0、1开头
				 997	平台返回找不到超时的短信，该信息是否成功无法确定
				 303	由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定
	 * @throws Exception
	 */
	public static Integer sendSMS(String[] mobiles,String smsContent){
		if(client==null){
			getClient();
		}
		int length = mobiles.length;
		int result = -2;
		int size = length/200;
		if(length%200!=0){
			size ++;
		}
		boolean success = true;
		if(length>200){
			for(int i=0;i<size;i++){
				String[] tempMobiles =splitArray(mobiles, i, 200);
				result = client.sendSMS(tempMobiles, smsContent, 5);
				if(result!=0){
					success = false;
				}
			}
		}else{
			result = client.sendSMS(mobiles, smsContent, 5);
			if(result!=0){
				success = false;
			}
		}
		return success?0:result;
	}
	
	/**
	 * 
	 * 
	 * @Description: 分割数值
	 * @param
	 * @Create: 2013-6-26 上午09:28:39
	 * @author longweier
	 * @update logs
	 * @param original   需要分割的源数组
	 * @param index      第几个  从0开始
	 * @param step       步长
	 * @return
	 * @return
	 * @throws Exception
	 */
	private static String[] splitArray(String[] original ,int index,int step){
		int begin = index*step;
		int end = Math.min(begin+step, original.length);
		
		return Arrays.copyOfRange(original, begin, end);
	}
	/**
	 * @Description: 当前短信账户还剩多少条可发送
	 * @Created: 2013-7-20 下午9:50:55
	 * @author lys
	 * @update logs
	 * @throws Exception
	 * @return
	 */
	public static int getRemainingCount(){
		if(client==null){
			getClient();
		}
		int count = 0;
		try {
			count = (int) ((client.getBalance()*100)/(client.getEachFee()*100));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int recharge(String cardNo,String cardPass){
		if(client==null){
			getClient();
		}
		int result = client.chargeUp(cardNo, cardPass);
		return result;
	}
}

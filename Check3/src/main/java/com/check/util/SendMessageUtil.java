package com.check.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMessageUtil {
	
	private static Logger log = LoggerFactory.getLogger(SendMessageUtil.class);
	private static String SENDMESSAGE_URL="http://123.103.9.205:7099/chanpay-message/sendMessage";

	public static String sendMessage(String telphone,String content){
		HttpClient client = new HttpClient();
		String sendResult = "";
		PostMethod method = new PostMethod(SENDMESSAGE_URL);
		method.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");		
		try {
			method.addParameter("telphone", telphone);
			method.addParameter("content", content);
			client.executeMethod(method);
			sendResult = method.getResponseBodyAsString();
			log.info("云平台返回码sendResult-------->" + sendResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return sendResult;
	}
	
}

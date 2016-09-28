package com.check.util;
/**
 * 变量配置类
 * @author Administrator
 *
 */
public class ConfigUtils {
	/**广发银行签名用私钥路径/var/cgb/GFBankDPClient.pfx**/
	public static final String CGB_PRI_KEY_PATH ="/var/checkchannel/cgb/GFBankDPClient.pfx";///var/cgb/GFBankDPClient.pfx  E:/CGB/GFBankDPClient.pfx
	public static final String CGB_PRI_KEY_PWD = "1234567";
	/**广发银行/var/cgb/dppaykey.cer**/
	public static final String CGB_PUB_KEY_PATH="/var/checkchannel/cgb/dppaykey.cer";// E:/CGB/dppaykey.cer    /var/cgb/dppaykey.cer
	/**成都明生银行keystoreURL/var/cdcmbc/bjcjtzf@100100000010071.p12**/
	public static final String KEY_STORE_URL="/var/checkchannel/cdcmbc/bjcjtzf@100100000010071.p12";// E:/CDCMBC/bjcjtzf@100100000010071.p12
	/**成都明生银行ssLJksURL/var/cdcmbc/bjcjtzf@100100000010071.jks**/
	public static final String SSL_JKS_URL="/var/checkchannel/cdcmbc/bjcjtzf@100100000010071.jks";
	/**成都明生银行keystorePWD**/
	public static final String KEY_STORE_PWD="bjcjtzf123456";
	/**成都明生银行serverURLZ**/
	public static final String SERVER_URLZ="https://118.122.92.62:8443/cmbcproxyfees";
	/**成都明生银行httpsPort**/
	public static final String HTTPSPORT="8443";
	public static final String ENCODING_utf8 = "UTF-8";
	/**
	 * 成都民生商户id
	 */
	public static final String CDCMBC_MERCDANT_ID="100100000010071";
	/**
	 * 业务类型
	 */
	public static final String QUERY_BUSINESS_TYPE="1004";
	/**
	 * 融宝商户id
	 */
	public static final String REAPAL_MERCHANT_ID="100000001300343";
	
	/**广发银行请求地址**/
	public static final String CGB_REQUEST_URL ="https://8.95508.com/ibank/service/financeRequest";
}

package com.check.Controller.bankController;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.util.JsonUtil;
import com.check.util.PropsUtil;
import com.check.util.ToolUtils;
import com.check.util.cmbci.CommonUtil;
import com.check.util.cmbci.CryptoUtil;
import com.check.util.cmbci.HttpClient4Util;

public class cmbciController {
	private static final Properties properties = PropsUtil.loadProps("com/bankProperties/cmbci.properties");
	private static final Logger LOGGER = LoggerFactory.getLogger(cmbciController.class);

    public static boolean CMBCIsend() {

        String status =null; // 订单状态（返回码）
        String sAmount =null; // 金额
        String instOrderNo =null; // 提交机构订单号
        try {

            String oid_partner = properties.getProperty("MERCHANT_NO"); // 商户号
            String no_order = ToolUtils.createUUID().substring(12);
            LOGGER.info("产生的交易订单号为："+no_order);
            //订单号		商户查询订单号
            String dt_order = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());    //发起订单查询的时间
            String ori_no_order = "FI102229080500020007";            //商户原交易订单号
            String signBef = "";        //签名
            Map<String,String> signs = new HashMap<String,String>();
            signs.put("oid_partner", oid_partner);
            signs.put("no_order", no_order);
            signs.put("dt_order", dt_order);
            signs.put("ori_no_order", ori_no_order);
            /*开始签名*/
            signBef = CommonUtil.genSignData(signs);
            PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix(properties.getProperty("PRIVATE_KEY_PATH"),
                    properties.getProperty("KEY_SUFFIX"), null,
                    properties.getProperty("KEY_TYPE"));
            byte[] base64SingDataBytes = Base64.encodeBase64(CryptoUtil.digitalSign(signBef.getBytes(properties.getProperty("CHARSET")),
                                            hzfPriKey, "SHA1WithRSA"));
            String signAft = new String(base64SingDataBytes, properties.getProperty("CHARSET"));
            /*结束签名*/
            signs.put("sign", signAft);
            String url = properties.getProperty("BANK_QUERY_URL");
            Map<String, String> result = queryVerifySign(signs, url);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                LOGGER.info("key=" + entry.getKey()+ "-----" + "value=" +entry.getValue());
            }
            String verifyPlainText = CommonUtil.genSignData(result);
            /*开始验签*/
            String verifySigns = result.get("sign");
            byte[] signBytes = Base64.decodeBase64(verifySigns.getBytes(properties.getProperty("CHARSET")));
            PublicKey hzfPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix(properties.getProperty("PUBLIC_KEY_PATH"),
                    properties.getProperty("KEY_SUFFIX"), properties.getProperty("KEY_TYPE"));
            boolean verifySign = CryptoUtil.verifyDigitalSign(verifyPlainText.getBytes(properties.getProperty("CHARSET")),
                                    signBytes,hzfPubKey, properties.getProperty("SIGN_TYPE"));
            if (verifySign) {
                LOGGER.info("银行------>商户合作方 [{}]验签通过");
            } else {
                LOGGER.error("银行------>商户合作方 [{}]报文验签不通过");
            }
            /*验签结束*/
            sAmount = result.get("money_order");
            System.out.println("----" + sAmount);
            instOrderNo= result.get("no_order");
            String resp_type = result.get("resp_type");
            //银行应答类型:E错误，S成功
            String resp_code = result.get("resp_code");
            //返回响应码
            String resp_msg = result.get("resp_msg");
            //银行返回的应答信息
            if (StringUtils.isBlank(resp_msg)) {

                resp_msg = "银行无返回";
            }
            String ori_resp_code = result.get("ori_resp_code");    //对订单查询结果码
            String ori_resp_type = result.get("ori_resp_type");    //对原订单查询结果状态
            String ori_resp_msg = result.get("ori_resp_msg");        //对原订单查询结果描述
            if ("S".equals(resp_type)) {
                if ("S".equals(ori_resp_type)) {
                    status = "00000";        //交易成功
                } else if ("E".equals(ori_resp_type)) {
                    status = "99999";        //处理失败
                }else if ("R".equals(ori_resp_type)) {
                    status = "55555";            //正在处理中
                }
            } else {
                status = "55555";        //交易查询失败
            }
            LOGGER.info("交易返回码为：" + "[" + resp_code + "]---" + "交易应答类型为:" +
                    "[" + resp_type + "]" + "银行应答信息：" + "[" + resp_msg + "]");
            LOGGER.info("原订单交易返回码为：" + "[" +
                    ori_resp_code + "]---" + "原订单交易应答类型为:"
                    + "[" + ori_resp_type + "]" + "原订单银行应答信息：" + "[" +
                    ori_resp_msg + "]");
            return true;
        } catch (Exception
                ex) {

            LOGGER.error("入款查询异常", ex);
            return true;
        }
    }

    public static Map<String,String> queryVerifySign(Map<String, String> map, String url) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();//map转换list
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            formParams.add(new BasicNameValuePair(key, map.get(key)));
        }
        for (int i = 0; i < formParams.size(); i++) {
            NameValuePair a = formParams.get(i);
            LOGGER.info("发送的信息===key:" + a.getName() +
                            "--------" + "value=" + a.getValue());
        }
        Map<String, String> result = new HashMap<String,
                String>();
        try {
            HttpClient4Util httpClient = new HttpClient4Util();
            byte[] responseBytes = httpClient.doPost(url, null, formParams);

            String jsons = new String(responseBytes);
            LOGGER.info("订单查询返回的字符串----" + jsons);
            Map jsonObject = JsonUtil.fromJson(jsons, Map.class);
            LOGGER.info("获取到的jsonMap---" + jsonObject);
            result = jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

package com.check.Controller;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.check.Controller.bankController.cmbciController;
import com.check.entity.CheckChannel;
import com.check.service.CheckChannelService;
import com.check.util.SendMessageUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class TimetaskController {
	private static final Logger LOGGER= LoggerFactory.getLogger(TimetaskController.class);
	@Resource(name="checkChannelService")
	public CheckChannelService checkchannelService;


	@Scheduled(cron = "0 0/2 * * * ? ")
	public void testTime(){
		System.out.println("测试定时程序开始");
		List<CheckChannel> ccList=checkchannelService.selectAll();
		LOGGER.info("----打日志----"+new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()));
		System.out.println("获得的所有渠道--------");
		for(int i=0;i<ccList.size();i++){
			System.out.println("channelID:---"+ccList.get(i).getPayChannelId());
		}

		for(CheckChannel checkChannel:ccList){
			LOGGER.info("渠道--->"+checkChannel);
			String name=checkChannel.getPayChannelId();
			if(name.equals("CMBCI")) {
//				----------------------------------------
//				LOGGER.error("CMBCI民生I测试开始");
//				String contacts1 ="15110216686"; //checkChannel.getContacts();
//				String[] conStr1 = contacts1.split(",");
//				for (int j = 0; j < conStr1.length; j++) {
//					SendMessageUtil.sendMessage(conStr1[j], "民生i支付测试开始");
//				}
//				-----------------------------------
				int failNum = 0;
				for (int i = 0; i <checkChannel.getNumber(); i++) {	//

					boolean ret = cmbciController.CMBCIsend();
					if (!ret) {
						failNum++;
					}
				}

//				if (failNum >= checkChannel.getThresholdVal() && checkChannel.getTime() < 3) {
//					//发短信
//					String contacts =checkChannel.getContacts(); //;
//					String[] conStr = contacts.split(",");
//					for (int j = 0; j < conStr.length; j++) {
//						SendMessageUtil.sendMessage(conStr[j], "银行：《民生i支付》已超过50%连接与银行服务器、前置机通信发生异常");
//					}
//					int t = checkchannelService.updateSendTime(checkChannel);
//				}
			}
		}
	}
}

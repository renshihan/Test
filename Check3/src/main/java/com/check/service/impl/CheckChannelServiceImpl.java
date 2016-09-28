package com.check.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.dao.SimpleDao;
import com.check.entity.CheckChannel;
import com.check.service.CheckChannelService;
@Service("checkChannelService")
public class CheckChannelServiceImpl implements CheckChannelService{
	
	

	@Resource(name="SimpleDao")
	private SimpleDao simpleDao;
	
	public List<CheckChannel> selectAll() {
		return simpleDao.selectList("checkchannel.mapper.queryAll");
	}
	
	public int updateTime() {
		return simpleDao.update("checkchannel.mapper.updateTime");
	}
	
	public int updateSendTime(CheckChannel checkChannel) {
		return simpleDao.update("checkchannel.mapper.updateSendNum",checkChannel);
	}
	
}

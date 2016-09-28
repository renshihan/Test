package com.check.service;

import java.util.List;

import com.check.entity.CheckChannel;

public interface CheckChannelService {
	public List<CheckChannel> selectAll();
	public int updateTime();
	public int updateSendTime(CheckChannel checkChannel);
}

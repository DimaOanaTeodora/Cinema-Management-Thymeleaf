package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.dto.BroadcastRequest;
import com.backend.cinema.model.Broadcast;

@Component
public class BroadcastMapper {

	private ScheduleMapper scheduleMapper;

	public BroadcastMapper(ScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}

	public Broadcast broadcastRequestToBroadcast(BroadcastRequest broadcastRequest) {

		return new Broadcast(scheduleMapper.scheduleRequestToSchedule(broadcastRequest.getSchedule()));
	}
}

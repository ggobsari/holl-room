package com.hollroom.chatting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequest {
	private String roomName;
	private Integer count;

	private Long sender;
	private Long receiver;
	private Long roomId;
}

package com.hollroom.chatting.service;


import com.hollroom.chatting.domain.dto.*;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.user.entity.UserEntity;

import java.util.List;

public interface ChatService {
	void createChatRoom(ChatRoomRequest room);
	ChatMessageResponse saveMessage(ChatMessageRequest message);
	List<ChatMessageResponse> getChatList(String roomId);
	Boolean isChatroomExist(Long sender, Long receiver);
	List<ChatRoom> getRoomList(Long sender);
	List<UserEntity> getChatUserList(Long id, List<ChatRoom> roomlist, List<ChatMessage> msglist);
	List<ChatMessage> getChatMsgList(List<ChatRoom> roomList);
	ChatRoom getRoom(Long roomId);
	RoommateUserDTO getUser(Long userId);
	ChatRoom getRoomByIds(Long sender, Long receiver);
}

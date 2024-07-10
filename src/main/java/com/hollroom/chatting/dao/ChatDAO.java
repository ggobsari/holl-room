package com.hollroom.chatting.dao;

import com.hollroom.chatting.domain.dto.ChatUserRequest;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.chatting.domain.entity.RoomInfoOfUser;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.user.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface ChatDAO {
    void createChatRoom(ChatRoom room);
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getChatList(String roomId);
    ChatRoom selectRoom(Long roomId);
    Boolean checkRoom(Long sender, Long receiver);
    List<ChatRoom> getRoomList(Long id);
    List<UserEntity> getChatUserList(List<Long> idList);
    List<ChatMessage> getChatMsgList(List<ChatRoom> roomList);
    RoommateUserDTO getUser(Long userId);
    ChatRoom getRoomByIds(Long sender, Long receiver);
}

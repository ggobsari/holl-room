package com.hollroom.chatting.dao;

import com.hollroom.chatting.domain.dto.ChatRoomRequest;
import com.hollroom.chatting.domain.dto.ChatUserRequest;
import com.hollroom.chatting.domain.entity.ChatListItem;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.chatting.domain.entity.RoomInfoOfUser;
import com.hollroom.chatting.repository.ChatMessageRepository;
import com.hollroom.chatting.repository.ChatRoomRepository;
import com.hollroom.chatting.repository.ChatUserRepository;
import com.hollroom.chatting.repository.RoomInfoOfUserRepository;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatDAOImpl implements ChatDAO{
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SqlSessionTemplate sessionTemplate;

    @Override
    public void createChatRoom(ChatRoom room) {
       chatRoomRepository.save(room);
    }

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
//        System.out.println(message);
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getChatList(String roomId) {
        ChatRoom chatRoomEntity = chatRoomRepository.findById(Long.parseLong(roomId)).get();
        List<ChatMessage> chatlist = chatRoomEntity.getChatlist();
        return chatlist;
    }

    @Override
    public ChatRoom selectRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId);
        return room;
    }

    @Override
    public Boolean checkRoom(Long sender, Long receiver) {
        ChatRoom room1 = chatRoomRepository.findBySenderAndReceiver(sender, receiver);
        ChatRoom room2 = chatRoomRepository.findBySenderAndReceiver(receiver, sender);
        return ((room1 != null) || (room2 != null));
    }

    @Override
    public List<ChatRoom> getRoomList(Long myid) {
        System.out.println("### dao / getRomList()");
        List<ChatRoom> roomList = sessionTemplate.selectList("com.hollroom.chatting.selectRoomByMyId", myid);
        for (ChatRoom room : roomList) {
            System.out.println("### dao / room: " + room);
        }
        return roomList;
    }

    @Override
    public List<UserEntity> getChatUserList(List<Long> idList) {
        List<UserEntity> userlist = new ArrayList<>();
        for (Long id : idList) {
            userlist.add(sessionTemplate.selectOne("com.hollroom.chatting.selectUserById", id));
        }
        // 상대방 정보 리스트
        return userlist;
    }

    @Override
    public List<ChatListItem> getChatListItems(List<ChatListItem> chatListItems) {
        System.out.println("### dao / getChatListItems()");
        System.out.println("### dao / chatlistitem: " + chatListItems);
        // 마지막 발신자 정보 목록
        List<ChatListItem> msgInfo = sessionTemplate.selectList("com.hollroom.chatting.selectLastMsg", chatListItems);
        //List<ChatListItem> opponentInfo = sessionTemplate.selectList("com.hollroom.chatting.selectOpp", chatListItems);
        int i = 0;
        for (ChatListItem item : chatListItems) {
//            System.out.println("### dao / getChatListItems() / for / 0: " + i);
//            System.out.println("### getOpponent: " + item.getOpponent());
            item.setLastMessage(msgInfo.get(i).getLastMessage());
            item.setLastDateTime(msgInfo.get(i).getLastDateTime());
            UserEntity opponentInfo = sessionTemplate.selectOne("com.hollroom.chatting.selectUserById", item.getOpponent());
//            System.out.println("### dao / UserEntity - oppInfo: " + opponentInfo);
            item.setUserNickname(opponentInfo.getUserNickname());
            item.setUserImage(opponentInfo.getUserImage());
            i++;
        }
        return chatListItems;
    }

    @Override
    public List<ChatMessage> getChatMsgList(List<ChatRoom> roomList) {
        List<ChatMessage> msglist = sessionTemplate.selectList("com.hollroom.chatting.selectMsgByRoomId", roomList);
        return msglist;
    }

    @Override
    public RoommateUserDTO getUser(Long userId) {
        return sessionTemplate.selectOne("com.hollroom.chatting.selectUser", userId);
    }

    @Override
    public ChatRoom getRoomByIds(Long sender, Long receiver) {
        // @@코드 수정 필요
        ChatRoom room1 = chatRoomRepository.findBySenderAndReceiver(sender, receiver);
        ChatRoom room2 = chatRoomRepository.findBySenderAndReceiver(receiver, sender);
        if (room1 == null) {
            return room2;
        } else {
            return room1;
        }
    }
}

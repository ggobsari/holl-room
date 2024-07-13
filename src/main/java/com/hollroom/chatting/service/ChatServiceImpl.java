package com.hollroom.chatting.service;

import com.hollroom.chatting.dao.ChatDAO;
import com.hollroom.chatting.domain.dto.*;
import com.hollroom.chatting.domain.entity.ChatListItem;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDAO dao;

    @Override
    public void createChatRoom(ChatRoomRequest room) {
        System.out.println("########### service: createChatRoom 실행");
        ModelMapper mapper = new ModelMapper();
        ChatRoom chatRoom = mapper.map(room, ChatRoom.class);
        dao.createChatRoom(chatRoom);
    }

    @Override
    public ChatMessageResponse saveMessage(ChatMessageRequest message) {
        ModelMapper mapper = new ModelMapper();
        ChatMessage chatMessage = mapper.map(message, ChatMessage.class);
        ChatMessageResponse responseMsg = mapper.map(dao.saveMessage(chatMessage), ChatMessageResponse.class);

        return responseMsg;
    }

    @Override
    public List<ChatMessageResponse> getChatList(String roomId) {
        ModelMapper mapper = new ModelMapper();
        return dao.getChatList(roomId).stream()
                .map(entity -> mapper.map(entity, ChatMessageResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isChatroomExist(Long sender, Long receiver) {
        return dao.checkRoom(sender, receiver);
    }

    @Override
    public List<ChatRoom> getRoomList(Long myid) {
//        System.out.println("### service / getRomList()");
        List<ChatRoom> roomlist = dao.getRoomList(myid);

//        for (ChatRoom room : roomlist) {
//            System.out.println("### service / room: " + room);
//        }
        return roomlist;
    }

    @Override
    public List<UserEntity> getChatUserList(Long id, List<ChatRoom> roomlist, List<ChatMessage> msglist) {
        List<Long> idlist = new ArrayList<>();
        for (int i = 0; i < msglist.size(); i++) {
            Long msgSender = Long.parseLong(msglist.get(i).getSender());
            if (id == msgSender) {
                idlist.add(roomlist.get(i).getReceiver());
//                System.out.println("******** idlist.add: " + roomlist.get(i).getReceiver());
            } else {
                idlist.add(msgSender);
//                System.out.println("******** idlist.add: " + msgSender);
            }
        }
        // 상대방 아이디 리스트
        return dao.getChatUserList(idlist);
    }

    @Override
    public List<ChatListItem> getChatListItems(Long myid, List<ChatRoom> roomlist) {
//        System.out.println("### service / getChatListItems()");
        List<ChatListItem> chatListItems = new ArrayList<>();
        // 상대방 아이디 리스트
        int i = 0;
        for (ChatRoom room : roomlist) {
            ChatListItem item = new ChatListItem();
            if (myid == room.getSender()) {
                item.setOpponent(room.getReceiver());
                chatListItems.add(item);
            } else {
                item.setOpponent(room.getSender());
                chatListItems.add(item);
            }
            item.setRoomId(room.getRoomId());
            i++;
        }
//        System.out.println("### service / opponent 세팅 완료");

//        for (ChatListItem item : chatListItems) {
//            System.out.println("### service / opponent: " + item.getOpponent());
//        }

        chatListItems = dao.getChatListItems(chatListItems);
        return chatListItems;
    }
        @Override
    public List<ChatMessage> getChatMsgList(List<ChatRoom> roomList) {
        return dao.getChatMsgList(roomList);
    }

    @Override
    public ChatRoom getRoom(Long roomId) {
        return dao.selectRoom(roomId);
    }

    @Override
    public RoommateUserDTO getUser(Long userId) {
        return dao.getUser(userId);
    }

    @Override
    public ChatRoom getRoomByIds(Long sender, Long receiver) {
        return dao.getRoomByIds(sender, receiver);
    }
}

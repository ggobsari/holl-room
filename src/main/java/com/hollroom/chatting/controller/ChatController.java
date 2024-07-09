package com.hollroom.chatting.controller;

import com.hollroom.chatting.domain.dto.*;
import com.hollroom.chatting.domain.entity.ChatListItem;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.chatting.domain.entity.MessageType;
import com.hollroom.chatting.service.ChatService;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.roommate.service.RoommateService;
import com.hollroom.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;
    private final SimpMessageSendingOperations operations;

//    @MessageMapping("/chat/enterUser")
//    public void startChat(@Payload ChatUserRequest chatUserRequest,
//                          SimpMessageHeaderAccessor headerAccessor) {
//        System.out.println(chatUserRequest);
//
//        //채팅참여 테이블에 인원 추가하기 - db에 저장
////        service.insertChatUser(chatUserRequest);
//
//        headerAccessor.getSessionAttributes().put("userId", chatUserRequest.getUserId());// ???
//        headerAccessor.getSessionAttributes().put("roomId", chatUserRequest.getRoomId());
//
//        ChatMessageRequest msg = new ChatMessageRequest();
//        msg.setRoomId(chatUserRequest.getRoomId());
//        msg.setType(MessageType.CREATE);
//        msg.setMessage(chatUserRequest.getUserId() + "님이 입장하셨습니다.");
//        msg.setSender(chatUserRequest.getUserId() + "");
//        System.out.println("----------------------");
//        System.out.println(msg);
//        System.out.println("----------------------");
////        operations.convertAndSend("/sub/chat/room/"+chatUserRequest.getRoomId(), msg);
//    }

    @MessageMapping("/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatMessageResponse chat(@DestinationVariable("roomId") Long roomId, ChatMessageRequest message) {
        message.setType(MessageType.MESSAGE);
        // 채팅메세지 저장
        ChatMessageResponse chatmessage = service.saveMessage(message);
        return chatmessage;
    }

    // 특정 채팅방 대화내역 불러오기
    @GetMapping("/chat/chatlist")
    @ResponseBody
    public List<ChatMessageResponse> chatlist(@RequestParam("roomId") String roomId, Model model) {
        List<ChatMessageResponse> data =  service.getChatList(roomId);
        return data;
    }

    // 채팅방 열기
    @PostMapping("/chat/room")
    public String chatroomPage(RoommateUserDTO user, ChatRoomRequest room, Model model) {
        System.out.println(room);
        Boolean isRoomExist = service.isChatroomExist(room.getSender(), room.getReceiver());
        if (!isRoomExist) {
            service.createChatRoom(room);
        }
        ChatRoom chatRoom = service.getRoomByIds(room.getSender(), room.getReceiver());
        model.addAttribute("roominfo", chatRoom);
        model.addAttribute("user", user);
        return "chatting/chat_detail";
    }

    // 내 채팅방 목록 불러오기
    @GetMapping("/chat/roomlist")
    public String chatlistPage(Long userId, Model model) {
        List<ChatRoom> roomlist = service.getRoomList(userId);
//        System.out.println("1****** roomlist: " + roomlist);
        List<ChatMessage> msglist = service.getChatMsgList(roomlist);
//        for (ChatMessage msg : msglist) {
//            System.out.println("2****** msglist: " + msg.getMessage());
//        }
        List<UserEntity> userlist = service.getChatUserList(userId, roomlist, msglist);
//        System.out.println("3****** userlist: " + userlist);
//        for (int i = 0; i < msglist.size(); i++) {
//            System.out.println("msg: " +  msglist.get(i).getMessage());
//        }

        // @@코드 수정 필요
        List<ChatListItem> chatlistitems = new ArrayList<>();
        for (int i = 0; i < userlist.size(); i++) {
            ChatListItem chat = new ChatListItem();
            chat.setUserId(userlist.get(i).getId());
            chat.setUserNickname(userlist.get(i).getUserNickname());
            chat.setUserImage(userlist.get(i).getUserImage());
            chat.setRoomId(roomlist.get(i).getRoomId());
            chat.setLastMessage(msglist.get(i).getMessage());
            chat.setLastDateTime(msglist.get(i).getCreateDate());
            chatlistitems.add(chat);
            System.out.println(chat);
        }
        model.addAttribute("chatlistitem", chatlistitems);
        return "chatting/chat_list";
    }

    // 채팅방 열기
    @GetMapping("/chat/room")
    public String chatroomPage(Long roomId, Long userId, Model model) {
        ChatRoom room = service.getRoom(roomId);
        RoommateUserDTO user = service.getUser(userId);
        model.addAttribute("roominfo", room);
        model.addAttribute("user", user);
        return "chatting/chat_detail";
    }
}

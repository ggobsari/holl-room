package com.hollroom.chatting.controller;

import com.hollroom.chatting.domain.dto.*;
import com.hollroom.chatting.domain.entity.ChatListItem;
import com.hollroom.chatting.domain.entity.ChatMessage;
import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.chatting.domain.entity.MessageType;
import com.hollroom.chatting.service.ChatService;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    private static Long myId;

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
    public List<ChatMessageResponse> chatlist(@RequestParam("roomId") String roomId, HttpSession session, Model model) {
        List<ChatMessageResponse> data =  service.getChatList(roomId);
        return data;
    }

    // 채팅방 열기 (룸메이트 게시글과 연결)
    @PostMapping("/chat/room")
    public String chatroomPage(RoommateUserDTO user, ChatRoomRequest room, Model model) {
        System.out.println("######## /chat/room Post");
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
    public String chatlistPage(Long myid, HttpSession session, Model model) {
        // 상대방 아이디
        // 방 아이디
        // 마지막 발신자 - 이미지, 닉네임 / 메세지, 시간
        System.out.println("######## /chat/roomlist Get");
//        if (myId == null) {
//            System.out.println("myId가 null임");
            getMyId(session);
            System.out.println(myid + " =?= " + myId);
//        }
        if (myId == myid) {
            System.out.println(myid + " =?= " + myId);
            System.out.println("myId == userId");
            // 내가 포함된 채팅방 목록 (메세지 내림차순)
            List<ChatRoom> roomlist = service.getRoomList(myid);  //selectRoomBySender
            if (roomlist.size() != 0) {
                for (ChatRoom room : roomlist) {
                    System.out.println("1****** room: " + room);
                }

                // 방 아이디, 상대방 아이디 (메세지 내림차순)
                // 마지막 발신자 정보 (메세지 내림차순)
                List<ChatListItem> chatListItems = service.getChatListItems(myid, roomlist);

                model.addAttribute("chatlistitem", chatListItems);
            }
            return "chatting/chat_list";
        } else {
            System.out.println("myId != userId");
            return "redirect:/roommate/home";
        }
    }

    // 채팅방 열기 (채팅 목록에서 선택)
    @GetMapping("/chat/room")
    public String chatroomPage(Long roomId, Long userId, HttpSession session, Model model) {
        System.out.println("######## /chat/room Get");
//        if (myId == null) {
//            System.out.println("myId가 null임");
            getMyId(session);
//        }
        ChatRoom room = service.getRoom(roomId);
        if ((myId == room.getReceiver()) || (myId == room.getSender())) {
//            System.out.println("채팅 참여자임");
            RoommateUserDTO user = service.getUser(userId);
            model.addAttribute("roominfo", room);
            model.addAttribute("user", user);
            return "chatting/chat_detail";
        } else {
            return "redirect:/roommate/home";
        }
    }

    private static void getMyId(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        myId = user.getId();
    }
}

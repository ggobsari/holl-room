package com.hollroom.chatting.domain.dto;

import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.chatting.domain.entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoOfUserResponse {
    private Long id;
    private ChatUser user;
    private ChatRoom room;
    private Date regDate;
}

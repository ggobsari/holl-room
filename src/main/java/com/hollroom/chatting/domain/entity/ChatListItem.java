package com.hollroom.chatting.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListItem {
    private Long roomId;
    private Long userId;
    private String userNickname;
    private String userImage;
    private String lastMessage;
    private Date lastDateTime;
}

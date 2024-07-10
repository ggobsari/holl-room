package com.hollroom.chatting.domain.dto;

import com.hollroom.chatting.domain.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private Long messageId;
    private Long roomId;
    private String sender;
    private String message;
    private MessageType type;
    private Date createDate;
}

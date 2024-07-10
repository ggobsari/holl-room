package com.hollroom.chatting.domain.dto;

import com.hollroom.chatting.domain.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    private Long roomId;
    private String sender;
    private String message;
    private MessageType type;
}

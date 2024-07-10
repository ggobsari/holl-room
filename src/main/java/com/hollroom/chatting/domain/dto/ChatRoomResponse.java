package com.hollroom.chatting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {
    private Long roomId;
    private String roomName;
    private Integer count;
    private Date createDate;
    private Date modifyDate;
}

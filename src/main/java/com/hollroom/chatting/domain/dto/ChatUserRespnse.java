package com.hollroom.chatting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserRespnse {
    private Long chatUserId;
  //  private List<RoomInfoOfUser> roomlist;
    private String userName;
    private Date createDate;
    private Date modifyDate;
}

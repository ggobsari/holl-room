package com.hollroom.chatting.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
//어떤 유저가 어떤 채팅방에 입장했는지 저장
//유저가 채팅룸에 입장하고 채팅룸에 입장한 유저들을 확인할 수 있다.
//다대다 관계에서 중간 역할을 하는 엔티티
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomInfoOfUser {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ChatUser user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom room;
    @CreatedDate
    private Date regData;

    public RoomInfoOfUser(ChatRoom room, ChatUser user) {
        this.user = user;
        this.room = room;
    }
}

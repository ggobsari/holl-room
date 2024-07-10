package com.hollroom.chatting.repository;


import com.hollroom.chatting.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomId(Long roomId);
    ChatRoom findBySenderAndReceiver(Long sender, Long receiver);
}

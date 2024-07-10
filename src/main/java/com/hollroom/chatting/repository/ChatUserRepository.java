package com.hollroom.chatting.repository;


import com.hollroom.chatting.domain.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

}

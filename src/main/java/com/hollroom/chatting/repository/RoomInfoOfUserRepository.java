package com.hollroom.chatting.repository;

import com.hollroom.chatting.domain.entity.RoomInfoOfUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoOfUserRepository extends JpaRepository<RoomInfoOfUser, Long> {
}

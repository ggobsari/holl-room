package com.hollroom.chatting.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {
	@Id @GeneratedValue
	private Long messageId;
	@ManyToOne
	@JoinColumn(name="room_id")
	private ChatRoom room;
  	private String sender;
    private String message;
   	@Enumerated(EnumType.STRING)
	private MessageType type;
	@CreationTimestamp
	private Date createDate;
}

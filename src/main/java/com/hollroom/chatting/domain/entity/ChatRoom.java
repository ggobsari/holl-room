package com.hollroom.chatting.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoom {
	@Id @GeneratedValue
	private Long roomId;
	private String roomName;
	private Integer count;
	private Long sender;
	private Long receiver;

	@CreationTimestamp
	private Date createDate;
	@UpdateTimestamp
	private Date modifyDate;

	@OneToMany(mappedBy = "room")
	@ToString.Exclude
	private List<RoomInfoOfUser> userlist = new ArrayList<>();

	@OneToMany(mappedBy = "room")
	@ToString.Exclude
	private List<ChatMessage> chatlist = new ArrayList<>();

}

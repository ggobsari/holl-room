package com.hollroom.chatting.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatUser {
	@Id
	@GeneratedValue
	private Long chatUserId;
	private String userName;
	@CreationTimestamp
	private Date createDate;
	@UpdateTimestamp
	private Date modifyDate;

	// 양방향
	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private List<RoomInfoOfUser> roomlist = new ArrayList<>();

	public ChatUser( Long chatUserId,String userName) {
		this.chatUserId = chatUserId;
		this.userName = userName;
	}

}

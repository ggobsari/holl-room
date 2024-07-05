package com.hollroom.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface  RoommateBoardMapper {
    @Select("SELECT COUNT(*) FROM board_roommate")
    long countRoommateBoard();
}

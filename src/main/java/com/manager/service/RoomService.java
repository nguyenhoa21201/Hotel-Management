package com.manager.service;

import com.manager.dto.SearchRoomRequest;
import com.manager.entity.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomService {

    void create(Room room) throws SQLException;

    List<Room> findAllRoom(SearchRoomRequest rq) throws SQLException;

    Room getRoomDetail(String id) throws SQLException;

    void updateRoom(Room room) throws SQLException;
}

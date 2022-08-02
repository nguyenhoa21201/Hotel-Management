package com.manager.DAO;

import com.manager.entity.Room;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RoomDAO {

    public void createRoom(Room room) throws SQLException;

    public List<Room> getAllRoom(Map<String, String> spec) throws SQLException;

    public void updateRoomById(Map<String, String> spec, String id) throws SQLException;

    public void deleteRoom(List<String> ids) throws SQLException;

    public Room getRoomDetail(String id) throws SQLException;
}

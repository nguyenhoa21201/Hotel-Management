package com.manager.serviceImpl;

import com.manager.DAOImpl.RoomDAOImpl;
import com.manager.dto.SearchRoomRequest;
import com.manager.entity.Room;
import com.manager.service.RoomService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomServiceImpl implements RoomService {

    @Override
    public void create(Room room) throws SQLException {
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        roomDAO.createRoom(room);
    }

    @Override
    public List<Room> findAllRoom(SearchRoomRequest rq) throws SQLException {
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Map<String, String> spec = new HashMap<>();
        if (rq != null) {
            if (rq.getName() != null && !rq.getName().isEmpty()) {
                spec.put("name", "'%" + rq.getName() + "%'");
            }
            if (rq.getPrice() != null && !rq.getPrice().isEmpty()) {
                spec.put("price", "'%" + rq.getPrice() + "%'");
            }
            if (rq.getBedNumber() != null && !rq.getBedNumber().isEmpty()) {
                spec.put("bed_number", "'%" + rq.getBedNumber() + "%'");
            }
            if (rq.getPeopleNumber() != null && !rq.getPeopleNumber().isEmpty()) {
                spec.put("people_number", "'%" + rq.getPeopleNumber() + "%'");
            }
            if (rq.getStatus() != null && !rq.getStatus().isEmpty()) {
                spec.put("status", "'%" + rq.getStatus() + "%'");
            }
        }
        return roomDAO.getAllRoom(spec);
    }

    @Override
    public Room getRoomDetail(String id) throws SQLException {
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        return roomDAO.getRoomDetail(id);
    }

    @Override
    public void updateRoom(Room room) throws SQLException {
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Map<String, String> spec = new HashMap<>();
        if (room != null) {
            if (room.getName() != null && !room.getName().isEmpty()) {
                spec.put("name", "'" + room.getName() + "'");
            }
            if (room.getDescription() != null && !room.getDescription().isEmpty()) {
                spec.put("description", "'" + room.getDescription() + "'");
            }
            if (room.getDiscountPrice() != null && !room.getDiscountPrice().isEmpty()) {
                spec.put("discount_price", "'" + room.getDiscountPrice() + "'");
            }
            if (room.getSquare() != null && !room.getSquare().isEmpty()) {
                spec.put("square", "'" + room.getSquare() + "'");
            }
            if (room.getBedNumber() != null && !room.getBedNumber().isEmpty()) {
                spec.put("bed_number", "'" + room.getBedNumber() + "'");
            }
            if (room.getPrice() != null && !room.getPrice().isEmpty()) {
                spec.put("price", "'" + room.getPrice() + "'");
            }
            if (room.getPeopleNumber() != null && !room.getPeopleNumber().isEmpty()) {
                spec.put("people_number", "'" + room.getPeopleNumber() + "'");
            }
            if (room.getStatus() != null && !room.getStatus().isEmpty()) {
                spec.put("status", "'" + room.getStatus() + "'");
            }
            if (room.getImage() != null && !room.getImage().isEmpty()) {
                spec.put("image", "'" + room.getImage() + "'");
            }
        } else {
            return;
        }
        roomDAO.updateRoomById(spec, room.getId());
    }
}

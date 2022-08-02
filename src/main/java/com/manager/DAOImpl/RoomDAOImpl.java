package com.manager.DAOImpl;

import com.manager.DAO.RoomDAO;
import com.manager.config.DatabaseSource;
import com.manager.entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomDAOImpl implements RoomDAO {
    public static DatabaseSource databaseSource = new DatabaseSource();

    @Override
    public void createRoom(Room room) throws SQLException {
        String query = "INSERT INTO `managerhotel`.`room` (`id`, `created_user`, `name`, `description`, `square`, `bed_number`, `people_number`, `price`, `discount_price`, `status`, `is_deleted`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, room.getId());
            prepare.setString(2, "SYSTEM");
            prepare.setString(3, room.getName());
            prepare.setString(4, room.getDescription());
            prepare.setString(5, room.getSquare());
            prepare.setString(6, room.getBedNumber());
            prepare.setString(7, room.getPeopleNumber());
            prepare.setString(8, room.getPrice());
            prepare.setString(9, room.getDiscountPrice());
            prepare.setString(10, room.getStatus());
            prepare.setBoolean(11, false);
            prepare.setString(12, room.getImage());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> getAllRoom(Map<String, String> spec) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "select * from room ";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
            query = query + "where ";
            for(Map.Entry<String, String> entry : spec.entrySet()){
                predicates.add(" " + entry.getKey() + " LIKE "  +entry.getValue() );
            }
            String predicate = String.join(" AND ", predicates);
            query = query + predicate;
        }
        System.out.println(query);
        Connection connection =  databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                Room room = new Room();

                room.setId(rs.getString("id"));
                room.setName(rs.getString("name"));
                room.setDescription(rs.getString("description"));
                room.setSquare(rs.getString("square"));
                room.setBedNumber(rs.getString("bed_number"));
                room.setPeopleNumber(rs.getString("people_number"));
                room.setPrice(rs.getString("price"));
                room.setDiscountPrice(rs.getString("discount_price"));
                room.setStatus(rs.getString("status"));
                room.setImage(rs.getString("image"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public void updateRoomById(Map<String, String> spec, String id) throws SQLException {
        String query = "UPDATE `managerhotel`.`room`";
        List<String> predicates =  new ArrayList<>();
        if(!spec.isEmpty()){
            query = query + "SET";
            for(Map.Entry<String, String> entry : spec.entrySet()){
                predicates.add(" " + entry.getKey() + "=" + entry.getValue());
            }
            String predicate = String.join(" , ", predicates);
            query = query + predicate + " where id = " + "'" +  id + "'";
        } else {
            return;
        }
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(List<String> ids) throws SQLException {
//        String query = "update room set is_deleted = ? where id in ?";
//        Connection connection = databaseSource.getDatasource();
//        PreparedStatement prepare = connection.prepareStatement(query);
//        try {
//            prepare.setBoolean(1, false);
//            ResultSet rs = prepare.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Room getRoomDetail(String id) throws SQLException {
        String query = "select * from room where id = ?";
        Room room = new Room();
        Connection connection = databaseSource.getDatasource();
        PreparedStatement prepare = connection.prepareStatement(query);
        try {
            prepare.setString(1, id);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                room.setId(rs.getString("id"));
                room.setName(rs.getString("name"));
                room.setDescription(rs.getString("description"));
                room.setSquare(rs.getString("square"));
                room.setBedNumber(rs.getString("bed_number"));
                room.setPeopleNumber(rs.getString("people_number"));
                room.setPrice(rs.getString("price"));
                room.setDiscountPrice(rs.getString("discount_price"));
                room.setStatus(rs.getString("status"));
                room.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
}

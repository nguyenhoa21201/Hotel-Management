package com.manager.ctrl;

import com.manager.dto.SearchRoomRequest;
import com.manager.entity.Room;
import com.manager.serviceImpl.RoomServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet( {"/staff", "/order_room", "/order_service"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class StaffCtrl extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RoomServiceImpl roomService = new RoomServiceImpl();
        String userName = session.getAttribute("username").toString();
        req.setAttribute("userName",userName);
        String url = req.getServletPath();
        if (url.equalsIgnoreCase("/staff")){
            req.setAttribute("rooms", roomService.findAllRoom(new SearchRoomRequest()));
            req.getRequestDispatcher("/views/staff/room_list.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userName = session.getAttribute("username").toString();
        req.setAttribute("userName",userName);
        String uri = req.getServletPath();
        if (uri.endsWith("/order_room")){
            session.setAttribute("customerIdOrderRoom",req.getParameter("customerId"));
            resp.sendRedirect("/rooms");
        }
        if(uri.endsWith("/order_service")){
            session.setAttribute("customerIdOrderService", req.getParameter("customerId"));
            resp.sendRedirect("/search_service");
        }
    }
}

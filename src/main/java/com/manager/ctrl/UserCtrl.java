package com.manager.ctrl;

import com.manager.config.StringUtil;
import com.manager.dto.DetailOrder;
import com.manager.dto.SearchRoomRequest;
import com.manager.dto.SearchServiceRequest;
import com.manager.entity.*;
import com.manager.serviceImpl.RoomServiceImpl;
import com.manager.serviceImpl.StaffServiceImpl;
import com.manager.serviceImpl.UserServiceImpl;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet({"/user/add-to-cart", "", "/rooms", "/room_detail", "/insert_room", "/update_room",
        "/user/create_order", "/staff/create_order", "/update_order", "/update_order_detail", "/order_list",
        "/user/order_list", "/insert_service", "/create_OrderDetail"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserCtrl extends HttpServlet {
    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public UserCtrl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("username") != null) {
            String userName = session.getAttribute("username").toString();
            req.setAttribute("userName", userName);
        }
        String uri = req.getServletPath();
        RoomServiceImpl roomService = new RoomServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        try {
            if (uri.isEmpty()) {
                req.setAttribute("rooms", roomService.findAllRoom(new SearchRoomRequest()));
                req.setAttribute("services", staffService.findAllService(new SearchServiceRequest()));
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
            if (uri.equalsIgnoreCase("/rooms")) {
                SearchRoomRequest rq = new SearchRoomRequest();
                if (session.getAttribute("roomRequest") != null) {
                    rq = (SearchRoomRequest) session.getAttribute("roomRequest");
                }
                req.setAttribute("rooms", roomService.findAllRoom(rq));
//                session.setAttribute("customerId", req.getParameter("customerId"));
                req.getRequestDispatcher("/views/staff/room_list.jsp").forward(req, resp);
                session.removeAttribute("rooms");
            }
            if (uri.equalsIgnoreCase("/room_detail")) {
                detailRoomGet(req, resp);
//                req.getRequestDispatcher("/views/web/room_detail.jsp").forward(req, resp);
            }
            if (uri.equalsIgnoreCase("/insert_room")) {
                req.getRequestDispatcher("/views/staff/createRoom.jsp").forward(req, resp);
            }
            if (uri.equalsIgnoreCase("/update_room")) {
                detailRoomGet(req, resp);
                session.removeAttribute("roomDetail");
            }
            if (uri.equalsIgnoreCase("/user/add-to-cart")) {
                String msg = "Đặt phòng thành công";
                req.setAttribute("msg", msg);

                req.getRequestDispatcher("/views/web/room_detail.jsp").forward(req, resp);
            }
            if (uri.endsWith("/create_order")) {
                createOrder(req, resp, session);
            }
            if (uri.equalsIgnoreCase("/order_list")) {
                getListOrder(req, resp, session);
            }
            if (uri.equalsIgnoreCase("/insert_service")) {
                req.getRequestDispatcher("/views/staff/insert_service.jsp").forward(req, resp);
            }
            if (uri.equalsIgnoreCase("/update_order")) {
                getListOrder(req, resp, session);
            }
            if (uri.endsWith("/user/order_list")) {
                detailOrderUser(req,resp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RoomServiceImpl roomService = new RoomServiceImpl();
        if (session.getAttribute("username") != null) {
            String userName = session.getAttribute("username").toString();
            req.setAttribute("userName", userName);
        }

        String uri = req.getServletPath();
        try {
            if (uri.equalsIgnoreCase("/rooms")) {
                searchRoom(req, resp, session);
            }
            if (uri.equalsIgnoreCase("/insert_room")) {
                insertRoom(req, resp);
            }
            if (uri.equalsIgnoreCase("/user/add-to-cart")) {
                String idroom = req.getParameter("idroom");
                String msg = "Đặt phòng thành công";
                req.setAttribute("msg", msg);
                resp.sendRedirect("");
                return;
            }
            if (uri.equalsIgnoreCase("/update_room")) {
                updateRoom(req, resp);
            }
            if (uri.endsWith("staff/create_order")) {
                createOrder(req, resp, session);
            }
            if (uri.endsWith("user/create_order")) {
                createOrder(req, resp, session);
            }
            if (uri.endsWith("/update_order")) {
                updateOrder(req, resp);
            }

            if (uri.equalsIgnoreCase("/update_order_detail")) {
                updateOrderDetail(req, resp);
            }
            if (uri.equalsIgnoreCase("/insert_service")) {
                insertService(req, resp);
            }
            if (uri.endsWith("/create_OrderDetail")) {
                createOrderDetail(req, resp);
            }
            if (uri.endsWith("/order_list")) {
                searchOrder(req, resp, session);
            }
        } catch (ExportException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void searchOrder(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException{
        if(req.getParameter("statusOrder") !=null){
            session.setAttribute("statusOrder", req.getParameter("statusOrder") );
        }
         if(req.getParameter("orderType") !=null){
            session.setAttribute("orderType", req.getParameter("orderType") );
        }
         resp.sendRedirect("/order_list");
        
    }

    private void searchRoom(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        RoomServiceImpl roomService = new RoomServiceImpl();
        SearchRoomRequest rq = new SearchRoomRequest();
        if (req.getParameter("searchRoomByName") != null) {
            rq.setName(String.valueOf(req.getParameter("searchRoomByName")));
        }
        if (req.getParameter("searchRoomByPrice") != null) {
            rq.setPrice(String.valueOf(req.getParameter("searchRoomByPrice")));
        }
        if (req.getParameter("searchRoomByBed") != null) {
            rq.setBedNumber(String.valueOf(req.getParameter("searchRoomByBed")));
        }
        if (req.getParameter("searchRoomByPeople") != null) {
            rq.setPeopleNumber(String.valueOf(req.getParameter("searchRoomByPeople")));
        }
        if (req.getParameter("searchRoomByStatus") != null) {
            rq.setStatus(String.valueOf(req.getParameter("searchRoomByStatus")));
        }
        session.setAttribute("roomRequest", rq);
        resp.sendRedirect("/rooms");
        session.removeAttribute("roomRequest");
    }

    private void insertRoom(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        RoomServiceImpl roomService = new RoomServiceImpl();
        Room room = new Room();
        if (req.getParameter("roomName") != null) {
            room.setName(String.valueOf(req.getParameter("roomName")));
        }
        if (req.getParameter("price") != null) {
            room.setPrice(String.valueOf(req.getParameter("price")));
        }
        if (req.getParameter("discountPrice") != null) {
            room.setDiscountPrice(String.valueOf(req.getParameter("discountPrice")));
        }
        if (req.getParameter("square") != null) {
            room.setSquare(String.valueOf(req.getParameter("square")));
        }
        if (req.getParameter("bedNumber") != null) {
            room.setBedNumber(String.valueOf(req.getParameter("bedNumber")));
        }
        if (req.getParameter("peopleNumber") != null) {
            room.setPeopleNumber(String.valueOf(req.getParameter("peopleNumber")));
        }
        if (req.getParameter("description") != null) {
            room.setDescription(String.valueOf(req.getParameter("description")));
        }
        if (req.getParameter("status") != null) {
            room.setStatus(String.valueOf(req.getParameter("status")));
        }
        if (req.getParameter("status") != null) {
            room.setStatus(String.valueOf(req.getParameter("status")));
        }
        String imagePath = uploadFile(req, resp);
        if (imagePath != null && !imagePath.isEmpty()) {
            room.setImage(imagePath);
        }
        room.setId(UUID.randomUUID().toString());
        room.setIsDeleted(Boolean.FALSE);
        roomService.create(room);
        resp.sendRedirect("/rooms");
    }

    private void detailRoomGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        Room room = new Room();
        RoomServiceImpl roomService = new RoomServiceImpl();
        String id = req.getParameter("idroom");
        String url = req.getServletPath();
        // FIX value
//        String id = "1";
        room = roomService.getRoomDetail(id);
        req.setAttribute("roomDetail", room);
        if (url.equalsIgnoreCase("/room_detail")) {
            req.getRequestDispatcher("/views/web/room_detail.jsp").forward(req, resp);
        }
        if (url.equalsIgnoreCase("/update_room")) {
            req.getRequestDispatcher("views/staff/updateRoom.jsp").forward(req, resp);
        }

    }

    private void updateRoom(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        RoomServiceImpl roomService = new RoomServiceImpl();
        Room room = new Room();
        if (req.getParameter("name") != null) {
            room.setName(String.valueOf(req.getParameter("name")));
        }
        if (req.getParameter("price") != null) {
            room.setPrice(String.valueOf(req.getParameter("price")));
        }
        if (req.getParameter("discountPrice") != null) {
            room.setDiscountPrice(String.valueOf(req.getParameter("discountPrice")));
        }
        if (req.getParameter("square") != null) {
            room.setSquare(String.valueOf(req.getParameter("square")));
        }
        if (req.getParameter("bedNumber") != null) {
            room.setBedNumber(String.valueOf(req.getParameter("bedNumber")));
        }
        if (req.getParameter("peopleNumber") != null) {
            room.setPeopleNumber(String.valueOf(req.getParameter("peopleNumber")));
        }
        String imagePath = uploadFile(req, resp);
        if (imagePath != null && !imagePath.isEmpty()) {
            room.setImage(imagePath);
        }
        if (req.getParameter("description") != null) {
            room.setDescription(String.valueOf(req.getParameter("description")));
        }
        if (req.getParameter("status") != null) {
            room.setStatus(String.valueOf(req.getParameter("status")));
        }
        String roomId = req.getParameter("roomId");
        room.setId(roomId);
        roomService.updateRoom(room);
        resp.sendRedirect("/rooms");
    }

    private String uploadFile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Part part = request.getPart("fileimage");
        if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
            String realPath = request.getServletContext().getRealPath("/images");
            String nameFile = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!Files.exists(Paths.get(realPath))) {
                Files.createDirectory(Paths.get(realPath));
            }
            part.write(realPath + "/" + nameFile);
            return nameFile;
        }
        return null;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public File getFolderUpload(HttpServletRequest req) {
        String path = "views/image/room";
        String imagePath = req.getServletContext().getRealPath(path);
        File folderUpload = new File(imagePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        System.out.println(imagePath);
        return folderUpload;
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        String customerId = "";
        Boolean checkStaff = false;
        UserServiceImpl userService = new UserServiceImpl();
        if (session.getAttribute("customerIdOrderRoom") != null || session.getAttribute("customerIdOrderService") != null) {
            checkStaff = true;
            if (session.getAttribute("customerIdOrderRoom") != null) {
                customerId = session.getAttribute("customerIdOrderRoom").toString();
            } else {
                customerId = session.getAttribute("customerIdOrderService").toString();
            }

        }
        if (session.getAttribute("role") != null && session.getAttribute("role").toString().equalsIgnoreCase("USER")) {
            User user = userService.findByUserName(session.getAttribute("username").toString());
            if (user.getCustomerId() != null && !user.getCustomerId().isEmpty()) {
                Customer customer = userService.findCustomerById(user.getCustomerId());
                customerId = customer.getId();
            } else {
                request.setAttribute("message", "Không có quyền");
                request.getRequestDispatcher("").forward(request, response);
                return;
            }
        }

        Order order = new Order();
        Order searchO = new Order();
        searchO.setCustomerId(customerId);

        List<Order> orders = userService.getAllOrder(searchO);
        Boolean updateRoom = false;
        Boolean updateService = false;

        if (request.getParameter("roomId") != null && order != null) {
            for (Order o : orders) {
                if (o.getOrderType().equalsIgnoreCase("0") && o.getStatus().equalsIgnoreCase("pending")) {
                    updateRoom = true;
                    order = o;
                }
            }
        } else {
            for (Order o : orders) {
                if (o.getOrderType().equalsIgnoreCase("1") && o.getStatus().equalsIgnoreCase("pending")) {
                    updateService = true;
                    order = o;
                }
            }
        }
        if (updateRoom.equals(false) && updateService.equals(false)) {
            order.setCustomerId(customerId);
            order.setStatus("pending");
            order.setId(String.valueOf(UUID.randomUUID()));

            if (request.getParameter("roomId") != null) {
                order.setOrderType("0");
            } else {
                order.setOrderType("1");
            }
            userService.createOrder(order);
        }
        List<OrderDetails> orderDetails = new ArrayList<>();

        OrderDetails details = new OrderDetails();
        if (Integer.valueOf(request.getParameter("amount")) < 1) {
            request.setAttribute("message", "Số lượng phải lớn hơn 0");
            if (checkStaff) {
                response.sendRedirect("update_order?orderId=" + order.getId());
            } else {
                response.sendRedirect("/room_detail?idroom=" + request.getParameter("roomId"));
            }
            return;
        }

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId(order.getId());
        orderDetails1.setRefId(request.getParameter("refId"));
        List<OrderDetails> orderDetailsSearch = userService.getAllOrderDetail(orderDetails1);
        StaffServiceImpl staffService = new StaffServiceImpl();
        if (!orderDetailsSearch.isEmpty()) {
            List<Service> services = staffService.findAllService(new SearchServiceRequest());
            for (int i = 0; i < services.size(); i++) {
                if (orderDetailsSearch.get(0).getRefId().equalsIgnoreCase(services.get(i).getId())) {
                    if (Integer.valueOf(request.getParameter("amount")) > Integer.valueOf(services.get(i).getAmount())) {
                        request.setAttribute("message", "Số lượng sp không hợp lệ");
                        if (checkStaff) {
                            response.sendRedirect("update_order?orderId=" + order.getId());
                        } else {
                            response.sendRedirect("/room_detail?idroom=" + request.getParameter("roomId"));
                        }
                        return;
                    }
                    break;
                }
            }
            if (orderDetailsSearch.get(0).getRefType().equalsIgnoreCase("0")) {
                if (checkStaff) {
                    response.sendRedirect("update_order?orderId=" + order.getId());
                } else {
                    response.sendRedirect("/room_detail?idroom=" + request.getParameter("roomId"));
                }
                session.removeAttribute("customerIdOrderRoom");
                session.removeAttribute("customerIdOrderService");
                return;
            }
            orderDetailsSearch.get(0).setAmount(String.valueOf(Integer.valueOf(request.getParameter("amount")) + Integer.valueOf(orderDetailsSearch.get(0).getAmount())));
            userService.updateOrderDetail(orderDetailsSearch.get(0));
            session.removeAttribute("customerIdOrderRoom");
            session.removeAttribute("customerIdOrderService");
            if (checkStaff) {
                response.sendRedirect("update_order?orderId=" + order.getId());
                return;
            } else {
                response.sendRedirect("/room_detail?idroom=" + request.getParameter("roomId"));
                return;
            }
        } else {
            details.setAmount(request.getParameter("amount"));
            details.setUnit(request.getParameter("unit"));
            details.setRefType(request.getParameter("refType"));
            details.setRefId(request.getParameter("refId"));
            details.setOrderId(order.getId());
            details.setPriceRef(Double.valueOf(request.getParameter("priceRef")));
            details.setNameRef(request.getParameter("nameRef"));
            details.setId(String.valueOf(UUID.randomUUID()));
            orderDetails.add(details);
            userService.createOrderDetail(orderDetails);
            session.removeAttribute("customerIdOrderRoom");
            session.removeAttribute("customerIdOrderService");
            if (checkStaff) {
                response.sendRedirect("/update_order?orderId=" + order.getId());
                return;
            } else {
                response.sendRedirect("/room_detail?idroom=" + request.getParameter("roomId"));
                return;
            }
        }
    }

    private void getListOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        List<DetailOrder> detailOrders = new ArrayList<>();
        UserServiceImpl userService = new UserServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        RoomServiceImpl roomService = new RoomServiceImpl();
        String url = request.getServletPath();
        
        Order order = new Order();
        List<Order> orders = new ArrayList<>();
       
          if(session.getAttribute("statusOrder") != null){
            order.setStatus(session.getAttribute("statusOrder").toString());
        }
          if(session.getAttribute("orderType") != null){
          order.setOrderType(session.getAttribute("orderType").toString());
        }
        if (request.getParameter("orderId") != null) {
            order.setId(request.getParameter("orderId"));
            order = userService.getOrderById(order.getId());
            orders.add(order);
        } else if (request.getParameter("customerId") != null) {
            order.setCustomerId(request.getParameter("customerId"));
            orders = userService.getAllOrder(order);
        } else {
            orders = userService.getAllOrder(order);
        }
        session.removeAttribute("statusOrder");
          session.removeAttribute("orderType");
        for (Order order1 : orders) {
            DetailOrder detailOrder = new DetailOrder();
            if (order1.getOrderType().equalsIgnoreCase("0")) {
                detailOrder.setOrderName("Đặt phòng");
            } else {
                detailOrder.setOrderName("Dịch vụ");
            }
            detailOrder.setId(order1.getId());
            detailOrder.setOrderType(order1.getOrderType());
            detailOrder.setOrderDetails(userService.getOrderDetailByOrderId(order1.getId()));
            detailOrder.setStatus(order1.getStatus());
            Customer customer = userService.findCustomerById(order1.getCustomerId());
            detailOrder.setCustomerName(StringUtil.checkValidString(customer.getName()));
            detailOrder.setCustomerPhone(StringUtil.checkValidString(customer.getPhone()));
            detailOrders.add(detailOrder);
        }
        request.setAttribute("detailOrders", detailOrders);

        if (request.getParameter("orderId") != null && url.endsWith("/update_order")) {
            List<Service> listService = staffService.findAllService(new SearchServiceRequest());
            List<Room> listRoom = roomService.findAllRoom(new SearchRoomRequest());
            request.setAttribute("listRooms", listRoom);
            request.setAttribute("listService", listService);
            request.getRequestDispatcher("/views/staff/update_order.jsp").forward(request, response);
        }
        if (url.equalsIgnoreCase("/order_list")) {
            request.getRequestDispatcher("/views/staff/list_Order.jsp").forward(request, response);
        }


    }

    private void updateOrder(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        UserServiceImpl userService = new UserServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        RoomServiceImpl roomService = new RoomServiceImpl();
        if (req.getParameter("orderId") == null) {
            req.setAttribute("message", "Chưa chọn order nào");
            req.getRequestDispatcher("/views/staff/list_Order.jsp").forward(req, resp);
            return;
        }
        Order order = userService.getOrderById(req.getParameter("orderId"));
        if (order == null) {
            req.setAttribute("message", "Chưa chọn order nào");
            req.getRequestDispatcher("").forward(req, resp);
            return;
        }
        if (req.getParameter("statusOrder") != null) {
            if (order.getStatus().equalsIgnoreCase("cancel") || order.getStatus().equalsIgnoreCase("success")) {
                req.setAttribute("message", "không chỉnh sủa order này");
                req.getRequestDispatcher("/views/staff/list_Order.jsp").forward(req, resp);
                return;
            }
            if (order.getStatus().equalsIgnoreCase("pending") && (req.getParameter("statusOrder").equalsIgnoreCase("confirm")
                    || req.getParameter("statusOrder").equalsIgnoreCase("success"))) {
                if (order.getOrderType().equalsIgnoreCase("1")) {
                    List<OrderDetails> orderDetails = userService.getOrderDetailByOrderId(order.getId());
                    List<Service> services = staffService.findAllService(new SearchServiceRequest());
                    for (OrderDetails details : orderDetails) {
                        for (int i = 0; i < services.size(); i++) {
                            if (details.getRefId().equalsIgnoreCase(services.get(i).getId())) {
                                services.get(i).setAmount(String.valueOf(Integer.valueOf(services.get(i).getAmount()) - Integer.valueOf(details.getAmount())));
                                staffService.updateService(services.get(i));
                            }
                        }
                    }
                } else if (order.getOrderType().equalsIgnoreCase("0")) {
                    List<OrderDetails> orderDetails = userService.getOrderDetailByOrderId(order.getId());
                    List<Room> rooms = roomService.findAllRoom(new SearchRoomRequest());
                    for (OrderDetails details : orderDetails) {
                        for (int i = 0; i < rooms.size(); i++) {
                            if (details.getRefId().equalsIgnoreCase(rooms.get(i).getId())) {
                                rooms.get(i).setStatus("0");
                                roomService.updateRoom(rooms.get(i));
                            }
                        }
                    }
                }
            }
            if ((!order.getStatus().equalsIgnoreCase("pending") && req.getParameter("statusOrder").equalsIgnoreCase("pending"))
                    || req.getParameter("statusOrder").equalsIgnoreCase("cancel") ) {
                if (order.getOrderType().equalsIgnoreCase("1")) {
                    List<OrderDetails> orderDetails = userService.getOrderDetailByOrderId(order.getId());
                    List<Service> services = staffService.findAllService(new SearchServiceRequest());
                    for (OrderDetails details : orderDetails) {
                        for (int i = 0; i < services.size(); i++) {
                            if (details.getRefId().equalsIgnoreCase(services.get(i).getId())) {
                                services.get(i).setAmount(String.valueOf(Integer.valueOf(services.get(i).getAmount()) + Integer.valueOf(details.getAmount())));
                                staffService.updateService(services.get(i));
                            }
                        }
                    }
                } else if (order.getOrderType().equalsIgnoreCase("0")) {
                    List<OrderDetails> orderDetails = userService.getOrderDetailByOrderId(order.getId());
                    List<Room> rooms = roomService.findAllRoom(new SearchRoomRequest());
                    for (OrderDetails details : orderDetails) {
                        for (int i = 0; i < rooms.size(); i++) {
                            if (details.getRefId().equalsIgnoreCase(rooms.get(i).getId())) {
                                rooms.get(i).setStatus("1");
                                roomService.updateRoom(rooms.get(i));
                            }
                        }
                    }
                }
            }
            order.setStatus(String.valueOf(req.getParameter("statusOrder")));
        }
        userService.updateOrder(order);

        HttpSession session = req.getSession();
        if (session.getAttribute("role").toString().equalsIgnoreCase("user")){
            resp.sendRedirect("/user/order_list");
        }else {
            resp.sendRedirect("update_order?orderId=" + order.getId());
        }
    }

    private void createOrderDetail(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException, SQLException {

        UserServiceImpl userService = new UserServiceImpl();
        List<OrderDetails> orderDetails = new ArrayList<>();

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setOrderId(request.getParameter("orderId"));
        orderDetails1.setRefId(request.getParameter("refId"));
        List<OrderDetails> orderDetailsSearch = userService.getAllOrderDetail(orderDetails1);
        StaffServiceImpl staffService = new StaffServiceImpl();
        if (!orderDetailsSearch.isEmpty()) {

            List<Service> services = staffService.findAllService(new SearchServiceRequest());
            for (int i = 0; i < services.size(); i++) {
                if (orderDetailsSearch.get(0).getRefId().equalsIgnoreCase(services.get(i).getId())) {
                    if ((Integer.valueOf(request.getParameter("amount")) + Integer.valueOf(orderDetailsSearch.get(0).getAmount())) > Integer.valueOf(services.get(i).getAmount())) {
                        request.setAttribute("message", "Số lượng sp không hợp lệ");
                        response.sendRedirect("update_order?orderId=" + orderDetails1.getOrderId());
                        return;
                    }
                    break;
                }
            }

            if (orderDetailsSearch.get(0).getRefType().equalsIgnoreCase("0")) {
                response.sendRedirect("update_order?orderId=" + request.getParameter("orderId"));
                return;
            }
            orderDetailsSearch.get(0).setAmount(String.valueOf(Integer.valueOf(request.getParameter("amount")) + Integer.valueOf(orderDetailsSearch.get(0).getAmount())));
            userService.updateOrderDetail(orderDetailsSearch.get(0));
            response.sendRedirect("update_order?orderId=" + request.getParameter("orderId"));
            return;
        } else {

            OrderDetails details = new OrderDetails();
            if (request.getParameter("amount") != null) {
                if (Integer.valueOf(request.getParameter("amount")) < 1) {
                    request.setAttribute("message", "Số lượng phải lớn hơn 0");
                    request.getRequestDispatcher("/views/staff/update_order.jsp").forward(request, response);
                    return;
                }
            }

            details.setOrderId(request.getParameter("orderId"));
            details.setAmount(request.getParameter("amount"));
            details.setUnit(request.getParameter("unit"));
            details.setRefType(request.getParameter("refType")); //kiểu dịch vụ mặc địch là 1
            details.setRefId(request.getParameter("refId")); //id của dịch vụ
            details.setPriceRef(Double.valueOf(request.getParameter("priceRef")));
            details.setNameRef(request.getParameter("nameRef"));
            details.setId(String.valueOf(UUID.randomUUID()));

            List<Service> services = staffService.findAllService(new SearchServiceRequest());
            for (int i = 0; i < services.size(); i++) {
                if (details.getRefId().equalsIgnoreCase(services.get(i).getId())) {
                    if (Integer.valueOf(details.getAmount()) > Integer.valueOf(services.get(i).getAmount())) {
                        request.setAttribute("message", "Số lượng sp không hợp lệ");
                        request.getRequestDispatcher("/views/staff/update_order.jsp").forward(request, response);
                        return;
                    }
                    break;
                }
            }
            orderDetails.add(details);
            userService.createOrderDetail(orderDetails);
            response.sendRedirect("update_order?orderId=" + details.getOrderId());
        }
    }

    private void updateOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        UserServiceImpl userService = new UserServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        if (req.getParameter("orderId") == null) {
            req.setAttribute("message", "Chưa chọn order nào");
            req.getRequestDispatcher("/views/staff/update_order.jsp").forward(req, resp);
            return;
        }
        Order order = userService.getOrderById(req.getParameter("orderId"));
        if (order == null && !order.getStatus().equalsIgnoreCase("pending")) {
            req.setAttribute("message", "không thể sửa bản ghi này");
            req.getRequestDispatcher("/views/staff/update_order.jsp").forward(req, resp);
            return;
        }
        OrderDetails orderDetails = userService.getOrderDetailById(req.getParameter("orderDetailId"));
        if (orderDetails == null) {
            req.setAttribute("message", "Chưa có trong order");
            req.getRequestDispatcher("/views/staff/update_order.jsp").forward(req, resp);
            return;
        }
        if (Integer.valueOf(req.getParameter("amount")) < 1) {
            userService.deleteOrderDetailById(orderDetails.getId());
            if (session.getAttribute("role").toString().equalsIgnoreCase("user")){
                resp.sendRedirect("/user/order_list");
                return;
            }else {
                resp.sendRedirect("/order_list");
                return;
            }

        }
        List<Service> services = staffService.findAllService(new SearchServiceRequest());
        for (int i = 0; i < services.size(); i++) {
            if (orderDetails.getRefId().equalsIgnoreCase(services.get(i).getId())) {
                Integer value = Integer.valueOf(orderDetails.getAmount());
                if (value <= Integer.valueOf(req.getParameter("amount"))) {
                    value = 0;
                } else {
                    value = Integer.valueOf(req.getParameter("amount")) - value;
                }
                if ((Integer.valueOf(req.getParameter("amount")) + value) > Integer.valueOf(services.get(i).getAmount())) {
                    req.setAttribute("message", "Số lượng sp không hợp lệ");
                    req.getRequestDispatcher("/views/staff/list_Order.jsp").forward(req, resp);
                    return;
                }
                break;
            }
        }
        orderDetails.setAmount(req.getParameter("amount"));
        userService.updateOrderDetail(orderDetails);
        if (session.getAttribute("role").toString().equalsIgnoreCase("user")){
            resp.sendRedirect("/order_list");
            return;
        }else {
            resp.sendRedirect("update_order?orderId=" + order.getId());
            return;
        }

    }


    private void insertService(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        StaffServiceImpl staffService = new StaffServiceImpl();
        Service service = new Service();
        if (req.getParameter("serviceName") != null) {
            service.setName(String.valueOf(req.getParameter("serviceName")));
        }
        if (req.getParameter("price") != null) {
            service.setPrice(Double.valueOf(req.getParameter("price")));
        }
        if (req.getParameter("amount") != null) {
            service.setAmount(req.getParameter("amount"));
        }
        if (req.getParameter("description") != null) {
            service.setDescription(req.getParameter("description"));
        }
        if (req.getParameter("unit") != null) {
            service.setUnit(req.getParameter("unit"));
        }
        String imagePath = uploadFileService(req, resp);
        if (imagePath != null && !imagePath.isEmpty()) {
            service.setImage(imagePath);
        }
        service.setId(UUID.randomUUID().toString());
        service.setIsDeleted(Boolean.FALSE);
        staffService.create(service);
        resp.sendRedirect("/search_service");
    }

    private void updateService(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        StaffServiceImpl staffService = new StaffServiceImpl();
        Service service = new Service();
        if (req.getParameter("name") != null) {
            service.setName(req.getParameter("name"));
        }
        if (req.getParameter("description") != null) {
            service.setDescription(req.getParameter("description"));
        }
        if (req.getParameter("price") != null) {
            service.setPrice(Double.valueOf(req.getParameter("price")));
        }
        String imagePath = uploadFileService(req, resp);
        if (imagePath != null && !imagePath.isEmpty()) {
            service.setImage(imagePath);
        }
        if (req.getParameter("amount") != null) {
            service.setAmount(String.valueOf(req.getParameter("amount")));
        }
        if (req.getParameter("unit") != null) {
            service.setUnit(String.valueOf(req.getParameter("unit")));
        }
        String serviceId = req.getParameter("serviceId");
        service.setId(serviceId);
        staffService.updateService(service);
        resp.sendRedirect("/search_service");
    }

    private String uploadFileService(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Part part = request.getPart("fileimage");
        if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
            String realPath = request.getServletContext().getRealPath("/images");
            String nameFile = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!Files.exists(Paths.get(realPath))) {
                Files.createDirectory(Paths.get(realPath));
            }
            part.write(realPath + "/" + nameFile);
            return nameFile;
        }
        return null;
    }

    private String extractFileNameService(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public File getFolderUploadService(HttpServletRequest req) {
        String path = "views/image/service";
        String imagePath = req.getServletContext().getRealPath(path);
        File folderUpload = new File(imagePath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        System.out.println(imagePath);
        return folderUpload;
    }

    private void detailBillUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = new UserServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        Customer customer = new Customer();
        if (session.getAttribute("username") != null ) {
            User user = userService.findByUserName(session.getAttribute("username").toString());
            if (user.getCustomerId() != null && !user.getCustomerId().isEmpty()) {
                customer = userService.findCustomerById(user.getCustomerId());
            } else {
                request.setAttribute("message", "Không có quyền");
                request.getRequestDispatcher("").forward(request, response);
                return;
            }
            Bill bill = new Bill();
            bill.setCustomerId(customer.getId());
            bill.setStatus("SUCCESS");
            staffService.getAllBill(bill);
        }
    }

    private void detailOrderUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        UserServiceImpl userService = new UserServiceImpl();
        Customer customer = new Customer();
        if (session.getAttribute("username") != null ) {
            User user = userService.findByUserName(session.getAttribute("username").toString());
            if (user.getCustomerId() != null && !user.getCustomerId().isEmpty()) {
                customer = userService.findCustomerById(user.getCustomerId());
            } else {
                request.setAttribute("message", "Không có quyền");
                request.getRequestDispatcher("").forward(request, response);
                return;
            }
            Order order = new Order();
            order.setCustomerId(customer.getId());
            order.setStatus("pending");

            List<Order> orders = userService.getAllOrder(order);
            List<DetailOrder> detailOrders = new ArrayList<>();
            for (Order order1 : orders) {
                DetailOrder detailOrder = new DetailOrder();
                if (order1.getOrderType().equalsIgnoreCase("0")) {
                    detailOrder.setOrderName("Đặt phòng");
                } else {
                    detailOrder.setOrderName("Dịch vụ");
                }
                detailOrder.setId(order1.getId());
                detailOrder.setOrderType(order1.getOrderType());
                detailOrder.setOrderDetails(userService.getOrderDetailByOrderId(order1.getId()));
                detailOrder.setStatus(order1.getStatus());
                detailOrder.setCustomerName(StringUtil.checkValidString(customer.getName()));
                detailOrder.setCustomerPhone(StringUtil.checkValidString(customer.getPhone()));
                detailOrders.add(detailOrder);
            }
            request.setAttribute("detaiOrderUser", detailOrders);
            request.getRequestDispatcher("/views/web/shoppingcart.jsp").forward(request, response);
        }
    }
}

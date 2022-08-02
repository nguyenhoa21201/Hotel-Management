package com.manager.ctrl;

import com.manager.DAOImpl.BillDetailDAOImpl;
import com.manager.dto.CreateBillRequest;
import com.manager.entity.*;
import com.manager.serviceImpl.RoomServiceImpl;
import com.manager.serviceImpl.StaffServiceImpl;
import com.manager.serviceImpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@WebServlet({"/search_bill", "/detail_bill", "/update_bill","/create_bill"})
public class BillCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = req.getServletPath();
        HttpSession session = req.getSession();
        if(session.getAttribute("username") != null){
            String userName = session.getAttribute("username").toString();
            req.setAttribute("userName", userName);
        }
        StaffServiceImpl staffService = new StaffServiceImpl();
        try {
            if (url.endsWith("search_bill")) {
                Bill bill = new Bill();
                if(session.getAttribute("customerIdSearchBill") != null){
                    bill.setCustomerId(session.getAttribute("customerIdSearchBill").toString());
                }
                if(session.getAttribute("billIdSearch") != null){
                    bill.setId(session.getAttribute("billIdSearch").toString());
                }
                if(session.getAttribute("billStatus") != null){
                    bill.setStatus(session.getAttribute("billStatus").toString());
                }
                req.setAttribute("billDetail", staffService.getAllBill(bill));
                if (session.getAttribute("role").toString().equalsIgnoreCase("user")){
                    req.getRequestDispatcher("/views/web/bill_list_user.jsp").forward(req, response);
                }else {
                    req.getRequestDispatcher("/views/staff/bill_list.jsp").forward(req, response);
                }

                session.removeAttribute("customerIdSearchBill");
                session.removeAttribute("billIdSearch");
                session.removeAttribute("billStatus");
            }
            if(url.endsWith("update_bill")){

            }
            if(url.endsWith("create_bill")){
                req.getRequestDispatcher("/views/staff/create_bill.jsp").forward(req, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = req.getServletPath();
        HttpSession session = req.getSession();
        if(session.getAttribute("username") != null){
            String userName = session.getAttribute("username").toString();
            req.setAttribute("userName", userName);
        }
        UserServiceImpl userService = new UserServiceImpl();
        StaffServiceImpl staffService = new StaffServiceImpl();
        RoomServiceImpl roomService = new RoomServiceImpl();
        try {
            if (url.endsWith("search_bill")) {
                session.setAttribute("customerIdSearchBill", req.getParameter("customerIdSearchBill"));
                session.setAttribute("billIdSearch", req.getParameter("billIdSearch"));
                session.setAttribute("billStatus", req.getParameter("billStatus"));
                response.sendRedirect("/search_bill");
            }
            if(url.endsWith("create_bill")){
                CreateBillRequest request = new CreateBillRequest();
                request.setCreatedUser(session.getAttribute("username").toString());
                request.setStatus("pending");
                request.setCheckinDate(Timestamp.valueOf(req.getParameter("checkInDate")+ " 00:00:00"));
                request.setCheckoutDate(Timestamp.valueOf(req.getParameter("checkOutDate")+ " 00:00:00"));
                request.setInvoiceDate(Timestamp.valueOf(req.getParameter("invoiceDate")+ " 00:00:00"));
                if (req.getParameter("orderId")!=null){
                    request.setOrderId(req.getParameter("orderId"));
                }
                if (req.getParameter("customerId")!=null){
                    request.setCustomerId(req.getParameter("customerId"));
                }
                request.setId(String.valueOf(UUID.randomUUID()));

                if(req.getParameter("orderId")!=null){
                    request.setOrderId(req.getParameter("orderId"));
                }
                if(req.getParameter("customerId")!=null){
                    request.setCustomerId(req.getParameter("customerId"));
                }
                request.setId(String.valueOf(UUID.randomUUID()));
                staffService.createBill(request);
                session.setAttribute("billIdSearch", request.getId());
                response.sendRedirect("/search_bill");

            }
            if(url.endsWith("update_bill")){
                Bill bill = new Bill();
                if (req.getParameter("billId") !=null){
                    bill = staffService.getBillById(req.getParameter("billId"));
                }
                if(bill!=null && req.getParameter("status")!=null && !req.getParameter("status").equalsIgnoreCase(bill.getStatus())){
                    if(bill.getStatus().equalsIgnoreCase("cancel") || bill.getStatus().equalsIgnoreCase("success")){
                        session.setAttribute("billIdSearch", bill.getId());
                        response.sendRedirect("/search_bill");
                        return;
                    }
                    if(bill.getStatus().equalsIgnoreCase("pending") && req.getParameter("status").equalsIgnoreCase("cancel")){
                        BillDetailDAOImpl billDetailDAO = new BillDetailDAOImpl();
                        List<BillDetails> billDetailsLists = billDetailDAO.getBillByOrderId(bill.getId());
                        for(BillDetails billDetailsList : billDetailsLists){
                            if(billDetailsList.getRefType().equalsIgnoreCase("0")){
                                Room room = roomService.getRoomDetail(billDetailsList.getRefId());
                                room.setStatus("1");
                                roomService.updateRoom(room);
                            } else {
                               Service service =  staffService.getServiceDetail(billDetailsList.getRefId());
                               service.setAmount(String.valueOf(Integer.valueOf(service.getAmount()) + Integer.valueOf(billDetailsList.getAmount())));
                               staffService.updateService(service);
                            }
                        }
                    }
                    if(bill.getStatus().equalsIgnoreCase("pending") && req.getParameter("status").equalsIgnoreCase("success")){
                        Order order = new Order();
                        order.setCustomerId(bill.getCustomerId());
                        order.setStatus("confirm");
                        List<Order> orders = userService.getAllOrder(order);
                        for (Order order1 : orders){
                            order1.setStatus("success");
                            userService.updateOrder(order1);
                        }
                    }
                    bill.setStatus(req.getParameter("status"));
                    staffService.updateBill(bill);
                }
                response.sendRedirect("/search_bill");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

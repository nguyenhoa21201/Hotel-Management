package com.manager.ctrl;

import com.manager.config.StringUtil;
import com.manager.dto.SearchCustomerRequest;
import com.manager.dto.SearchServiceRequest;
import com.manager.entity.Customer;
import com.manager.serviceImpl.StaffServiceImpl;
import com.manager.serviceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@WebServlet({"/customers", "/customer_detail", "/customer_update", "/customer_insert"})
public class CustomerCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            String url = request.getServletPath();
            if(url.endsWith("customers")){
                SearchCustomerRequest req = new SearchCustomerRequest();
                if (session.getAttribute("nameCustomerSearch") !=null){
                    req.setName(StringUtil.checkValidString(session.getAttribute("nameCustomerSearch").toString()));
                }
                if (session.getAttribute("phonNumberSearch") !=null){
                    req.setPhone(StringUtil.checkValidString(session.getAttribute("phonNumberSearch").toString()));
                }
                if (session.getAttribute("emailSearch") !=null){
                    req.setEmail(StringUtil.checkValidString(session.getAttribute("emailSearch").toString()));
                }
                request.setAttribute("listCustomer", userService.findAllCustomers(req));
                session.removeAttribute("nameCustomerSearch");
                session.removeAttribute("phonNumberSearch");
                session.removeAttribute("emailSearch");

                request.getRequestDispatcher("/views/staff/list_customer.jsp").forward(request, response);

            }
            if(url.endsWith("customer_update")){
                Customer customer = userService.findCustomerById(request.getParameter("customerId"));
                request.setAttribute("customerDetail", customer);
                request.getRequestDispatcher( "/views/staff/update_customer.jsp").forward(request, response);
            }
            if(url.endsWith("/customer_insert")){
                request.getRequestDispatcher("/views/staff/insert_customer.jsp").forward(request, response);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserServiceImpl userService = new UserServiceImpl();
            HttpSession session = request.getSession();
            String url = request.getServletPath();
            Customer customer = new Customer();
            if(url.endsWith("customer_update")){
                customer = userService.findCustomerById(request.getParameter("customerId"));
                if(customer == null){
                    request.setAttribute("message", "Không tồn tại user");
                    request.getRequestDispatcher("/views/user/updateCustomer.jsp").forward(request, response);
                }
                String name = request.getParameter("name");

                Date birthDay = null;
                if(request.getParameter("birthDay") != null && !request.getParameter("birthDay").isEmpty()){
                    birthDay = new SimpleDateFormat("dd-MM-yyyy").parse(request.getParameter("birthDay"));
                }

                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                customer.setId(customer.getId());
                customer.setName(name);
                customer.setBirthDay(birthDay);
                customer.setAddress(address);
                customer.setEmail(email);
                customer.setId(customer.getId());
                customer.setPhone(phone);
                customer.setType(customer.getType());
                userService.updateCustomer(customer);
                response.sendRedirect("/customers");
            };
            if(url.endsWith("customer_insert")){
                String customerId = String.valueOf(UUID.randomUUID());
                String name = request.getParameter("name");
                Date birthDay = null;
                if(request.getParameter("birthDay") != null && !request.getParameter("birthDay").isEmpty()){
                    birthDay = new SimpleDateFormat("dd-mm-yyyy").parse(request.getParameter("birthDay"));
                }
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String type = "0";
                customer.setName(name);
                customer.setBirthDay(birthDay);
                customer.setAddress(address);
                customer.setEmail(email);
                customer.setId(customerId);
                customer.setPhone(phone);
                customer.setType(type);
                userService.createCustomer(customer);
                response.sendRedirect("/customers");
            }
            if (url.endsWith("/customers")){
                session.setAttribute("nameCustomerSearch",request.getParameter("nameCustomerSearch"));
                session.setAttribute("phonNumberSearch",request.getParameter("phonNumberSearch"));
                session.setAttribute("addressSearch",request.getParameter("addressSearch"));
                session.setAttribute("emailSearch",request.getParameter("emailSearch"));

                response.sendRedirect("/customers");

            }
            if (request.getParameter("customerId")!=null && url.endsWith("/customers")){
                session.setAttribute("customerIdOrderRoom", request.getParameter("customerId"));
                response.sendRedirect("/rooms");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}

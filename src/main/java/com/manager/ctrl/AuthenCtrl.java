package com.manager.ctrl;

import com.manager.entity.*;
import com.manager.serviceImpl.UserServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet({"/authen/login", "/authen/register","/logout" })
public class AuthenCtrl extends HttpServlet {
    private static final String PATH = "/authen/";
    private static final String PATH_JSP = "/views/authen/";
    public AuthenCtrl(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String uri = req.getServletPath();
        if(uri.equalsIgnoreCase(PATH + "login")){
            req.getRequestDispatcher(PATH_JSP + "login.jsp").forward(req, resp);
        }
        if(uri.equalsIgnoreCase(PATH + "register")){
            req.getRequestDispatcher(PATH_JSP + "register.jsp").forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String uri = req.getServletPath();
        if(uri.equalsIgnoreCase(PATH + "login")){
            login(req, resp, session);
        }
        if(uri.equalsIgnoreCase(PATH + "register")){
            register(req, resp, session);
        }


    }

    private void login(HttpServletRequest req, HttpServletResponse resp, HttpSession session  ) throws SQLException, IOException, ServletException {
        String username =  req.getParameter("username");
        String password =  req.getParameter("password");
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.findByUserName(username);
        Role role = new Role();
        Integer flag = 0;
        if(user != null && user.getUsername() != null){
            role =  userService.findRoleUser(user.getId());
            flag = 1;
        }
        if(flag != 0 && user.getPassword().equals(password) && role != null){
            session.setAttribute("role", role.getRoleCode().toUpperCase(Locale.ROOT));
            session.setAttribute("username", username);

            if(role.getRoleCode().equalsIgnoreCase("admin")){
                resp.sendRedirect("/admin");
            }
            if(role.getRoleCode().equalsIgnoreCase("staff")){
                resp.sendRedirect("/staff");
            }
            if(role.getRoleCode().equalsIgnoreCase("user")){
                resp.sendRedirect("/");
            }
        } else {
            req.setAttribute("message", "Tài khoản mật khẩu không chính xác");
            req.getRequestDispatcher("/views/authen/login.jsp").forward(req, resp);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response, HttpSession session  ){
        UserServiceImpl userService = new UserServiceImpl();
        List<Role> roles = new ArrayList<>();
        User user = null;
        Customer customer = new Customer();
        try {
            roles.addAll(userService.findAllRole());
            //user
            String userId = String.valueOf(UUID.randomUUID());
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // customer
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
            // user customer
            // user role
            String roleCode = "USER";
            String roleId = "";
            for (Role role : roles) {
                if (role.getRoleCode().equalsIgnoreCase(roleCode)) {
                    roleId = role.getId();
                }
            }
            // check user có tồn tại hay không
            user = userService.findByUserName(username);
            if (user != null && user.getUsername() != null) {
                request.setAttribute("message", "Tài khoản đã tồn tại");
                request.getRequestDispatcher("/views/authen/register.jsp").forward(request, response);
            }

            //create customer
            customer.setName(name);
            customer.setBirthDay(birthDay);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setId(customerId);
            customer.setPhone(phone);
            customer.setType(type);
            userService.createCustomer(customer);
            //create user
            User user1 = new User();
            user1.setId(userId);
            user1.setUsername(username);
            user1.setPassword(password);
            user1.setCustomerId(customerId);
            user1.setRoleCode(roleCode);
            user1.setRoleId(roleId);
            user1.setIsDeleted(false);
            userService.create(user1);
            request.setAttribute("message", "Đăng ký thành công, vui lòng đăng nhập lại");
            response.sendRedirect("/authen/login");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

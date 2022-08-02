package com.manager.ctrl;

import com.manager.config.StringUtil;
import com.manager.dto.BusinessReport;
import com.manager.dto.BusinessReportRequest;
import com.manager.dto.SearchUserDto;
import com.manager.dto.UserCustomer;
import com.manager.entity.Customer;
import com.manager.entity.Role;
import com.manager.entity.User;
import com.manager.serviceImpl.AdminServiceImpl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@WebServlet({"/admin/search/user", "/admin/create/user", "/admin", "/admin/create/logout", "/admin/update/user", "/admin/report/business"})
public class AdminCtrl extends HttpServlet {
    private static final String PATH_JSP = "/views/admin/";
    private static final String PATH = "/admin/";

    public static long serialVersionUID = -8899517213540670829L;

    public AdminCtrl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        HttpSession session = req.getSession();
        String userName = session.getAttribute("username").toString();
        req.setAttribute("userName", userName);
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (url.equalsIgnoreCase(PATH + "search/user")) {
                searchUserGet(req, resp, userService, session);
            }
            if (url.equalsIgnoreCase(PATH + "create/user")) {
                req.getRequestDispatcher(PATH_JSP + "createUser.jsp").forward(req, resp);
            }
            if (url.equalsIgnoreCase("/admin")) {
                List<User> listUser = userService.getListUser();
                req.setAttribute("listUser", listUser);

                req.getRequestDispatcher("/views" + PATH + "AdminController.jsp").forward(req, resp);
            }
            if (url.equalsIgnoreCase(PATH + "update/user")) {
                 String _userName = req.getParameter("username");
                req.setAttribute("username", _userName);
                detailUserGet(req, resp, session, userService);
            }
            if (url.equalsIgnoreCase(PATH + "report/business")) {
                getReportBusiness(req,resp,session);
                req.getRequestDispatcher(PATH_JSP + "report.jsp").forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String url = req.getServletPath();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (url.equalsIgnoreCase(PATH + "search/user")) {
                searchUserPost(req, resp, session);
            }
            if (url.equalsIgnoreCase(PATH + "create/user")) {
                createUserAdminPos(req, resp, session, userService);
            }
            if (url.equalsIgnoreCase(PATH + "update/user")) {
                updateUserPost(req, resp, session, userService);
            }
            if (url.equalsIgnoreCase(PATH + "report/business") || url.equalsIgnoreCase("/admin")) {
                List<User> listUser = userService.getListUser();
                req.setAttribute("listUser", listUser);
                getReportBusiness(req, resp, session);
            }
        } catch (IOException | SQLException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void getReportBusiness(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SQLException, ServletException, IOException {
        AdminServiceImpl adminService = new AdminServiceImpl();
        BusinessReportRequest request = new BusinessReportRequest();
        if (req.getParameter("month") != null  && !req.getParameter("month").isEmpty()) {
            request.setMonth(String.valueOf(req.getParameter("month")));
        }
        if (req.getParameter("year") != null  && !req.getParameter("year").isEmpty()) {
            request.setYear(String.valueOf(req.getParameter("year")));
        }
        if (req.getParameter("fromDate") != null && !req.getParameter("fromDate").isEmpty()) {
            request.setFromDate(Timestamp.valueOf(req.getParameter("fromDate")));
        }
        if (req.getParameter("toDate") != null && !req.getParameter("toDate").isEmpty()) {
            request.setToDate(Timestamp.valueOf(req.getParameter("toDate")));
        }
        List<BusinessReport>  reports =  adminService.getBusinessReport(request);
        req.setAttribute("reports", reports);
        req.getRequestDispatcher(PATH_JSP + "AdminController.jsp").forward(req, resp);
    }

    private void searchUserGet(HttpServletRequest req, HttpServletResponse resp, UserServiceImpl userService, HttpSession session) throws ServletException, IOException, SQLException {
        SearchUserDto searchUserDto = new SearchUserDto();
        if (session.getAttribute("usernameSearchUser") != null) {
            searchUserDto.setUsername(String.valueOf(session.getAttribute("usernameSearchUser")));
        }
        if (session.getAttribute("roleCodeSearchUser") != null) {
            searchUserDto.setRoleCode(String.valueOf(session.getAttribute("roleCodeSearchUser")));
        }
        req.setAttribute("users", userService.findAllUser(searchUserDto));
        session.removeAttribute("usernameSearchUser");
        session.removeAttribute("roleCodeSearchUser");
        req.getRequestDispatcher(PATH_JSP + "listUser.jsp").forward(req, resp);

    }

    private void searchUserPost(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        session.setAttribute("usernameSearchUser", req.getParameter("usernameSearchUser"));
        session.setAttribute("roleCodeSearchUser", req.getParameter("roleCodeSearchUser"));
        resp.sendRedirect("/admin/search/user");
    }

    private void createUserAdminPos(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserServiceImpl userService) {
        List<Role> roles = new ArrayList<>();
        User user = null;
        Customer customer = new Customer();
        try {
            roles.addAll(userService.findAllRole());
            //user
            String userId = String.valueOf(UUID.randomUUID());
            String username = request.getParameter("username");
            String password = "123";
            // customer
            String customerId = String.valueOf(UUID.randomUUID());
            String name = request.getParameter("name");
            Date birthDay = null;
            if (request.getParameter("birthDay") != null && !request.getParameter("birthDay").isEmpty()) {
                birthDay = new SimpleDateFormat("dd-mm-yyyy").parse(request.getParameter("birthDay"));
            }
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String type = "1";
            // user customer
            // user role
            String roleCode = request.getParameter("roleCode");
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
            response.sendRedirect("/admin/search/user");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void detailUserGet(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserServiceImpl userService) throws SQLException, ServletException, IOException {
        String username = request.getAttribute("username").toString();
        UserCustomer userCustomer = new UserCustomer();
        Customer customer = new Customer();
        User user = userService.findByUserName(username);
        if (user != null) {
            if (user.getCustomerId() != null) {
                customer = userService.findCustomerById(user.getCustomerId());
            }
            userCustomer.setUserId(StringUtil.checkValidString(user.getId()));
            userCustomer.setName(StringUtil.checkValidString(customer.getName()));
            userCustomer.setUsername(StringUtil.checkValidString(user.getUsername()));
            userCustomer.setRoleCode(StringUtil.checkValidString(user.getId()));
            userCustomer.setAddress(StringUtil.checkValidString(customer.getAddress()));
            userCustomer.setEmail(StringUtil.checkValidString(customer.getEmail()));
            userCustomer.setBirthDay(customer.getBirthDay());
            userCustomer.setRoleCode(StringUtil.checkValidString(user.getRoleCode()));
            userCustomer.setPhone(StringUtil.checkValidString(customer.getPhone()));
            request.setAttribute("userDetail", userCustomer);
            request.getRequestDispatcher(PATH_JSP + "updateUser.jsp").forward(request, response);
        }
    }

    private void updateUserPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, UserServiceImpl userService) throws SQLException {
        List<Role> roles = userService.findAllRole();
        User user;
        Customer customer;
        try {
            user = userService.findByUserName(request.getParameter("username"));
            customer = userService.findCustomerById(user.getCustomerId());

            // customer
            String name = request.getParameter("name");

            Date birthDay = null;
            if (request.getParameter("birthDay") != null && !request.getParameter("birthDay").isEmpty()) {
                birthDay = new SimpleDateFormat("dd-MM-yyyy").parse(request.getParameter("birthDay"));
            }

            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            // user customer
            String roleCode = "";
            if (request.getParameter("roleCode") != null && !request.getParameter("roleCode").isEmpty()) {
                roleCode = request.getParameter("roleCode");
            }
            String roleId = "";
            for (Role role : roles) {
                if (role.getRoleCode().equalsIgnoreCase(roleCode)) {
                    roleId = role.getId();
                }
            }
            // check user có tồn tại hay không
            //create customer
            customer.setId(user.getCustomerId());
            customer.setName(name);
            customer.setBirthDay(birthDay);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setId(customer.getId());
            customer.setPhone(phone);
            customer.setType(customer.getType());
            userService.updateCustomer(customer);
            //create user
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setRoleId(roleId);
            user1.setRoleCode(roleCode);
            user1.setIsDeleted(false);
            user1.setId(user.getId());
            userService.updateUser(user1);

            response.sendRedirect("/admin/search/user");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

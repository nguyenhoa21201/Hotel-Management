package com.manager.ctrl;

import com.manager.config.StringUtil;
import com.manager.dto.SearchServiceRequest;
import com.manager.entity.Service;
import com.manager.serviceImpl.StaffServiceImpl;
import com.manager.serviceImpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet({"/search_service", "/detail_service", "/update_service"})
public class ServiceCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = req.getServletPath();
        HttpSession session = req.getSession();
        String userName = session.getAttribute("username").toString();
        req.setAttribute("userName", userName);
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (url.endsWith("search_service")) {
                SearchServiceRequest request = new SearchServiceRequest();
                if (session.getAttribute("nameService")!=null){
                    request.setName(StringUtil.checkValidString(session.getAttribute("nameService").toString()));
                }

                StaffServiceImpl staffService = new StaffServiceImpl();
                req.setAttribute("serviceList", staffService.findAllService(request));
                req.getRequestDispatcher("/views/staff/list_service.jsp").forward(req, response);
                session.removeAttribute("nameService");
            }
            if (url.equalsIgnoreCase("/detail_service")) {
                detailServiceGet(req,response);
//                req.getRequestDispatcher("/views/staff/detail_service.jsp").forward(req,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = req.getServletPath();
        HttpSession session = req.getSession();
        String userName = session.getAttribute("username").toString();
        req.setAttribute("userName", userName);
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (url.equalsIgnoreCase("/search_service")) {
                session.setAttribute("nameService", StringUtil.checkValidString(req.getParameter("nameService")));
                response.sendRedirect("search_service");
            }
            if (url.equalsIgnoreCase("/update_service") || url.equalsIgnoreCase("/detail_service")) {
                updateService(req, response);
            }
//            if (url.equalsIgnoreCase("/insert_service")) {
//                insertService(req, response);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void detailServiceGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException, ServletException {
        Service service = new Service();
        StaffServiceImpl staffService = new StaffServiceImpl();
//        String id = req.getParameter("roomId");
        String id = req.getParameter("serviceId");
        // FIX value
        service = staffService.getServiceDetail(id);
        req.setAttribute("serviceDetail", service);
        req.getRequestDispatcher("views/staff/detail_service.jsp").forward(req, resp);
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
        resp.sendRedirect("/service");
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
        if (part !=null && part.getSubmittedFileName() != null &&  !part.getSubmittedFileName().isEmpty() ){
            String realPath = request.getServletContext().getRealPath("/images");
            String nameFile = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if(!Files.exists(Paths.get(realPath))){
                Files.createDirectory(Paths.get(realPath));
            }
            part.write(realPath+"/"+nameFile);
            return  nameFile;
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
}

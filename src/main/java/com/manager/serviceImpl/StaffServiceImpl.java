package com.manager.serviceImpl;

import com.manager.DAO.OrderDao;
import com.manager.DAOImpl.*;
import com.manager.dto.BillResponse;
import com.manager.dto.CreateBillRequest;
import com.manager.dto.SearchServiceRequest;
import com.manager.entity.*;
import com.manager.service.StaffService;

import java.sql.SQLException;
import java.util.*;

public class StaffServiceImpl implements StaffService {

    @Override
    public void create(Service service) throws SQLException {
        ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
        serviceDAO.createService(service);
    }

    @Override
    public List<Service> findAllService(SearchServiceRequest rq) throws SQLException {
        ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
        Map<String, String> spec = new HashMap<>();
        if (rq != null) {
            if (rq.getName() != null && !rq.getName().isEmpty()) {
                spec.put("name", "'%" + rq.getName() + "%'");
            }
        }
        return serviceDAO.getAllService(spec);
    }

    @Override
    public Service getServiceDetail(String id) throws SQLException {
        ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
        return serviceDAO.getServiceDetail(id);
    }

    @Override
    public void updateService(Service service) throws SQLException {
        ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
        Map<String, String> spec = new HashMap<>();
        if (service != null) {
            if (service.getName() != null && !service.getName().isEmpty()) {
                spec.put("name", "'" + service.getName() + "'");
            }
            if (service.getAmount() != null && !service.getAmount().isEmpty()) {
                spec.put("amount", "'" + service.getAmount() + "'");
            }
            if (service.getDescription() != null && !service.getDescription().isEmpty()) {
                spec.put("description", "'" + service.getDescription() + "'");
            }
            if (service.getPrice() != null) {
                spec.put("price", "'" + service.getPrice() + "'");
            }
            if (service.getImage() != null) {
                spec.put("image", "'" + service.getImage() + "'");
            }
            if (service.getUnit() != null) {
                spec.put("unit", "'" + service.getUnit() + "'");
            }
        } else {
            return;
        }
        serviceDAO.updateService(spec, service.getId());
    }

    @Override
    public void createBill(CreateBillRequest request) throws SQLException {
        //Get order and orderDetail by order
        BillDAOImpl billDAO = new BillDAOImpl();
        BillDetailDAOImpl billDetailDAO = new BillDetailDAOImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserServiceImpl userService = new UserServiceImpl();
        OrderDetailDaoImpl orderDetailDao = new OrderDetailDaoImpl();
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        Boolean flag = false;
        if (request.getOrderId() != null) {
            order = orderDao.getOrder(request.getOrderId());
            orders.add(order);
            flag = true;
        }
        if (request.getCustomerId() != null && flag.equals(false)) {
            order.setCustomerId(request.getCustomerId());
            order.setStatus("confirm");
            orders = userService.getAllOrder(order);
        }
        for (Order order1 : orders) {
            if (order1.getStatus().equalsIgnoreCase("confirm")) {
                List<OrderDetails> orderDetails = orderDetailDao.getOrderDetailByOrderId(order1.getId());
                //Create bill
                Bill bill = new Bill();
                String billId = UUID.randomUUID().toString();
                bill.setCustomerId(order.getCustomerId());
                bill.setInvoiceDate(request.getInvoiceDate());
                bill.setCheckinDate(request.getCheckinDate());
                bill.setCheckoutDate(request.getCheckoutDate());
                bill.setCreatedUser(request.getCreatedUser());
                bill.setIsDeleted(Boolean.FALSE);
                bill.setStatus("pending");
                bill.setId(billId);
                billDAO.newBill(bill);

                //creted billDetail
                List<BillDetails> billDetails = new ArrayList<>();
                for (OrderDetails orderDetail : orderDetails) {
                    BillDetails billDetail = new BillDetails();
                    billDetail.setBillId(billId);
                    billDetail.setAmount(orderDetail.getAmount());
                    billDetail.setRefId(orderDetail.getRefId());
                    billDetail.setNameRef(orderDetail.getNameRef());
                    billDetail.setPriceRef(orderDetail.getPriceRef());
                    billDetail.setRefType(orderDetail.getRefType());
                    billDetail.setCreatedUser(orderDetail.getCreatedUser());
                    billDetail.setId(UUID.randomUUID().toString());
                    billDetails.add(billDetail);
                }
                if (!billDetails.isEmpty()) {
                    billDetailDAO.newBillDetail(billDetails);
                }
            }
        }
    }

    @Override
    public void updateBill(Bill bill) {
        BillDAOImpl billDAO = new BillDAOImpl();
        Map<String, String> spec = new HashMap<>();
        if (bill != null) {
            if (bill.getStatus() != null && !bill.getStatus().isEmpty()) {
                spec.put("status", "'" + bill.getStatus() + "'");
            }
        } else {
            return;
        }
        billDAO.updateBill(spec, bill.getId());
    }

    @Override
    public List<BillResponse> getAllBill(Bill bill) throws SQLException {
        BillDAOImpl billDAO = new BillDAOImpl();
        BillDetailDAOImpl billDetailDAO = new BillDetailDAOImpl();
        UserServiceImpl userService = new UserServiceImpl();
        Map<String, String> spec = new HashMap<>();
        if (bill != null) {
            if (bill.getId() != null && !bill.getId().isEmpty()) {
                spec.put("id", "'" + bill.getId() + "'");
            }
            if (bill.getCustomerId() != null && !bill.getCustomerId().isEmpty()) {
                spec.put("customer_id", "'" + bill.getCustomerId() + "'");
            }
            if (bill.getStatus() != null && !bill.getStatus().isEmpty()) {
                spec.put("status", "'" + bill.getStatus() + "'");
            }
        }
        List<Bill> bills = billDAO.findAlBill(spec);
        List<BillResponse> responses = new ArrayList<>();
        for (Bill bill1 : bills) {
            List<BillDetails> billDetails = billDetailDAO.getBillByOrderId(bill1.getId());
            Double totalPrice = 0.0;
            for (BillDetails details : billDetails) {
                totalPrice += details.getPriceRef();
            }
            BillResponse billResponse = new BillResponse();
            billResponse.setId(bill1.getId());
            billResponse.setCustomerId(bill1.getCustomerId());
            Customer customer = userService.findCustomerById(bill1.getCustomerId());
            billResponse.setCustomerName(customer.getName());
            billResponse.setCreatedUser(bill1.getCreatedUser());
            billResponse.setCheckinDate(bill1.getCheckinDate());
            billResponse.setCheckoutDate(bill1.getCheckoutDate());
            billResponse.setInvoiceDate(bill1.getInvoiceDate());
            billResponse.setStatus(bill1.getStatus());
            billResponse.setTotalPrice(totalPrice);
            billResponse.setDetails(billDetails);
            responses.add(billResponse);
        }
        return responses;
    }

    public Bill getBillById(String id) throws SQLException {
        return  new BillDAOImpl().getBill(id);
    }
}

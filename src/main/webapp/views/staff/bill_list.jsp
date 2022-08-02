<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Staff</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="./assets/customstyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/line-awesome/1.3.0/line-awesome/css/line-awesome.min.css" integrity="sha512-vebUliqxrVkBy3gucMhClmyQP9On/HAWQdKDXRaAlb/FKuTbxkjPKUyqVOxAcGwFDka79eTF+YXwfke1h3/wfg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='/template/css/adminstyle.css'/> ">
</head>
<body>
<input type="checkbox" id="nav-toggle">
<div class="sidebar">
    <div class="sidebar-brand">
        <h2><span class="lab la-accusoft"></span><span>Ngàn Sao</span> </h2>
    </div>
    <div class="sidebar-menu">
        <ul>
            <c:if test="${role=='ADMIN'}">
                <li>
                    <a href="<c:url value='/admin'/>" ><span class="las la-igloo"></span>
                        <span>Dashboard</span></a>
                </li>
                <li>
                    <a href="<c:url value='/admin/search/user'/>" ><span class="las la-users"></span>
                        <span>User</span></a>
                </li>
            </c:if>
            <c:if test="${role=='STAFF'}">
                <li>
                    <a href="<c:url value='/rooms'/>" ><span class="las la-table"></span>
                        <span>Room</span></a>
                </li>
                <li>
                    <a href="/search_service"><span class="las la-table"></span>
                        <span>Service</span></a>
                </li>
                <li>
                    <a href="/customers"><span class="las la-table"></span>
                        <span>Customer</span></a>
                </li>
                <li>
                    <a href="/search_bill"><span class="las la-table"></span>
                        <span>Bill</span></a>
                </li>
                <li>
                    <a href="/order_list"><span class="la la-opencart"></span>
                        <span>Order</span></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="maincontent">
    <header>
        <h2>
            <label for="nav-toggle">
                <span class="las la-bars"></span>
            </label>
            Trang quản lý
        </h2>
        <div class="wrapuser">
            <div>
                <h4>${userName}</h4>
            </div>
            <div class="sigout" *ngIf="username">
                <a href="/authen/logout">Đăng xuất</a>
            </div>
        </div>
    </header>
    <main>

        <legend>Tìm kiếm</legend>
        <form method="post" action="">
            <table class="table">
                <tr>
                    <td>Trạng thái</td>
                    <td>
                        <select name="billStatus" id="" class="form-control">
                            <option value="pending">chưa thanh toán</option>
                            <option value="success">Đã thanh toán</option>
                            <option value="cancel">Hủy</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="Tìm kiếm" class="btn btn-success"></td>
                    <br> ${ message } <br>
                </tr>
            </table>
        </form>


        <br>
        <h3>Danh sách hóa đơn</h3>
        <table class="table .table-bordered">
            <tr>
                <td>Người tạo</td>
                <td>Tổng tiền</td>
                <td>Ngày tạo hóa đơn</td>
                <td>Ngày nhận phòng</td>
                <td>Ngày trả phòng</td>
                <td>Tên khách hàng</td>
                <td>Trạng thái</td>
                <td>Hành động</td>
            </tr>
            <c:forEach var="bill" items="${billDetail}">
                <tr>
                    <td>${ bill.createdUser }</td>
                    <td>${ bill.totalPrice }</td>
                    <td>${ bill.invoiceDate }</td>
                    <td>${ bill.checkinDate }</td>
                    <td>${ bill.checkoutDate }</td>
                    <td>${ bill.customerName }</td>
                    <td>${ bill.status }</td>
                    <td>
                        <form action="/update_bill" method="post">
                            <select name="status" class="form-control">
                                <option value="pending">Chờ thanh toán</option>
                                <option value="success">Đã thanh toán</option>
                                <option value="cancel">Hủy thanh toán</option>
                            </select>
                            <input type="text" name="billId" value="${bill.id}" hidden>
                            <button type="submit" class="btn btn-danger">Cập nhật trạng thái</button>
                        </form>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<script>

</script>
</body>
</html>

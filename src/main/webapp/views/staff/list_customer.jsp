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
                    <td>Tên khách hàng</td>
                    <td><input type="text" name="nameCustomerSearch" class="form-control"></td>
                </tr>
                <tr>
                    <td>Điện thoại</td>
                    <td><input type="text" name="phonNumberSearch" class="form-control"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="emailSearch" class="form-control"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="Tìm kiếm" class="btn btn-success"></td>
                    <br> ${ message } <br>
                </tr>
            </table>
        </form>

        <button type="button" class="btn btn-danger"><a href="/customer_insert">Thêm mới</a> </button>
        <br>
        <h3>Danh sách </h3>
        <table class="table .table-bordered">
            <tr>
                <td>Tên khách hàng</td>
                <td>Địa chỉ</td>
                <td>Số điện thoại</td>
                <td>Email</td>
                <td>Hành động</td>
            </tr>
            <c:forEach var="cus" items="${listCustomer}">
                <tr>
                    <td>${ cus.name }</td>
                    <td>${ cus.address }</td>
                    <td>${ cus.phone }</td>
                    <td>${ cus.email }</td>
                    <td>
                        <a href="/customer_update?customerId=${cus.id}" style="margin: 10px" class="btn btn-primary">Cập nhật thông tin</a>
                        <form action="order_room" method="post">
                            <input type="text" name="customerId" value="${cus.id}" hidden>
                            <input type="submit" value="Đặt phòng" class="btn btn-primary">
                        </form>
                        <p style="margin-left: 10px"></p>
                        <form action="order_service" method="post">
                            <input type="text" name="customerId" value="${cus.id}" hidden style="margint: 15px" >
                            <input type="submit" value="Thêm dịch vụ" class="btn btn-primary">
                        </form>
                        <p style="margin-left: 10px"></p>
                        <a href="/order_list?customerId=${cus.id}" class="btn btn-primary">Chi tiết và thông tin đặt phòng</a>
                        <p style="margin-left: 10px"></p>
                        <a href="/create_bill?customerId=${cus.id}" class="btn btn-primary">Xuất hóa đơn</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

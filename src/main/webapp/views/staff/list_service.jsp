<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body bgcolor="#ffffff">

</body>
</html>
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
                    <td>Tên dich vụ</td>
                    <td><input type="text" name="nameService" class="form-control"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="Tìm kiếm" class="btn btn-success"></td>
                    <br> ${ message } <br>
                </tr>
            </table>
        </form>

        <button type="button" class="btn btn-danger"><a href="/insert_service">Thêm mới</a> </button>
        <br>
        <h3>Danh dịch vụ</h3>
        <table class="table .table-bordered">
            <tr>
                <td>Tên dịch vụ</td>
                <td>Mô tả dịch vụ</td>
                <td>Giá</td>
                <td>Số lượng</td>
                <td>Đơn vị tiền</td>
                <td>Hình ảnh</td>
                <td>Hành động</td>
            </tr>
            <c:forEach var="sv" items="${serviceList}">
                <tr>
                    <td>${ sv.name }</td>
                    <td>${ sv.description }</td>
                    <td>${ sv.price }</td>
                    <td>${ sv.amount }</td>
                    <td>${ sv.unit }</td>
                    <td><img src="images/${sv.image}" alt="" style="width: 100px"></td>
                    <td>
                        <a href="/detail_service?serviceId=${sv.id}" style="margin: 10px" class="btn btn-primary">Chi tiết</a>
                        <c:if test="${customerIdOrderRoom!=null || customerIdOrderService!=null }">
                        <form action="staff/create_order" method="post">
                            <input type="text" name="unit" value="${ sv.unit }" hidden>
                            <input type="number" name="amount" value="1" hidden>
                            <input type="hidden" name="refType" value="1">
                            <input type="hidden" name="refId" value="${sv.id}">
                            <input type="hidden" name="priceRef" value="${sv.price}">
                            <input type="hidden" name="nameRef" value="${sv.name}">
                            <input type="submit" value="Đặt dịch vụ" class="btn btn-primary">
                        </form>
                        </c:if>
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

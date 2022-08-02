<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <p (click)="signout();">Sign Out</p>
            </div>
        </div>
    </header>
    <main>
        <h3>Cập nhập thông tin phòng</h3>
        <br>
        <p>Thông tin phòng</p>
        <img src="images/${roomDetail.image}" alt="" width="300px">
        <form method="post" action="" enctype="multipart/form-data">
            <table  class="table table-striped">
                <tr style="display: none">
                    <td><input type="hidden" name="roomId" value="${ roomDetail.id }" hidden class="form-control"></td>
                </tr>
                <tr>
                    <td>Tên phòng</td>
                    <td><input type="text" name="name" value="${ roomDetail.name }" class="form-control"></td>
                </tr>
                <tr>
                    <td>Diện tích</td>
                    <td><input type="text" name="square"  value="${ roomDetail.square }" class="form-control"></td>
                </tr>
                <tr>
                    <td>Số giường</td>
                    <td><input type="text" name="bedNumber" value="${ roomDetail.bedNumber }" class="form-control"></td>
                </tr>
                <tr>
                    <td>Số người</td>
                    <td><input type="text" name="peopleNumber" value="${ roomDetail.peopleNumber }" class="form-control"></td>
                </tr>
                <tr>
                    <td>Giá</td>
                    <td><input type="text" name="price" value="${ roomDetail.price }" class="form-control"></td>
                </tr>

                <tr>
                    <td>Giảm giá</td>
                    <td><input type="text" pattern="^[0-9]{1,2}" name="discountPrice" value="${ roomDetail.discountPrice }" class="form-control"></td>
                </tr>
                <tr>
                    <td>Thông tin thêm</td>
                    <td><input type="text" name="description" value="${ roomDetail.description }" class="form-control"></td>
                </tr>
                 <tr>
                    <td>Ảnh</td>
                    <td><input type="file" name="fileimage" class="form-control"></td>
                </tr>
                <tr>
                    <td><label for="status">Trạng thái</label></td>
                    <td><select name="status" id="status" value="${ roomDetail.status }" class="form-control">
                        <option value="0">Đã đặt</option>
                        <option value="1">Còn phòng</option>
                        <option value="2">Đang tu sửa</option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="Cập nhật thông tin" class="btn btn-primary"></td>
                    <br> ${ message } <br>
                </tr>
            </table>
        </form>

    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

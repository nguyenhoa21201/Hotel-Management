<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Order</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700|Work+Sans:300,400,700"
              rel="stylesheet">
        <link rel="stylesheet" href="fonts/icomoon/style.css">
        <link rel="stylesheet" href="<c:url value='/template/css/bootstrap.min.css'/> ">
        <link rel="stylesheet" href="<c:url value='/template/css/magnific-popup.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/css/jquery-ui.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/css/owl.carousel.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/css/owl.theme.default.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/css/bootstrap-datepicker.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/css/animate.css'/>">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/mediaelementplayer.min.css">


        <link rel="stylesheet" href="<c:url value='/template/fonts/flaticon/font/flaticon.css'/>">

        <link rel="stylesheet" href="<c:url value='/template/css/aos.css'/>">

        <link rel="stylesheet" href="<c:url value='/template/css/style.css'/>">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Manrope:wght@200&display=swap');

            /*body {*/
            /*  font-family: 'Manrope', sans-serif;*/
            /*  background:#eee;*/
            /*}*/

            /*.size span {*/
            /*  font-size: 11px;*/
            /*}*/

            /*.color span {*/
            /*  font-size: 11px;*/
            /*}*/

            /*.product-deta {*/
            /*  margin-right: 70px;*/
            /*}*/

            /*.gift-card:focus {*/
            /*  box-shadow: none;*/
            /*}*/

            /*.pay-button {*/
            /*  color: #fff;*/
            /*}*/

            /*.pay-button:hover {*/
            /*  color: #fff;*/
            /*}*/

            /*.pay-button:focus {*/
            /*  color: #fff;*/
            /*  box-shadow: none;*/
            /*}*/

            /*.text-grey {*/
            /*  color: #a39f9f;*/
            /*}*/

            /*.qty i {*/
            /*  font-size: 11px;*/
            /*}*/
        </style>
    </head>
    <body>
        <div class="site-wrap">

            <div class="site-mobile-menu">
                <div class="site-mobile-menu-header">
                    <div class="site-mobile-menu-close mt-3">
                        <span class="icon-close2 js-menu-toggle"></span>
                    </div>
                </div>
                <div class="site-mobile-menu-body"></div>
            </div> <!-- .site-mobile-menu -->


            <div class="site-navbar-wrap js-site-navbar bg-white">

                <div class="container">
                    <div class="site-navbar bg-light">
                        <div class="py-1">
                            <div class="row align-items-center">
                                <div class="col-2">
                                    <h2 class="mb-0 site-logo"><a href="/">Ngàn Sao</a></h2>
                                </div>
                                <div class="col-10">
                                    <nav class="site-navigation text-right" role="navigation">
                                        <div class="container">

                                            <div class="d-inline-block d-lg-none  ml-md-0 mr-auto py-3"><a href="#" class="site-menu-toggle js-menu-toggle"><span class="icon-menu h3"></span></a></div>
                                            <ul class="site-menu js-clone-nav d-none d-lg-block">
                                                <li class="active">
                                                    <a href="<c:url value='/'/>">Trang chủ</a>
                                                </li>
                                                <li class="has-children">
                                                    <a href="<c:url value=''/>">Dịch vụ</a>
                                                    <ul class="dropdown arrow-top">
                                                        <c:forEach items="${services}" var="ser" >
                                                            <li><a href="/">${ser.name}</a></li>
                                                            </c:forEach>
                                                    </ul>
                                                </li>
                                                <li>
                                                    <a href="/user/order_list">Thông tin đơn hàng</a> </li>
                                                <li>
                                                    <!--<a href="/search_bill">Thông tin hóa đơn</a> </li>-->
                                                    <c:choose>
                                                        <c:when test="${username !=null}">
                                                        <li><a href="/authen/logout">${username} - Đăng xuất</a></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <li><a href="<c:url value='/authen/login'/>">Đăng nhập</a></li>
                                                        </c:otherwise>
                                                    </c:choose>

                                            </ul>
                                        </div>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${msg}">
                <div class="alert alert-success" role="alert">
                    <h4 class="alert-heading">Well done!</h4>
                    <hr>
                    <p class="mb-0">${msg}</p>
                </div>
            </c:if>
            <div class="site-blocks-cover overlay" style="background-image: url(<c:url value='/template/images/hero_1.jpg'/>);"
                 data-aos="fade" data-stellar-background-ratio="0.5">
                <div class="container">
                    <div class="row align-items-center justify-content-center">
                        <div class="col-md-7 text-center" data-aos="fade">
                            <span class="caption mb-3">Luxurious Rooms</span>
                            <h1 class="mb-4">Hotel Rooms</h1>
                        </div>
                    </div>
                </div>
            </div>


            <div class="site-section bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6 mx-auto text-center mb-5 section-heading">
                            <h2 class="mb-5">Order detail</h2>
                        </div>
                    </div>
                    <div class="d-flex justify-content-center row">
                        <div class="col-md-8">
                            <table class="table .table-bordered">
                                <tr>
                                    <td></td>
                                    <td>Tên đơn hàng</td>
                                    <td>Tên khách hàng</td>
                                    <td>Số điện thoại</td>
                                    <td>Loại</td>
                                    <td>Trạng thái</td>
                                    <td>
                                        Hành động
                                    </td>
                                </tr>
                                <c:forEach var="order" items="${detaiOrderUser}">
                                    <c:set var="itemOrder" value="${order}"/>

                                    <tr>
                                        <td><input type="text" value="${order.id}" hidden id="orderid"></td>
                                        <td><input type="text" name="orderName" value="${ order.orderName }" class="form-control"
                                                   readonly></td>
                                        <td>
                                            <input type="text" name="customerName" value="${ order.customerName }"
                                                   class="form-control" readonly>
                                        </td>
                                        <td>
                                            <input type="text" name="customerPhone" value="${ order.customerPhone }"
                                                   class="form-control" readonly>
                                        </td>

                                        <c:choose>
                                            <c:when test="${order.orderType==1}">
                                                <td>
                                                    <input type="text" name="orderType" value="Đặt dịch vụ" class="form-control"
                                                           readonly>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <input type="text" name="orderType" value="Đặt phòng" class="form-control"
                                                           readonly>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                            <input type="text" name="customerPhone" value="${  order.status }"
                                                   class="form-control" readonly>
                                        </td>
                                        <td>
                                            <c:if test="${itemOrder.status == 'pending'}">
                                                <form action="/update_order" method="post">
                                                    <input type="text" value="${order.id}" name="orderId" hidden>
                                                    <input type="text" value="cancel" name="statusOrder" hidden>
                                                    <input type="submit" class="btn btn-danger" value="Hủy">
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                </table>
                                <br>
                                <br>
                                <c:if test="${itemOrder.orderType==0}">
                                    <h4>Danh sách phòng chi tiết</h4>
                                    <table class="table table-striped">
                                        <tr>
                                            <td></td>
                                            <td>Tên phòng</td>
                                            <td>Giá</td>
                                            <td>Hành động</td>
                                        </tr>
                                        <c:forEach items="${order.orderDetails}" var="it" varStatus="i">
                                            <tr>
                                                <td><input type="text" hidden name="orderDetailId" class="orderDetailId${i.index}"
                                                           value="${it.id}"></td>
                                                    <c:set var="orderDetailId" value="${it.id}"/>
                                                <td><input type="text" name="nameRef" value="${it.nameRef}" class="form-control"
                                                           readonly></td>
                                                <td><input type="text" name="priceRef" value="${it.priceRef}" class="form-control"
                                                           readonly>
                                                </td>
                                                <td>
                                                    <form action="/update_order_detail" method="post">
                                                        <input type="text" value="${order.id}" name="orderId" hidden>
                                                        <input type="text" value="${it.id}" name="orderDetailId" hidden>
                                                        <input type="text" value="0" name="amount" hidden>
                                                        <input type="submit" class="btn btn-danger" value="Xóa">
                                                    </form>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                    </table>
                                </c:if>
                                <c:if test="${itemOrder.orderType==1}">
                                    <h4>Chi tiết dịch vụ</h4>
                                    <table class="table table-striped">
                                        <tr>
                                            <td></td>
                                            <th>Tên dịch vụ</th>
                                            <th>Giá</th>
                                            <th>Đơn vị tiền</th>
                                            <th>Số lượng</th>
                                            <td>Hành dộng</td>
                                        </tr>
                                        <c:forEach items="${order.orderDetails}" var="it" varStatus="i">
                                            <tr>
                                                <td><input type="text" hidden name="orderDetailId" class="orderDetailId${i.index}"
                                                           value="${it.id}"
                                                           id="orderDetailId"></td>
                                                    <c:set var="orderDetailId" value="${it.id}"/>
                                                <td><input type="text" name="nameRef" value="${it.nameRef}" class="form-control"
                                                           readonly></td>
                                                <td><input type="text" name="priceRef" value="${it.priceRef}" class="form-control"
                                                           readonly>
                                                </td>
                                                <td><input type="text" name="unit" value="${it.unit}" class="form-control" readonly>
                                                </td>
                                                <c:set var="value" value="${it.amount}"/>
                                                <td><input type="number" id="amount" name="amount" value="${it.amount}"
                                                           onchange="myFunction(this.value,${i.index})" class="form-control"></td>
                                                <td>
                                                <td>
                                                    <form action="/update_order_detail" method="post">
                                                        <input type="text" value="${order.id}" name="orderId" hidden>
                                                        <input type="text" value="${it.id}" name="orderDetailId" hidden>
                                                        <input type="text" value="0" name="amount" hidden>
                                                        <input type="submit" class="btn btn-danger" value="Xóa">
                                                    </form>
                                                </td>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                    </table>
                                </c:if>

                                </tr>
                            </c:forEach>
                            </table>
                        </div>


                    </div>
                </div>
                <div class="py-5 quick-contact-info">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-4 text-center">
                                <div>
                                    <span class="icon-room text-white h2 d-block"></span>
                                    <h2>Địa chỉ</h2>
                                    <p class="mb-0">100 Trần Phú, Phường Lộc Thọ<br>Tp Nha Trang, Tỉnh Khánh Hòa</p>
                                </div>
                            </div>
                            <div class="col-md-4 text-center">
                                <div>
                                    <span class="icon-clock-o text-white h2 d-block"></span>
                                    <h2>Thời gian phục vụ</h2>
                                    <p class="mb-0">Tất cả các ngày trong tuần từ 6:30 - 19:30 <br>
                                </div>
                            </div>
                            <div class="col-md-4 text-center">
                                <div>
                                    <span class="icon-comments text-white h2 d-block"></span>
                                    <h2>Lấy liên hệ</h2>
                                    <p class="mb-0">Email: ngansao@gmail.com <br>
                                        Phone: (123) 6789-222-6789 </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <footer class="site-footer">
                    <div class="container">


                        <div class="row">
                            <div class="col-md-4">
                                <h3 class="footer-heading mb-4 text-white">Về chúng tôi</h3>
                                <p>Chúng tôi với phương châm đem đến sự tiện nghi và sang trọng cho quý khách</p>
                            </div>
                            <div class="col-md-6">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h3 class="footer-heading mb-4 text-white">Trang Chính</h3>
                                        <ul class="list-unstyled">
                                            <li><a href="/">Trang chủ</a></li>
                                            <li><a href="/">dịch vụ</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-2">

                            </div>
                        </div>
                        <div class="row pt-5 mt-5 text-center">
                            <div class="col-md-12">
                                <p>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    Copyright &copy; <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script>document.write(new Date().getFullYear());</script> Ngan Sao Hotel | This template is made with <i class="icon-heart text-primary" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank" >Colorlib</a>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                </p>
                            </div>

                        </div>
                    </div>
                </footer>
            </div>

            <script src="<c:url value='/template/js/jquery-3.3.1.min.js'/> "></script>
            <script src="<c:url value='/template/js/jquery-migrate-3.0.1.min.js'/>"></script>
            <script src="<c:url value='/template/js/jquery-ui.js'/>"></script>
            <script src="<c:url value='/template/js/popper.min.js'/>"></script>
            <script src="<c:url value='/template/js/bootstrap.min.js'/>"></script>
            <script src="<c:url value='/template/js/owl.carousel.min.js'/>"></script>
            <script src="<c:url value='/template/js/jquery.stellar.min.js'/>"></script>
            <script src="<c:url value='/template/js/jquery.countdown.min.js'/>"></script>
            <script src="<c:url value='/template/js/jquery.magnific-popup.min.js'/>"></script>
            <script src="<c:url value='/template/js/bootstrap-datepicker.min.js'/>"></script>
            <script src="<c:url value='/template/js/aos.js'/>"></script>


            <script src="<c:url value='/template/js/mediaelement-and-player.min.js'/>"></script>

            <script src="<c:url value='/template/js/main.js'/>"></script>


            <script>
                                                           document.addEventListener('DOMContentLoaded', function () {
                                                               var mediaElements = document.querySelectorAll('video, audio'), total = mediaElements.length;

                                                               for (var i = 0; i < total; i++) {
                                                                   new MediaElementPlayer(mediaElements[i], {
                                                                       pluginPath: 'https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/',
                                                                       shimScriptAccess: 'always',
                                                                       success: function () {
                                                                           var target = document.body.querySelectorAll('.player'), targetTotal = target.length;
                                                                           for (var j = 0; j < targetTotal; j++) {
                                                                               target[j].style.visibility = 'visible';
                                                                           }
                                                                       }
                                                                   });
                                                               }
                                                           });
            </script>
    </body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Room Detail</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700|Work+Sans:300,400,700" rel="stylesheet">
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
            <div class="site-blocks-cover overlay" style="background-image: url(<c:url value='/template/images/hero_1.jpg'/>);" data-aos="fade" data-stellar-background-ratio="0.5">
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
                            <h2 class="mb-5">Rooms detail</h2>
                        </div>
                    </div>
                    <div class="row">
                        <form action="user/create_order" method="post">
                            <div class="col-md">
                                <div class="card">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="images p-3">
                                                <img src="images/${roomDetail.image}" alt=""  class="img img-fluid">
                                                <%--                      <img src="images/${roomdetail.image}" alt="" class="img-fluid">--%>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="product p-4">
                                                <div class="mt-4 mb-3"> <span class="text-uppercase text-muted brand">${roomDetail.name}</span>
                                                    <%--                    <h5 class="text-uppercase">${roomdetail.description}</h5>--%>
                                                    <div class="price d-flex flex-row align-items-center"> <span class="act-price">${roomdetail.price}</span>
                                                        <c:set var="priceDis" scope="session" value="${roomDetail.price/100 * roomdetail.discountPrice}"/>
                                                        <div class="ml-2"> <span class="dis-price">${roomDetail.price - priceDis} đ</span> <span>(${roomDetail.discountPrice}% OFF)</span </div>
                                                        <br>
                                                        <p> số người : ${roomDetail.peopleNumber}</p>
                                                        <br>
                                                        <p> số giường : ${roomDetail.bedNumber}</p>
                                                        <br>
                                                        <c:if test="${roomDetail.status ==0}">
                                                            <p>Trạng thái: Đã đặt </p>
                                                        </c:if>
                                                        <c:if test="${roomDetail.status ==1}">
                                                            <p>Trạng thái: Còn phòng </p>
                                                        </c:if>
                                                        <c:if test="${roomDetail.status ==2}">
                                                            <p>Trạng thái: Đang sửa chữa </p>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <p class="about">Mô tả: ${roomDetail.description}</p>
                                                <input type="hidden" name="amount" value="1">
                                                <input type="hidden" name="refType" value="0">
                                                <input type="hidden" name="unit" value="1">
                                                <input type="hidden" name="refId" value="${roomDetail.id}">
                                                <input type="hidden" name="roomId" value="${roomDetail.id}">
                                                <input type="hidden" name="priceRef" value="${roomDetail.price}">
                                                <input type="hidden" name="nameRef" value="${roomDetail.name}">
                                                <div class="cart mt-4 align-items-center">
                                                    <c:if test="${roomDetail.status ==1}">
                                                        <button type="submit" class="btn btn-danger text-uppercase mr-2 px-4">Đặt phòng</button>
                                                    </c:if>
                                                    <i class="fa fa-heart text-muted"></i>
                                                    <i class="fa fa-share-alt text-muted"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
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

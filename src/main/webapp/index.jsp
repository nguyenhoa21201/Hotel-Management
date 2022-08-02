<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> >
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ngàn Sao &mdash; Hotel</title>
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


            <div class="slide-one-item home-slider owl-carousel">

                <div class="site-blocks-cover overlay" style="background-image: url(<c:url value='template/images/hero_1.jpg'/>);" data-aos="fade" data-stellar-background-ratio="0.5">
                    <div class="container">
                        <div class="row align-items-center justify-content-center">
                            <div class="col-md-7 text-center" data-aos="fade">

                                <h1 class="mb-2">Welcome To Suites</h1>
                                <h2 class="caption">Hotel &amp; Resort</h2>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="site-blocks-cover overlay" style="background-image: url(<c:url value='template/images/hero_2.jpg'/>);" data-aos="fade" data-stellar-background-ratio="0.5">
                    <div class="container">
                        <div class="row align-items-center justify-content-center">
                            <div class="col-md-7 text-center" data-aos="fade">
                                <h1 class="mb-2">Unique Experience</h1>
                                <h2 class="caption">Enjoy With Us</h2>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="site-blocks-cover overlay" style="background-image: url(<c:url value='template/images/hero_3.jpg'/>);" data-aos="fade" data-stellar-background-ratio="0.5">
                    <div class="container">
                        <div class="row align-items-center justify-content-center">
                            <div class="col-md-7 text-center" data-aos="fade">
                                <h1 class="mb-2">Relaxing Room</h1>
                                <h2 class="caption">Your Room, Your Stay</h2>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="site-section bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6 mx-auto text-center mb-5 section-heading">
                            <h2 class="mb-5">Danh sách phòng</h2>
                        </div>
                    </div>
                    <div class="row">
                        <c:forEach items="${rooms}" var="r">
                            <div class="col-md-6 col-lg-4 mb-5">
                                <div class="hotel-room text-center">
                                    <a href="/room_detail?idroom=${r.id}" class="d-block mb-0 thumbnail"><img src="images/${r.image}" alt="Image" class="img-fluid"></a>
                                    <div class="hotel-room-body">
                                        <h3 class="heading mb-0"><a href="/room_detail?idroom=${r.id}">${r.name}</a></h3>
                                        <strong class="price">${r.price} đ / đêm</strong>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>


            <div class="site-section">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-md-6 mb-5 mb-md-0">

                            <div class="img-border">
                                <a href="<c:url value='https://vimeo.com/28959265'/>" class="popup-vimeo image-play">
                                    <span class="icon-wrap">
                                        <span class="icon icon-play"></span>
                                    </span>
                                    <img src="<c:url value='/template/images/img_2.jpg'/>" alt="" class="img-fluid">
                                </a>
                            </div>

                            <img src="<c:url value='/template/images/img_1.jpg'/>" alt="Image" class="img-fluid image-absolute">

                        </div>
                        <div class="col-md-5 ml-auto">


                            <div class="section-heading text-left">
                                <h2 class="mb-5">Thông tin</h2>
                            </div>
                            <p class="mb-4">Tọa lạc tại vị trí đắc địa trên con đường vàng Trần Phú, khách sạn Ngàn Sao Nha Trang đạt tiêu chuẩn năm sao là điểm đến yêu thích của nhiều du khách trong và ngoài nước. Với 276 phòng được thiết kế độc đáo trực diện biển, nội thất sang trọng được nhập khẩu hoàn toàn từ Châu Âu, hệ thống dịch vụ đa dạng đáp ứng đầy đủ nhu cầu của khách hàng cùng đội ngũ nhân viên được đào tạo chuyên nghiệp, Ngàn Sao Nha Trang hứa hẹn sẽ mang đến cho quý khách những trải nghiệm tuyệt vời tại thành phố biển Nha Trang.</p>
                            <p><a href="https://vimeo.com/28959265" class="popup-vimeo text-uppercase">Xem Video <span class="icon-arrow-right small"></span></a></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="site-section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6 mx-auto text-center mb-5 section-heading">
                            <h2 class="mb-5">Dịch Vụ</h2>
                        </div>
                    </div>
                    <div class="row">
                        <c:forEach items="${services}" var="sv">
                            <div class="col-md-6 col-lg-4 mb-5">
                                <div class="hotel-room text-center">
                                    <img src="images/${sv.image}" alt="Image" class="img-fluid"></a>
                                    <div class="hotel-room-body">
                                        <h3 class="heading mb-0"><b href="/room_detail?idroom=${sv.id}">${sv.name}</b></h3>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
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
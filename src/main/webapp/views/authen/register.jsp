<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style type="text/css">
        * {
            padding: 0px;
            margin: 0px;
        }
        body {
            background-color: lightgreen;
        }
        header {
            background-color: black;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 15vh;
            box-shadow: 5px 5px 10px rgb(0,0,0,0.3);
        }
        h1 {
            letter-spacing: 1.5vw;
            font-family: 'system-ui';
            text-transform: uppercase;
            text-align: center;
        }
        main {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 75vh;
            width: 100%;
            background: url(https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Mountains-1412683.svg/1280px-Mountains-1412683.svg.png) no-repeat center center;
            background-size: cover;
        }
        .form_class {
            width: 500px;
            padding: 40px;
            border-radius: 8px;
            background-color: white;
            font-family: 'system-ui';
            box-shadow: 5px 5px 10px rgb(0,0,0,0.3);
        }
        .form_div {
            text-transform: uppercase;
        }
        .form_div > label {
            letter-spacing: 3px;
            font-size: 1rem;
        }
        .info_div {
            text-align: center;
            margin-top: 20px;
        }
        .info_div {
            letter-spacing: 1px;
        }
        .field_class {
            width: 100%;
            border-radius: 6px;
            border-style: solid;
            border-width: 1px;
            padding: 5px 0px;
            text-indent: 6px;
            margin-top: 10px;
            margin-bottom: 20px;
            font-family: 'system-ui';
            font-size: 0.9rem;
            letter-spacing: 2px;
        }
        .submit_class {
            border-style: none;
            border-radius: 5px;
            background-color: #FFE6D4;
            padding: 8px 20px;
            font-family: 'system-ui';
            text-transform: uppercase;
            letter-spacing: .8px;
            display: block;
            margin: auto;
            margin-top: 10px;
            box-shadow: 2px 2px 5px rgb(0,0,0,0.2);
            cursor: pointer;
        }
        footer {
            height: 10vh;
            background-color: black;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: -5px -5px 10px rgb(0,0,0,0.3);
        }
        footer > p {
            text-align: center;
            font-family: 'system-ui';
            letter-spacing: 3px;
        }
        footer > p > a {
            text-decoration: none;
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body>
<header>
    <h1>Wellcome to Ngan Sao Hotel</h1>
</header>
<main>

    <form id="register_form" class="form_class" name="registerform" action="" method="post">
        <h3> ${ message } </h3>
            <div class="form_div">
                <label>Tên tài khoản:</label>
                <input class="field_class" name="username" required type="text" placeholder="nhập tên tài khoản" autofocus >
                <label>Mật khẩu:</label>
                <input id="pass" class="field_class" name="password" required type="password" placeholder="nhập mật khẩu" >
                <label>Họ tên:</label>
                <input id="hoten" class="field_class" name="name" type="text" placeholder="" required>
                <label>Ngày sinh:</label>
                <input id="ngaysinh" class="field_class" name="birthDay" type="date" required>
                <label>Địa chỉ:</label>
                <input id="diachi" class="field_class" name="address" type="text" required>
                <label>Email:</label>
                <input type="email" class="field_class" pattern=".+@gmail\.com" id="email" name="email" required>
                <label>Số điện thoại</label>
                <input type="text"  class="field_class" id="phone" name="phone" required>
                <button class="submit_class" type="submit" form="register_form" onclick="return validarLogin()">Đăng ký</button>
            </div>
            <div class="info_div">
                <p>Trở về trang đăng nhập? <a href="${pageContext.request.contextPath}/authen/login">Trở lại</a></p>
            </div>
    </form>
</main>

</body>
</html>
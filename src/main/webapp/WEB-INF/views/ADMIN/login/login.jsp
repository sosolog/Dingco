<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body style="background-color:#444;">
<div id="login">
    <form action="/admin/login.action" name="loginForm" method="post">
        <div class="wrap_loginBox">
            <div class="title"><span> Pedal </span></div>
            <div class="subtitle"><span> Administration </span></div>
            <table class="loginBox">
                <tr>
                    <td><span>ID</span></td>
                    <td><input id="userid" name="userid" placeholder="아이디를 입력하세요" autocomplete="off"></td>
                </tr>
                <tr>
                    <td><span>PW</span></td>
                    <td><input type="password" name="passwd" id="passwd"
                               onkeypress="javascript:if(event.keyCode==13) {loginValidCheck(loginForm)}"
                               placeholder="비밀번호를 입력하세요" autocomplete="off"></td>
                </tr>
            </table>
            <a class="btn_login" onclick="loginValidCheck(loginForm)"><span>Log In</span></a>
            <span id="result" class="login-result"></span>
        </div>
    </form>
</div>
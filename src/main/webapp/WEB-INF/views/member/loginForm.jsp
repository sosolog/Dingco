<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<h2>로그인 페이지</h2>
<form id="loginForm">
    * 아이디:<input type="text" name="userid" id="userid" autofocus>
    * 비밀번호:<input type="password" name="passwd" id="passwd">
    <input type="button" onclick="return loginValidCheck()" value="로그인"><br>
    <span id="result" style="color:red">
    </span>

</form>
    <button id="joinBtn" onclick="location.href='join'">회원가입</button><br>
    <button id="find_ID_PWBtn" onclick="location.href='find_ID_PW'">아이디/비밀번호 찾기</button><br>
    <button id="kakaoLoginBtn">카카오톡으로 로그인</button><br>
    <button id="googleLoginBtn">구글로 로그인</button><br>
    <button id="naverLoginBtn">네이버로 로그인</button><br>
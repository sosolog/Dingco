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
    <c:set var="REST_API_KEY" value="ee5887b0e2e8cce297b9421bb915bc70"/>
    <c:set var="REDIRECT_URI" value="http://localhost:9090/kakaologin"/>

    <button id="joinBtn" onclick="location.href='join'">회원가입</button><br>
    <button id="find_ID_PWBtn" onclick="location.href='find_ID_PW'">아이디/비밀번호 찾기</button><br>
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code"><img src="${pageContext.request.contextPath}/images/kakao_login_medium_wide.png"></a>
    <br>
    <button id="googleLoginBtn">구글로 로그인</button><br>
    <button id="naverLoginBtn">네이버로 로그인</button><br>
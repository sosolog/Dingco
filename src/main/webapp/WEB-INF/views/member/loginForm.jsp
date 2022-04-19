<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>

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

<!-- 네이버 로그인 버튼 노출 영역 -->
<div id="naver_id_login"></div>
<!-- //네이버 로그인 버튼 노출 영역 -->
<script type="text/javascript">
    var naver_id_login = new naver_id_login("srtVLSBDQTIgJD7D65Ls", "http://localhost:9090/callback");
    var state = naver_id_login.getUniqState();
    naver_id_login.setButton("white", 2,40);
    naver_id_login.setDomain("http://localhost:9090/login");
    naver_id_login.setState(state);
    naver_id_login.init_naver_id_login();
</script>
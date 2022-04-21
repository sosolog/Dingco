<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>

<h2>로그인 페이지</h2>
<form id="loginForm">
    <span id="result" style="color:red">
    </span><br>
    * 아이디:<input type="text" name="userid" id="userid" autofocus>
    * 비밀번호:<input type="password" name="passwd" id="passwd">
    <input type="button" onclick="return loginValidCheck()" value="로그인"><br>

</form>
    <c:set var="REST_API_KEY" value="ee5887b0e2e8cce297b9421bb915bc70"/>
    <c:set var="REDIRECT_URI" value="http://localhost:9090/kakaologin"/>

    <button id="joinBtn" onclick="location.href='join'">회원가입</button><br>
    <button id="find_ID_PWBtn" onclick="location.href='find_ID_PW'">아이디/비밀번호 찾기</button><br>
<div id="gSignInWrapper">
        <a href="http://localhost:9090/auth/GOOGLE">
    <div id="customBtn" class="customGPlusSignIn">
        <span class="icon"></span>
        <span class="buttonText">Google</span>
    </div>
        </a>
</div>

<div id="name"></div>
<script>startApp();</script>
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code"><img src="${pageContext.request.contextPath}/images/kakao_login_medium_wide.png"></a>
    <br>
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
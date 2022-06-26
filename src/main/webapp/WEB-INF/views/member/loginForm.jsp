<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="login">
    <span class="logo">Pedal</span>
    <form name="loginForm" class="loginForm" action="/login.action" method="post">
        <div class="input_wrap">
            <input type="text" name="userid" id="userid" placeholder="아이디">
            <input type="password" name="passwd" id="passwd" placeholder="패스워드"
                   onkeydown="if(event.keyCode === 13) loginValidCheck(loginForm);">
        </div>
        <span class="result" id="result"></span>
        <a class="btn_login" onclick="loginValidCheck(loginForm)"><span>로그인</span></a>
    </form>
    <div class="options">
        <div class="box">
            <a href="/find/userid"><span>아이디 찾기</span></a>
            <div class="wall"></div>
            <a href="/find/passwd"><span>비밀번호 찾기</span></a>
            <div class="wall"></div>
            <a href="/join"><span>회원가입</span></a>
            <div class="reset"></div>
        </div>
    </div>

    <div class="snslogin">
        <div id="naver_id_login" style="display: none"></div>
        <div class="loginimg" onclick="naverlogin()"><img src="/images/snslogin/naver_login_01.png"></div>
<%--        <div class="loginimg"><a href="http://localhost:9090/login/oauth/google"><img src="/images/snslogin/google_login_01.png"></a></div>--%>
<%--        <div class="loginimg"><a href="http://3.37.182.2:9090/login/oauth/google"><img src="/images/snslogin/google_login_01.png"></a></div>--%>
        <div class="loginimg"><a href="https://www.pedal-dingco.site/login/oauth/google"><img src="/images/snslogin/google_login_01.png"></a></div>
        <!--카카오 로그인-->
        <div class="loginimg">
            <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URI}">
                <img src="/images/snslogin/kakao_login_01.png">
            </a>
        </div>
    </div>

    <div id="name"></div>
    <br>
</div>



<script type="text/javascript">
    var naver_id_login = new naver_id_login("srtVLSBDQTIgJD7D65Ls", "https://www.pedal-dingco.site/callback");
    var state = naver_id_login.getUniqState();
    naver_id_login.setButton("white", 2,40);
    naver_id_login.setDomain("https://www.pedal-dingco.site/login");
    naver_id_login.setState(state);
    naver_id_login.init_naver_id_login();
</script>

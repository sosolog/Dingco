<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<h2>소셜 회원가입 페이지</h2>
<br>
    ${snsLoginDTO}
<form id="socialMemberKakaoLogin" name="socialMemberKakaoLogin">
    <input type="hidden" name="snsType" value="kakao">
    <input type="hidden" id="kakao_idx" name="kakao_idx">
    <%--<input type="hidden" id="socialIdCheckResult" value="false">--%>
    * 아이디:<input type="text" id="userid" name="userid">
    <span id="idCheckResult"></span><br>
    * 이름:<input id= "username" name="username" readonly="readonly"><br>
    <br>
    * 이메일:<input name="email1" id="email1" readonly="readonly">@
    <input name="email2" id="email2" readonly="readonly">
    <br>
    <br>
    <input type="button" value="회원가입" onclick="socialLoginValidCheck(socialMemberKakaoLogin)"/>
</form>

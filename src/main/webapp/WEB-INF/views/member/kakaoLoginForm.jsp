<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<h2>소셜 회원가입 페이지</h2>
<br>
<c:set var="kakao_idx" value="${snsLoginDTO.kakao_idx}"/>
<c:set var="username" value="${snsLoginDTO.username}"/>

<form id="socialMemberKakaoLogin" name="socialMemberKakaoLogin" method="post" action="/login/kakao.action">
    <input type="hidden" id="kakao_idx" name="kakao_idx" value="${kakao_idx}">
    * 아이디:<input type="text" id="userid" name="userid">
    <span id="idCheckResult"></span><br>
    * 이름:<input id= "username" name="username" readonly="readonly" value="${username}"><br>
    <br>
    <input type="button" value="회원가입" onclick="socialLoginValidCheck(socialMemberKakaoLogin)"/>
</form>

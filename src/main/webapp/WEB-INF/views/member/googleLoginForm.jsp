<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <h2>소셜 회원가입 페이지</h2>
    <br>

<form id="socialMemberGoogleLogin" name="socialMemberGoogleLogin" action="/auth/google/loginCheck" method="post">
    <input type="hidden" id="google_idx" name="google_idx" value="${google_idx}">
    * 아이디:<input type="text" id="userid" name="userid" >
    <span id="idCheckResult"></span><br>
    * 이름:<input id= "username" name="username" readonly="readonly" value="${username}"><br>
    <br>
    <br>
    <input type="button" value="회원가입" onclick="socialLoginValidCheck(socialMemberGoogleLogin)"/>
</form>

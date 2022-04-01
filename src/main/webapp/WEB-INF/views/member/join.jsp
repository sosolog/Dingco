<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>로그인 페이지</h2>
<form action="memberAdd" method="post" onsubmit="">
    * 아이디:<input type="text" name="userid" id="userid" onkeyup="req()">
    <span id="result" style="color:red"></span><br>
    * 비밀번호:<input type="text" name="passwd" id="passwd" ><br>
    비밀번호확인:<input type="text" name="passwd2" id="passwd2" onkeyup="pwcheck()" >
    <span id="result2" style="color:#722424" ></span>
    <br>
    이름:<input type="text" name="username"><br>

    <span id="guide" style="color:#999"></span><br>
    전화번호:<select name="phone1">
    <option value="010">010</option>
    <option value="011">011</option>
</select>-
    <input type="text" name="phone2" >-
    <input type="text" name="phone3" >
    <br>
    <br>
    이메일:<input type="text" name="email1" id="email1">@
    <input type="text" name="email2" id="email2" placeholder="직접입력">
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="취소">
</form>

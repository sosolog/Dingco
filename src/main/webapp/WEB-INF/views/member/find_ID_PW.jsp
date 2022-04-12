<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h4>비밀번호 찾기</h4>
<div style="color: #ac2925">
    <center>입력된 정보로 임시 비밀번호가 전송됩니다.</center>
</div>
<hr>
<form role="form">
    <label for="userEmail"><span class="glyphicon glyphicon-user"></span>email</label>
    <input type="text" id="userEmail" placeholder="가입시 이메일을 입력하세요.">
<br>
    <label for="userid"><span class="glyphicon glyphicon-eye-open"></span>id</label>
    <input type="text" id="userid" placeholder="아이디를 입력하세요.">
<br>
    <button type="button" id="checkEmail" onclick="pw_CheckAndSendMail()">임시비밀번호 발송</button>
</form>
<hr>
<div class="text-center small mt-2" id="checkMsg" style="color: red"></div>

<button onclick="location.href='login'">취소</button>
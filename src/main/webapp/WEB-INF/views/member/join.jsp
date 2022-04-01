<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(function () {
        $("#url").on("change", function () {
            $("#email2").val($("#url option:selected").val());
        });
    });

</script>
<h2>회원가입 페이지</h2>
<br>
<form action="memberAdd" id="memberAdd" method="post" onsubmit="">
    * 아이디:<input type="text"  id="userid" name="userid"onkeyup="">
    <span id="idCheckResult" style="color:red"></span><br><br>
    * 비밀번호:<input type="text"  id="passwd" name="passwd"><br><br>
    * 비밀번호확인:<input type="text" id="passwd1" name="passwd1" onkeyup="" >
    <span id="pwCheckResult" style="color:#722424" ></span>
    <br><br>
    * 이름:<input type="text" id= "name" name="name"><br><br>
    * 전화번호:<select name="phone1">
        <option value="010">010</option>
        <option value="011">011</option>
            </select>-
    <input type="text" name="phone2" >-
    <input type="text" name="phone3" >
    <button>휴대전화 인증</button>
    <br>
    <br>
    <img src="images/KakaoTalk_Photo_2022-04-01-19-55-13.jpeg", width="200", height="150"><br>
    <button>프로필 사진 등록</button><br><br>
    이메일:<input type="text" name="email1" id="email1">@
    <input type="text" name="email2" id="email2">
    <select id="url">
        <option value="daum.net">daum.net</option>
        <option value="naver.com">naver.com</option>
        <option value="google.com">google.com</option>
    </select>
    <br>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="취소">
</form>

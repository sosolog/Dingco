<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(function () {
        $("#url").on("change", function () {
            $("#email2").val($("#url option:selected").val());
        });
    });
</script>
<script>
    <c:set var="memberDTO" value="${memberDTO}"/>
    <c:set var="errors" value="${errors}"/>
</script>



<h2>회원가입 페이지</h2>
<!--<p class="errors_join">${errors}</p> -->

<br>
<form action="memberAdd" id="memberAdd" method="post" onsubmit="">
    * 아이디:<input type="text"  id="userid" name="userid"onkeyup="" value="${memberDTO.userid}" >
    <button>아이디 중복확인</button><br>
    <c:if test = "${fn:contains(errors, 'getUserid')}">
        <span id = "errors_userid" style="color:red">${errors['getUserid']}</span>
    </c:if>
    <br>
    * 비밀번호:<input type="text"  id="passwd" name="passwd" ><br><br>
    * 비밀번호 확인:<input type="text" id="passwd1" name="passwd1" onkeyup=""><br>
    <c:if test = "${fn:contains(errors, 'getPasswd')}">
        <span id = "errors_passwd" style="color:red">${errors['getPasswd']}</span>
    </c:if><br>
    <c:if test = "${fn:contains(errors, 'passwdCheck')}">
        <span id = "errors_passwdCheck" style="color:red">${errors['passwdCheck']}</span>
    </c:if><br>
    * 이름:<input type="text" id= "name" name="name" value="${memberDTO.name}"><br>
    <c:if test = "${fn:contains(errors, 'getName')}">
        <span id = "errors_name" style="color:red">${errors['getName']}</span>
    </c:if><br><br>
    * 전화번호:<select name="phone1" value="${memberDTO.phone1}">
        <option value="010">010</option>
        <option value="011">011</option>
            </select>-
    <input type="text" name="phone2" value="${memberDTO.phone2}">-
    <input type="text" name="phone3" value="${memberDTO.phone3}">
    <button>휴대전화 인증</button>
    <br>
    <c:if test = "${fn:contains(errors, 'getPhone')}">
        <span id = "errors_phone" style="color:red">${errors['getPhone']}</span>
    </c:if><br><br>
    <br>
    <img src="images/KakaoTalk_Photo_2022-04-01-19-55-13.jpeg", width="250", height="150"><br>
    <button>프로필 사진 등록</button><br><br>
    * 이메일:<input type="text" name="email1" id="email1" value="${memberDTO.email1}">@
    <input type="text" name="email2" id="email2" value="${memberDTO.email2}">
    <select id="url">
        <option value="daum.net">daum.net</option>
        <option value="naver.com">naver.com</option>
        <option value="google.com">google.com</option>
    </select>
    <br>
    <c:if test = "${fn:contains(errors, 'getEmail')}">
    <span id = "errors_email" style="color:red">${errors['getEmail']}</span>
    </c:if><br>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="취소">
</form>

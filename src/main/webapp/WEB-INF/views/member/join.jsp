<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script>
    $(function () {
        $("#url").on("change", function () {
            $("#email2").val($("#url option:selected").val());
        });
    });
</script>




<h2>회원가입 페이지</h2>

<br>
<form action="memberAdd" id="memberAdd" method="post">
    * 아이디:<input type="text" id="userid" name="userid" value="${memberDTO.userid}">
    <button>아이디 중복확인</button><br>
    <span>
        <spring:bind path="memberDTO.userid">
            ${status.errorMessage }
        </spring:bind>
    </span><br>
    <br>
    * 비밀번호:<input type="text"  id="passwd" name="passwd" ><br><br>
    * 비밀번호 확인:<input type="text" id="passwd1" name="passwd1" onkeyup=""><br>
    <span>
        <spring:bind path="memberDTO.passwd">
            ${status.errorMessage }
        </spring:bind>
    </span><br>
    * 이름:<input type="text" id= "name" name="name" value="${memberDTO.name}"><br>
    <span>
        <spring:bind path="memberDTO.name">
                ${status.errorMessage }
        </spring:bind>
    </span><br>
    * 전화번호:<select name="phone1" value="${memberDTO.phone1}">
        <option value="010">010</option>
        <option value="011">011</option>
            </select>-
    <input type="text" name="phone2" value="${memberDTO.phone2}">-
    <input type="text" name="phone3" value="${memberDTO.phone3}">
    <button>휴대전화 인증</button>
    <br>
    <spring:bind path="memberDTO.phone1">
        ${status.errorMessage }
    </spring:bind>
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
    <spring:bind path="memberDTO.email1">
        ${status.errorMessage }
    </spring:bind>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="취소">
</form>
<%--</form:form>--%>
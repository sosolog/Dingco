<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h2>회원가입 페이지</h2>
<br>
<form action="memberAdd" id="memberAdd" method="post" enctype="multipart/form-data">

    <c:if test="${idCheckHidden == true}">
        <input type="hidden" value="true" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    <c:if test="${idCheckHidden == false}">
        <input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    <c:if test="${idCheckHidden == null}">
        <input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    <input type="hidden" value="false" id="pwCheckHidden" name="pwCheckHidden">

    * 아이디:<input type="text" id="userid" name="userid" value="${memberDTO.userid}"><br>
    <span id = "idCheckResult">
        <spring:bind path="memberDTO.userid">
            ${status.errorMessage }
        </spring:bind>
    </span><br>
    <br>
    * 비밀번호:<input type="password"  id="passwd" name="passwd"><br><br><br>
    * 비밀번호 확인:<input type="password" id="passwd1" name="passwd1" onkeyup=""><br>
    <span id="pwCheckResult">
        <spring:bind path="memberDTO.passwd">
            ${status.errorMessage }
        </spring:bind><br>
    </span><br>
    * 이름:<input type="text" id= "username" name="username" value="${memberDTO.username}"><br>
    <span>
        <spring:bind path="memberDTO.username">
                ${status.errorMessage}
        </spring:bind><br>
    </span><br>
    * 전화번호:<select name="phone1" value="${memberDTO.phone1}">
        <option value="010">010</option>
        <option value="011">011</option>
            </select>-
    <input type="text" name="phone2" value="${memberDTO.phone2}">-
    <input type="text" name="phone3" value="${memberDTO.phone3}">
    <br>
    <spring:bind path="memberDTO.phone2">
        ${status.errorMessage}
    </spring:bind>
    <spring:bind path="memberDTO.phone3">
        ${status.errorMessage}
    </spring:bind>
    <br>
    <br>
    <input id="file" name = "file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic"/><br>
    <br>프로필 사진 업로드(크기:3MB 이내, 확장자:gif,jpg,png,bmp,jpeg,heic)<br>
    <!-- accept: 지정한 확장자 이외에는 클릭 자체가 안됨-->

    <br><br>
    * 이메일:<input type="text" name="email1" id="email1" value="${memberDTO.email1}">@
    <input type="text" name="email2" id="email2" value="${memberDTO.email2}">
    <select id="url">
        <option value="">이메일 선택</option>
        <option value="daum.net">daum.net</option>
        <option value="naver.com">naver.com</option>
        <option value="gmail.com">gmail.com</option>
    </select>
    <br>
    <spring:bind path="memberDTO.email1">
        ${status.errorMessage }
    </spring:bind>
    <spring:bind path="memberDTO.email2">
        ${status.errorMessage }
    </spring:bind>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="다시작성" onclick="location.href='join'">
    <input type="button" value="뒤로가기" onclick="location.href='login'">
</form>

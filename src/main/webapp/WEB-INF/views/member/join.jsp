<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h2>회원가입 페이지</h2>
<br>
<form action="memberAdd" id="joinForm" name="joinForm" method="post" enctype="multipart/form-data" onsubmit="return joinSubmitCheck()">

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

    <c:if test="${mobileCheckHidden == true}">
        <input type="hidden" value="true" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    <c:if test="${mobileCheckHidden == false}">
        <input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    <c:if test="${mobileCheckHidden == null}">
        <input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden">
    </c:if>
    * 아이디:<input type="text" id="userid" name="userid" value="${memberDTO.userid}" onkeyup="memberIdCheck()"><br>
    <span id = "idCheckResult">
        <spring:bind path="memberDTO.userid">
            ${status.errorMessage}
        </spring:bind>
    </span><br>
    <br>
    * 비밀번호:<input type="password"  id="passwd" name="passwd"><br><br><br>
    * 비밀번호 확인:<input type="password" id="passwd1" name="passwd1" onkeyup="memberPwCheck()"><br>
    <span id="pwCheckResult">
        <spring:bind path="memberDTO.passwd">
            ${status.errorMessage}
        </spring:bind><br>
    </span><br>
    * 이름:<input type="text" id= "username" name="username" value="${memberDTO.username}"><br>
    <span>
        <spring:bind path="memberDTO.username">
                ${status.errorMessage}
        </spring:bind><br>
    </span><br>
    <br>
    <input id="file" name = "file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic" onchange="checkFileSize()"/><br>
    <br>프로필 사진 업로드(크기:3MB 이내, 확장자:gif,jpg,png,bmp,jpeg,heic)<br>
    <!-- accept: 지정한 확장자 이외에는 클릭 자체가 안됨-->

    <br><br>
    * 이메일:<input type="text" name="email1" id="email1" value="${memberDTO.email1}">@
    <input type="text" name="email2" id="email2" value="${memberDTO.email2}">
    <input type="button" id="emailValidate" value="이메일 인증" onclick="emailValidationSend()"/>
    <spring:bind path="memberDTO.email1">
        ${status.errorMessage }
    </spring:bind>
    <spring:bind path="memberDTO.email2">
        ${status.errorMessage }
    </spring:bind>
    <br>
    <input type="text" placeholder="인증번호를 입력하세요" id="emailValidationCheckNumber"><input type="button" id="emailValidationCheck" value="인증 확인" onclick="emailValidationCheck()"/>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="다시작성" onclick="location.href='join'">
    <input type="button" value="뒤로가기" onclick="location.href='login'">
</form>

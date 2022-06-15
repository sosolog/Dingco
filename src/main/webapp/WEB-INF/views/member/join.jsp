<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="join">
    <form action="/join.action" id="joinForm" name="joinForm" method="post" enctype="multipart/form-data">
        <!-- ID/PW Check -->
        <c:if test="${idCheckHidden == true}"><input type="hidden" value="true" id="idCheckHidden" name="idCheckHidden"></c:if>
        <c:if test="${idCheckHidden == false || idCheckHidden == null}"><input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden"></c:if>
        <input type="hidden" value="false" id="pwCheckHidden" name="pwCheckHidden">

        <!-- Input Table -->
        <div class="profile">
            <label class="image" for="file">
                <c:if test="${storeFileName == null}"><img id="preview" src="/images/join/profile_no_image.png"></c:if>
                <c:if test="${storeFileName != null}"><img id="preview" src="/files/member/${storeFileName}"></c:if>
            </label>
            <input id="file" name="file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic" onchange="imageFileSizeCheck('file')"/>
            <span class="tit">프로필 사진 업로드</span>
            <span class="info">3MB 이내 (gif,jpg,png,bmp,jpeg,heic)</span>
        </div>
        <table class="join_table">
            <tr>
                <td class="item_th"><span>이름</span></td>
                <td class="item_box">
                    <div><input type="text" id="username" name="username" value="${memberDTO.username}" placeholder="이름을 입력해주세요"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="id_check"><spring:bind path="memberDTO.username">${status.errorMessage}</spring:bind></span></td>
            </tr>
            <tr>
                <td class="item_th"><span>아이디</span></td>
                <td class="item_box">
                    <div><input type="text" id="userid" name="userid" value="${memberDTO.userid}" placeholder="아이디를 입력해주세요" onkeyup="memberIdDuplicateCheck()"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span id="idCheckResult" class="id_check"><spring:bind path="memberDTO.userid">${status.errorMessage}</spring:bind></span></td>
            </tr>
            <tr>
                <td class="item_th"><span>비밀번호</span></td>
                <td class="item_box">
                    <div><input type="password" id="passwd" name="passwd" placeholder="비밀번호를 입력하세요" autocomplete="off"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="id_check"></span></td>
            </tr>
            <tr>
                <td class="item_th"><span>재확인</span></td>
                <td class="item_box">
                    <div><input type="password" id="passwd1" name="passwd1" onkeyup="memberPwDuplicateCheck()" placeholder="비밀번호를 다시 한번 입력하세요" autocomplete="off"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span id="pwCheckResult" class="id_check"><spring:bind path="memberDTO.passwd">${status.errorMessage}</spring:bind></span></td>
            </tr>
            <tr>
                <td class="item_th"><span>이메일</span></td>
                <td class="td_email">
                    <input type="text" name="email1" id="email1" value="${memberDTO.email1}" placeholder="앞자리 입력">
                    <span class="link">@</span>
                    <input type="text" name="email2" id="email2" value="${memberDTO.email2}" placeholder="직접 입력">
                </td>
            </tr>
            <tr>
                <td class="item_th"></td>
                <td class="item_box" style="position: relative">
                    <div><input type="text" id="emailValidationCheckNumber" name="emailValidationCheckNumber" value="${emailValidationCheckNumber}" placeholder="이메일 인증번호를 입력하세요" autocomplete="off"></div>
                    <a class="btn_sendemail2" onclick="emailValidateSend()"><span>인증번호 전송</span></a>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <span id="emailCheckResult" class="id_check">
                        <spring:bind path="memberDTO.email1">${status.errorMessage}</spring:bind>
                        <spring:bind path="memberDTO.email2">${status.errorMessage}</spring:bind>
                    </span>
                    <a class="btn_sendemail" onclick="emailValidateCheck()"><span>인증 확인</span></a>
                </td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <c:if test="${emailCheckHidden == true}"><input type="hidden" value="true" id="emailCheckHidden" name="emailCheckHidden"></c:if>
                    <c:if test="${emailCheckHidden == false || emailCheckHidden == null}"><input type="hidden" value="false" id="emailCheckHidden" name="emailCheckHidden"></c:if>
                </td>
            </tr>
        </table>
        <a class="btn_login" onclick="joinSubmitCheck(joinForm)"><span>가입하기</span></a>
    </form>

</div>


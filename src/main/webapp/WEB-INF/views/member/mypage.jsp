<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%------ Start : JSTL 변수 설정 ------%>

<c:set var="m_idx" value="${memberDTO.m_idx}"/>
<c:set var="userid" value="${memberDTO.userid}"/>
<c:set var="passwd" value="${memberDTO.passwd}"/>
<c:set var="username" value="${memberDTO.username}"/>
<c:set var="email1" value="${memberDTO.email1}"/>
<c:set var="email2" value="${memberDTO.email2}"/>
<c:set var="authorities" value="${memberDTO.authorities}"/>
<c:set var="joindate" value="${memberDTO.joindate}"/>
<c:set var="uploadFileName" value="${memberDTO.uploadFileName}"/>
<c:set var="storeFileName" value="${memberDTO.storeFileName}"/>

<%------ End : JSTL 변수 설정 ------%>

<div id="mypage">
    <form action="/mypage.action" id="editMypage" name="editMypage" method="post" enctype="multipart/form-data">
        <div class="profile">
            <!-- Original -->
            <input type="hidden" name="oUploadFileName" value="${uploadFileName}">
            <input type="hidden" name="oStoreFileName" value="${storeFileName}">
            <!-- New -->
            <label class="image" for="file">
                <c:if test="${storeFileName == null}"><img id="preview" src="/images/join/profile_no_image.png"></c:if>
                <c:if test="${storeFileName != null}"><img id="preview" src="/files/member/${storeFileName}"></c:if>
            </label>
            <input id="file" name="file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic" onchange="imageFileSizeCheck('file')"/>
            <span class="tit">프로필 사진 업로드</span>
            <span class="info">3MB 이내 (gif,jpg,png,bmp,jpeg,heic)</span>
        </div>
        <input type="hidden" name="snslogin" value="${passwd}"/>
        <input type="hidden" name="m_idx" value="${m_idx}">
        <table class="join_table">
            <tr>
                <td class="item_th"><span>이름</span></td>
                <td class="item_box">
                    <div><input readonly="readonly" id="username" name="username" value="${username}"></div>
                </td>
            </tr>
            <tr>
                <td class="item_th"><span>아이디</span></td>
                <td class="item_box">
                    <div><input readonly="readonly" id="userid" name="userid" value="${userid}"></div>
                </td>
            </tr>
            <tr>
                <td class="item_th"><span>비밀번호</span></td>
                <td class="item_box">
                    <div><input type="password" id="passwd" name="passwd" onkeyup="passwd_check('editMypage')" placeholder="비밀번호를 입력하세요" autocomplete="off" maxlength="15"></div>
                </td>
            </tr>
            <tr>
                <td class="item_th"><span>재확인</span></td>
                <td class="item_box">
                    <div><input type="password" data-role="none" id="passwd2" name="passwd2" onkeyup="passwd_check('editMypage')" placeholder="비밀번호를 다시 한번 입력하세요" autocomplete="off" maxlength="15"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <div>
                    <spring:bind path="memberDTO.passwd">
                        ${status.errorMessage }
                    </spring:bind>
                </div>
                <input type="hidden" id="chk_pw" name="chk_pw" value="false" data-role="none">
                <td><span class="pw_check">비밀번호를 입력해주세요</span></td>
            </tr>
            <tr>
                <td class="item_th"><span>이메일</span></td>
                <td class="td_email">
                    <input readonly="readonly" name="email1" id="email1" value="${email1}">
                    <span class="link">@</span>
                    <input readonly="readonly" name="email2" id="email2" value="${email2}">
                </td>
            </tr>
            <tr>
                <td class="item_th">
                    <div><span>가입일</span></div>
                </td>
                <td class="item_box">
                    <div><input readonly="readonly" id="joindate" name="joindate" value="${joindate}"></div>
                </td>
            </tr>
        </table>
        <a class="btn_login" onclick="editUserForm_submit(editMypage)"><span>수정하기</span></a>
    </form>

</div>
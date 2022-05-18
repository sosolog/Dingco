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
    <form action="/editMypage.action" id="editMypage" name="editUserForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="snslogin" value="${passwd}"/>
        <table class="join_table">
            <tr style="display: none;">
                <td class="item_th"><span>회원 번호</span></td>
                <td class="item_box">
                    <div><input readonly id="m_idx" name="m_idx" value="${m_idx}"></div>
                </td>
            </tr>
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
            <tr style="display: none;">
                <td class="item_box">
                    <div><input type="hidden" id="passwd" name="passwd" value="pedal1234" onkeyup="passwd_check('wrap_editUser')"
                                placeholder="비밀번호를 입력하세요" autocomplete="off"></div>
                </td>
            </tr>
            <tr style="display: none;">
                <td></td>
                <input type="hidden" id="chk_pw" name="chk_pw" value="true">
                <td><span class="pw_check"></span></td>
            </tr>
            <tr style="display: none;">
                <td class="item_th"><div>프로필 사진 업로드</div></td>
                <td>
                    <input type="hidden" name="oUploadFileName" value="${uploadFileName}">
                    <input type="hidden" name="oStoreFileName" value="${storeFileName}">
                    <input id="file" name="file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic" onchange="checkFileSize()"/>
                    <div>기존 사진 : ${uploadFileName} </div>
                    <div><img src="/files/member/${storeFileName}" style="width:100px; height:100px; overflow: hidden; object-fit: cover;"></div>
                    <div>프로필 사진 업로드(크기:2MB 이내, 확장자:gif,jpg,png,bmp,jpeg,heic)</div>
                    <!-- accept: 지정한 확장자 이외에는 클릭 자체가 안됨-->
                </td>
            </tr>
            <tr style="display: none;">
                <td class="td_email">
                    <input type="hidden" name="email1" id="email1" value="pedalemail" autocomplete="off">
                    <input type="hidden" name="email2" id="email2" value="pedalemail" autocomplete="off">
                </td>
            </tr>
            <tr>
                <td class="item_th"><span>가입일</span></td>
                <td class="item_box">
                    <div><input readonly="readonly" id="joindate" name="joindate" value="${joindate}"></div>
                </td>
            </tr>
        </table>
<%--        <a class="btn_login" onclick="editUserForm_submit(editUserForm)"><span>수정하기</span></a>--%>
    </form>
</div>

<%------ End : HTML ------%>
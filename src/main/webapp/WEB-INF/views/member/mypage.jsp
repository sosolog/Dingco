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

<div id="wrap_editUser">
    <div id="main">
        <div class="title">
			<h2> 사용자 정보 수정 </h2>
        </div>
        <form action="/editMypage.action" id="editUser_form" name="editUserForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="snslogin" value="${passwd}"/>
            <table class="editUser_table">
                <tr>
                    <td class="item_th">
                        <div><span>회원 번호</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly id="m_idx" name="m_idx" value="${m_idx}"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>이름</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly id="username" name="username" value="${username}"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>아이디</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly id="userid" name="userid" value="${userid}"></div>
                    </td>
                </tr>
                <c:if test="${passwd}!=''">
                <tr>
                    <td class="item_th">
                        <div><span>비밀번호</span></div>
                    </td>
                    <td class="item_box">
                        <div><input type="password" id="passwd" name="passwd" value="" onkeyup="passwd_check('wrap_editUser')"
                                    placeholder="비밀번호를 입력하세요" autocomplete="off"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>재확인</span></div>
                    </td>
                    <td class="item_box">
                        <div><input type="password" id="passwd2" name="passwd2" onkeyup="passwd_check('wrap_editUser')"
                                    placeholder="비밀번호를 다시 한번 입력하세요" autocomplete="off"></div>
                        <div>
                            <spring:bind path="memberDTO.passwd">
                                ${status.errorMessage }
                            </spring:bind>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <input type="hidden" id="chk_pw" name="chk_pw" value="false">
                    <td><span class="pw_check"></span></td>
                </tr>
                </c:if>
                <tr>
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
                <tr>
                    <td class="item_th"><div>이메일</div></td>
                    <td class="td_email">
                        <input type="text" name="email1" id="email1" value="${email1}" autocomplete="off">
                        <span class="link">@</span>
                        <input type="text" name="email2" id="email2" value="${email2}" autocomplete="off">
                        <select id="emailSelect" onchange="f_emailSelect(this)">
                            <option value="">직접입력</option>
                            <option value="daum.net">daum.net</option>
                            <option value="naver.com">naver.com</option>
                            <option value="gmail.com">gmail.com</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><span class="email_check">
                        <spring:bind path="memberDTO.email1">
                            ${status.errorMessage }
                        </spring:bind>
                        <spring:bind path="memberDTO.email2">
                            ${status.errorMessage }
                        </spring:bind>
                    </span></td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>가입일</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly id="joindate" name="joindate" value="${joindate}"></div>
                    </td>
                </tr>
            </table>
            <div class="wrap_btn" style="width: 100px; height: 40px; background-color: #888; text-align: center; margin-top:10px;">
                <a class="submit_box" onclick="editUserForm_submit(editUserForm)" style="cursor:pointer; display: inline-block; margin-top:10px; color:#fff;">수정하기</a>
            </div>
        </form>
    </div>
</div>

<%------ End : HTML ------%>
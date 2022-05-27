<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--등록 모드-->
<c:if test="${empty memberDTO.m_idx}">
    <c:set var="mode" value="추가"/>
    <c:set var="m_idx" value=""/>
</c:if>
<!--수정 모드-->
<c:if test="${not empty memberDTO.m_idx}">
    <c:set var="mode" value="수정"/>
    <c:set var="m_idx" value="${memberDTO.m_idx}"/>
    <c:set var="username" value="${memberDTO.username}"/>
    <c:set var="userid" value="${memberDTO.userid}"/>
    <c:set var="passwd" value="${memberDTO.passwd}"/>
    <c:if test="${not empty memberDTO.email1}">
        <c:set var="email1" value="${memberDTO.email1}"/>
        <c:set var="email2" value="${memberDTO.email2}"/>
    </c:if>
    <c:set var="uploadFileName" value="${memberDTO.uploadFileName}"/>
    <c:set var="joindate" value="${memberDTO.joindate}"/>
    <c:if test="${not empty memberDTO.kakao_idx}">
        <c:set var="isSNSUser" value="카카오 (ID: ${memberDTO.kakao_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.google_idx}">
        <c:set var="isSNSUser" value="구글 (ID: ${memberDTO.google_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.naver_idx}">
        <c:set var="isSNSUser" value="네이버 (ID: ${memberDTO.naver_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.email1}">
        <c:set var="isSNSUser" value="X"/>
    </c:if>
</c:if>

<form action="" name="editForm" method="post" enctype="multipart/form-data">
<div id="main-box">
    <div id="top-menu">
        <span class="title">사용자 정보 ${mode}</span>
        <a class="btn-save" onclick="saveEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="history.back()"><span>취소</span></a>
    </div>
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>회원번호</td>
                    <td><input readonly="readonly" name="" value="${m_idx}"></td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>프로필 사진</td>
                    <td><span>${uploadFileName}</span></td>
                </tr>
                <tr class="short-line">
                    <td>이름</td>
                    <td><input name="" placeholder="이름을 입력해주세요" value="${username}"></td>
                </tr>
                <tr class="short-line">
                    <td>아이디</td>
                    <td><input name="" placeholder="아이디를 입력해주세요" value="${userid}"></td>
                </tr>
                <c:if test="${not empty memberDTO.email1}">
                <tr class="short-line">
                    <td>이메일</td>
                    <td><input name="" placeholder="이메일을 입력해주세요 (ex. abcde@naver.com)" value="${email1}@${email2}"></td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>비밀번호</td>
                    <td><input name="" placeholder="비밀번호를 입력해주세요" value="${passwd}"></td>
                </tr>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>SNS 회원</td>
                    <td><input readonly="readonly" value="${isSNSUser}"></td>
                </tr>
                <tr class="short-line">
                    <td>가입일</td>
                    <td><input readonly="readonly" value="${joindate}"></td>
                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
</form>

<!--삭제 버튼-->
<c:if test="${mode == '수정'}">
    <a id="btn-delete" href="/admin/member/delete?idx=${m_idx}&role=USER"><img src="/ADMIN/images/remove.png"></a>
</c:if>
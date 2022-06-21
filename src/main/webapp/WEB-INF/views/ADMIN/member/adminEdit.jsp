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
    <c:set var="joindate" value="${memberDTO.joindate}"/>
</c:if>

<form action="/admin/member/admin/edit.action" name="editForm" method="post">
<input type="hidden" name="mode" value="${mode}">
<div id="main-box">
    <div id="top-menu">
        <span class="title">관리자 정보 ${mode}</span>
        <a class="btn-save" onclick="saveAdminEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="history.back()"><span>취소</span></a>
    </div>
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>관리자 번호</td>
                    <td><input readonly="readonly" name="m_idx" value="${m_idx}"></td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>이름</td>
                    <td><input name="username" placeholder="이름을 입력해주세요" value="${username}"></td>
                </tr>
                <tr class="short-line">
                    <td>아이디</td>
                    <td>
                        <input type="hidden" name="o_userid" id="o_userid" value="${userid}">
                        <input name="userid" id="userid" placeholder="아이디를 입력해주세요" value="${userid}" onkeyup="inputID()" style="width: 300px; margin-top: 6px;">
                        <span id="useridCheckResult" style="color: #E74133; display: inline-block;"></span>
                        <a onclick="dupliCheckID()" style="padding: 7px 13px; border: 1px solid #999999; float:right;"><span>중복확인</span></a>
                        <input name="chk_id" id="chk_id" type="hidden" value="false">
                        <c:if test="${mode == '수정'}">
                            <a onclick="returnID()" style="padding: 7px 13px; border: 1px solid #999999; float:right; margin-right: 10px;"><span>기존 아이디 사용</span></a>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${mode == '추가'}">
                    <tr class="short-line">
                        <td>비밀번호</td>
                        <td>
                            <input name="passwd" placeholder="비밀번호를 입력해주세요" value="${passwd}" style="width: 300px;">
                            <span id="passwdCheckResult" style="color: #E74133; display: inline-block;"></span>
                        </td>
                    </tr>
                </c:if>
                <%--<c:if test="${mode == '수정'}">
                    <tr class="short-line">
                        <td>비밀번호</td>
                        <td><a style="padding: 7px 13px; border: 1px solid #999999;"><span>비밀번호 초기화</span></a></td>
                    </tr>
                </c:if>--%>
                <c:if test="${mode == '수정'}">
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
    <a id="btn-delete" href="/admin/member/delete?idx=${m_idx}&role=ADMIN"><img src="/ADMIN/images/remove.png"></a>
</c:if>
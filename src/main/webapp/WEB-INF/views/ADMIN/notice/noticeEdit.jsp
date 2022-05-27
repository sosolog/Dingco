<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--등록 모드-->
<c:if test="${empty noticeDTO.number_idx}">
    <c:set var="mode" value="insert"/>
    <c:set var="number_idx" value=""/>
</c:if>
<!--수정 모드-->
<c:if test="${not empty noticeDTO.number_idx}">
    <c:set var="mode" value="update"/>
    <c:set var="number_idx" value="${noticeDTO.number_idx}"/>
    <c:set var="userid" value="${noticeDTO.userid}"/>
    <c:set var="title" value="${noticeDTO.title}"/>
    <c:set var="content" value="${noticeDTO.content}"/>
    <c:set var="writeday" value="${noticeDTO.writeday}"/>
    <c:set var="readcnt" value="${noticeDTO.readcnt}"/>
    <c:set var="1" value="${noticeDTO.category_idx}"/>
</c:if>

<form action="/admin/notice/edit.action" name="editForm" method="post" enctype="multipart/form-data">
<div id="main-box">
    <div id="top-menu">
        <span class="title">
            공지사항
            <c:if test="${mode == 'update'}">수정</c:if>
            <c:if test="${mode == 'insert'}">등록</c:if>
        </span>
        <a class="btn-save" onclick="saveEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="javascript:history.back()"><span>취소</span></a>
    </div>
    <input type="hidden" name="category_idx" value="1"> <!-- 카테고리 번호 -->
    <input type="hidden" name="mode" value="${mode}"> <!-- 등록 or 수정 -->
    <input type="hidden" name="m_idx" value="${loginMember.m_idx}"> <!-- 현 로그인 회원번호 -->
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${mode == 'update'}">
                <tr class="short-line">
                    <td>글 번호</td>
                    <td><input readonly="readonly" name="number_idx" value="${number_idx}"></td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>제목</td>
                    <td><input name="title" value="${title}" placeholder="제목을 입력해주세요"></td>
                </tr>
                <tr class="long-line">
                    <td>내용</td>
                    <td><textarea name="content" placeholder="내용을 입력해주세요">${content}</textarea></td>
                </tr>
                <c:if test="${mode == 'update'}">
                <tr class="short-line">
                    <td>최근 작성자</td>
                    <td>
                        <input readonly="readonly" value="${userid}">
                    </td>
                </tr>
                <tr class="short-line">
                    <td>조회수</td>
                    <td><input readonly="readonly" value="${readcnt}"></td>
                </tr>
                <tr class="short-line">
                    <td>작성일</td>
                    <td><input readonly="readonly" value="${writeday}"></td>
                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
</form>

<!--삭제 버튼-->
<c:if test="${mode == 'update'}">
    <a id="btn-delete" href="/admin/notice/delete?idx=${number_idx}"><img src="/ADMIN/images/remove.png"></a>
</c:if>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--등록 모드-->
<c:if test="${empty noticeDTO.number_idx}">
    <c:set var="mode" value="추가"/>
    <c:set var="number_idx" value=""/>
</c:if>
<!--수정 모드-->
<c:if test="${not empty noticeDTO.number_idx}">
    <c:set var="mode" value="수정"/>
    <c:set var="number_idx" value="${noticeDTO.number_idx}"/>
    <c:set var="userid" value="${noticeDTO.userid}"/>
    <c:set var="title" value="${noticeDTO.title}"/>
    <c:set var="content" value="${noticeDTO.content}"/>
    <c:set var="writeday" value="${noticeDTO.writeday}"/>
    <c:set var="readcnt" value="${noticeDTO.readcnt}"/>
    <c:set var="1" value="${noticeDTO.category_idx}"/>
</c:if>

<form action="" name="editForm" method="post" enctype="multipart/form-data">
<div id="main-box">
    <div id="top-menu">
        <span class="title">공지사항 ${mode}</span>
        <a class="btn-save" onclick="saveEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="javascript:history.back()"><span>취소</span></a>
    </div>
    <input type="hidden" name="" value="${category_idx}">
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>글 번호</td>
                    <td><input readonly="readonly" name="" value="${number_idx}"></td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>제목</td>
                    <td><input name="" value="${title}" placeholder="제목을 입력해주세요"></td>
                </tr>
                <tr class="long-line">
                    <td>내용</td>
                    <td><textarea name="" placeholder="내용을 입력해주세요">${content}</textarea></td>
                </tr>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>최근 작성자</td>
                    <td><input readonly="readonly" value="${userid}"></td>
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
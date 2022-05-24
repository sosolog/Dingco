<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="main-box">
    <div id="top-menu">
        <span class="title">1:1문의</span>
        <a class="btn-add" href="/admin/faq/edit"><span>글 추가</span></a>
    </div>
    <div id="search">
        <form action="/admin/inquiry" method="get" name="searchForm">
            <a class="icon" onclick="adminSearch(searchForm)"><img src="/images/admin/search.png"></a>
            <input id="sch" name="sch" placeholder="제목 또는 내용을 입력하세요" value="${sch}"
                   onKeypress="javascript:if(event.keyCode==13) {adminSearch(searchForm)}" autocomplete="off">
        </form>
    </div>
    <div id="list-table">
        <table>
            <thead>
            <tr class="thead-line">
                <td>문의 번호</td>
                <td>질문인</td>
                <td>제목</td>
                <td>최초 등록일</td>
                <td>카테고리</td>
                <td>답변 여부</td>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr class="inquiry-line">
                    <td style="width: 10%">${dto.i_idx}</td>
                    <td style="width: 10%">${dto.m_idx}</td>
                    <td style="width: 50%"><a href="/admin/inquiry/edit?idx=${dto.i_idx}"><span>${dto.title}</span></a></td>
                    <td style="width: 10%">${dto.upload_date}</td>
                    <td style="width: 10%">${dto.category_id}</td>
                    <td style="width: 10%">${dto.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 페이징 -->
    <!-- 페이지 번호 출력 -->
     <%@ include file="../page.jsp" %>
</div>
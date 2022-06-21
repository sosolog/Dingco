<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="main-box">
    <div id="top-menu">
        <span class="title">더치페이 방 목록</span>
    </div>
    <div id="search">
        <form action="/admin/member/admin" method="get" name="searchForm">
            <a class="icon" onclick="adminSearch(searchForm)"><img src="/ADMIN/images/search.png"></a>
            <input id="sch" name="sch" placeholder="방 제목 또는 방장 이름을 입력하세요" value="${sch}"
                   onKeypress="javascript:if(event.keyCode==13) {adminSearch(searchForm)}" autocomplete="off">
        </form>
    </div>
    <div id="list-table">
        <table>
            <thead class="thead-line">
            <tr>
                <td style="width: 10%">방 번호</td>
                <td style="width: 60%">제목</td>
                <td style="width: 15%">방장</td>
                <td style="width: 15%">생성일</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr class="inquiry-line">
                    <td>${dto.pr_idx}</td>
                    <td><a href="/admin/payEdit?pr_idx=${dto.pr_idx}"><span style="text-decoration: underline;">${dto.room_name}</span></a></td>
                    <td>${dto.userid} (${dto.m_idx})</td>
                    <td>${dto.create_date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- 페이징 -->
    <!-- 페이지 번호 출력 -->
    <%@ include file="../page.jsp" %>
</div>
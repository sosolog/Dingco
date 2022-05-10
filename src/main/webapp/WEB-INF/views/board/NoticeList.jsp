<%@page import="com.dingco.pedal.dto.PageDTO"%>
<%@page import="com.dingco.pedal.dto.FAQDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<h2>게시판 목록보기</h2>
<jsp:include page="../common/menu.jsp" flush="true" />
<table border="1">
    <tr>
        <th>번호</th>
        <th>글번호</th>
        <th>제목</th>
        <th>카테고리</th>
        <th>작성자</th>
        <th>내용</th>
        <th>작성일</th>
        <th>조회수</th>
    </tr>
    <c:set var="pageDTO" value="${pageDTO}"></c:set>
    <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${dto.number_idx}</td>
            <td><a href="/notice/${dto.number_idx}">${dto.title}</a></td>
            <td>${dto.category_name}</td>
            <td>${dto.username}</td>
            <td>${dto.content}</td>
            <td>${dto.writeday}</td>
            <td>${dto.readcnt}</td>
        </tr>
    </c:forEach>
</table>
<!-- 페이지 번호 출력 -->
<jsp:include page="../page.jsp"></jsp:include>
<br>
<!-- 페이지 번호 출력 -->
<a href="/faq/write">글쓰기</a>
</body>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
</html>




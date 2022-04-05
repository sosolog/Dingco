<%@page import="com.dingco.pedal.dto.PageDTO"%>
<%@page import="com.dingco.pedal.dto.FAQDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h2>게시판 목록보기</h2>
<table border="1">
    <tr>
        <th>게시글 순서</th>
        <th>문의번호</th>
        <th>작성자번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>내용</th>
        <th>사용자 업로드파일명</th>
        <th>서버 저장파일명</th>
        <th>업로드 날짜</th>
        <th>수정 날짜</th>
        <th>상위 문의 고유번호</th>
        <th>문의 상태</th>
    </tr>
    <c:set var="path" value="${requestMapping}"></c:set>
    <c:set var="pageDTO" value="${pageDTO}"></c:set>
    <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${dto.i_idx}</td>
            <td>${dto.m_idx}</td>
                <%--            <td><a href="boardRetrieve?num=${dto.num}">${dto.title}</a></td>--%>
            <td>${dto.category_id}</td>
            <td><a href="inquiry/${dto.i_idx}">${dto.title}</a></td>
            <td>${dto.content}</td>
            <td>${dto.image}</td>
            <td>${dto.image_db}</td>
            <td>${dto.upload_date}</td>
            <td>${dto.last_updated_date}</td>
            <td>${dto.i_idx2}</td>
            <td>${dto.status.message}, ${dto.status == 'YET'}</td>
        </tr>
    </c:forEach>
</table>
<!-- 페이지 번호 출력 -->
<jsp:include page="../page.jsp"></jsp:include>
<!-- 페이지 번호 출력 -->
<br>
<a href="/faq/write">문의하기</a>
</body>
</html>




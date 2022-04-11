<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h2>게시판 목록보기</h2>
<c:set var="path" value="${requestMapping}"></c:set>
<c:set var="pageDTO" value="${pageDTO}"></c:set>
<c:if test="${fn:length(pageDTO.dtoList) == 0}">
    <div style="color: red; text-align: center"><b>존재하는 문의가 없습니다.</b></div>
</c:if>
<c:if test="${fn:length(pageDTO.dtoList) > 0}">
<table border="1">
    <tr>
        <th>게시글 순서</th>
        <th>문의번호</th>
        <th>작성자번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>내용</th>
        <th>업로드 날짜</th>
        <th>수정 날짜</th>
        <th>상위 문의 고유번호</th>
        <th>문의 상태</th>
    </tr>

    <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${dto.i_idx}</td>
            <td>${dto.m_idx}</td>
                <%--            <td><a href="boardRetrieve?num=${dto.num}">${dto.title}</a></td>--%>
            <td>${dto.category_id}</td>
            <td><a href="inquiry/${dto.i_idx}">${dto.title}</a></td>
            <td>${dto.content}</td>
            <td>${dto.upload_date}</td>
            <td>${dto.last_updated_date}</td>
            <td>${dto.i_idx2}</td>
            <td>${dto.status.message}, ${dto.status == 'YET'}</td>
        </tr>
    </c:forEach>
</table>
</c:if>
<!-- 페이지 번호 출력 -->
<jsp:include page="../page.jsp"></jsp:include>
<!-- 페이지 번호 출력 -->
<br>
<a href="/inquiry/write">문의하기</a>
</body>
</html>




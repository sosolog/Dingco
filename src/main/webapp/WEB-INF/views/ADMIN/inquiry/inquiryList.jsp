<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="main-box">
    <div id="top-menu">
        <span class="title">1:1문의</span>
        <a class="cate-inquiry-o<c:if test="${status == 'RE_INQUIRY'}">n</c:if>" href="/admin/inquiry?status=R&category=${category}&sch=${sch}"><span>재문의</span></a>
        <a class="cate-inquiry-o<c:if test="${status == 'DONE'}">n</c:if>" href="/admin/inquiry?status=D&category=${category}&sch=${sch}"><span>응답 완료</span></a>
        <a class="cate-inquiry-o<c:if test="${status == 'YET'}">n</c:if>" href="/admin/inquiry?status=Y&category=${category}&sch=${sch}"><span>미응답</span></a>
        <a class="cate-inquiry-o<c:if test="${status == ''}">n</c:if>" href="/admin/inquiry?category=${category}&sch=${sch}"><span>전체 보기</span></a>
    </div>
    <div id="search-inquiry">
        <form action="/admin/inquiry" method="get" name="searchForm">
            <input type="hidden" name="status" value="${status}">
            <a class="icon" onclick="adminSearch(searchForm)"><img src="/ADMIN/images/search.png"></a>
            <input id="sch" name="sch" placeholder="제목 또는 내용을 입력하세요" value="${sch}"
                   onKeypress="javascript:if(event.keyCode==13) {adminSearch(searchForm)}" autocomplete="off">

            <select class="search-inquiry-select" name="category" onchange="submitSearchForm(searchForm)">
                <option value="" <c:if test="${category == ''}">selected</c:if>>전체 카테고리</option>
                <option value="건의사항" <c:if test="${category == '건의사항'}">selected</c:if>>건의사항</option>
                <option value="버그" <c:if test="${category == '버그'}">selected</c:if>>버그</option>
                <option value="기타" <c:if test="${category == '기타'}">selected</c:if>>기타</option>
            </select>
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
                    <td style="width: 10%">${dto.userid}(${dto.username})</td>
                    <td style="width: 50%"><a href="/admin/inquiry/edit?idx=${dto.i_idx}"><span style="text-decoration: underline">${dto.title}</span></a></td>
                    <td style="width: 10%">${dto.upload_date}</td>
                    <td style="width: 10%">${dto.category_id}</td>
                    <td style="width: 10%">
                        <c:if test="${dto.status == 'YET'}"><span style="color: #FF4040">미응답</span></c:if>
                        <c:if test="${dto.status == 'DONE'}"><span style="color: #303030">응답완료</span></c:if>
                        <c:if test="${dto.status == 'RE_INQUIRY'}"><span style="color: #4677DE">재문의</span></c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 페이징 -->
    <!-- 페이지 번호 출력 -->
     <%@ include file="../page.jsp" %>
</div>
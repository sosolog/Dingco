<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="main-box">
    <div id="top-menu">
        <span class="title">공지사항</span>
        <a class="btn-add" href="/admin/notice/edit"><span>글 추가</span></a>
    </div>
    <div id="search">
        <form>
            <a class="icon" href=""><img src="/images/admin/search.png"></a>
            <input id="" name="" placeholder="제목 또는 내용을 입력하세요" value="">
        </form>
    </div>
    <div id="list-table">
        <table>
            <thead>
            <tr>
                <td>글 번호</td>
                <td>제목</td>
                <td>작성자</td>
                <td>작성일</td>
                <td>edit</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr>
                    <td style="width: 10%">${dto.number_idx}</td>
                    <td style="width: 60%">${dto.title}</td>
                    <td style="width: 10%">${dto.userid}</td>
                    <td style="width: 15%">${dto.writeday}</td>
                    <td style="width: 5%"><a href="/admin/notice/edit?${dto.number_idx}"><img src="/images/admin/edit.png"></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 페이징 -->
    <!-- 페이지 번호 출력 -->
    <%@ include file="../page.jsp" %>
    <%--criteriaOfPage=10, criteriaOfBlock=5, totalRecord=13, totalPage=2, totalBlock=1, curPage=1, curBlock=1,
    firstPageInNextBlock=null, lastPageInPrevBlock=null, firstPageInCurBlock=1, lastPageInCurBlock=2,
    pageListInBlock=[1, 2]--%>
</div>
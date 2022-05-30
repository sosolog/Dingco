<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="main-box">
    <div id="top-menu">
        <span class="title">관리자</span>
        <a class="btn-add" href="/admin/member/admin/edit"><span>관리자 추가</span></a>
    </div>
    <div id="search">
        <form action="/admin/member/admin" method="get" name="searchForm">
            <a class="icon" onclick="adminSearch(searchForm)"><img src="/ADMIN/images/search.png"></a>
            <input id="sch" name="sch" placeholder="이름 또는 아이디를 입력하세요" value="${sch}"
                   onKeypress="javascript:if(event.keyCode==13) {adminSearch(searchForm)}" autocomplete="off">
        </form>
    </div>
    <div id="list-table">
        <table>
            <thead class="thead-line">
            <tr>
                <td>회원 번호</td>
                <td>이름</td>
                <td>아이디</td>
                <td>이메일</td>
                <td>가입일</td>
                <td>edit</td>
            </tr>
            </thead>
            <tbody>
            <%--dtoList=[MemberDTO(m_idx=118, kakao_idx=null, naver_idx=null, google_idx=110402302331587180464,
            username=김영준, userid=96youngjun, passwd=null, email1=null, email2=null, uploadFileName=null,
            storeFileName=null, authorities=null, joindate=2022-05-10),--%>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr class="notice-line">
                    <td style="width: 10%">${dto.m_idx}</td>
                    <td style="width: 20%">${dto.username}</td>
                    <td style="width: 20%">${dto.userid}</td>
                    <td style="width: 20%">
                        <c:if test="${not empty dto.email1}">${dto.email1}@${dto.email2}</c:if>
                        <c:if test="${empty dto.email1}">-</c:if>
                    </td>
                    <td style="width: 10%">${dto.joindate}</td>
                    <td style="width: 10%"><a class="edit" href="/admin/member/admin/edit?idx=${dto.m_idx}"><img src="/ADMIN/images/edit.png"></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- 페이징 -->
    <!-- 페이지 번호 출력 -->
    <%@ include file="../page.jsp" %>
</div>
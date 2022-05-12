<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="inquiryList">
    <form class="sch" method="get" name="searchForm">
        <div class="sch_wrap">
            <div class="sch_input_wrap">
                <input type="text" data-role="none" name="searchKey" class="sch_input"
                       placeholder="검색어를 입력하세요" onkeydown="if(event.keyCode === 13) go_search(searchForm);">
            </div>
            <div class="sch_ico_rt">
                <a onclick="go_search(searchForm)"><img src="/images/ico_search_02.png"></a>
            </div>
        </div>
    </form>
    <div class="wrap_table">
        <table>
            <thead>
            <tr>
                <td class="idx" style="width: 12%;"> No </td>
                <td class="title"> 제목 </td>
                <td class="writeday"> 작성일 </td>
                <td class="answer"> 답변 </td>
            </tr>
            </thead>
            <tbody>
            <c:set var="pageDTO" value="${pageDTO}"/>
            <c:set var="totalRecord" value="${pageDTO.totalRecord}"/>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr>
                    <td class="idx"><span> ${totalRecord - (pageDTO.curPage-1) * pageDTO.perPage - status.index} </span></td>
                    <td class="title">
                        <a href="inquiry/${dto.i_idx}">
                            <span> ${dto.title} </span>
                            <c:if test="${dto.i_idx2 > 0}"><img src="/images/request.png"></c:if>
                        </a>
                    </td>
                    <td class="writeday">
                        <a href="inquiry/${dto.i_idx}">
                            <span> ${dto.upload_date} </span>
                        </a>
                    </td>
                    <td class="answer">
                        <a href="inquiry/${dto.i_idx}">
                            <c:if test="${dto.status == 'YET'}"><img src="/images/beforeAnswer.png"></c:if>
                            <c:if test="${dto.status != 'YET'}"><img src="/images/afterAnswer.png"></c:if>
                        </a>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- 페이지 번호 출력 -->
    <%@ include file="../page.jsp" %>
    <!-- 페이지 번호 출력 -->
    <br>
    <c:if test="${memberDTO.authorities!='ADMIN'}">
        <a href="/inquiry/write">문의하기</a>
    </c:if>
</div>



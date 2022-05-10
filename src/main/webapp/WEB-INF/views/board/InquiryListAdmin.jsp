<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--<div id="inquiryList">
    <div class="wrap_table">
        <table>
            <thead>
            <tr>
                <td class="idx" style="width: 12%;"> No </td>
                <td class="title"> 제목 </td>
                <td class="btn_openflip" style="width: 10%;"></td>
            </tr>
            </thead>
            <tbody>
            <c:set var="pageDTO" value="${pageDTO}"/>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr id="flipFAQ${dto.number_idx}" class="flipFAQ">
                    <td class="idx"><span> ${status.count} </span></td>
                    <td class="title"><span> ${dto.title} </span></td>
                    <td class="btn_openFAQ"><a onclick="openFAQ(${dto.number_idx})"><img src="/images/openFAQ.png"></a></td>
                    <td class="btn_flipFAQ"><a onclick="flipFAQ(${dto.number_idx})"><img src="/images/flipFAQ.png"></a></td>
                </tr>
                <tr id="openFAQ${dto.number_idx}" class="openFAQ" style="display: none;">
                    <td class="content" colspan="100%">
                        <div class="open_title">
                            <span>Q. </span>
                            <span>${dto.title}</span>
                        </div>
                        <div class="open_content">
                            <span>A. </span>
                            <span>${dto.content}</span>
                        </div>
                        <div class="open_writeday">
                            <span>${dto.writeday}</span>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- 페이지 번호 출력 -->
    <%@ include file="../page.jsp" %>
    <br>
    <!-- 페이지 번호 출력 -->
    <a href="/faq/write">글쓰기</a>
</div>--%>

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
    <c:set var="pageDTO" value="${pageDTO}"></c:set>
    <c:if test="${memberDTO.authorities=='ADMIN'}">
        <b>관리자 모드</b>
    </c:if>
    <c:if test="${fn:length(pageDTO.dtoList) == 0}">
        <div style="color: red; text-align: center"><b>존재하는 문의가 없습니다.</b></div>
    </c:if>
    <c:if test="${fn:length(pageDTO.dtoList) > 0}">
    <table border="1">
        <tr>
    <%--        <th>게시글 순서</th>--%>
            <th>문의번호</th>
            <th>작성자번호</th>
            <th>카테고리</th>
            <th>내용</th>
            <th>업로드 날짜</th>
            <th>수정 날짜</th>
    <%--        <th>상위 문의 고유번호</th>--%>
            <th>문의 상태</th>
        </tr>

        <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
            <tr>
    <%--            <td>${status.count}</td>--%>
                <td>${dto.i_idx}</td>
                <td>${dto.m_idx}</td>
                    <%--            <td><a href="boardRetrieve?num=${dto.num}">${dto.title}</a></td>--%>
                <td>${dto.category_id}</td>
                <td>
                    <c:if test="${dto.i_idx2 > 0}">
                    <span class="rgyBadge">재문의</span>
                    </c:if>
                    <a href="inquiry/${dto.i_idx}">${dto.title}</a></td>
                <td>${dto.upload_date}</td>
                <td>${dto.last_updated_date}</td>
    <%--            <td>${dto.i_idx2}</td>--%>
                <td>${dto.status.message}, ${dto.status == 'YET'}</td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
    <div class="wrap_table">
        <table>
            <thead>
            <tr>
                <td class="idx" style="width: 12%;"> No </td>
                <td class="title"> 제목 </td>
                <td class="btn_openflip" style="width: 10%;"></td>
            </tr>
            </thead>
            <tbody>
            <c:set var="pageDTO" value="${pageDTO}"/>
            <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
                <tr id="flipFAQ${dto.number_idx}" class="flipFAQ">
                    <td class="idx"><span> ${status.count} </span></td>
                    <td class="title"><span> ${dto.title} </span></td>
                    <td class="btn_openFAQ"><a onclick="openFAQ(${dto.number_idx})"><img src="/images/openFAQ.png"></a></td>
                    <td class="btn_flipFAQ"><a onclick="flipFAQ(${dto.number_idx})"><img src="/images/flipFAQ.png"></a></td>
                </tr>
                <tr id="openFAQ${dto.number_idx}" class="openFAQ" style="display: none;">
                    <td class="content" colspan="100%">
                        <div class="open_title">
                            <span>Q. </span>
                            <span>${dto.title}</span>
                        </div>
                        <div class="open_content">
                            <span>A. </span>
                            <span>${dto.content}</span>
                        </div>
                        <div class="open_writeday">
                            <span>${dto.writeday}</span>
                        </div>
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



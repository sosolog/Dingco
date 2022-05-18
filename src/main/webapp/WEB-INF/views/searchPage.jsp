<%@page import="com.dingco.pedal.dto.PageDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="dto" value="${pageDTO}"></c:set>
<c:set var="pg" value="${dto.curPage}"></c:set>
<c:set var="totalRecord" value="${dto.totalRecord}"></c:set>
<c:set var="perPage" value="${dto.criteriaOfPage}"></c:set>
<c:set var="firstPageInNextBlock" value="${dto.firstPageInNextBlock}"></c:set>
<c:set var="lastPageInPrevBlock" value="${dto.lastPageInPrevBlock}"></c:set>
<c:set var="pageListInBlock" value="${dto.pageListInBlock}"></c:set>
<c:set var="totalPage" value="${dto.totalPage}"></c:set>

<c:set var="path" value="${path}?searchKey=${searchKey}"></c:set>

<!-- 현재 블럭의 페이지 리스트의 길이가 0일 경우 -->
<c:if test="${pageListInBlock.size() == 0}">

	<!-- 레코드가 없을 경우 나오는 경우 => totalPage = 0 -> totalPage = 0이면 빈 리스트라서 페이지 번호가 뜨지 않는다.
	     => 빈 리스트에 totalPage + 1(=1)을 넣어주어서 레코드가 없는 경우에도 페이지 번호가 뜰 수 있게끔 작업
	-->
	<c:forEach var="i" begin="1" end="${totalPage+1}">
		<c:if test="${i==pg}">
			${i}
		</c:if>
	<!-- 끝 -->
	</c:forEach>
</c:if>

<!-- 현재 블럭의 페이지 리스트의 길이가 0이 아닐경우-->
<c:if test="${pageListInBlock.size() != 0}">

	<!-- 지난 블럭이 존재할 경우 '<' 버튼 클릭시 지난 블럭의 마지막 페이지로 이동-->
	<c:if test="${lastPageInPrevBlock != null}">
		<a href="${path}&pg=${lastPageInPrevBlock}">&lt;</a>
	</c:if>

	<!-- 현재 블럭의 페이지 리스트에서 값을 들고와서 현재 위치가 현재 페이지와 같다면 현재 페이지 값만 출력 그렇지 않다면 이동할 수 있도록 <a> 사용-->
	<c:forEach var="i" items="${pageListInBlock}">
		<c:if test="${i==pg}">
			${i}
		</c:if>
		<c:if test="${i!=pg}">
			<a href="${path}&pg=${i}">${i}</a>
		</c:if>
	</c:forEach>

	<!-- 다음 블럭이 존재할 경우 '>' 버튼 클릭시 다음 블럭의 첫 번째 페이지로 이동-->
	<c:if test="${firstPageInNextBlock != null}">
		<a href="${path}&pg=${firstPageInNextBlock}">&gt;</a>
	</c:if>

</c:if>


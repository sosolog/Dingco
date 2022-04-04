<%@page import="com.dingco.pedal.dto.PageDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="dto" value="${pageDTO}"></c:set>
<c:set var="curPage" value="${dto.curPage}"></c:set>
<c:set var="totalRecord" value="${dto.totalRecord}"></c:set>
<c:set var="perPage" value="${dto.perPage}"></c:set>
<c:set var="firstPageInNextBlock" value="${dto.firstPageInNextBlock}"></c:set>
<c:set var="lastPageInPrevBlock" value="${dto.lastPageInPrevBlock}"></c:set>
<c:set var="pageListInBlock" value="${dto.pageListInBlock}"></c:set>
<c:set var="totalPage" value="${totalRecord/perPage}"></c:set>
<c:if test="${pageListInBlock == null}">
	<c:if test="${totalRecord%perPage != 0}">
	   <c:set var="totalPage" value="${totalPage+1}" ></c:set>
	</c:if>
	<c:forEach var="i" begin="1" end="${totalPage}">
		<c:if test="${i==curPage}">
			${i}
		</c:if>
		<c:if test="${i!=curPage}">
		<a href="faq?curPage=${i}">${i}</a>
		</c:if>
	</c:forEach>
</c:if>
<c:if test="${pageListInBlock != null}">
	<c:if test="${firstPageInNextBlock != null}">
		<a href="faq?curPage=${firstPageInNextBlock}">&lt;</a>
	</c:if>
	<c:forEach var="i" items="${pageListInBlock}">
		<c:if test="${i==curPage}">
			${i}
		</c:if>
		<c:if test="${i!=curPage}">
			<a href="faq?curPage=${i}">${i}</a>
		</c:if>
	</c:forEach>
	<c:if test="${lastPageInPrevBlock != null}">
		<a href="faq?curPage=${lastPageInPrevBlock}">&gt;</a>
	</c:if>
</c:if>
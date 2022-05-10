<%@page import="com.dingco.pedal.dto.PageDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="dto" value="${pageDTO}"></c:set>
<c:set var="pg" value="${dto.curPage}"></c:set>
<c:set var="totalRecord" value="${dto.totalRecord}"></c:set>
<c:set var="perPage" value="${dto.perPage}"></c:set>
<c:set var="firstPageInNextBlock" value="${dto.firstPageInNextBlock}"></c:set>
<c:set var="lastPageInPrevBlock" value="${dto.lastPageInPrevBlock}"></c:set>
<c:set var="pageListInBlock" value="${dto.pageListInBlock}"></c:set>
<c:set var="totalPage" value="${totalRecord/perPage}"></c:set>
<c:set var="path" value="${requestMapping}"></c:set>
<c:if test="${pageListInBlock == null}">
	<c:if test="${totalRecord%perPage != 0}">
	   <c:set var="totalPage" value="${totalPage+1}" ></c:set>
	</c:if>
	<c:forEach var="i" begin="1" end="${totalPage}">
		<c:if test="${i==pg}">
			${i}
		</c:if>
		<c:if test="${i!=pg}">
		<a href="${path}?pg=${i}">${i}</a>
		</c:if>
	</c:forEach>
</c:if>
<c:if test="${pageListInBlock != null}">
	<c:if test="${lastPageInPrevBlock != null}">
		<a href="${path}?pg=${lastPageInPrevBlock}">&lt;</a>
	</c:if>
	<c:forEach var="i" items="${pageListInBlock}">
		<c:if test="${i==pg}">
			${i}
		</c:if>
		<c:if test="${i!=pg}">
			<a href="${path}?pg=${i}">${i}</a>
		</c:if>
	</c:forEach>
	<c:if test="${firstPageInNextBlock != null}">
		<a href="${path}?pg=${firstPageInNextBlock}">&gt;</a>
	</c:if>
</c:if>
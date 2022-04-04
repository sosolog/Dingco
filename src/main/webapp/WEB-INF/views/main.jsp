<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="./include/header.jsp" %>

<c:if test="${login==null}">
<%@ include file="main/main_unLogin.jsp" %>
</c:if>

<c:if test="${login!=null}">
<%@ include file="main/main_login.jsp" %>
</c:if>

<%@ include file="./include/footer.jsp" %>

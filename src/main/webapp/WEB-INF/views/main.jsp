<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<%@ include file="./include/header.jsp" %>

<c:if test="${login==null}">
<%@ include file="main/main_unLogin.jsp" %>
</c:if>

<c:if test="${login!=null}">
<%@ include file="main/main_login.jsp" %>
</c:if>

<%@ include file="./include/footer.jsp" %>

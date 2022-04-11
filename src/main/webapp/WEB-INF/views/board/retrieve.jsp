<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js" />




<h3>상세글 화면</h3>
<form action="retrieve" name="retrieve" method="post">
    제목:<input type="text" name="title" id="title" value="${faqDTO.title}"><br>
    카테고리:
    <select>
        <c:forEach var="cat" items="${category}" varStatus="status">
            <option value="${cat.category_id}" id="category">${cat.category_name}</option>
        </c:forEach>
    </select><br>
    작성자:<input type="text" name="username" id="username" value="${memberDTO.username}" readonly><br>
    내용:<textarea rows="10" cols="10" name="content" id="content">${faqDTO.content}</textarea><br>
    <input type="submit" onclick="" value="수정">
</form>
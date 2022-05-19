<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>





<h3>FAQ</h3>
<form name="retrieve">
    제목:<input type="text" name="title" id="title" value="${faqDTO.title}"><br>
    카테고리: <input type="text" name="title" id="category" value="${faqDTO.category_name}"><br>
    내용:<textarea rows="10" cols="10" name="content" id="content">${faqDTO.content}</textarea>
</form>


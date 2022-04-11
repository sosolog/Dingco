<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js" ></script>
<script>

    $(document).ready(function (){

        $("form").on("submit", function(){
            var title = $("#title").val()
            var content = $("#content").val()

            if(title.length==0){
                alert("제목을 입력하시오")
                $("#title").focus()
                event.preventDefault()
            }else if(content.length==0){
                alert("내용을 입력하시오")
                $("#content").focus()
                event.preventDefault()
            }
        })

    })


</script>

<h3>글쓰기화면</h3>
<form action="write" method="post">
    제목:<input type="text" name="title" id="title"><br>
    카테고리:
    <select>
        <c:forEach var="cat" items="${category}" varStatus="status">
            <option value="${cat.category_idx}" id="category">${cat.category_name}</option>
        </c:forEach>
    </select><br>
    작성자:<input type="text" name="username" id="username" value="${dto.username}" readonly><br>
    내용:<textarea rows="10" cols="10" name="content" id="content"></textarea><br>
    <input type="submit" id="submit" value="저장">
    <input type="hidden" name="m_idx" value="${dto.m_idx}">
    <c:forEach var="cat" items="${category}" varStatus="status">
        <input type="hidden" name="category_idx" value="${cat.category_idx}">
    </c:forEach>
</form>
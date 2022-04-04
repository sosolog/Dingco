<%--
  Created by IntelliJ IDEA.
  User: siros
  Date: 2022-04-04
  Time: 오후 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>글쓰기화면</h3>
<form action="BoardWriteServlet" method="post">
    제목:<input type="text" name="title"><br>
    작성자:<input type="text" name="userid"><br>
    내용:<textarea rows="10" cols="10" name="content"></textarea>
    <input type="submit" value="저장">
</form>
</body>
</html>

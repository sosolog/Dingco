<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ssy04
  Date: 2022-04-27
  Time: 오후 3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/script/jquery-3.6.0.js"></script>
    <script src="/script/jquery.tmpl.js"></script>
    <script>

        $(document).ready(function(){

        });

        // 새로운 더치페이 폼 생성
        function createNewDutch(){
            let today = new Date();
            let year = today.getFullYear().toString().slice(2);
            let month = String(today.getMonth() + 1).padStart(2, "0");
            let day = String(today.getDay()).padStart(2, "0");
            let formattedDate = [year, month, day].join("/");
            console.log(formattedDate)
            $("#new-dutch-tmpl").tmpl({today:formattedDate}).appendTo("#dutchList");
        }

        //
        function createNewDutchCfm(){
            console.log("새 더치페이 생성");
            console.log($("#new-dutch-name").val());
        }
    </script>
    <!-- 새 더치페이 생성 template-->
    <script type="text/html" id="new-dutch-tmpl">
        <tr style="color: #888888" id="new-dutch-form">
            <td>\${today}</td>
            <td><input type="text" id="new-dutch-name"></td>
            <td>0</td>
            <td><button onclick="createNewDutchCfm()">확인</button></td>
        </tr>
    </script>
</head>
<body>
<button>취소</button><h2>Pedal</h2>
<button>등록</button><br>
방이름<input type="text" name="room_name" id="room_name"><br>
방멤버<input type="text" name="groupMember" id="groupMember"><button onclick="">추가</button><br>
<button>1개만</button><button>여러개</button><br>
<span>결제 목록</span><button onclick="createNewDutch()">추가</button><br>
<table id="dutchList">
    <tr>
        <th>결제일</th>
        <th>제목</th>
        <th>총 결제금액</th>
        <th>정산현황</th>
    </tr>
<%--    <c:forEach items="${payList}" var="payDTO">
        <tr>
            <td>${payDTO.createDate}</td>
            <td>${payDTO.title}</td> <!-- payDTO에 title이 없었..-->
            <td>${payDTO.totalPay}</td>
            <td>${payDTO.???}</td> <!--정산현황은 어떻게 처리해야 할까유-->
        </tr>
    </c:forEach>--%>
</table>
</body>
</html>

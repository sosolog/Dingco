<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript"  src="/script/jquery.tmpl.js"></script>
<script type="text/javascript"  src="https://cdn.jsdelivr.net/npm/moment@2.29.3/moment.min.js"></script>

<style>
    .modal {
        position: absolute; top: 0; left: 0; width: 100%; height: 100%;
        display: none; background-color: rgba(0, 0, 0, 0.4);
    }
    .modal.show { display: block; }
    .modal_body {
        position: absolute; top: 50%; left: 50%; width: 400px; height: 600px;
        padding: 40px; text-align: center; background-color: rgb(255, 255, 255);
        border-radius: 10px; box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
        transform: translateX(-50%) translateY(-50%);
    }
</style>


<script>
    moment.locale('ko');
    let memberArr = [];
    let payRoomArr = ${payRoomList};

    $(document).ready(function() {
        $("#payRoomList_data").html($("#payRoom-list-tmpl").tmpl({pList:payRoomArr}));
    });
</script>
<!-- 방생성 modal member 명단 template-->
<script type="text/html" id="member-list-tmpl">
    {{each(index, m) mList}}
    <span id="mList_\${index}">
        \${m}<button data-idx="\${index}" onclick="deleteMemberDuringCreatingPayRoom(this)">X</button>
   </span>
    {{/each}}
</script>

<script type="text/html" id="payRoom-list-tmpl">
    {{each(index, p) pList}}
    <div>
        <a id="pList_\${index}" href="/pay/\${p.pr_idx}">
            \${p.room_name}
        </a>
        <span>
        \${p.create_date}
   </span>
    </div>
    {{/each}}
</script>


<div id="main">
<c:if test="${loginMember == null}">
    <div class="unlogin">
        <span class="info">로그인 후 이용해 주세요</span>
        <a href="/login"><span>로그인</span></a>
    </div>
</c:if>
<c:if test="${loginMember != null}">
    <div id="payRoomList">
        <div>진행 중인 더치페이</div>
        <button onclick="openPayRoomForm()">방생성</button>
        <hr>
        <div id="payRoomList_data"></div>
        <div class="modal">
            <div class="modal_body">
                <button onclick="closePayRoomForm()">X</button>
                <hr>
                방이름 : <input type="text" name="room_name" id="room_name"><br>
                방멤버 : <input type="text" name="groupMember" id="groupMember"><button onclick="memberList()">추가</button><br>
                <div id="memberList"></div>
                <span id="result_payRoom" style="color: red"></span><br>
                <button onclick="payRoomInfo()">방생성</button>
            </div>
        </div>
    </div>
</c:if>
</div>


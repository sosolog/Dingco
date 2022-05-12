<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

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

        $("#payRoomList").html($("#payRoom-list-tmpl").tmpl({pList:payRoomArr}));
        // 방생성 modal open
        $("#btn-open-modal").on("click", function(){
           $(".modal").addClass("show");
        });
        // 방생성 modal close ( 방생성 미완료 상태에서 종료 )
        $("#btn-close-modal").on("click", function(){
            // modal 안보이도록 css 변경
            $(".modal").removeClass("show");
            // 현재까지 저장되어있던 정보 삭제
            memberArr = [];
            $("#room_name").val("")
        });

        // 방생성 modal open 이후
        // 1. memberList에서 일부 member 삭제
        $(document).on("click", ".btn-member-delete", function(){
            console.log("memberList에서 member 삭제")
            let index = $(this).attr("data-idx");
            memberArr.splice(index,1); // memberArr 에서 member 삭제
            $(this).parent().remove(); // html에서 해당 member span 태그 삭제
            console.log("[END] index:", index, ", memberArr:", memberArr);
        });
    });



</script>
<!-- 방생성 modal member 명단 template-->
<script type="text/html" id="member-list-tmpl">
    {{each(index, m) mList}}
   <span id="mList_\${index}">
        \${m}<button class="btn-member-delete" data-idx="\${index}">X</button>
   </span>
    {{/each}}
</script>
<script type="text/html" id="payRoom-list-tmpl">
    {{each(index, p) pList}}
   <div>
    <a id="pList_\${index}" href="\${p.pr_idx}">
        \${p.room_name}
   </a>
    <span>
        \${p.create_date}
   </span>
   </div>
    {{/each}}
</script>
<hr>
<h1>진행 중인 더치페이</h1>
<button id="btn-open-modal">방생성</button>
<hr>
<div id="payRoomList"></div>




<div class="modal">
    <div class="modal_body">
        <button id="btn-close-modal">X</button>
        <hr>
        방이름 : <input type="text" name="room_name" id="room_name"><br>
        방멤버 : <input type="text" name="groupMember" id="groupMember"><button onclick="memberList()">추가</button><br>
        <div id="memberList"></div>
        <span id="result_payRoom" style="color: red"></span><br>
        <button onclick="payRoomInfo()">방생성</button>
    </div>
</div>
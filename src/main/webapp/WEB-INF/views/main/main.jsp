<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="/script/jquery.tmpl.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/moment@2.29.3/moment.min.js"></script>

<script>
    moment.locale('ko');
    let memberArr = [];
    let payRoomArr = ${payRoomList};

    $(document).ready(function () {
        $("#payRoomList_data").html($("#payRoom-list-tmpl").tmpl({pList: payRoomArr}));
    });
</script>

<!-- 방생성 modal member 명단 template-->
<script type="text/html" id="member-list-tmpl">
    {{each(index, m) mList}}
        <span id="mList_\${index}" style="display: inline-block; margin-right: 38px;">
            \${m}<a class="btn-member-delete" data-idx="\${index}" onclick="deleteMemberDuringCreatingPayRoom(this)"><img src="/images/delete_member.png"></a>
        </span>
    {{/each}}
</script>

<!--더치페이 리스트 목록 템플릿-->
<script type="text/html" id="payRoom-list-tmpl">
    {{each(index, p) pList}}
    <div class="wrap_payRoom">
        <a class="box" id="pList_\${index}" href="/pay/\${p.pr_idx}">
            <span class="tit">\${p.room_name}</span>
            <span class="date">\${p.create_date}</span>
        </a>
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
            <div class="list_top">
                <span class="tit">진행 중인 더치페이</span>
                <a class="btn-add-room" onclick="openPayRoomForm()"><img src="/images/ico_add_room.png"></a>
            </div>
            <div id="payRoomList_data"></div>
            <div class="list_bottom">
                <a><span>더보기</span></a>
            </div>


            <div class="modal">
                <div class="modal_body">
                    <div class="top">
                        <span class="tit">방 만들기</span>
                        <a onclick="closePayRoomForm()"><img src="/images/ico_close.png"></a>
                    </div>
                    <div class="input_box">
                        <div class="one_box">
                            <span>방 이름</span>
                            <input type="text" class="room_name" name="room_name" id="room_name">
                        </div>
                        <div class="one_box">
                            <span>방 멤버</span>
                            <input type="text" class="room_member" name="groupMember" id="groupMember">
                            <a onclick="memberList()"><img src="/images/btn-add-member.png"></a>
                        </div>
                        <div class="memberList" id="memberList"></div>
                    </div>
                    <span class="result_payRoom" id="result_payRoom"></span>
                    <a class="btn-create-room" onclick="payRoomInfo()"><span>방생성</span></a>
                </div>
            </div>
        </div>
    </c:if>
</div>


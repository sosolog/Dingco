<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<script type="text/javascript"  src="/script/jquery.tmpl.js"></script>
<script type="text/javascript"  src="https://cdn.jsdelivr.net/npm/moment@2.29.3/moment.min.js"></script>

    <style>
        .modal, .second_modal {
            position: absolute; top: 0; left: 0; width: 100%; height: 100%;
            display: none; background-color: rgba(0, 0, 0, 0.4);
        }
        .modal.show, .second_modal.show { display: block; }
        .modal_body {
            position: absolute; top: 50%; left: 50%; width: 400px; height: 600px;
            padding: 40px; text-align: center; background-color: rgb(255, 255, 255);
            border-radius: 10px; box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
            transform: translateX(-50%) translateY(-50%);
        }
    </style>
    <script>
        // 페이방 기본 정보
        let payRoom = ${payRoom};
        let pr_idx = payRoom.pr_idx;
        let room_name = payRoom.room_name;
        let groupMemberArr = payRoom.groupMemberList;
        // console.log("페이방 멤버 목록:", groupMemberArr);
        // 기타 로직을 위한 정보

        let payArr = []; // 새로 더치페이 생성시, 추가하는 결제 목록 리스트
        let tempObj = null;
        let resultList = null;

        let savedPayArr = [];
        let updatedPayArr = new Set();
        let deletedPayArr = []; //DB에 저장된 애들 중에서 삭제할 결제목록

        // TODO: 현재 방 멤버 목록 수정 된 이후, memberArr도 수정되는지 확인
        let memberArr = []; // 현재 방 멤버 이름 목록
        groupMemberArr.forEach( x => memberArr.push(x.payMeber_name));

        $(document).ready(function(){
            // 페이방 정보 보여주기
            $("#room_name").val(room_name); // 페이방 이름
            $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr})); // 방 멤버 목록
            $("#accountList").html($("#account-form-tmpl").tmpl({pSave:groupMemberArr, accountIdx:1})); // 계좌번호 목록
            showDutchPayList(pr_idx) // 더치페이 목록


        });

        $(document).keydown(function(event) {
            if ( event.keyCode == 27 || event.which == 27 ) {
                closeDutchPayForm()
            }
        });


    </script>

    <!-- 방생성 modal member 명단 template-->
    <script type="text/html" id="member-list-tmpl">
        {{each(index, m) mList}}
            <span id="mList_\${index}" class="mList_\${index}" data-idx="\${m.prgm_idx}">
            \${m.payMember_name}<button type="button" class="btn-member-delete" data-idx="\${index}" onclick="memberCheck($(this))">X</button>
            </span>
        {{/each}}
    </script>

    <script type="text/html" id="pay-form-tmpl">
        <tr id="pay-form">
            <td>
                <input type="text" id="form-pay-name" style="width: 50px" value="{{= pay ? pay.payName : ''}}">
            </td>
            <td><input type="text" id="form-pay-price" onkeyup="inputNumberFormat(this)" style="width: 100px" value="{{= pay ? pay.payPrice : ''}}"></td>
            <td>
                <select id="form-pay-payer">
                        {{each(index,p) groupMember}}
                            <option class="form-pay-selector" value="\${p.prgm_idx}"
                                    {{= pay && (p.prgm_idx == pay.payPayer.prgm_idx) ? 'selected' : null}}>
                                \${p.payMember_name}
                            </option>
                        {{/each}}
                </select>
            </td>
            <td>
                <button type="button" id="form-pay-participants" style="width: 50px"  onclick="changeParticipants()">
                    <span>{{= participants_prgm_idx ? participants_prgm_idx.length : groupMember.length}}</span>명
                </button>
                <div id="form-pay-participants-list" style="display: none">
                    {{each(index, p) groupMember}}
                        <input type="checkbox" class="form-pay-participants-check" value="\${p.prgm_idx}"
                               data-prgm-name="\${p.payMember_name}"
                               {{= participants_prgm_idx && !participants_prgm_idx.includes(p.prgm_idx) ? null : 'checked'}}>
                        \${p.payMember_name}
                    {{/each}}
                    <button type="button" id="btn-participants" onclick="changeParticipantsNumber()">OK</button>
                </div>
            </td>
            <td><button type="button" id="btn-updated-pay" onclick="return {{= pay ? 'saveUpdatedPay('+pay.p_idx+', this)' : 'saveNewPay()'}}">저장</button></td>
        </tr>
    </script>

    <!--입력한 결제 저장 template-->
    <script type="text/html" id="pay-list-tmpl">
        {{each(index, p) pSave}}
        <tr style="color: #888888" class="save-pay-form\${index}">
            <td id="save-name">\${p.payName}</td>
            <td id="save-price">\${p.payPrice}</td>
            <td id="save-payer">\${p.payPayer.payMember_name}</td>
            <td id="save-participants">
                \${p.payParticipants.length}명
            </td>
            <td> <!-- DB에 저장된 더치페이 내 결제 내역 -- 수정/삭제 -- DB단으로 바로 갔다와요...-->
                <!-- DB 저장된 더치페이 -- 결제 목록 -- 1. DB에서 가져올때 같이 가져온 결제 목록 2. 새로 추가한 결제 목록 -->
                <button type="button" class="btn-delete-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="deleteSavePay(this)">삭제</button>
                <button type="button" class="btn-update-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="showUpdatePayForm(this)">수정</button>
            </td>
        </tr>
        {{/each}}
    </script>

    <script type="text/html" id="show-dutch-list-tmpl">
        {{each(index, p) dList}}
        <tr>
            <td>\${p.createDate}</td>
            <td><a href="javascript:showDutchPayInfo(\${p.dp_idx})"> \${p.dutchPayName}</a></td>
            <td>\${p.totalPay}</td>
            <td>정산현황</td>
            <td><button type="button" onclick="deleteOneDutchPay(\${p.dp_idx})">삭제</button></td>
        </tr>
        {{/each}}
    </script>

    <!-- 계좌번호 template-->
    <script type="text/html" id="account-form-tmpl">


        {{if accountIdx == 0}}
            <td><input type="hidden" value="{{= accountInfo ? accountInfo.prgm_idx : ''}}" id="saved-account-prgm_idx">
            <input type="text" id="new-account-bank" style="width: 50px" value="{{= accountInfo ? accountInfo.payMember_bank : ''}}"></td>
            <td><input type="text" id="new-account-number" style="width: 100px" value="{{= accountInfo ? accountInfo.payMember_account : ''}}"></td>
            <td>
                {{if accountInfo == null}}
                <select id="new-account-owner">
                    {{each(index,p) pr}}
                    {{if p.payMember_account == null}}
                    <option class="new-account-selector" value="\${p.prgm_idx}">\${p.payMember_name}</option>
                    {{/if}}
                    {{/each}}
                </select>
            </td>
            <td>
                <button type="button" id="btn-update-account" onclick="saveNewAccount($(this))">저장</button>
            </td>
            <td>
                {{/if}}
                {{if accountInfo != null}}
                    <input readonly value="\${accountInfo.payMember_name}">
            </td>
            <td>
                <button type="button" id="btn-updated-account" onclick="updateSavedAccount($(this))">저장</button>
            </td>
                {{/if}}
        {{/if}}

        {{if accountIdx == 1}}
        {{each(index, p) pSave}}
        {{if p.payMember_account != null}}
        <tr style="color: #888888" class="save-account-form\${index}">

            <td id="save-bank">\${p.payMember_bank}</td>
            <td id="save-number">\${p.payMember_account}</td>
            <td id="save-owner">\${p.payMember_name}</td>
            <td>
                <input type="hidden" id="prgm_idx" value="\${p.prgm_idx}">
                <button type="button" id="btn-delete-account-ajax" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</button>
                <button type="button" id="btn-update-account-ajax" class="btn-update-account" data-idx="\${index}" onclick="updateSaveAccount($(this))">수정</button>
            </td>
        </tr>
        {{/if}}
        {{/each}}
        {{/if}}
    </script>

<h1>여기는 PAY방입니다</h1><br>
    <button type="button" onclick="javascript:location.href='/pay/list'">취소</button>
    <h2>Pedal</h2>
<button>등록</button><br>
방이름 : <input type="text" name="room_name" id="room_name" readonly><br>
방멤버 : <input type="text" name="groupMember" id="groupMember" ><button type="button" onclick="memberListOne()">추가</button><br>
<div id="memberList"></div>
<button id="one-btn">1개만</button><button id="all-btn">여러개</button><br>
<span>결제 목록</span><button onclick="openDutchPayForm()">추가</button><br>
<table>
    <thead>
        <tr>
            <th>결제일</th>
            <th>제목</th>
            <th>총 결제금액</th>
            <th>정산현황</th>
            <th></th>
        </tr>
    </thead>
    <tbody id="dutchList">
    </tbody>
</table>

<span>계좌 목록</span><button type="button" id="btn-account-plus" onclick="createNewAccount($(this))">+</button><br>
<table>
    <thead>
        <tr>
            <th>은행</th>
            <th>계좌번호</th>
            <th>이름</th>
            <th></th>
        </tr>
    </thead>
    <tbody id="accountList">
    </tbody>
</table>
<div class="modal">
    <div class="modal_body">
        <button type="button" onclick="closeDutchPayForm()">X</button>
        <button type="button" onclick="saveDutchPayForm()">저장</button>
        <input type="hidden" id="retrieve-pay-id">
        <hr>
        편집하기<br>

        <input type="text" id="pay_name" name="pay_name" placeholder="결제이름">
            <button type="button" id="btn-pay-plus"onclick="return togglePayForm($(this))">+</button>
        <br>
        <input type="hidden" id="is-pay-form-opened">
        <table>
            <thead>
            <tr>
                <th>결제 목록</th>
                <th>결제 금액</th>
                <th>결제자</th>
                <th>참여인원</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="payList">

            </tbody>
            <tbody id="payList2">

            </tbody>
        </table>

        총금액<input name="allPrice" id="allPrice" value="0" readonly><br>
        절사옵션
        <select name="cutPrice" id="cutPrice">
            <option value="0" id="noCut" selected=true>없음</option>
            <option value="10" >10원</option>
            <option value="100">100원</option>
            <option value="1000">1000원</option>
        </select><br>
        결제일<input type="date" name="pay-date" id="pay-date"><br>
        마감일<input type="date" name="due-date" id="due-date"><br>
        영수증<input type="text" name="bill" id="bill"><br>
        <button type="button" onclick="showDutchPayResult()">결과 미리보기(정산하기)</button>
    </div>
</div>
<div class="second_modal">
    <div class="modal_body">
        <button type="button" onclick="closeDutchPayForm()">X</button>
        <hr>
        <span>결과보기</span><br>
        <span><input type="text" id="pay-name-last" name="pay_name" readonly></span>
        <!-- 정렬 하기 관련 ㅎㅎㅎ-->
        <table>
            <thead>
            <tr>
                <th>누가</th>
                <th>누구에게</th>
                <th>얼마를?</th>
                <th>정산 여부</th>
            </tr>
            </thead>
            <tbody id="payResultList">

            </tbody>
        </table>

        <span>계좌 정보</span>
        <table>
            <thead>
            <tr>
                <th>은행</th>
                <th>계좌번호</th>
                <th>이름</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="accountList2">
            </tbody>
        </table>

        결제일<input type="date" name="pay-date" id="pay-date-last" readonly><br>
        마감일<input type="date" name="due-date" id="due-date-last" readonly><br>
        <button type="button" onclick="alert('공유 로직??')">공유</button>
        <button type="button" onclick="getDutchPayInfo(getDp_idx())">수정하기</button><br>
        <button type="button" onclick="saveDutchPayResult()">저장하기</button>
    </div>
</div>
<script type="text/html" id="pay-result-tmpl">
    {{each(index, result) resultList}}
    <tr>
        <td>
            <input type="text" id="pay-result-sender" style="width: 80px" value="{{= sender.payMember_name }}" readonly>
        </td>
        <td>
            <input type="text" id="pay-result-recipient" style="width: 80px" value="{{= recipient.payMember_name }}" readonly>
        </td>
        <td>
            <input type="text" id="pay-result-amount" value="{{= comma(amount)}}" readonly>
        </td>
        <td>
            <input type="checkbox" id="pay-result-paid" onchange="changePaidStatus(this, {{= index}})" {{= paid ? 'checked' : ''}} >
        </td>
    </tr>
    {{/each}}
</script>
<script type="text/html" id="account-form-tmpl2">
    {{each(index, p) pSave}}
    {{if p.payMember_account != null}}
    <tr style="color: #888888">
        <td>\${p.payMember_bank}</td>
        <td>\${p.payMember_account}</td>
        <td>\${p.payMember_name}</td>
    </tr>
    {{/if}}
    {{/each}}
</script>
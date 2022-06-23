<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<script type="text/javascript"  src="/script/jquery.tmpl.js"></script>
<script type="text/javascript"  src="https://cdn.jsdelivr.net/npm/moment@2.29.3/moment.min.js"></script>

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
        <div class="memberBox" style="display: inline-block;">
            {{each(index, m) mList}}
                <span id="mList_\${index}" class="mList_\${index}" data-idx="\${m.prgm_idx}">
                \${m.name}<a class="btn-member-delete" data-idx="\${index}" onclick="memberCheck($(this))"><img src="/images/delete_member.png"></a>
                </span>
            {{/each}}
        </div>
    </script>

    <script type="text/html" id="pay-form-tmpl">
        <tr id="pay-form">
            <td>
                <input type="text" id="form-pay-name" style="width: 50px" value="{{= pay ? pay.name : ''}}">
            </td>
            <td><input type="text" id="form-pay-price" onkeyup="inputNumberFormat(this)" style="width: 100px" value="{{= pay ? pay.price : ''}}"></td>
            <td>
                <select id="form-pay-payer">
                        {{each(index,p) groupMember}}
                            <option class="form-pay-selector" value="\${p.prgm_idx}"
                                    {{= pay && (p.prgm_idx == pay.payer.prgm_idx) ? 'selected' : null}}>
                                \${p.name}
                            </option>
                        {{/each}}
                </select>
            </td>
            <td>
                <button type="button" id="form-pay-participants" class="mini-btn"  onclick="changeParticipants()">
                    <span>{{= participants_prgm_idx ? participants_prgm_idx.length : groupMember.length}}</span>명
                </button>
                <div id="form-pay-participants-list" style="display: none">
                    {{each(index, p) groupMember}}
                        <input type="checkbox" class="form-pay-participants-check" value="\${p.prgm_idx}"
                               data-prgm-name="\${p.name}"
                               {{= participants_prgm_idx && !participants_prgm_idx.includes(p.prgm_idx) ? null : 'checked'}}>
                        \${p.name}
                    {{/each}}
                    <button type="button" id="btn-participants" class="mini-btn" onclick="changeParticipantsNumber()">OK</button>
                </div>
            </td>
            <td><button type="button" id="btn-updated-pay" class="mini-btn" onclick="return {{= pay ? 'saveUpdatedPay('+pay.p_idx+', this)' : 'saveNewPay()'}}">저장</button></td>
        </tr>
    </script>

    <!--입력한 결제 저장 template-->
    <script type="text/html" id="pay-list-tmpl">
        {{each(index, p) pSave}}
        <tr style="color: #888888" class="save-pay-form\${index}">
            <td id="save-name">\${p.name}</td>
            <td id="save-price">\${p.price}</td>
            <td id="save-payer">\${p.payer.name}</td>
            <td id="save-participants">
                \${p.participants.length}명
            </td>
            <td>
                <!-- DB 저장된 더치페이 -- 결제 목록 -- 1. DB에서 가져올때 같이 가져온 결제 목록 2. 새로 추가한 결제 목록 -->
                <a class="btn-update-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="showUpdatePayForm(this)">수정</a>
                <a class="btn-delete-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="deleteSavePay(this)">삭제</a>
                <%--<button type="button" class="btn-delete-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="deleteSavePay(this)">삭제</button>
                <button type="button" class="btn-update-pay" data-idx="{{= p.p_idx ? p.p_idx : index}}" onclick="showUpdatePayForm(this)">수정</button>--%>
            </td>
        </tr>
        {{/each}}
    </script>

    <!-- 더치페이 방 리스트 template -->
    <script type="text/html" id="show-dutch-list-tmpl">
        {{each(index, p) dList}}
        <tr>
            <td><span>\${p.create_date}</span></td>
            <td><a href="javascript:showDutchPayInfo(\${p.dp_idx})"><span style="text-decoration: underline">\${p.name}</span></a></td>
            <td><span>\${p.total}</span></td>

            <td><span>
                {{if p.paid_list == null || p.paid_list.length <= 0}} 정산전
                {{else p.paid_list.length == 1 & p.paid_list[0] == 'Y'}} 정산완료
                {{else}} 정산중 {{/if}}
                {{if !(p.paid_list.length == 1 & p.paid_list[0] == 'Y')}}
                    {{if p.remainDate == '-'}}
                    <span style="font-weight: bold">(-)</span>
                    {{else p.remainDate == 0}}
                    <span style="color: red; font-weight: bold">(D-Day)</span>
                    {{else p.remainDate > 0}}
                    <span style="color: red; font-weight: bold">(D+{{= p.remainDate}})</span>
                    {{else}}
                    <span style="font-weight: bold">(D{{= p.remainDate}})</span>
                    {{/if}}
                {{/if}}
            </span></td>
            <td><a onclick="deleteOneDutchPay(\${p.dp_idx})"><span style="color: #E74133; text-decoration: underline">삭제</span></a></td>
        </tr>
        {{/each}}
    </script>

    <!-- 계좌번호 template -->
    <script type="text/html" id="account-form-tmpl">
        <!-- 추가/수정 폼 -->
        {{if accountIdx == 0}}
            <td style="width: 140px;">
                <input type="hidden" value="{{= accountInfo ? accountInfo.prgm_idx : ''}}" id="saved-account-prgm_idx">
                <input type="text" class="new-account-bank" id="new-account-bank" value="{{= accountInfo ? accountInfo.bank : ''}}">
            </td>
            <td style="width: 310px;"><input type="text" class="new-account-number" id="new-account-number" value="{{= accountInfo ? accountInfo.account : ''}}"></td>
            <td>
                {{if accountInfo == null}}<!-- 추가 -->
                <select class="new-account-owner" id="new-account-owner">
                    {{each(index,p) pr}}
                    {{if p.account == null}}
                    <option class="new-account-selector" value="\${p.prgm_idx}">\${p.name}</option>
                    {{/if}}
                    {{/each}}
                </select>
            </td>
            <td>
                <a id="btn-update-account" class="btn-updated-account" onclick="saveNewAccount($(this))">저장</a>
            </td>
            <td>
                {{/if}}
                {{if accountInfo != null}} <!-- 수정 -->
                    <input readonly value="\${accountInfo.name}" style="text-align: center; font-size: 20px; width: 100px;">
            </td>
            <td>
                <a id="btn-updated-account" class="btn-updated-account" onclick="updateSavedAccount($(this))">저장</a>
                <%--<button type="button" id="btn-updated-account" onclick="updateSavedAccount($(this))">저장</button>--%>
            </td>
                {{/if}}
        {{/if}}
        <!-- 계좌번호 목록 -->
        {{if accountIdx == 1}}
        {{each(index, p) pSave}}
        {{if p.account != null}}
        <tr style="color: #888888" class="save-account-form\${index}">
            <td id="save-bank"><span>\${p.bank}</span></td>
            <td id="save-number"><span>\${p.account}</span></td>
            <td id="save-owner"><span>\${p.name}</span></td>
            <td>
                <input type="hidden" id="prgm_idx" value="\${p.prgm_idx}">
                <a id="btn-update-account-ajax" class="btn-update-account" data-idx="\${index}" onclick="updateSaveAccount($(this))">수정</a>
                <a id="btn-delete-account-ajax" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</a>
                <%--<button type="button" id="btn-delete-account-ajax" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</button>
                <button type="button" id="btn-update-account-ajax" class="btn-update-account" data-idx="\${index}" onclick="updateSaveAccount($(this))">수정</button>--%>
            </td>
        </tr>
        {{/if}}
        {{/each}}
        {{/if}}
    </script>

<!------------------- html ------------------->

<div id="payRoom">
    <div class="roomInfo">
        <div class="room_tit"><input type="text" name="room_name" id="room_name" readonly></div>
        <div class="room_member">
            <span>참여자</span>
            <input type="text" name="groupMember" id="groupMember">
            <a onclick="memberListOne()"><img src="/images/btn-add-member.png"></a>
        <div id="memberList"></div>
        </div>
    </div>

    <div class="payRoomList">
        <div class="list_top">
            <span class="tit">결제 목록</span>
            <a class="btn-add-room" onclick="openDutchPayForm()"><img src="/images/btn-add-member.png"></a>
        </div>
        <table>
            <thead>
            <tr>
                <th><span>결제일</span></th>
                <th><span>제목</span></th>
                <th><span>총 결제금액</span></th>
                <th><span>정산현황</span></th>
                <th></th>
            </tr>
            </thead>
            <tbody id="dutchList"></tbody>
        </table>
    </div>



    <div class="payRoomList">
        <div class="list_top">
            <span class="tit">계좌 목록</span>
            <a class="btn-add-room" id="btn-account-plus" onclick="createNewAccount($(this))"><img src="/images/btn-add-member.png"></a>
        </div>
        <table>
            <thead>
            <tr>
                <th><span>은행</span></th>
                <th><span>계좌번호</span></th>
                <th><span>이름</span></th>
                <th></th>
            </tr>
            </thead>
            <tbody id="accountList">
            </tbody>
        </table>
    </div>

<!-- 결제 목록 추가하기 template -->
<div class="modal">
    <div class="modal_body">
        <div class="top">
            <span class="tit">편집하기</span>
            <a onclick="closeDutchPayForm()"><img src="/images/ico_close.png"></a>
        </div>
        <input type="hidden" id="retrieve-pay-id">

        <div class="room_tit">
            <input type="text" id="pay_name" name="pay_name" placeholder="결제이름">
            <a id="btn-pay-plus"onclick="return togglePayForm($(this))"><img src="/images/btn-add-member.png"></a>
        </div>

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
            <!--pay-form-tmpl-->
            </tbody>
            <tbody id="payList2">

            </tbody>
        </table>
        <div class="calInfo">
            <div class="box">
                <span>총 금액</span>
                <input name="allPrice" id="allPrice" value="0" readonly>
            </div>
            <div class="box">
                <span>절사 옵션</span>
                <select name="cutPrice" id="cutPrice">
                    <option value="0" id="noCut" selected=true>없음</option>
                    <option value="10" >10원</option>
                    <option value="100">100원</option>
                    <option value="1000">1000원</option>
                </select>
                <div class="reset"></div>
            </div>
            <div class="box">
                <span>결제일</span>
                <input type="date" name="pay-date" id="pay-date">
            </div>
            <div class="box">
                <span>마감일</span>
                <input type="date" name="due-date" id="due-date">
            </div>
            <div class="box">
                <span>영수증</span>
                <input type="text" name="bill" id="bill">
            </div>
        </div>
        <a class="btn-save" onclick="saveDutchPayForm()"><span>저장</span></a>
        <a class="btn-show-result" onclick="showDutchPayResult()"><span>결과 미리보기(정산하기)</span></a>
    </div>
</div>

<!-- 결과 미리보기 화면 -->
<div class="second_modal">
    <div class="modal_body">
        <div class="top">
            <span class="tit">결과보기</span>
            <a onclick="closeDutchPayForm()"><img src="/images/ico_close.png"></a>
        </div>
        <%--<button type="button" onclick="closeDutchPayForm()">X</button>
        <hr>--%>
        <span><input type="text" class="sub-tit" id="pay-name-last" name="pay_name" readonly></span>
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

        <span class="sub-tit">계좌 정보</span>
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

        <div class="calInfo">
            <div class="box">
                <span>결제일</span>
                <input type="date" name="pay-date" id="pay-date-last" readonly>
            </div>
            <div class="box">
                <span>마감일</span>
                <input type="date" name="due-date" id="due-date-last" readonly>
            </div>
        </div>

        <a class="btn-edit" onclick="copyClipDutchPayInfo()"><span>공유하기</span></a>
        <a class="btn-edit" onclick="getDutchPayInfo(getDp_idx())"><span>수정하기</span></a>
        <a class="btn-save" onclick="saveDutchPayResult()"><span>저장하기</span></a>
    </div>
</div>
</div>


<!------------------- html ------------------->


<script type="text/html" id="pay-result-tmpl">
    {{each(index, result) resultList}}
    <tr class="tmpl_class">
        <td>
            <input type="text" id="pay-result-sender" class="pay-result-sender-class" style="width: 80px" value="{{= sender.name }}" readonly>
        </td>
        <td>
            <input type="text" id="pay-result-recipient" class="pay-result-recipient-class" style="width: 80px" value="{{= recipient.name }}" readonly>
        </td>
        <td>
            <input type="text" id="pay-result-amount" class="pay-result-amount-class" value="{{= comma(amount)}}" readonly>
        </td>
        <td>
            <input type="checkbox" id="pay-result-paid" class="pay-result-paid-class" onchange="changePaidStatus(this, {{= index}})" {{= paid ? 'checked' : ''}} >
        </td>
    </tr>
    {{/each}}
</script>
<script type="text/html" id="account-form-tmpl2">
    {{each(index, p) pSave}}
    {{if p.account != null}}
    <tr style="color: #888888">
        <td class="account-form-tmpl-bank">\${p.bank}</td>
        <td class="account-form-tmpl-account">\${p.account}</td>
        <td class="account-form-tmpl-name">\${p.name}</td>
    </tr>
    {{/if}}
    {{/each}}
</script>

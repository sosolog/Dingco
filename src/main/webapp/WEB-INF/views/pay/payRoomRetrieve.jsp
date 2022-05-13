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
        // 페이방 기본 정보
        let payRoom = ${payRoom};
        let pr_idx = payRoom.pr_idx;
        let room_name = payRoom.room_name;
        let groupMemberArr = payRoom.groupMemberList;
        console.log("페이방 멤버 목록:", groupMemberArr);


        // 기타 로직을 위한 정보
        let payArr = []; // 새로 더치페이 생성시, 추가하는 결제 목록 리스트
        let tempObj = null;

        // TODO: 현재 방 멤버 목록 수정 된 이후, memberArr도 수정되는지 확인
        let memberArr = []; // 현재 방 멤버 이름 목록
        groupMemberArr.forEach( x => memberArr.push(x.payMeber_name));

        $(document).ready(function(){
            // 페이방 정보 보여주기
            $("#room_name").val(room_name); // 페이방 이름
            $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr})); // 방 멤버 목록
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave:groupMemberArr})); // 계좌번호 목록
            showDutchPayList(pr_idx) // 더치페이 목록
        });

        // 더치페이 생성 폼 열기
        function openDutchPayForm() {
            $(".modal").addClass("show");

            // 더치페이 생성 폼 > 더치페이 생성일 오늘로 지정
            var today = new Date().toISOString().substring(0, 10);
            $("#pay-date").val(today);
        }


        // 더치페이 폼 닫기
        function closeDutchPayForm() {

            // TODO: 저장시, 유효성 검사 조건 및 저장 조건 확정하여 변경!
            // 저장된 결제 목록이 있으면 저장! (이름 없으면 임의 생성)
            if( payArr.length > 0 && !isRetrievedDutchInfo()){

                // 더치페이 이름 정보 없을 시, 임의로 이름 생성(현재 날짜 & 시간 기준)
                if($("#pay_name").val().trim().length <= 0){
                    $("#pay_name").val(createArbitraryName());
                }

                // 더치페이 폼에서 정보 가져와서 데이터 저장
                saveNewDutchPayInfo();

            } else if (isRetrievedDutchInfo()) { // TODO: retrieve한 내용 수정 한 이후, 닫기

            }

            // modal 안보이도록 css 변경
            $(".modal").removeClass("show");

            // 현재까지 저장되어있던 정보 삭제
            clearDutchPayForm();
        }


        // 새로 생성한 더치페이 정보 저장
        function saveNewDutchPayInfo() {

            // 더치페이 폼에서 정보 가져오기
            var dutchObj = getDutchPayInfoFromForm();
            getNewPayListFromForm(dutchObj);
            console.log(dutchObj);

            // 가져온 정보 Ajax로 저장
            $.ajax({
                url:"/pay/new",
                type:"POST",
                data: dutchObj,
                success:function (data){
                    console.log(data);
                    showDutchPayList(pr_idx);
                },
                error:function (x,i,e){
                    console.log(e);
                }
            })

        }


        // 더치페이 폼에서 정보(payList 제외) 가져오기
        function getDutchPayInfoFromForm() {
            var dutchPayObj = {
                "pr_idx": pr_idx,
                "dp_idx": getDp_idx(),
                "dutchPayName": $("#pay_name").val().trim() ? $("#pay_name").val().trim() : null,
                "totalPay": uncomma($("#allPrice").val()) * 1,
                "option": $("#cutPrice").val(),
                "createDate": $("#pay-date").val() ? $("#pay-date").val() : null,
                "dueDate": $("#due-date").val() ? $("#due-date").val() : null,
            }

            // 유효하지 않은 값이 들어간 키의 경우 삭제
            // null => ajax를 통해 백단으로 가면 "" 빈문자열로 들어간다.
            Object.keys(dutchPayObj).forEach(k => {
                if(!dutchPayObj[k]) delete dutchPayObj[k];
            });
            return dutchPayObj;
        }

        // 새로 추가한 결제목록 정보 가져오기
        function getNewPayListFromForm(obj){
            payArr.forEach((v, index) => {
                mappingPay(obj, v, 'payList['+index+'].' )
            });
        }

        // 더치페이 폼에서 현재까지 저장되어있던 정보 삭제
        function clearDutchPayForm() {
            payArr = [];
            $("#pay_name").val("");
            $("#allPrice").val(0);
            $("#cutPrice").val("0");
            $("#pay-date").val("");
            $("#due-date").val("");
            $("#bill").val("");
            $("#retrieve-pay-id").val("");
            $("#payList").html("");

            $("#is-pay-form-opened").val("");
            $("#btn-pay-plus").text("+");
        }

        // payList를 주면 총 결제금액 계산 하여 반환!
        function calculateTotalPay(payList) {
            var total = 0;
            payList.forEach(pay => {
                total += uncomma(pay.payPrice) * 1;
            });
            return total;
        }

        // DB에 저장된 정보를 불러온 것인가(아니면, 새로 생성하는 dutchPay 의미)
        function isRetrievedDutchInfo(){
            var dp_idx = $("#retrieve-pay-id").val();
            return dp_idx.length > 0;
        }

        function getDp_idx() {
            return isRetrievedDutchInfo() ? Number.parseInt($("#retrieve-pay-id").val()) : null;
        }

        // ajax를 통해 보낼 수 있도록 맵핑
        function mappingPay(obj, payObj, prefix = "" ){
            obj[prefix+'p_name'] = payObj.payName;
            obj[prefix+'price'] = uncomma(payObj.payPrice) * 1;
            mappingGroupMember(obj, payObj.payPayer, prefix+'payMember.');
            mappingPayParticipants(obj, payObj.payParticipants, prefix);
        }

        function mappingPayParticipants(obj, participants, prefix = "") {
            participants.forEach((p, idx) => {
                mappingGroupMember(obj, p, prefix+'participants['+idx+'].');
            })
        }

        function mappingGroupMember(obj, groupMember, prefix = "") {
            obj[prefix+'prgm_idx'] = groupMember.prgm_idx;
            obj[prefix+'payMember_name'] = groupMember.payMember_name;
        }


        // 현재 날짜/시간 기준으로 임의 이름 생성 (MMDD_HH:mm:ss 형식)
        function createArbitraryName() {
            let today = new Date();
            let year = today.getFullYear().toString().slice(-2);
            let month = (today.getMonth() + 1).toString().padStart(2, "0");  // 월
            let date = (today.getDate()).toString().padStart(2, "0");  // 날짜
            let hours = today.getHours(); // 시
            let minutes = today.getMinutes();  // 분
            let seconds = today.getSeconds();  // 초
            return year + month + date + "_"+ hours + ':' + minutes + ':' + seconds;
        }

        // 결제 목록(payList)를 제외한 더치페이 정보 수정
        function saveUpdateDutchPay(){
            var dutchpayObj = getDutchPayInfoFromForm();
            console.log(dutchpayObj);
            $.ajax({
                url:`/pay/\${pr_idx}/dutch/\${dp_idx}`,
                type:"PUT",
                data: dutchpayObj,
                success:function (data){
                    console.log(data);
                    showDutchPayList(pr_idx)
                },
                error:function (x,i,e){
                    console.log(e);
                }
            });
        }

        // 최초 화면 - 결제목록(더치페이목록) - 삭제 버튼
        // 더치페이 내역 하나 삭제
        function deleteOneDutchPay(dp_idx){
            console.log(`\${dp_idx} 번 더치페이 삭제`);
            var isOk = confirm("정말로 삭제하시겠습니까? 이후엔 다시 복구할 수 없습니다.");
            if(isOk){
                $.ajax({
                    url:`/pay/\${pr_idx}/dutch/\${dp_idx}`,
                    type:"DELETE",
                    success:function (data){
                        console.log(data);
                        showDutchPayList(pr_idx);
                    },
                    error:function (x,i,e){
                        console.log(e);
                    }
                })
            }
        }

        // TODO: 여기까지 리팩토링 완료

        function updateSavePay(btn) {
            console.log($(btn).parents("tr"));
            var groupMember = null;
            // 현재 페이방 그룹 멤버 가져오기
            $.ajax({
                url:`/pay/\${pr_idx}/member`,
                type:"GET",
                success:function (data){
                    console.log(data);
                    groupMember = data;
                    // afterSuccessGetGroupMember(groupMember, btn);
                },
                error:function (x,i,e){
                    console.log(e);
                }
            })
        }

        function afterSuccessGetGroupMember(groupMember, btn){
            var p_idx = $(btn).attr("data-idx");

            if (isRetrievedDutchInfo()) {
                var dp_idx = getDp_idx();
                $.ajax({
                    url:`/pay/\${pr_idx}/dutch/\${dp_idx}/\${p_idx}`,
                    type:"GET",
                    success:function (data){
                        // console.log(data);
                        var participantsNum = [];
                        data.participants.forEach(participant => participantsNum.push(participant.prgm_idx));
                        var payObj = {
                            "groupMember": groupMemberArr,
                            "pay": parsePayIntoPayObj(data),
                            "participants_prgm_idx":participantsNum
                        }
                        $(btn).parents("tr").html($("#pay-form-tmpl").tmpl(payObj));
                        console.log(payObj)
                    },
                    error:function (x,i,e){
                        console.log(e);
                    }
                })
            } else {
                var index = Number.parseInt($(btn).attr("data-idx"));
                var data = payArr[index];
                data.p_idx = index;
                var participantsNum = [];
                data.payParticipants.forEach(participant => {
                    var prgm_idx = Number.parseInt(participant.prgm_idx);
                    participantsNum.push(prgm_idx);
                    participant.prgm_idx = prgm_idx;
                });
                var payObj = {
                    "groupMember": groupMemberArr,
                    "pay": data,
                    "participants_prgm_idx":participantsNum
                }
                $(btn).parents("tr").html($("#pay-form-tmpl").tmpl(payObj));
                $(btn).parents("tr").attr("id", "update-pay-form");
            }
        }

        function saveUpdatedPay(p_idx) {
            var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);
            console.log(p_idx);


            var dp_idx = $("#retrieve-pay-id").val();
            var savePayName = $("#update-pay-name").val();
            var savePayPrice = $("#update-pay-price").val();
            var savePayPayer = {"prgm_idx":$(".update-pay-selector:selected").val(), "payMember_name":$(".update-pay-selector:selected").text()};
            var savePayParticipants = [];
            $(".new-pay-participants-check:checked").each((idx, chked) => savePayParticipants.push({"prgm_idx": chked.value, "payMember_name":$(chked).attr("data-prgm-name")}));
            // console.log(savePayName, savePayPrice, savePayPayer, savePayParticipants);
            if (isRetrieveInfo) {
                var payObj = {
                    "p_idx":p_idx,
                    "p_name":savePayName,
                    "price":uncomma(savePayPrice)*1
                }
                payObj['payMember.prgm_idx'] = savePayPayer.prgm_idx;
                payObj['payMember.payMember_name'] = savePayPayer.payMember_name;
                savePayParticipants.forEach((v, index) => {
                    payObj['participants['+index+'].prgm_idx'] = v.prgm_idx;
                    payObj['participants['+index+'].payMember_name'] = v.payMember_name;
                })
                console.log(payObj);
                $.ajax({
                    url:`/pay/\${pr_idx}/dutch/\${dp_idx}/\${p_idx}`,
                    type:"PUT",
                    data: payObj,
                    success:function (data){
                        console.log(data);
                        showDutchPayInfo(dp_idx);
                    },
                    error:function (x,i,e){
                        console.log(e);
                    }
                })
            } else {
                // console.log(payArr[p_idx])
                savePayParticipants.forEach(participant => participant.prgm_idx = Number.parseInt(participant.prgm_idx));
                payArr[p_idx] = PayObjForPayArr(savePayName, savePayPrice, savePayPayer, savePayParticipants);
                // console.log(payArr[p_idx])
                $("#update-pay-form").remove();
                $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));
                var total = 0;
                console.log(payArr);
                payArr.forEach(pay => total += uncomma(pay.spp)*1);
                $("#allPrice").val(comma(total));
            }


        }

        // TODO: 아래 부분도 리팩토링 완료
        function parsePayIntoPayObj(dtoObj) {
            var payMember = Member(dtoObj.payMember.prgm_idx, dtoObj.payMember.payMember_name);
            var participants = dtoObj.forEach( p => Member(p.prgm_idx, p.payMember_name) );
            return PayObjForPayArr(dtoObj.p_name, dtoObj.price, payMember, participants);
        }

        function PayObjForPayArr(payName = null, payPrice = null, payPayer = null, payParticipants = null, p_idx = null) {
            var payObj = {
                "payName": payName,
                "payPrice": payPrice,
                "payPayer": payPayer,
                "payParticipants": payParticipants,
                "p_idx": p_idx
            }
            return payObj;
        }

        function Member(prgm_idx = null, payMember_name = null) {
            var member = {
                "prgm_idx": prgm_idx,
                "payMember_name": payMember_name
            }
            return member;
        }

        // pay form 열려 있는가?
        function isPayFormOpened() {
            return new Boolean($("#is-pay-form-opened").val()).valueOf();
        }

        // 새 pay 입력시, 폼 탬플릿 보여주기/없애기 토글
        function togglePayForm(){
            if(!isPayFormOpened()){
                $("#btn-pay-plus").text("-");
                $("#pay-form-tmpl").tmpl({groupMember:groupMemberArr, pay: null, participants_prgm_idx:null}).appendTo("#payList");
                $("#is-pay-form-opened").val("true");
            }else{
                $("#btn-pay-plus").text("+");
                $("#pay-form:last").remove();
                $("#is-pay-form-opened").val("");
            }
            return false;
        }

        // pay 폼에 작성된 정보 가져오기
        function getPayInfoFromForm() {
            var payName = $("#form-pay-name").val().trim();
            var payPrice = $("#form-pay-price").val();
            var payPayer = Member(
                prgm_idx = $(".form-pay-selector:selected").val(),
                payMember_name = $(".form-pay-selector:selected").text().trim()
            );
            var payParticipants = [];
            $(".form-pay-participants-check:checked")
                .each(
                    (idx, chked) =>
                    payParticipants.push(Member(
                        prgm_idx = chked.value,
                        payMember_name = $(chked).attr("data-prgm-name")
                    ))
                );
            return PayObjForPayArr(payName, payPrice, payPayer, payParticipants);
        }

        // 결제내역 폼에 작성된 정보 저장해주는 함수
        function saveNewPay() {
            // 폼에서 정보가져오기
            var payObj = getPayInfoFromForm();
            console.log(payObj);

            // 저장전 유효성 검사
            if(payObj.payName.length <= 0){ // payName 유효성 체크
                alert("결제 내역 이름을 입력하세요.");
                return false;
            } else if(payObj.payPrice <= 0) { // payName 유효성 체크
                alert("결제 금액을 입력하세요.");
                return false;
            } else if(payObj.payParticipants.length < 1){ // payParticipants 유효성 체크
                alert("참여인원은 1명 이상이어야 합니다.");
                return false;
            }

            togglePayForm();

            if (!isRetrievedDutchInfo()) {
                payArr.push(payObj);
                console.log(payArr);

                $("#payList").html($("#save-pay-tmpl").tmpl({pSave: payArr}));
                $("#allPrice").val(comma(calculateTotalPay(payArr)));

                return false;
            } else {
                var dp_idx = getDp_idx();
                var mappedObj = {};
                mappingPay(mappedObj, payObj); // ajax로 데이터 보낼 수 있도록 맵핑하기
                console.log(mappedObj);

                $.ajax({
                    url: `/pay/\${pr_idx}/dutch/\${dp_idx}`,
                    type: "POST",
                    data: mappedObj,
                    success: function (data) {
                        console.log(data);
                        showDutchPayInfo(dp_idx);
                    },
                    error: function (x, i, e) {
                        console.log(e);
                    }
                });
            }
        }

        //저장된 결제 삭제하는 함수
        function deleteSavePay(tr) {
            let index = $(tr).attr("data-idx");

            if (!isRetrievedDutchInfo()) {
                payArr.splice(index, 1);
                // console.log(payArr);
                $("#payList").html($("#save-pay-tmpl").tmpl({pSave: payArr}));
                $("#allPrice").val(comma(calculateTotalPay(payArr)));
            } else {
                var dp_idx = getDp_idx();
                var p_idx = index;

                $.ajax({
                    url: `/pay/\${pr_idx}/dutch/\${dp_idx}/\${p_idx}`,
                    type: "DELETE",
                    success: function (data) {
                        console.log(data);
                        showDutchPayInfo(dp_idx);
                    },
                    error: function (x, i, e) {
                        console.log(e);
                    }
                });
            }
        }



    </script>

    <!-- 방생성 modal member 명단 template-->
    <script type="text/html" id="member-list-tmpl">
        {{each(index, m) mList}}
            <span id="mList_\${index}" class="mList_\${index}" data-idx="\${m.prgm_idx}">
            \${m.payMember_name}<button class="btn-member-delete" data-idx="\${index}" onclick="memberCheck($(this))">X</button>
            </span>
        {{/each}}
    </script>

    <!-- 새 더치페이 생성 template-->
    <script type="text/html" id="new-pay-tmpl">
        <tr style="color: #888888" id="new-pay-form" >
            <td><input type="text" id="new-pay-name" style="width: 50px"></td>
            <td><input type="text" id="new-pay-price" onkeyup="inputNumberFormat(this)" style="width: 100px"></td>
            <td>
                <select id="new-pay-payer">
                    {{each(index,p) pr}}
                    <option class="new-pay-selector" value="\${p.prgm_idx}">\${p.payMember_name}</option>
                    {{/each}}
                </select>
            </td>
            <td><button type="button" id="new-pay-participants" style="width: 50px"  onclick="changeParticipants()"> <span>\${pr.length}</span>명</button>
                <div id="form-pay-participants-form" style="display: none">
                    {{each(index, p) pr}}
                    <input type="checkbox" class="new-pay-participants-check" value="\${p.prgm_idx}" data-prgm-name="\${p.payMember_name}" checked>\${p.payMember_name}
                    {{/each}}
                    <button type="button" onclick="changeParticipantsNumber()">OK</button>
                </div>
            </td>
            <td><button id="btn-update-pay" type="button" onclick="return saveNewPay()">저장</button></td>
        </tr>
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
                    <button type="button" onclick="changeParticipantsNumber()">OK</button>
                </div>
            </td>
            <td><button type="button" onclick="return {{= pay ? 'saveUpdatedPay('+pay.p_idx+')' : 'saveNewPay()'}}">저장</button></td>
        </tr>
    </script>

    <!--입력한 결제 저장 template-->
    <script type="text/html" id="save-pay-tmpl">
        {{each(index, p) pSave}}
        <tr style="color: #888888" class="save-pay-form\${index}">
            <td id="save-name">\${p.payName}</td>
            <td id="save-price">\${p.payPrice}</td>
            <td id="save-payer">\${p.payPayer.payMember_name}</td>
            <td id="save-participants">
                \${p.payParticipants.length}명
            </td>
            <td>
                <button type="button" id="btn-delete-pay" class="btn-delete-pay" data-idx="\${index}" onclick="deleteSavePay($(this))">삭제</button>
                <button type="button" class="btn-update-pay" data-idx="\${index}" onclick="updateSavePay($(this))">수정</button>
            </td>
        </tr>
        {{/each}}
    </script>

    <script type="text/html" id="retrieve-pay-tmpl">
        {{each(index, p) payList}}
        <tr style="color: #888888" class="save-pay-form\${index}">
            <td>\${p.p_name}</td>
            <td>\${p.price}</td>
            <td>\${p.payMember.payMember_name}</td>
            <td>{{each(index, prgm) p.participants}}
                \${prgm.payMember_name}
                {{/each}}
            </td>
            <td>
                <button type="button" id="btn-delete-pay-ajax" class="btn-delete-pay" data-idx="\${p.p_idx}" onclick="deleteSavePay($(this))">삭제</button>
                <button type="button" id="btn-update-pay-ajax" class="btn-update-pay" data-idx="\${p.p_idx}" onclick="updateSavePay($(this))">수정</button>
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

    <!-- 새 계좌번호 생성 template-->
    <script type="text/html" id="new-account-tmpl">
        <tr style="color: #888888" id="new-account-form" >
            <td><input type="text" id="new-account-bank" style="width: 50px"></td>
            <td><input type="text" id="new-account-number" style="width: 100px"></td>
            <td>
                <select id="new-account-owner">
                    {{each(index,p) pr}}
                    {{if p.payMember_account == null}}
                    <option class="new-account-selector" value="\${p.prgm_idx}">\${p.payMember_name}</option>
                    {{/if}}
                    {{/each}}
                </select>
            </td>
            <td><button id="btn-update-account" onclick=" return saveNewAccount()">저장</button></td>
        </tr>
    </script>

    <!--입력한 계좌번호 저장 template-->
    <script type="text/html" id="save-account-tmpl">

            {{each(index, p) pSave}}
                {{if p.payMember_account != null}}
        <tr style="color: #888888" class="save-account-form\${index}">

            <td id="save-bank">\${p.payMember_bank}</td>
            <td id="save-number">\${p.payMember_account}</td>
            <td id="save-owner">\${p.payMember_name}</td>
            <td>
                <input type="hidden" id="prgm_idx" value="\${p.prgm_idx}">
                <button id="btn-delete-account-ajax" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</button>
                <button id="btn-update-account-ajax" class="btn-update-account" data-idx="\${index}" onclick="updateSaveAccount($(this))">수정</button>
            </td>
        </tr>
                {{/if}}
            {{/each}}
    </script>

<script type="text/html" id="update-account-tmpl">
        <td>
            <input type="hidden" value="\${prgm_idx}" id="saved-account-prgm_idx">
            <input type="text" id="saved-account-bank" style="width: 50px" value="\${payMember_bank}"></td>
        <td><input type="text" id="saved-account-number" style="width: 100px" value="\${payMember_account}"></td>
        <td>
            <input readonly value="\${payMember_name}">
        </td>
        <td><button id="btn-updated-account" onclick="updateSavedAccount($(this))">저장</button></td>
</script>

<h1>여기는 PAY방입니다<h1><br>
    <button onclick="javascript:location.href='/pay/list'">취소</button>
    <h2>Pedal</h2>
<button>등록</button><br>
방이름 : <input type="text" name="room_name" id="room_name" readonly><br>
방멤버 : <input type="text" name="groupMember" id="groupMember" ><button onclick="memberListOne()">추가</button><br>
<div id="memberList"></div>
<button>1개만</button><button>여러개</button><br>
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

<span>계좌 목록</span><button id="btn-account-plus" onclick="return createNewAccount()">추가</button><br>
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
        <button type="button" onclick="saveUpdateDutchPay()">수정하기</button>
        <input type="hidden" id="retrieve-pay-id">
        <hr>
        편집하기<br>

        <input type="text" id="pay_name" name="pay_name" placeholder="결제이름">
            <button type="button" id="btn-pay-plus"onclick="return togglePayForm()">+</button>
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
        <input type="button" onclick="alert('여기에 뭐 넣지')">결과 미리보기(정산하기)</input>
    </div>
</div>
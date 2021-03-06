/* START : PAYROOM, PAYGROUPMEMBER 정보 입력
    방이름 - PayRoom.room_name
    참여자 - PayRoom.pr_idx -> PayGroupMember.pr_idx , PayGroupMember.prgm_idx
*/
// 방생성 modal open
function openPayRoomForm() {
    $(".modal").addClass("show");
}

// 방생성 modal close ( 방생성 미완료 상태에서 종료 )
function closePayRoomForm() {
    // modal 안보이도록 css 변경
    $(".modal").removeClass("show");
    $(".second_modal").removeClass("show");

    // 현재까지 저장되어있던 정보 삭제
    memberArr = [];
    $("#room_name").val("")
}

// 방생성 중 memberList에서 member 삭제
function deleteMemberDuringCreatingPayRoom(btn){
    let index = $(btn).attr("data-idx");

    memberArr.splice(index,1); // memberArr 에서 member 삭제
    $(btn).parent().remove(); // html에서 해당 member span 태그 삭제
}

// 방생성 중 memberList에 member 추가
function memberList() {
    var member = $("#groupMember").val().trim();
    if (member.length > 0) {
        memberArr.push(member);

        $("#memberList").html($("#member-list-tmpl").tmpl({mList: memberArr}));

    }
    $("#groupMember").val(""); //추가 후 빈칸으로 만들기
}

// 방생성 이후 멤버 추가 : ajax를 이용한 insert
function memberListOne(){
    var member = $("#groupMember").val().trim();
    if (member.length > 0){
        postMemberCheckAjax(member);
    }
    $("#groupMember").val("");
}


// 방생성 이후 삭제할 멤버가 결제목록에서 결제자이거나 결제 참여자인지 확인하고 없으면 삭제, 있으면 alert
function memberCheck(self){
    console.log("call memberCheck");
    var prgm_idx = self.parent().attr("data-idx");
    getMemberCheckAjax(prgm_idx,self);
}



//ajax room_name,groupMember Controller에 넘기기(방제목, 참여자 유효성 검사)
function payRoomInfo(){
    var roomName = $("#room_name").val();
    if(roomName.length<2){
        $("#result_payRoom").text("방제목은 최소 2자 이상이어야 합니다.");
    }else if(memberArr.length==0){
        $("#result_payRoom").text("참여자는 최소 1명 이상이어야 합니다.");
    }else{
       postRoomAjax(roomName);
    }
}


/* END : PAYROOM, PAYGROUPMEMBER 정보 입력*/

/* START : 기타 기능 */

<!--숫자 천자리 콤마 없앤 후 다시 콤마 찍어주는 함수-->
function inputNumberFormat(obj) {
    obj.value = comma(uncomma(obj.value));
}
<!--숫자 천자리 콤마 없앤 후 다시 콤마 찍어주는 함수-->

<!--숫자 천자리 콤마 찍어주는 함수-->
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}
<!--숫자 천자리 콤마 찍어주는 함수-->

<!--숫자 천자리 콤마 없애주는 함수-->
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}
<!--숫자 천자리 콤마 없애주는 함수-->

/* END : 기타 기능 */


// 더치페이 생성 폼 열기
function openDutchPayForm() {
    $(".modal").addClass("show");

    // 더치페이 생성 폼 > 더치페이 생성일 오늘로 지정
    var today = new Date().toISOString().substring(0, 10);
    $("#pay-date").val(today);
}


// 새로 생성한 더치페이 정보 저장
function saveNewDutchPayInfo(success_fn=function (data){
    console.log("saveNewDutchPayInfo - result : ",data)
    let dp_idx = data;
    console.log("--> getDutchPayInfo")
    getDutchPayInfo(dp_idx)
    console.log("--> showDutchPayList")
    showDutchPayList(pr_idx);

}) {
    // 더치페이 폼에서 정보 가져오기
    var dutchObj = getDutchPayInfoFromForm();
    payArr.forEach(pay => pay.price = uncomma(pay.price) * 1);
    dutchObj.pay_list = payArr;

    console.log(dutchObj);

    // 가져온 정보 Ajax로 저장
    $.ajax({
        url:"/pay/new",
        type:"POST",
        data: JSON.stringify(dutchObj),
        contentType: "application/json; charset=UTF-8",
        success:success_fn,
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}


// 더치페이 폼에서 정보(payList 제외) 가져오기
function getDutchPayInfoFromForm() {
    var dutchPayObj = {
        "pr_idx": pr_idx,
        "dp_idx": getDp_idx(),
        "name": $("#pay_name").val().trim() ? $("#pay_name").val().trim() : null,
        "pay": uncomma($("#allPrice").val()) * 1,
        "option": $("#cutPrice").val(),
        "total_date": $("#pay-date").val() ? $("#pay-date").val() : null,
        "due_date": $("#due-date").val() ? $("#due-date").val() : null,
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

    // 유효하지 않은 값이 들어간 키의 경우 삭제
    // null => ajax를 통해 백단으로 가면 "" 빈문자열로 들어간다.
    Object.keys(obj).forEach(k => {
        if(!obj[k]) delete obj[k];
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
    $("#btn-pay-plus").html('<img src="/images/btn-add-member.png">');

    $("#payList2").html("");
    savedPayArr = [];
    updatedPayArr = new Set();
    deletedPayArr = [];
    resultList = null;
}

// payList를 주면 총 결제금액 계산 하여 반환!
function calculateTotalPay(payList) {
    var total = 0;
    payList.forEach(pay => {
        total += uncomma(pay.price) * 1;
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
    obj[prefix+'dp_idx'] = obj.dp_idx;
    obj[prefix+'p_idx'] = payObj.p_idx;
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

// 최초 화면 - 결제목록(더치페이목록) - 삭제 버튼
// 더치페이 내역 하나 삭제
function deleteOneDutchPay(dp_idx){
    console.log(`${dp_idx} 번 더치페이 삭제`);
    var isOk = confirm("정말로 삭제하시겠습니까? 이후엔 다시 복구할 수 없습니다.");
    if(isOk){
        $.ajax({
            url:`/pay/${pr_idx}/dutch/${dp_idx}`,
            type:"DELETE",
            success:function (data){
                console.log(data);
                showDutchPayList(pr_idx);
            },
            error:function (xhr,sta,error){
                console.log(error);
            }
        })
    }
}


// 결제 참여자 prgm_idx 만 들어있는 배열 생성
function makeParticipantsIdxList(participants) {
    var participantsIdxList = [];
    participants.forEach(participant => {
        participantsIdxList.push(participant.prgm_idx);
    });
    return participantsIdxList;
}

// 수정할 결제 정보를 수정 폼에 맵핑 후 보여주기
function mapInfoToUpdatePayForm(pay, btn){
    var payObj = {
        "groupMember": groupMemberArr,
        "pay": pay,
        "participants_prgm_idx":makeParticipantsIdxList(pay.participants)
    }
    $("#pay-form-tmpl").template("pay-form"); // 템플릿 소스를 가진 객체를 지정해 템플릿 이름을 부여
    $.tmpl("pay-form", payObj).insertAfter($(btn).parents("tr"));
    $(btn).parents("tr").remove();
}

function parsePayIntoPayObj(dtoObj) {
    console.log(dtoObj);
    var payMember = Member(dtoObj.payMember.prgm_idx, dtoObj.payMember.payMember_name);

    dtoObj.participants.forEach( p => Member(p.prgm_idx, p.payMember_name) );
    var participants = dtoObj.participants;
    return PayObj(dtoObj.p_name, dtoObj.price, payMember, participants, dtoObj.p_idx);
}

function PayObj(name = null, price = null, payer = null, participants = null, p_idx = null) {
    var pay = {
        "name": name,
        "price": price,
        "payer": payer,
        "participants": participants,
        "p_idx": p_idx ? Number.parseInt(p_idx) : null
    }
    return pay;
}

function Member(prgm_idx = null, name = null) {
    var member = {
        "prgm_idx": prgm_idx ? Number.parseInt(prgm_idx) : null,
        "name": name
    }
    return member;
}

// pay form 열려 있는가?
function isPayFormOpened() {
    return new Boolean($("#is-pay-form-opened").val()).valueOf();
}

// 새 pay 입력시, 폼 탬플릿 보여주기/없애기 토글
function togglePayForm(btn){
    if(!isPayFormOpened()){
        $("#btn-pay-plus").html('<img src="/images/btn_remove_room.png">');
        $("#pay-form-tmpl").tmpl({groupMember:groupMemberArr, pay: null, participants_prgm_idx:null}).appendTo("#payList");
        $("#is-pay-form-opened").val("true");
        btnDisabled(btn);
    }else{
        $("#btn-pay-plus").html('<img src="/images/btn-add-member.png">');
        $("#pay-form:last").remove();
        $("#is-pay-form-opened").val("");
        btnAbled();
    }
    return false;
}

// pay 폼에 작성된 정보 가져오기
function getPayInfoFromForm() {
    var payName = $("#form-pay-name").val().trim();
    var payPrice = $("#form-pay-price").val();
    var payPayer = Member(
        prgm_idx = Number.parseInt($(".form-pay-selector:selected").val()),
        name = $(".form-pay-selector:selected").text().trim()
    );
    var payParticipants = [];
    $(".form-pay-participants-check:checked")
        .each(
            (idx, chked) =>
                payParticipants.push(Member(
                    prgm_idx = Number.parseInt(chked.value),
                    name = $(chked).attr("data-prgm-name")
                ))
        );
    return PayObj(payName, payPrice, payPayer, payParticipants);
}

// 결제내역 폼에 작성된 정보 저장해주는 함수
function saveNewPay() {
    // 폼에서 정보가져오기
    var payObj = getPayInfoFromForm();
    // payObj["p_idx"] = -(payArr.length+1);
    // console.log(payObj);

    // 저장전 유효성 검사
    if(payObj.name.length <= 0){ // payName 유효성 체크
        alert("결제 내역 이름을 입력하세요.");
        return false;
    } else if(payObj.price <= 0) { // payName 유효성 체크
        alert("결제 금액을 입력하세요.");
        return false;
    } else if(payObj.participants.length < 1){ // payParticipants 유효성 체크
        alert("참여인원은 1명 이상이어야 합니다.");
        return false;
    }

    togglePayForm();

    payArr.push(payObj);
    console.log(payArr);
    if (!isRetrievedDutchInfo()) {

        $("#payList").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
        $("#allPrice").val(comma(calculateTotalPay(payArr)));

        return false;
    } else {
        $("#payList2").html($("#pay-list-tmpl").tmpl({pSave: payArr}));

        var preTotal = uncomma($("#allPrice").val()) * 1;
        $("#allPrice").val(comma(preTotal + calculateTotalPay(payArr)));
    }
}

/* START : PAYGROUPMEMBER 계좌정보 UPDATE */


//새로운 계좌 템플릿 테이블에 넣는 함수
function createNewAccount(btn) {
    if(groupMemberArr.filter(gm => gm.account == null).length == 0){
        alert("추가할 인원이 없습니다.");
    }else{
        var tmplInfo = {pr: groupMemberArr, accountInfo:null, accountIdx:0};
        var idInfoArr = ["#btn-update-account", "#btn-account-plus", "#account-form-tmpl", tmplInfo,
            "#accountList", "#btn-account-plus", "#accountList:last"]
        plusMinus(idInfoArr,btn);
    }
}

//적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
function saveNewAccount(btn) {
    console.log("call saveNewAccount");

    btnAbled();
    var savebank = $("#new-account-bank").val();
    var saveNumber = $("#new-account-number").val();
    var gm_idx = $("#new-account-owner").val();

    if (savebank.length > 0 && saveNumber.length > 0) {
        $("#btn-account-plus").text("+");

        var updatedMember = groupMemberArrRefresh(btn, gm_idx, savebank, saveNumber);
        $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr, accountIdx: 1}));

        putAccountAjax(updatedMember);
    }
}

function plusMinus(idInfoArr,btn) {
    if ($(idInfoArr[0]).length == 0) {
        btnDisabled(btn);
        $(idInfoArr[1]).html('<img src="/images/btn_remove_room.png">');
        $(idInfoArr[2]).tmpl(idInfoArr[3]).appendTo(idInfoArr[4]);
    } else {
        btnAbled();
        $(idInfoArr[5]).html('<img src="/images/btn-add-member.png">');
        $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr, accountIdx: 1}));
    }
}

//저장된 계좌 삭제하는 함수
function deleteSaveAccount(btn) {
    console.log("call deleteSaveAccount");

    var plusValid = $("#btn-account-plus").text();
    if (plusValid == "-" || $("#btn-updated-account").length != 0) {
        // pass
    } else {
        let prgm_idx = $(btn).parent().find("#prgm_idx").val();
        console.log(`prgm_idx = ${prgm_idx}`);
        var updatedMember = groupMemberArrRefresh(btn, prgm_idx);

        putAccountAjax(updatedMember);
        // TODO: /accountNull 을 안 쓰고 /accountInfo 에서 account 정보 삭제 로직도 처리하도록 mapper 수정함.
        // 왜나면,,, account 정보를 삭제한다고 표현했지만, 실제로는 account 정보만 null 로 update 하는 것과 다름 없기 때문임.

        // $.ajax({
        //     url: "/pay/accountNull",
        //     type: "PUT",
        //     data: {
        //         "prgm_idx": prgm_idx
        //     },
        //     success: function (data) {
        //         console.log(data);
        //     },
        //     error: function (x, i, e) {
        //         console.log(e);
        //     }
        // })
    }
}

function updateSaveAccount(btn) {
    console.log("call updateSaveAccount");

    btnDisabled(btn);
    var prgm_idx = btn.parents("tr").find("#prgm_idx").val(); // .find() : 어떤 요소의 하위 요소 중 특정 요소를 찾을 때 사용
    console.log(`prgm_idx = ${prgm_idx}`);

    $.ajax({
        url: `/pay/accountInfo/${prgm_idx}`,
        type: "GET",
        success: function (data) {
            console.log("after ajax succces : data = ", data);
            btn.parents("tr").html($("#account-form-tmpl").tmpl({accountInfo:data,accountIdx:0}));
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function updateSavedAccount(btn) {
    console.log("call updateSavedAccount");
    btnAbled();
    var savebank = $("#new-account-bank").val();
    var saveNumber = $("#new-account-number").val();
    var gm_idx = $("#saved-account-prgm_idx").val();

    if (savebank.length > 0 && saveNumber.length > 0) {
        var updatedMember = groupMemberArrRefresh(btn, gm_idx, savebank, saveNumber);
        $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr,accountIdx:1}));

        console.log(groupMemberArr, updatedMember);

        putAccountAjax(updatedMember);
    }
}

//계좌번호 수정 및 삭제시 groupMemberArr를 수정하는 함수
function groupMemberArrRefresh(btn, gm_idx, savebank=null, saveNumber=null) {
    var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
    findMember[0].bank = savebank;
    findMember[0].account = saveNumber;
    btn.parents("tr").remove();
    return findMember[0];
}

/* END : PAYGROUPMEMBER 계좌정보 UPDATE */

function changeParticipants() {
    $("#form-pay-participants-list").css("display", "block");
    $("#form-pay-participants").css("display", "none");
}

function changeParticipantsNumber() {
    // console.log($(".new-pay-participants-check:checked").length);
    var participants_num = $(".form-pay-participants-check:checked").length;
    if (participants_num > 0) {
        var number_display = $("#form-pay-participants").children()[0];
        $(number_display).html(participants_num);

        $("#form-pay-participants-list").css("display", "none");
        $("#form-pay-participants").css("display", "block");
    } else {
        alert("참여인원은 1명 이상이어야 합니다.");
    }

}

function showDutchPayList(pr_idx) {
    $.ajax({
        url: `/pay/${pr_idx}/dutch/list`,
        type: "GET",
        dataType: "json",
        traditional: "true",
        success: function (data) {
            console.log(data);
            var st_date = new Date().toISOString().substr(0, 10).replace('T', ' ');
            let now = new Date(st_date);
            data.forEach(d => {
                if (d.due_date == null) {
                    d.remainDate = '-'
                } else {
                    let dueDate = new Date(d.due_date);
                    let diffDate = now.getTime() - dueDate.getTime();
                    d.remainDate = diffDate / (1000 * 60 * 60 * 24);
                }
            });
            $("#dutchList").html($("#show-dutch-list-tmpl").tmpl({dList: data}));
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function copyClipDutchPayInfo() {
    var clipText = '';

    var pay_cnt = $('.pay-result-sender-class').length;
    var senderList = $('.pay-result-sender-class');
    var recipientList = $('.pay-result-recipient-class');
    var amount = $('.pay-result-amount-class');
    var paid = $('.pay-result-paid-class');

    var bank_cnt = $('.account-form-tmpl-bank').length;
    var bank = $('.account-form-tmpl-bank');
    var account = $('.account-form-tmpl-account');
    var name = $('.account-form-tmpl-name');


    const header = $("#pay-name-last").val() + " 모임 정산 내용입니다.\n\n"

    var content = "정산내용\n\n"

    for (let i = 0; i < pay_cnt; i++) {
        if($(paid[i]).prop("checked") == false) {
            content += senderList[i].value + "님은 " + recipientList[i].value + "님에게 " + amount[i].value + "원 입금 부탁드립니다.\n";
        }
    }
    content += "\n";

    var footer = "계좌정보\n\n"

    for (let i = 0; i < bank_cnt; i++) {
        footer += $(name[i]).text() + "에게 보내실 분은 " + $(bank[i]).text() + $(account[i]).text() + "로 보내주세요.\n";
    }
    footer += "\n계좌가 없는 인원은 카카오톡으로 보내주세요.";


    try {
        clipText += header;
        clipText += content;
        clipText += footer;

        navigator.clipboard.writeText(clipText).then(function () {
            window.alert("클립보드로 복사되었습니다.")//클립보드로 복사
        })
    } catch (err) {
        window.alert("복사할 수 없습니다. 에러코드는 다음과 같습니다." + err)//클립보드로 복사
    }
}

function getDutchPayInfo(dp_idx) {
    $(".second_modal").removeClass("show");
    $.ajax({
        url: `/pay/${pr_idx}/dutch/${dp_idx}`,
        type: "GET",
        dataType: "json",
        traditional: "true",
        success: function (data) {
            console.log(data);

            data.pay_list.forEach(p => p.price = comma(p.price));
            savedPayArr = data.pay_list;

            $(".modal").addClass("show");
            $("#payList").html($("#pay-list-tmpl").tmpl({pSave: savedPayArr}));
            $("#pay_name").val(data.name);
            $("#allPrice").val(comma(data.total));
            $("#cutPrice").val(data.option);
            $("#pay-date").val(data.create_date);
            $("#due-date").val(data.due_date);
            $("#bill").val("");
            $("#retrieve-pay-id").val(data.dp_idx);
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

//5.18
//저장된 결제 삭제하는 함수
function deleteSavePay(tr) {
    let index = Number.parseInt($(tr).attr("data-idx"));

    if (!isRetrievedDutchInfo()) {
        payArr.splice(index, 1);
        $("#payList").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
        $("#allPrice").val(comma(calculateTotalPay(payArr)));
    } else {
        var position = $(tr).parent().parent().parent().attr("id");
        if(position == 'payList2') {
            var payObj = payArr[index];
            payArr.splice(index, 1);
            console.log(payArr);

            $("#payList2").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
        } else {
            var p_idx = index;
            deletedPayArr.push(p_idx);
            if(updatedPayArr.has(p_idx)){
                updatedPayArr.delete(p_idx);
            }
            $(tr).parent().parent().remove();

            var idx = 0;
            savedPayArr.forEach((p, i) => {
                if(p.p_idx == p_idx) {
                    idx = i;
                }
            });
            savedPayArr.splice(idx, 1);

        }
        $("#allPrice").val(comma(calculateTotalPay(payArr)+calculateTotalPay(savedPayArr)));
    }
}


// pay 수정 폼 보여주기
function showUpdatePayForm(btn){

    var index = Number.parseInt($(btn).attr("data-idx"));
    if (!isRetrievedDutchInfo()) {
        var data = payArr[index];
        data.p_idx = index;

        mapInfoToUpdatePayForm(data, btn);
    } else {
        var position = $(btn).parent().parent().parent().attr("id");

        if(position == 'payList2') {
            var data = payArr[index];
            data.p_idx = index;
            mapInfoToUpdatePayForm(data, btn);
        } else {
            var p_idx = index;
            var findPay = savedPayArr.filter(p => p.p_idx == p_idx);
            mapInfoToUpdatePayForm(findPay[0], btn);
        }
    }
    btnDisabled(btn);
}

function saveUpdatedPay(p_idx, btn) {
    var payObj = getPayInfoFromForm();

    if (!isRetrievedDutchInfo()) {
        payArr[p_idx] = payObj;
        $("#pay-form").remove();

        $("#payList").html($("#pay-list-tmpl").tmpl({pSave:payArr}));
        $("#allPrice").val(comma(calculateTotalPay(payArr)));
    } else {
        var position = $(btn).parent().parent().parent().attr("id");
        if(position == 'payList2') {
            payArr[p_idx] = payObj;
            $("#pay-form").remove();
            $("#payList2").html($("#pay-list-tmpl").tmpl({pSave:payArr}));
        } else {
            payObj.p_idx = p_idx;
            var findPay = savedPayArr.filter(p => p.p_idx == p_idx);
            updatedPayArr.add(p_idx);

            findPay[0].name = payObj.name;
            findPay[0].price = payObj.price;
            findPay[0].payer = payObj.payer;
            findPay[0].participants = payObj.participants;

            $("#pay-form").remove();
            $("#payList").html($("#pay-list-tmpl").tmpl({pSave:savedPayArr}));
        }
        $("#allPrice").val(comma(calculateTotalPay(payArr)+calculateTotalPay(savedPayArr)));
    }
    btnAbled();
}

// 더치페이 폼 닫기
function closeDutchPayForm() {
    // modal 안보이도록 css 변경
    $(".modal").removeClass("show");
    $(".second_modal").removeClass("show");

    // 현재까지 저장되어있던 정보 삭제
    clearDutchPayForm();
    showDutchPayList(pr_idx);
}

function saveDutchPayForm(success_fn) {

    var dp_idx = getDp_idx();
    console.log("saveDutchPayForm : dp_idx = "+dp_idx)
    // 저장된 결제 목록이 있으면 저장! (이름 없으면 임의 생성)
    if(!isRetrievedDutchInfo()){
        // 유효성 검사 (결제목록도 없고, 이름도 없을 시 생성 안됨)
        if( payArr.length <= 0 && $("#pay_name").val().trim().length <= 0) {
            alert("생성할 페이목록의 이름을 작성하거나, 결제목록을 추가하세요.");
            return false;
        }

        // 더치페이 이름 정보 없을 시, 임의로 이름 생성(현재 날짜 & 시간 기준)
        if($("#pay_name").val().trim().length <= 0){
            $("#pay_name").val(createArbitraryName());
        }

        // 더치페이 폼에서 정보 가져와서 데이터 저장
        if(!success_fn) {
            saveNewDutchPayInfo();
        } else {
            saveNewDutchPayInfo(success_fn);
        }

    } else { // retrieve한 내용 수정 한 이후, 닫기
        if(!success_fn) {
            saveDutchPayForRetrievedInfo();
        } else {
            saveDutchPayForRetrievedInfo(success_fn);
        }
    }
}

function saveDutchPayForRetrievedInfo(success_fn = function (data){
    console.log(data);
    // 현재까지 저장되어있던 정보 삭제 및 정보 reload
    var dp_idx = getDp_idx();
    clearDutchPayForm();
    showDutchPayInfo(dp_idx);
}) {

    var dp_idx = getDp_idx();
    console.log("saveDutchPayForRetrievedInfo",dp_idx)
    payArr.forEach(p => {p.price = uncomma(p.price) * 1});

    var uArr = savedPayArr.filter(p => updatedPayArr.has(p.p_idx));
    uArr.forEach(p => p.price = uncomma(p.price) * 1);

    var obj = {
        dutch_info:getDutchPayInfoFromForm(),
        insert_list: payArr,
        update_list: uArr,
        delete_list: deletedPayArr
    };
    console.log(obj)
    $.ajax({
        url:`/pay/RetrieveInfo`,
        type:"POST",
        data: JSON.stringify(obj),
        contentType: "application/json; charset=UTF-8",
        success:success_fn,
        error:function (xhr,sta,error){
            console.log(error);
        }
    });
}

function btnDisabled(btn){
    for (var i = 0; i < $("button").length; i++) {
        $($("button")[i]).prop("disabled",true);
    }
    if($("#btn-updated-pay").length!=0){
        $("#btn-updated-pay").prop("disabled",false);
        $("#form-pay-participants").prop("disabled",false);
        $("#btn-participants").prop("disabled",false);
    }
    $(btn).prop("disabled",false);

}

function btnAbled(){
    for (var i = 0; i < $("button").length; i++) {
        $($("button")[i]).prop("disabled",false);
    }
}
//5.18

//ajaxfind - ajax 함수 모음

function postMemberCheckAjax(member){
    console.log("call postMemberCheckAjax");
    $.ajax({
        url:"/pay/membercheck",
        type:"POST",
        data:{
            "payMember_name":member,
            "pr_idx":pr_idx
        },
        success:function (data){
            console.log("after success : data = ", data); // PayGroupMemberDTO를 가져옴
            groupMemberArr.push(data);
            $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr}));
            console.log(groupMemberArr);
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function getMemberCheckAjax(prgm_idx,self){
    console.log("call getMemberCheckAjax");
    $.ajax({
        url: "/pay/membercheck",
        type: "GET",
        data: {
            "pr_idx": pr_idx,
            "prgm_idx": prgm_idx
        },
        success:function (data){
            // console.log(data);
            if(data){
                alert("결제참여자인지 확인해주십시오.");
            }else{
                // console.log("memberList에서 member 삭제")
                let index = self.attr("data-idx");
                groupMemberArr.splice(index,1); // groupMemberArr 에서 member 삭제
                self.parent().remove(); // html에서 해당 member span 태그 삭제
                $("#accountList").html($("#account-form-tmpl").tmpl({pSave:groupMemberArr, accountIdx:1})); // 계좌정보에 삭제된 멤버 없애기
                // console.log("[END] index:", index, ", groupMemberArr:", groupMemberArr);
                $.ajax({
                    url: "/pay/membercheck",
                    type: "DELETE",
                    data: {"prgm_idx": prgm_idx},
                    success:function (data){
                        // console.log("성공한 ajax"+data);
                    },
                    error:function (xhr,sta,error){
                        console.log(error);
                    }
                })
            }

        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function postRoomAjax(roomName){
    $.ajax({
        url:"/pay/room",
        type:"POST",
        data:{
            "roomName":roomName,
            "memberList":memberArr
        },
        success:function(data){
            //data = pr_idx
            // console.log(data);

            location.href = "/pay/"+data;
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function putAccountAjax(member) {
    console.log("call putAccountAjax")
    $.ajax({
        url: "/pay/accountInfo",
        type: "PUT",
        data: JSON.stringify(member),
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            console.log("success : return void"); // return 타입 void
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function showDutchPayResult() {
    saveDutchPayForm(success_fn=function(data){
        var dp_idx = data;
        console.log("--> getDutchPayInfo")
        getDutchPayInfo(dp_idx)
        console.log("--> showDutchPayList")
        showDutchPayList(pr_idx);
        calculateDutchPayResult(dp_idx);
        clearDutchPayForm();

    });
}

function calculateDutchPayResult(dp_idx){
    console.log("calculateDutchPayResult - dp_idx : "+ dp_idx)
    $.ajax({
        url:`/pay/${pr_idx}/dutch/${dp_idx}/result`,
        type:"GET",
        success:showDutchPayResultInfoToModal,
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function showDutchPayResultInfoToModal(data){
    console.log(data);
    console.log(data.result_list);
    resultList = data.result_list;

    $(".modal").removeClass("show");
    $(".second_modal").addClass("show");
    $("#payResultList").html($("#pay-result-tmpl").tmpl({resultList:data.result_list}));

    $("#pay-name-last").val(data.name);
    $("#pay-date-last").val(data.create_date);
    $("#due-date-last").val(data.due_date);
    $("#retrieve-pay-id").val(data.dp_idx);


    $("#accountList2").html($("#account-form-tmpl2").tmpl({pSave:groupMemberArr}))
}

function showDutchPayInfo(dp_idx){
    $.ajax({
        url: `/pay/${pr_idx}/dutch/${dp_idx}/result/chk`,
        type: "GET",
        success: function (data) {
            console.log(data); // dutch-pay dto 인데, result_list 가진 dto 반환
            if(data){
                // dutch-pay result 있으면, result 보여주는 화면
                showDutchPayResultInfoToModal(data);
            } else {
                // result 없으면, 결제 목록 보여주는 화면
                getDutchPayInfo(dp_idx);
            }
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function saveDutchPayResult(){
    var dp_idx = getDp_idx();
    $.ajax({
        url: `/pay/${pr_idx}/dutch/${dp_idx}/result`,
        type: "POST",
        data: JSON.stringify(resultList),
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            console.log(data)
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function changePaidStatus(chk, index) {
    resultList[index].paid = chk.checked;
}


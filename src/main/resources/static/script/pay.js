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

    // 현재까지 저장되어있던 정보 삭제
    memberArr = [];
    $("#room_name").val("")
}

// 방생성 중 memberList에서 member 삭제
function deleteMemberDuringCreatingPayRoom(btn){
    let index = $(btn).attr("data-idx");

    memberArr.splice(index,1); // memberArr 에서 member 삭제
    $(this).parent().remove(); // html에서 해당 member span 태그 삭제
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

//+, - 버튼 바꿔주는 로직
/*function plusMinus(idInfoArr) {
    if ($(idInfoArr[0]).length == 0) {
        $(idInfoArr[1]).text("-");
        $(idInfoArr[2]).tmpl({pr: groupMemberArr}).appendTo(idInfoArr[3]);
    } else {
        $(idInfoArr[4]).text("+");
        $(idInfoArr[5]).remove();
    }
}*/

/* END : 기타 기능 */


// 더치페이 생성 폼 열기
function openDutchPayForm() {
    $(".modal").addClass("show");

    // 더치페이 생성 폼 > 더치페이 생성일 오늘로 지정
    var today = new Date().toISOString().substring(0, 10);
    $("#pay-date").val(today);
}

// // 더치페이 폼 닫기
// function closeDutchPayForm() {
//
//     // TODO: 저장시, 유효성 검사 조건 및 저장 조건 확정하여 변경!
//     // 저장된 결제 목록이 있으면 저장! (이름 없으면 임의 생성)
//     if( payArr.length > 0 && !isRetrievedDutchInfo()){
//
//         // 더치페이 이름 정보 없을 시, 임의로 이름 생성(현재 날짜 & 시간 기준)
//         if($("#pay_name").val().trim().length <= 0){
//             $("#pay_name").val(createArbitraryName());
//         }
//
//         // 더치페이 폼에서 정보 가져와서 데이터 저장
//         saveNewDutchPayInfo();
//
//     } else if (isRetrievedDutchInfo()) { // TODO: retrieve한 내용 수정 한 이후, 닫기
//
//     }
//
//     // modal 안보이도록 css 변경
//     $(".modal").removeClass("show");
//
//     // 현재까지 저장되어있던 정보 삭제
//     clearDutchPayForm();
// }


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
    $("#btn-pay-plus").text("+");

    $("#payList2").html("");
    savedPayArr = [];
    updatedPayArr = new Set();
    deletedPayArr = [];
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

// 결제 목록(payList)를 제외한 더치페이 정보 수정 -- 삭제됨
// function saveUpdateDutchPay(){
//     var dutchpayObj = getDutchPayInfoFromForm();
//     console.log(dutchpayObj);
//     $.ajax({
//         url:`/pay/${pr_idx}/dutch/${dp_idx}`,
//         type:"PUT",
//         data: dutchpayObj,
//         success:function (data){
//             console.log(data);
//             showDutchPayList(pr_idx)
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     });
// }

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
            error:function (x,i,e){
                console.log(e);
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

// // pay 수정 폼 보여주기
// function showUpdatePayForm(btn){
//     var index = Number.parseInt($(btn).attr("data-idx"));
//
//     console.log($(btn).parents("tr").after())
//     if (!isRetrievedDutchInfo()) {
//         var data = payArr[index];
//         data.p_idx = index;
//
//         mapInfoToUpdatePayForm(data, btn);
//     } else {
//         var dp_idx = getDp_idx();
//         var p_idx = index;
//
//         $.ajax({
//             url:`/pay/${pr_idx}/dutch/${dp_idx}/${p_idx}`,
//             type:"GET",
//             success:function (data){
//                 // console.log(data);
//                 mapInfoToUpdatePayForm(parsePayIntoPayObj(data), btn);
//
//             },
//             error:function (x,i,e){
//                 console.log(e);
//             }
//         });
//     }
// }

// 수정할 결제 정보를 수정 폼에 맵핑 후 보여주기
function mapInfoToUpdatePayForm(pay, btn){
    var payObj = {
        "groupMember": groupMemberArr,
        "pay": pay,
        "participants_prgm_idx":makeParticipantsIdxList(pay.payParticipants)
    }
    $("#pay-form-tmpl").template("pay-form"); // 템플릿 소스를 가진 객체를 지정해 템플릿 이름을 부여
    $.tmpl("pay-form", payObj).insertAfter($(btn).parents("tr"));
    $(btn).parents("tr").remove();
}

// function saveUpdatedPay(p_idx) {
//     console.log(p_idx);
//     var payObj = getPayInfoFromForm();
//
//     if (!isRetrievedDutchInfo()) {
//         payArr[p_idx] = payObj;
//         $("#pay-form").remove();
//
//         $("#payList").html($("#pay-list-tmpl").tmpl({pSave:payArr}));
//         $("#allPrice").val(comma(calculateTotalPay(payArr)));
//     } else {
//         var dp_idx = getDp_idx();
//         var mappedObj = {};
//         mappingPay(mappedObj, payObj);
//
//         $.ajax({
//             url:`/pay/${pr_idx}/dutch/${dp_idx}/${p_idx}`,
//             type:"PUT",
//             data: mappedObj,
//             success:function (data){
//                 console.log(data);
//                 showDutchPayInfo(dp_idx);
//             },
//             error:function (x,i,e){
//                 console.log(e);
//             }
//         })
//     }
// }

function parsePayIntoPayObj(dtoObj) {
    console.log(dtoObj);
    var payMember = Member(dtoObj.payMember.prgm_idx, dtoObj.payMember.payMember_name);

    dtoObj.participants.forEach( p => Member(p.prgm_idx, p.payMember_name) );
    var participants = dtoObj.participants;
    return PayObjForPayArr(dtoObj.p_name, dtoObj.price, payMember, participants, dtoObj.p_idx);
}

function PayObjForPayArr(payName = null, payPrice = null, payPayer = null, payParticipants = null, p_idx = null) {
    var payObj = {
        "payName": payName,
        "payPrice": payPrice,
        "payPayer": payPayer,
        "payParticipants": payParticipants,
        "p_idx": p_idx ? Number.parseInt(p_idx) : null
    }
    return payObj;
}

function Member(prgm_idx = null, payMember_name = null) {
    var member = {
        "prgm_idx": prgm_idx ? Number.parseInt(prgm_idx) : null,
        "payMember_name": payMember_name
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
        $("#btn-pay-plus").text("-");
        $("#pay-form-tmpl").tmpl({groupMember:groupMemberArr, pay: null, participants_prgm_idx:null}).appendTo("#payList");
        $("#is-pay-form-opened").val("true");
        btnDisabled(btn);
    }else{
        $("#btn-pay-plus").text("+");
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
        payMember_name = $(".form-pay-selector:selected").text().trim()
    );
    var payParticipants = [];
    $(".form-pay-participants-check:checked")
        .each(
            (idx, chked) =>
                payParticipants.push(Member(
                    prgm_idx = Number.parseInt(chked.value),
                    payMember_name = $(chked).attr("data-prgm-name")
                ))
        );
    return PayObjForPayArr(payName, payPrice, payPayer, payParticipants);
}

// 결제내역 폼에 작성된 정보 저장해주는 함수
function saveNewPay() {
    // 폼에서 정보가져오기
    var payObj = getPayInfoFromForm();
    // payObj["p_idx"] = -(payArr.length+1);
    // console.log(payObj);

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

    payArr.push(payObj);
    console.log(payArr);
    if (!isRetrievedDutchInfo()) {

        $("#payList").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
        $("#allPrice").val(comma(calculateTotalPay(payArr)));

        return false;
    } else {
        // var dp_idx = getDp_idx();
        // var mappedObj = {};
        // mappingPay(mappedObj, payObj); // ajax로 데이터 보낼 수 있도록 맵핑하기
        // console.log(mappedObj);
        //
        // $.ajax({
        //     url: `/pay/${pr_idx}/dutch/${dp_idx}`,
        //     type: "POST",
        //     data: mappedObj,
        //     success: function (data) {
        //         console.log(data);
        //         showDutchPayInfo(dp_idx);
        //     },
        //     error: function (x, i, e) {
        //         console.log(e);
        //     }
        // });
        $("#payList2").html($("#pay-list-tmpl").tmpl({pSave: payArr}));

        var preTotal = uncomma($("#allPrice").val()) * 1;
        $("#allPrice").val(comma(preTotal + calculateTotalPay(payArr)));
    }
}

// //저장된 결제 삭제하는 함수
// function deleteSavePay(tr) {
//     let index = Number.parseInt($(tr).attr("data-idx"));
//     console.log($(tr).parent().parent().parent().attr("id"));
//
//     if (!isRetrievedDutchInfo()) {
//         payArr.splice(index, 1);
//         // console.log(payArr);
//         $("#payList").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
//         $("#allPrice").val(comma(calculateTotalPay(payArr)));
//     } else {
//         // var dp_idx = getDp_idx();
//         // var p_idx = index;
//         //
//         // $.ajax({
//         //     url: `/pay/${pr_idx}/dutch/${dp_idx}/${p_idx}`,
//         //     type: "DELETE",
//         //     success: function (data) {
//         //         console.log(data);
//         //         showDutchPayInfo(dp_idx);
//         //     },
//         //     error: function (x, i, e) {
//         //         console.log(e);
//         //     }
//         // });
//         console.log(index);
//         if(index < 0) {
//             for (let i = 0; i < payArr.length; i++) {
//                 var pay = payArr[i];
//                 if(pay.p_idx == Math.index){
//
//                 }
//             }
//             var payObj = payArr[Math.abs(index)-1];
//             payArr.splice(Math.abs(index)-1, 1);
//             console.log(payArr);
//
//             $("#payList2").html($("#pay-list-tmpl").tmpl({pSave: payArr}));
//             var total = uncomma($("#allPrice").val()) * 1;
//             var price = uncomma(payObj.payPrice) * 1;
//             $("#allPrice").val(comma(total - price));
//         } else {
//             var p_idx = index;
//             deletedPayArr.push(p_idx);
//             console.log($(tr).parent().parent());
//             $(tr).parent().parent().remove();
//         }
//     }
// }


    /* START : PAYGROUPMEMBER 계좌정보 UPDATE */

/*//새로운 계좌 템플릿 테이블에 넣는 함수
    function createNewAccount() {
        if ($("#btn-updated-account").length != 0) {
          
        } else {

            var idInfoArr = ["#btn-update-account", "#btn-account-plus", "#new-account-tmpl",
                "#accountList", "#btn-account-plus", "#new-account-form:last"]
            plusMinus(idInfoArr);
        }
    }*/

//적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
/*
    function saveNewAccount(btn) {
        var savebank = $("#new-account-bank").val();
        var saveNumber = $("#new-account-number").val();
        if (savebank.length > 0 && saveNumber.length > 0) {
            $("#btn-account-plus").text("+");

            var gm_idx = $("#new-account-owner").val();

            groupMemberArrRefresh(btn, gm_idx, savebank, saveNumber);

            // $("#accountList").html($("#save-account-tmpl").tmpl({pSave: groupMemberArr}));
            $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr, accountIdx: 1}));

            putAccountAjax(gm_idx, savebank, saveNumber);
        }
    }
*/


//새로운 계좌 템플릿 테이블에 넣는 함수
function createNewAccount(btn) {
        if(groupMemberArr.filter(gm => gm.payMember_account == null).length == 0){
            alert("추가할 인원이 없습니다.");
        }else{
            var tmplInfo = {pr: groupMemberArr,accountInfo:null, accountIdx:0};
            var idInfoArr = ["#btn-update-account", "#btn-account-plus", "#account-form-tmpl", tmplInfo,
                "#accountList", "#btn-account-plus", "#accountList:last"]
            plusMinus(idInfoArr,btn);
        }
}

//적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
function saveNewAccount(btn) {
    btnAbled();
    var savebank = $("#new-account-bank").val();
    var saveNumber = $("#new-account-number").val();
    if (savebank.length > 0 && saveNumber.length > 0) {
        $("#btn-account-plus").text("+");

        var gm_idx = $("#new-account-owner").val();

        groupMemberArrRefresh(btn, gm_idx, savebank, saveNumber);
        putAccountAjax(gm_idx, savebank, saveNumber);

        // $("#accountList").html($("#save-account-tmpl").tmpl({pSave: groupMemberArr}));
        $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr, accountIdx: 1}));

    }
}

function plusMinus(idInfoArr,btn) {
    if ($(idInfoArr[0]).length == 0) {
        btnDisabled(btn);
        $(idInfoArr[1]).text("-");
        $(idInfoArr[2]).tmpl(idInfoArr[3]).appendTo(idInfoArr[4]);
    } else {
        btnAbled();
        $(idInfoArr[5]).text("+");
        $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr, accountIdx: 1}));
    }
}

//저장된 계좌 삭제하는 함수
    function deleteSaveAccount(btn) {

        var plusValid = $("#btn-account-plus").text();
        if (plusValid == "-" || $("#btn-updated-account").length != 0) {
        } else {

        let prgm_idx = $(btn).parent().find("#prgm_idx").val();
        console.log(prgm_idx);

        groupMemberArrRefresh(btn, prgm_idx);

        $.ajax({
            url: "/pay/accountNull",
            type: "PUT",
            data: {
                "prgm_idx": prgm_idx
            },
            success: function (data) {
                console.log(data);
            },
            error: function (x, i, e) {
                console.log(e);
            }
        })
        }

    }

    function updateSaveAccount(btn) {
        btnDisabled(btn);
        var plusValid = $("#btn-account-plus").text();

            var prgm_idx = btn.parents("tr").find("#prgm_idx").val();
            console.log(prgm_idx);

            $.ajax({
                url: `/pay/accountInfo/${prgm_idx}`,
                type: "GET",
                success: function (data) {
                    console.log(data);
                    var accountInfo = JSON.parse(data);
                    console.log(accountInfo);
                    btn.parents("tr").html($("#account-form-tmpl").tmpl({accountInfo:accountInfo,accountIdx:0}));
                },
                error: function (x, i, e) {
                    console.log(e);
                }
            })
    }

    function updateSavedAccount(btn) {
        btnAbled();
        var savebank = $("#new-account-bank").val();
        var saveNumber = $("#new-account-number").val();
        var gm_idx = $("#saved-account-prgm_idx").val();

        if (savebank.length > 0 && saveNumber.length > 0) {

            console.log(groupMemberArr);
            groupMemberArrRefresh(btn, gm_idx, savebank, saveNumber);
            console.log(groupMemberArr);
            $("#accountList").html($("#account-form-tmpl").tmpl({pSave: groupMemberArr,accountIdx:1}));

            putAccountAjax(gm_idx, savebank, saveNumber);

        }
    }

    //계좌번호 수정 및 삭제시 groupMemberArr를 수정하는 함수
    function groupMemberArrRefresh(btn, gm_idx, savebank=null, saveNumber=null) {
        var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
        findMember[0].payMember_bank = savebank;
        findMember[0].payMember_account = saveNumber;
        btn.parents("tr").remove();
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
            url: "/pay/" + pr_idx + "/dutch/list",
            type: "GET",
            dataType: "json",
            traditional: "true",
            success: function (data) {
                console.log(data);
                $("#dutchList").html($("#show-dutch-list-tmpl").tmpl({dList: data}));
            },
            error: function (x, i, e) {
                console.log(e);
            }
        })
    }

    function showDutchPayInfo(dp_idx) {
        $.ajax({
            url: `/pay/${pr_idx}/dutch/${dp_idx}`,
            type: "GET",
            dataType: "json",
            traditional: "true",
            success: function (data) {
                console.log(data);

                var payList = [];
                data.payList.forEach(p => payList.push(parsePayIntoPayObj(p)));
                payList.forEach(p => p.payPrice = comma(p.payPrice));
                console.log(payList)
                savedPayArr = payList;

                $(".modal").addClass("show");
                $("#payList").html($("#pay-list-tmpl").tmpl({pSave: payList}));
                $("#pay_name").val(data.dutchPayName);
                $("#allPrice").val(comma(data.totalPay));
                $("#cutPrice").val(data.option);
                $("#pay-date").val(data.createDate);
                $("#due-date").val(data.dueDate);
                $("#bill").val("");
                $("#retrieve-pay-id").val(data.dp_idx);
            },
            error: function (x, i, e) {
                console.log(e);
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

            findPay[0].payName = payObj.payName;
            findPay[0].payPrice = payObj.payPrice;
            findPay[0].payPayer = payObj.payPayer;
            findPay[0].payParticipants = payObj.payParticipants;

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

    // 현재까지 저장되어있던 정보 삭제
    clearDutchPayForm();
}

function saveDutchPayForm() {
    var dp_idx = getDp_idx();
    // TODO: 저장시, 유효성 검사 조건 및 저장 조건 확정하여 변경!
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
        saveNewDutchPayInfo();

    } else { // TODO: retrieve한 내용 수정 한 이후, 닫기
        var dp_idx = getDp_idx();
        var pArr = [];
        payArr.forEach(p => pArr.push(parsePayForAjax(p, dp_idx)));

        var uArr = [];
        savedPayArr.forEach(p => {
            if (updatedPayArr.has(p.p_idx)){
                uArr.push(parsePayForAjax(p, dp_idx))
            }
        });

        // console.log("insert", pArr, "updated", uArr, "delete", deletedPayArr);

        $.ajax({
            url:`/pay/RetrieveInfo`,
            type:"POST",
            data: {
                iArr:JSON.stringify(pArr),
                uArr:JSON.stringify(uArr),
                dArr:JSON.stringify(deletedPayArr),
                dutchInfo:JSON.stringify(getDutchPayInfoFromForm())
            },
            success:function (data){
                console.log(data);
                // 현재까지 저장되어있던 정보 삭제 및 정보 reload
                clearDutchPayForm();
                showDutchPayInfo(dp_idx);
            },
            error:function (x,i,e){
                console.log(e);
            }
        });
    }
}

function parsePayForAjax(payObj, dp_idx){
    var pay = {
        p_name: payObj.payName,
        price: uncomma(payObj.payPrice) * 1,
        dp_idx: dp_idx,
        payMember: parsePayMemberForAjax(payObj.payPayer)
    }

    if (payObj.p_idx) {
        pay.p_idx = payObj.p_idx;
    }

    var participants = [];
    payObj.payParticipants.forEach(m => participants.push(parsePayMemberForAjax(m)));

    if (participants) {
        pay.participants = participants;
    }
    return pay;
}

function parsePayMemberForAjax(m){
    var member = {
        pr_idx: pr_idx,
        prgm_idx: m.prgm_idx,
        payMember_name: m.payMember_name
    }
    return member;
}

function btnDisabled(btn){
    for (var i = 0; i < $("button").length; i++) {
        console.log($($("button")[i]).prop("disabled",true));
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
        console.log($($("button")[i]).prop("disabled",false));
    }
}
//5.18

//ajaxfind - ajax 함수 모음

function postMemberCheckAjax(member){
    $.ajax({
        url:"/pay/membercheck",
        type:"POST",
        data:{"payMember_name":member,
            "pr_idx":pr_idx},
        success:function (data){
            // console.log(data); PayGroupMemberDTO를 가져옴
            groupMemberArr.push(JSON.parse(data));
            $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr}));
            console.log(groupMemberArr);
        },
        error:function (x,i,e){
            console.log(e);
        }
    })
}

function getMemberCheckAjax(prgm_idx,self){
    $.ajax({
        url: "/pay/membercheck",
        type: "GET",
        data: {
            "pr_idx": pr_idx,
            "prgm_idx": prgm_idx
        },
        success:function (data){
            console.log(data);
            if(data){
                alert("결제참여자인지 확인해주십시오.");
            }else{
                console.log("memberList에서 member 삭제")
                let index = self.attr("data-idx");
                groupMemberArr.splice(index,1); // groupMemberArr 에서 member 삭제
                self.parent().remove(); // html에서 해당 member span 태그 삭제
                $("#accountList").html($("#save-account-tmpl").tmpl({pSave: groupMemberArr})); // 계좌정보에 삭제된 멤버 없애기
                console.log("[END] index:", index, ", groupMemberArr:", groupMemberArr);
                $.ajax({
                    url: "/pay/membercheck",
                    type: "DELETE",
                    data: {"prgm_idx": prgm_idx},
                    success:function (data){
                        console.log("성공한 ajax"+data);
                    },
                    error(x,i,e){
                        console.log(e);
                    }
                })
            }

        },
        error:function (x,i,e){
            console.log(e);
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
            console.log(data);

            location.href = "/pay/"+data;
        },
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

function putAccountAjax(prgm_idx, payMember_bank, payMember_account) {
    $.ajax({
        url: "/pay/accountInfo",
        type: "PUT",
        data: {
            "payMember_account": payMember_account,
            "payMember_bank": payMember_bank,
            "prgm_idx": prgm_idx
        },
        success: function (data) {
            console.log(data);
        },
        error: function (x, i, e) {
            console.log(e);
        }
    })
}
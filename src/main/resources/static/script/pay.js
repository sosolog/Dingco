// 2. memberList에 member 추가
function memberList(){
    var member = $("#groupMember").val().trim();
    if (member.length > 0){
        memberArr.push(member);
        console.log($("#member-list-tmpl").tmpl({mList:memberArr}));
        $("#memberList").html($("#member-list-tmpl").tmpl({mList:memberArr}));
        console.log(memberArr);
    }
    $("#groupMember").val("");
}
// 방생성 이후 멤버 추가
function memberListOne(){
    var member = $("#groupMember").val().trim();
    if (member.length > 0){
        $.ajax({
            url:"/pay/membercheck",
            type:"POST",
            data:{"payMember_name":member,
            "pr_idx":pr_idx},
            success:function (data){
                console.log(data);
                groupMemberArr.push(JSON.parse(data));
                $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr}));
                console.log(groupMemberArr);
            },
            error:function (x,i,e){
                console.log(e);
            }
        })
    }
    $("#groupMember").val("");
}

//ajax room_name,groupMember Controller에 넘기기
function payRoomInfo(){
    var roomName = $("#room_name").val();
    if(roomName.length<2){
        $("#result_payRoom").text("방제목은 최소 2자 이상이어야 합니다.");
    }else if(memberArr.length==0){
        $("#result_payRoom").text("참여자는 최소 1명 이상이어야 합니다.");
    }else{
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
        });
    }
}

// 새로운 더치페이 폼 생성
function createNewDutch(){
    let today = new Date();
    let year = today.getFullYear().toString().slice(2);
    let month = String(today.getMonth() + 1).padStart(2, "0");
    let day = String(today.getDay()).padStart(2, "0");
    let formattedDate = [year, month, day].join("/");
    console.log(formattedDate)
    // $("#new-dutch-tmpl").tmpl({today:formattedDate}).appendTo("#dutchList");
}

<!--숫자 천자리 콤마 찍어주는 함수-->
function inputNumberFormat(obj) {

    // 콤마없는 정수가 필요한 경우
    // console.log(uncomma($(obj).val()));

    obj.value = comma(uncomma(obj.value));
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}
<!--숫자 천자리 콤마 찍어주는 함수-->


//새로운 결제 템플릿 테이블에 넣는 함수
function createNewPay(){
    console.log($("#btn-update-pay").length, $("#btn-update-pay"))
    if($("#btn-update-pay").length==0){
        // console.log($("#new-pay-tmpl").tmpl());
        $("#btn-pay-plus").text("-");
        $("#new-pay-tmpl").tmpl({pr:groupMemberArr}).appendTo("#payList");
    }else{
        $("#btn-pay-plus").text("+");
        $("#new-pay-form:last").remove();
    }
    return false;
}

//새로운 계좌 템플릿 테이블에 넣는 함수
function createNewAccount(){
    if($("#btn-update-account").length==0){
        // console.log($("#new-pay-tmpl").tmpl());
        $("#btn-account-plus").text("-");
        $("#new-account-tmpl").tmpl({pr:groupMemberArr}).appendTo("#accountList");
    }else{
        $("#btn-account-plus").text("+");
        $("#new-account-form:last").remove();
    }
    return false;
}

//적은 결제 텍스트로 저장해주는 함수
function saveNewPay(){
    var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);

    $("#btn-pay-plus").text("+");
    var savePayName = $("#new-pay-name").val();
    var savePayPrice = $("#new-pay-price").val();
    var savePayPayer = {"prgm_idx":$(".new-pay-selector:selected").val(), "payMember_name":$(".new-pay-selector:selected").text()};
    var savePayParticipants = [];
    $(".new-pay-participants-check:checked").each((idx, chked) => savePayParticipants.push({"prgm_idx": chked.value, "payMember_name":$(chked).attr("data-prgm-name")}));
    // console.log(savePayParticipants)

    if ( !isRetrieveInfo && savePayName.length > 0 && savePayPrice.length > 0 ){
        payArr.push({"sn":savePayName,"spp":savePayPrice,"spp2":savePayPayer,"spp3":savePayParticipants});
        console.log(payArr);
        $("#new-pay-form").remove();
        $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));

        var allprice = uncomma($("#allPrice").val())*1;
        var addprice = uncomma(savePayPrice)*1;
        $("#allPrice").val(comma(allprice+addprice));

        return false;
    } else if (isRetrieveInfo){
        var dp_idx = $("#retrieve-pay-id").val();
        var payObj = {
            "p_name":savePayName,
            "price":uncomma(savePayPrice)*1
        }
        payObj['payMember.prgm_idx'] = savePayPayer.prgm_idx;
        payObj['payMember.payMember_name'] = savePayPayer.payMember_name;
        savePayParticipants.forEach((v, index) => {
            payObj['participants['+index+'].prgm_idx'] = v.prgm_idx;
            payObj['participants['+index+'].payMember_name'] = v.payMember_name;
        })
        $.ajax({
            url:`/pay/${pr_idx}/dutch/${dp_idx}`,
            type:"POST",
            data: payObj,
            success:function (data){
                console.log(data);
                showDutchPayInfo(dp_idx);
            },
            error:function (x,i,e){
                console.log(e);
            }
        })
    }



}

//적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
function saveNewAccount(){
    var savebank = $("#new-account-bank").val();
    var saveNumber = $("#new-account-number").val();
    if (savebank.length > 0 && saveNumber.length > 0){
        $("#btn-account-plus").text("+");

        var saveOwner = $(".new-account-selector:selected").text();
        var gm_idx = $("#new-account-owner").val();
        accountArr.push({"prgm":gm_idx,"sb":savebank,"sn":saveNumber,"so":saveOwner});
        var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
        findMember[0].payMember_bank = savebank;
        findMember[0].payMember_account = saveNumber;
        console.log(findMember, ">>", groupMemberArr);

        // console.log(accountArr);
        $("#new-account-form").remove();
        $("#accountList").html($("#save-account-tmpl").tmpl({pSave:groupMemberArr}));

        $.ajax({
            url:"/pay/accountInfo",
            type:"PUT",
            data:{
                "payMember_account":saveNumber,
                "payMember_bank":savebank,
                "prgm_idx":gm_idx
            },
            success:function (data){
                console.log(data);
            },
            error:function (x,i,e){
                console.log(e);
            }
        })
    }

    return false;
}

//저장된 결제 삭제하는 함수
function deleteSavePay(tr){
    var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);
    if (!isRetrieveInfo){
        var allprice = uncomma($("#allPrice").val())*1;
        var delprice = uncomma($(tr).parent().parent().find("#save-price").text())*1;
        $("#allPrice").val(comma(allprice-delprice));

        let index = $(tr).attr("data-idx");
        payArr.splice(index,1);
        // console.log(payArr);
        $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));
    } else {

    }
}

//저장된 계좌 삭제하는 함수
function deleteSaveAccount(tr){

    // let index = $(tr).attr("data-idx");
    // accountArr.splice(index,1);
    // console.log(accountArr);


    let prgm_idx = $(tr).parent().find("#prgm_idx").val();
    console.log(prgm_idx);
    var findMember = groupMemberArr.filter(gm => gm.prgm_idx == prgm_idx);
    findMember[0].payMember_bank = null;
    findMember[0].payMember_account = null;
    $(tr).parent().parent().remove();

    $.ajax({
        url:"/pay/accountNull",
        type:"PUT",
        data:{
            "prgm_idx":prgm_idx
        },
        success:function (data){
            console.log(data);
        },
        error:function (x,i,e){
            console.log(e);
        }
    })

}

//저장된 결제정보들 데이터 보내주는 함수
function dataAjax(f){

    var m_idx = payRoom.m_idx;
    var pay_name = $("#pay-name").val();
    var allPrice = $("#allPrice").val();
    var cutPrice = $("#cutPrice").val();
    var pay_date = $("#pay-date").val();
    var due_date = $("#due-date").val();
    var bill = $("#bill").val();


    $.ajax({
        url:"/pay/payInfo",
        type:"POST",
        dataType:"json",
        traditional:"true",
        data:{
            "payArr":JSON.stringify(payArr),
            "pr_idx":pr_idx,
            "m_idx":m_idx,
            "pay_name":pay_name,
            "allPrice":allPrice,
            "cutPrice":cutPrice,
            "pay_date":pay_date,
            "due_date":due_date,
            "bill":bill
        },
        success:function (data){
            console.log(data);
        },
        error:function (x,i,e){
            console.log(e);
        }
    })

}

function changeParticipants() {
    $("#new-pay-participants-form").css("display", "block");
    $("#new-pay-participants").css("display", "none");
}

function changeParticipantsNumber() {
    // console.log($(".new-pay-participants-check:checked").length);
    var participants_num = $(".new-pay-participants-check:checked").length;
    if (participants_num > 0) {
        var number_display = $("#new-pay-participants").children()[0];
        $(number_display).html(participants_num);

        $("#new-pay-participants-form").css("display", "none");
        $("#new-pay-participants").css("display", "block");
    } else {
        alert("참여인원은 1명 이상이어야 합니다.");
    }

}

function showDutchPayList(pr_idx) {
    $.ajax({
        url:"/pay/"+pr_idx+"/dutch/list",
        type:"GET",
        dataType:"json",
        traditional:"true",
        success:function (data){
            console.log(data);
            $("#dutchList").html($("#show-dutch-list-tmpl").tmpl({dList:data}));
        },
        error:function (x,i,e){
            console.log(e);
        }
    })
}

function showDutchPayInfo(dp_idx) {
    $.ajax({
        url:`/pay/${pr_idx}/dutch/${dp_idx}`,
        type:"GET",
        dataType:"json",
        traditional:"true",
        success:function (data){
            console.log(data);
            $(".modal").addClass("show");
            $("#payList").html($("#retrieve-pay-tmpl").tmpl({payList:data.payList}));
            $("#pay_name").val(data.dutchPayName);
            $("#allPrice").val(comma(data.totalPay));
            $("#cutPrice").val(data.option);
            $("#pay-date").val(data.createDate);
            $("#due-date").val(data.dueDate);
            $("#bill").val("");
            $("#retrieve-pay-id").val(data.dp_idx);
        },
        error:function (x,i,e){
            console.log(e);
        }
    })
}

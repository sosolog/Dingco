/* START : PAYROOM, PAYGROUPMEMBER 정보 입력
    방이름 - PayRoom.room_name
    참여자 - PayRoom.pr_idx -> PayGroupMember.pr_idx , PayGroupMember.prgm_idx
*/

// 방생성 중 memberList에 member 추가
function memberList() {
    var member = $("#groupMember").val().trim();
    if (member.length > 0) {
        memberArr.push(member);

        // console.log($("#member-list-tmpl").tmpl({mList:memberArr}));

        $("#memberList").html($("#member-list-tmpl").tmpl({mList: memberArr}));

        // console.log(memberArr);

    }
    $("#groupMember").val(""); //추가 후 빈칸으로 만들기
}

// 방생성 이후 멤버 추가 : ajax를 이용한 insert
function memberListOne(){
    var member = $("#groupMember").val().trim();
    if (member.length > 0){
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
    $("#groupMember").val("");
}

// 방생성 이후 삭제할 멤버가 결제목록에서 결제자이거나 결제 참여자인지 확인하고 없으면 삭제, 있으면 alert
function memberCheck(self){
    var prgm_idx = self.parent().attr("data-idx");
    console.log(pr_idx,prgm_idx);
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

//ajax room_name,groupMember Controller에 넘기기(방제목, 참여자 유효성 검사)
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

//적은 결제 텍스트로 저장해주는 함수
function saveNewPay() {
    var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);

    $("#btn-pay-plus").text("+");
    var savePayName = $("#new-pay-name").val();
    var savePayPrice = $("#new-pay-price").val();
    var savePayPayer = {
        "prgm_idx": $(".new-pay-selector:selected").val(),
        "payMember_name": $(".new-pay-selector:selected").text()
    };
    var savePayParticipants = [];
    $(".new-pay-participants-check:checked").each((idx, chked) => savePayParticipants.push({
        "prgm_idx": chked.value,
        "payMember_name": $(chked).attr("data-prgm-name")
    }));
    // console.log(savePayParticipants)

    if (!isRetrieveInfo && savePayName.length > 0 && savePayPrice.length > 0) {
        payArr.push({
            "payName": savePayName,
            "payPrice": savePayPrice,
            "payPayer": savePayPayer,
            "payParticipants": savePayParticipants
        });
        console.log(payArr);
        $("#new-pay-form").remove();
        $("#payList").html($("#save-pay-tmpl").tmpl({pSave: payArr}));

        var allprice = uncomma($("#allPrice").val()) * 1;
        var addprice = uncomma(savePayPrice) * 1;
        $("#allPrice").val(comma(allprice + addprice));

        return false;
    } else if (isRetrieveInfo) {
        var dp_idx = $("#retrieve-pay-id").val();
        var payObj = {
            "p_name": savePayName,
            "price": uncomma(savePayPrice) * 1
        }
        payObj['payMember.prgm_idx'] = savePayPayer.prgm_idx;
        payObj['payMember.payMember_name'] = savePayPayer.payMember_name;
        savePayParticipants.forEach((v, index) => {
            payObj['participants[' + index + '].prgm_idx'] = v.prgm_idx;
            payObj['participants[' + index + '].payMember_name'] = v.payMember_name;
        })
        $.ajax({
            url: `/pay/${pr_idx}/dutch/${dp_idx}`,
            type: "POST",
            data: payObj,
            success: function (data) {
                console.log(data);
                showDutchPayInfo(dp_idx);
            },
            error: function (x, i, e) {
                console.log(e);
            }
        })
    }
}

//저장된 결제 삭제하는 함수
    function deleteSavePay(tr) {
        var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);

        if (!isRetrieveInfo) {
            var allprice = uncomma($("#allPrice").val()) * 1;
            var delprice = uncomma($(tr).parent().parent().find("#save-price").text()) * 1;
            $("#allPrice").val(comma(allprice - delprice));

            let index = $(tr).attr("data-idx");
            payArr.splice(index, 1);
            // console.log(payArr);
            $("#payList").html($("#save-pay-tmpl").tmpl({pSave: payArr}));
        } else {
            var dp_idx = $("#retrieve-pay-id").val();
            var p_idx = $(tr).attr("data-idx");
            $.ajax({
                url: `/pay/${pr_idx}/dutch/${dp_idx}/${p_idx}`,
                type: "DELETE",
                success: function (data) {
                    console.log(data);
                    showDutchPayInfo(dp_idx);
                },
                error: function (x, i, e) {
                    console.log(e);
                }
            })
        }
    }

    /* START : PAYGROUPMEMBER 계좌정보 UPDATE */

//새로운 계좌 템플릿 테이블에 넣는 함수
    function createNewAccount() {
        if ($("#btn-update-account").length == 0) {
            // console.log($("#new-pay-tmpl").tmpl());
            $("#btn-account-plus").text("-");
            $("#new-account-tmpl").tmpl({pr: groupMemberArr}).appendTo("#accountList");
        } else {
            $("#btn-account-plus").text("+");
            $("#new-account-form:last").remove();
        }
        return false;
    }

//적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
    function saveNewAccount() {
        var savebank = $("#new-account-bank").val();
        var saveNumber = $("#new-account-number").val();
        if (savebank.length > 0 && saveNumber.length > 0) {
            $("#btn-account-plus").text("+");

            var gm_idx = $("#new-account-owner").val();
            var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
            findMember[0].payMember_bank = savebank;
            findMember[0].payMember_account = saveNumber;
            console.log(findMember, ">>", groupMemberArr);

            $("#new-account-form").remove();
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave: groupMemberArr}));

            putAccountAjax(gm_idx, savebank, saveNumber);
        }
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

//저장된 계좌 삭제하는 함수
    function deleteSaveAccount(tr) {

        let prgm_idx = $(tr).parent().find("#prgm_idx").val();
        console.log(prgm_idx);
        var findMember = groupMemberArr.filter(gm => gm.prgm_idx == prgm_idx);
        findMember[0].payMember_bank = null;
        findMember[0].payMember_account = null;
        $(tr).parent().parent().remove();

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

    function updateSaveAccount(btn) {

        var prgm_idx = btn.parents("tr").find("#prgm_idx").val();
        console.log(prgm_idx);

        $.ajax({
            url: `/pay/accountInfo/${prgm_idx}`,
            type: "GET",
            success: function (data) {
                console.log(data);
                var accountInfo = JSON.parse(data);
                console.log(accountInfo);
                btn.parents("tr").html($("#update-account-tmpl").tmpl(accountInfo));
            },
            error: function (x, i, e) {
                console.log(e);
            }
        })

    }

    function updateSavedAccount(btn) {
        var savebank = $("#saved-account-bank").val();
        var saveNumber = $("#saved-account-number").val();

        if (savebank.length > 0 && saveNumber.length > 0) {
            $("#btn-account-plus").text("+");
            var gm_idx = $("#saved-account-prgm_idx").val();
            var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
            findMember[0].payMember_bank = savebank;
            findMember[0].payMember_account = saveNumber;
            console.log(findMember, ">>", groupMemberArr);

            btn.parents("tr").remove();
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave: findMember}));

            putAccountAjax(gm_idx, savebank, saveNumber);

        }
    }

    /* END : PAYGROUPMEMBER 계좌정보 UPDATE */

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
                $(".modal").addClass("show");
                $("#payList").html($("#retrieve-pay-tmpl").tmpl({payList: data.payList}));
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


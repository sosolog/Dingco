// // 2. memberList에 member 추가
// function memberList(){
//     var member = $("#groupMember").val().trim();
//     if (member.length > 0){
//         memberArr.push(member);
//         console.log($("#member-list-tmpl").tmpl({mList:memberArr}));
//         $("#memberList").html($("#member-list-tmpl").tmpl({mList:memberArr}));
//         console.log(memberArr);
//     }
//     $("#groupMember").val("");
// }
// // 방생성 이후 멤버 추가
// function memberListOne(){
//     var member = $("#groupMember").val().trim();
//     if (member.length > 0){
//         $.ajax({
//             url:"/pay/membercheck",
//             type:"POST",
//             data:{"payMember_name":member,
//             "pr_idx":pr_idx},
//             success:function (data){
//                 console.log(data);
//                 groupMemberArr.push(JSON.parse(data));
//                 $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr}));
//                 console.log(groupMemberArr);
//             },
//             error:function (x,i,e){
//                 console.log(e);
//             }
//         })
//     }
//     $("#groupMember").val("");
// }
//
// //ajax room_name,groupMember Controller에 넘기기
// function payRoomInfo(){
//     var roomName = $("#room_name").val();
//     if(roomName.length<2){
//         $("#result_payRoom").text("방제목은 최소 2자 이상이어야 합니다.");
//     }else if(memberArr.length==0){
//         $("#result_payRoom").text("참여자는 최소 1명 이상이어야 합니다.");
//     }else{
//         $.ajax({
//             url:"/pay/room",
//             type:"POST",
//             data:{
//                 "roomName":roomName,
//                 "memberList":memberArr
//             },
//             success:function(data){
//                 //data = pr_idx
//                 console.log(data);
//
//                 location.href = "/pay/"+data;
//             },
//             error:function (xhr,sta,error){
//                 console.log(error);
//             }
//         });
//     }
// }
//
// // 새로운 더치페이 폼 생성
// function createNewDutch(){
//     let today = new Date();
//     let year = today.getFullYear().toString().slice(2);
//     let month = String(today.getMonth() + 1).padStart(2, "0");
//     let day = String(today.getDay()).padStart(2, "0");
//     let formattedDate = [year, month, day].join("/");
//     console.log(formattedDate)
//     // $("#new-dutch-tmpl").tmpl({today:formattedDate}).appendTo("#dutchList");
// }
//
// <!--숫자 천자리 콤마 찍어주는 함수-->
// function inputNumberFormat(obj) {
//
//     // 콤마없는 정수가 필요한 경우
//     // console.log(uncomma($(obj).val()));
//
//     obj.value = comma(uncomma(obj.value));
// }
//
// function comma(str) {
//     str = String(str);
//     return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
// }
//
// function uncomma(str) {
//     str = String(str);
//     return str.replace(/[^\d]+/g, '');
// }
// <!--숫자 천자리 콤마 찍어주는 함수-->
//
//
// //새로운 결제 템플릿 테이블에 넣는 함수
// function createNewPay(){
//     console.log($("#btn-update-pay").length, $("#btn-update-pay"))
//     if($("#btn-update-pay").length==0){
//         // console.log($("#new-pay-tmpl").tmpl());
//         $("#btn-pay-plus").text("-");
//         $("#new-pay-tmpl").tmpl({pr:groupMemberArr}).appendTo("#payList");
//     }else{
//         $("#btn-pay-plus").text("+");
//         $("#new-pay-form:last").remove();
//     }
//     return false;
// }
//
// //새로운 계좌 템플릿 테이블에 넣는 함수
// function createNewAccount(){
//     if($("#btn-update-account").length==0){
//         // console.log($("#new-pay-tmpl").tmpl());
//         $("#btn-account-plus").text("-");
//         $("#new-account-tmpl").tmpl({pr:groupMemberArr}).appendTo("#accountList");
//     }else{
//         $("#btn-account-plus").text("+");
//         $("#new-account-form:last").remove();
//     }
//     return false;
// }
//
// //적은 결제 텍스트로 저장해주는 함수
// function saveNewPay(){
//     var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);
//
//     $("#btn-pay-plus").text("+");
//     var savePayName = $("#new-pay-name").val();
//     var savePayPrice = $("#new-pay-price").val();
//     var savePayPayer = {"prgm_idx":$(".new-pay-selector:selected").val(), "payMember_name":$(".new-pay-selector:selected").text()};
//     var savePayParticipants = [];
//     $(".new-pay-participants-check:checked").each((idx, chked) => savePayParticipants.push({"prgm_idx": chked.value, "payMember_name":$(chked).attr("data-prgm-name")}));
//     // console.log(savePayParticipants)
//
//     if ( !isRetrieveInfo && savePayName.length > 0 && savePayPrice.length > 0 ){
//         payArr.push({"sn":savePayName,"spp":savePayPrice,"spp2":savePayPayer,"spp3":savePayParticipants});
//         console.log(payArr);
//         $("#new-pay-form").remove();
//         $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));
//
//         var allprice = uncomma($("#allPrice").val())*1;
//         var addprice = uncomma(savePayPrice)*1;
//         $("#allPrice").val(comma(allprice+addprice));
//
//         return false;
//     } else if (isRetrieveInfo){
//         var dp_idx = $("#retrieve-pay-id").val();
//         var payObj = {
//             "p_name":savePayName,
//             "price":uncomma(savePayPrice)*1
//         }
//         payObj['payMember.prgm_idx'] = savePayPayer.prgm_idx;
//         payObj['payMember.payMember_name'] = savePayPayer.payMember_name;
//         savePayParticipants.forEach((v, index) => {
//             payObj['participants['+index+'].prgm_idx'] = v.prgm_idx;
//             payObj['participants['+index+'].payMember_name'] = v.payMember_name;
//         })
//         $.ajax({
//             url:`/pay/${pr_idx}/dutch/${dp_idx}`,
//             type:"POST",
//             data: payObj,
//             success:function (data){
//                 console.log(data);
//                 showDutchPayInfo(dp_idx);
//             },
//             error:function (x,i,e){
//                 console.log(e);
//             }
//         })
//     }
//
//
//
// }
//
// //적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
// function saveNewAccount(){
//     var savebank = $("#new-account-bank").val();
//     var saveNumber = $("#new-account-number").val();
//     if (savebank.length > 0 && saveNumber.length > 0){
//         $("#btn-account-plus").text("+");
//
//         var saveOwner = $(".new-account-selector:selected").text();
//         var gm_idx = $("#new-account-owner").val();
//         var findMember = groupMemberArr.filter(gm => gm.prgm_idx == gm_idx);
//         findMember[0].payMember_bank = savebank;
//         findMember[0].payMember_account = saveNumber;
//         console.log(findMember, ">>", groupMemberArr);
//
//         $("#new-account-form").remove();
//         $("#accountList").html($("#save-account-tmpl").tmpl({pSave:groupMemberArr}));
//
//         $.ajax({
//             url:"/pay/accountInfo",
//             type:"PUT",
//             data:{
//                 "payMember_account":saveNumber,
//                 "payMember_bank":savebank,
//                 "prgm_idx":gm_idx
//             },
//             success:function (data){
//                 console.log(data);
//             },
//             error:function (x,i,e){
//                 console.log(e);
//             }
//         })
//     }
//
//     return false;
// }
//
// //저장된 결제 삭제하는 함수
// function deleteSavePay(tr){
//     var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);
//
//     if (!isRetrieveInfo){
//         var allprice = uncomma($("#allPrice").val())*1;
//         var delprice = uncomma($(tr).parent().parent().find("#save-price").text())*1;
//         $("#allPrice").val(comma(allprice-delprice));
//
//         let index = $(tr).attr("data-idx");
//         payArr.splice(index,1);
//         // console.log(payArr);
//         $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));
//     } else {
//         var dp_idx = $("#retrieve-pay-id").val();
//         var p_idx = $(tr).attr("data-idx");
//         $.ajax({
//             url:`/pay/${pr_idx}/dutch/${dp_idx}/${p_idx}`,
//             type:"DELETE",
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
//
// //저장된 계좌 삭제하는 함수
// function deleteSaveAccount(tr){
//
//
//
//     let prgm_idx = $(tr).parent().find("#prgm_idx").val();
//     console.log(prgm_idx);
//     var findMember = groupMemberArr.filter(gm => gm.prgm_idx == prgm_idx);
//     findMember[0].payMember_bank = null;
//     findMember[0].payMember_account = null;
//     $(tr).parent().parent().remove();
//
//     $.ajax({
//         url:"/pay/accountNull",
//         type:"PUT",
//         data:{
//             "prgm_idx":prgm_idx
//         },
//         success:function (data){
//             console.log(data);
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     })
//
// }
//
// /*
// //저장된 결제정보들 데이터 보내주는 함수
// function dataAjax(f){
//
//     var m_idx = payRoom.m_idx;
//     var pay_name = $("#pay-name").val();
//     var allPrice = $("#allPrice").val();
//     var cutPrice = $("#cutPrice").val();
//     var pay_date = $("#pay-date").val();
//     var due_date = $("#due-date").val();
//     var bill = $("#bill").val();
//
//
//     $.ajax({
//         url:"/pay/payInfo",
//         type:"POST",
//         dataType:"json",
//         traditional:"true",
//         data:{
//             "payArr":JSON.stringify(payArr),
//             "pr_idx":pr_idx,
//             "m_idx":m_idx,
//             "pay_name":pay_name,
//             "allPrice":allPrice,
//             "cutPrice":cutPrice,
//             "pay_date":pay_date,
//             "due_date":due_date,
//             "bill":bill
//         },
//         success:function (data){
//             console.log(data);
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     })
//
// }
// */
//
// function changeParticipants() {
//     $("#new-pay-participants-form").css("display", "block");
//     $("#new-pay-participants").css("display", "none");
// }
//
// function changeParticipantsNumber() {
//     // console.log($(".new-pay-participants-check:checked").length);
//     var participants_num = $(".new-pay-participants-check:checked").length;
//     if (participants_num > 0) {
//         var number_display = $("#new-pay-participants").children()[0];
//         $(number_display).html(participants_num);
//
//         $("#new-pay-participants-form").css("display", "none");
//         $("#new-pay-participants").css("display", "block");
//     } else {
//         alert("참여인원은 1명 이상이어야 합니다.");
//     }
//
// }
//
// function showDutchPayList(pr_idx) {
//     $.ajax({
//         url:"/pay/"+pr_idx+"/dutch/list",
//         type:"GET",
//         dataType:"json",
//         traditional:"true",
//         success:function (data){
//             console.log(data);
//             $("#dutchList").html($("#show-dutch-list-tmpl").tmpl({dList:data}));
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     })
// }
//
// function showDutchPayInfo(dp_idx) {
//     $.ajax({
//         url:`/pay/${pr_idx}/dutch/${dp_idx}`,
//         type:"GET",
//         dataType:"json",
//         traditional:"true",
//         success:function (data){
//             console.log(data);
//             $(".modal").addClass("show");
//             $("#payList").html($("#retrieve-pay-tmpl").tmpl({payList:data.payList}));
//             $("#pay_name").val(data.dutchPayName);
//             $("#allPrice").val(comma(data.totalPay));
//             $("#cutPrice").val(data.option);
//             $("#pay-date").val(data.createDate);
//             $("#due-date").val(data.dueDate);
//             $("#bill").val("");
//             $("#retrieve-pay-id").val(data.dp_idx);
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     })
// }
//
// //삭제할 멤버가 결제에 참여하고 있는지 확인하고 없으면 삭제, 있으면 alert
// function memberCheck(self){
//     var prgm_idx = self.parent().attr("data-idx");
//     console.log(pr_idx,prgm_idx);
//     $.ajax({
//         url: "/pay/membercheck",
//         type: "GET",
//         data: {
//             "pr_idx": pr_idx,
//             "prgm_idx": prgm_idx
//         },
//         success:function (data){
//             console.log(data);
//             if(data){
//                 alert("결제참여자인지 확인해주십시오.");
//             }else{
//                 console.log("memberList에서 member 삭제")
//                 let index = self.attr("data-idx");
//                 groupMemberArr.splice(index,1); // groupMemberArr 에서 member 삭제
//                 self.parent().remove(); // html에서 해당 member span 태그 삭제
//                 console.log("[END] index:", index, ", groupMemberArr:", groupMemberArr);
//                 $.ajax({
//                     url: "/pay/membercheck",
//                     type: "DELETE",
//                     data: {"prgm_idx": prgm_idx},
//                     success:function (data){
//                         console.log("성공한 ajax"+data);
//                     },
//                     error(x,i,e){
//                         console.log(e);
//                     }
//                 })
//             }
//
//         },
//         error:function (x,i,e){
//             console.log(e);
//         }
//     })
// }

// pay관련 객체 및 함수

function getObject(cls) {
    var obj = {};
    Object.keys(cls).forEach(k => {
        if(cls[k]) obj[k] = cls[k];
    });
    return obj;
}

function isNumeric(data) {
    return !isNaN(data);
}

class GroupMember {
    id(prgm_idx) {
        this.prgm_idx = prgm_idx;
        return this;
    }

    _id() {
        return this.prgm_idx;
    }

    name(name){
        this.payMember_name = name;
        return this;
    }

    _name(){
        return this.payMember_name;
    }

    account(account) {
        this.payMember_account = account;
        return this;
    }

    _account(){
        return this.payMember_account;
    }

    pay_room(pr_idx) {
        this.pr_idx = pr_idx;
        return this;
    }

    _pay_room(pr_idx) {
        return this.pr_idx;
    }

    get() { return getObject(this) }
}



class Pay {
    id(p_idx) {
        this.p_idx = p_idx;
        return this;
    }

    _id(){
        return this.p_idx;
    }

    name(p_name) {
        this.p_name = p_name;
        return this;
    }

    _name() {
        return this.p_name;
    }

    amount(price){
        if (isNumeric(price)) {
            this.price = Number.parseInt(price);
        } else {
            throw new Error('amount() : Argument must be a type of number or string that can be parsed into number.')
        }
        return this;
    }

    _amount() {
        return this.price;
    }

    payer(payMember) {
        if (payMember instanceof GroupMember) {
            this.payMember = payMember;
        } else {
            throw new Error('payer() : Argument must be an instance of class GroupMember.');
        }
        return this;
    }

    _payer(){
        return this.payMember;
    }

    parties(...participants) {
        this.participants = [];
        this.addParties(...participants);
    }

    _parties() {
        return this.participants;
    }

    addParties(...participants) {
        participants.forEach(p => {
            if (p instanceof GroupMember) {
                this.participants.push(p);
            } else {
                throw new Error('parties() : All arguments must be an instance of class GroupMember.');
            }
        });
        return this;
    }

    findParty(prgm_idx) {
        for (let p in this.participants) {
            if(p._id() == prgm_idx) {
                return p;
            }
        }
        return null;
    }

    removeParty(prgm_idx) {
        let find_index = -1;
        for (let i = 0; i < this.participants.length; i++ ) {
            let p = this.participants[i]
            if(p._id() == prgm_idx) {
                find_index = i;
                break;
            }
        }
        if (find_index > -1)
            return this.participants.splice(find_index, 1);
        return null;
    }
    //TODO : AJAX 맵핑용 메소드
    //TODO : get() : class -> object로 변경
}

class DutchPay {
    id(dp_idx) {
        this.dp_idx = dp_idx;
        return this;
    }

    _id() {
        return this.dp_idx;
    }

    name(dutchPayName) {
        this.dutchPayName = dutchPayName;
        return this;
    }

    _name() {
        return this.dutchPayName;
    }

    pay_room(pr_idx){
        this.pr_idx = pr_idx;
        return this;
    }

    _pay_room(){
        return this.pr_idx;
    }

    cut_option(option) {
        this.option = option;
        return this;
    }

    _cut_option() {
        return this.option;
    }

    create_date(createDate){
        this.createDate = createDate;
        return createDate;
    }

    _create_date(){
        return this.createDate;
    }

    due_date(dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    _due_date() {
        return this.dueDate;
    }

    calc_total() {
        this.totalPay = 0;
        this.payList.forEach(p => {
            if (p._amount()) {
                this.totalPay += p._amount();
            }
        });
    }

    _calc_total() {
        return this.totalPay;
    }

    pay_list(...payList) {
        this.payList = [];
        this.addParties(...payList);
    }

    _pay_list() {
        return this.payList;
    }

    addPays(...payList) {
        payList.forEach(p => {
            if (p instanceof Pay) {
                this.payList.push(p);
            } else {
                throw new Error('parties() : All arguments must be an instance of class Pay.');
            }
        });
        this.calc_total();
        return this;
    }

    findPay(p_idx) {
        for (let p in this.payList) {
            if(p._id() == p_idx) {
                return p;
            }
        }
        return null;
    }

    removePay(p_idx) {
        let find_index = -1;
        for (let i = 0; i < this.payList.length; i++ ) {
            let p = this.payList[i]
            if(p._id() == p_idx) {
                find_index = i;
                break;
            }
        }
        if (find_index > -1) {
            return this.payList.splice(find_index, 1);
        }
        this.calc_total()
        return null;
    }
}
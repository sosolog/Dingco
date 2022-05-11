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
        let payRoom = ${payRoom};
        let pr_idx = payRoom.pr_idx;
        let room_name = payRoom.room_name;
        let groupMemberArr = payRoom.groupMemberList;
        let payArr = [];
        let memberArr = [];
        let accountArr = [];


        for (let x of groupMemberArr) {
            memberArr.push(x.payMember_name);
            if(x.payMember_account != null || x.payMember_bank != null){
                accountArr.push({"prgm":x.prgm_idx,"sb":x.payMember_bank,"sn":x.payMember_account,"so":x.payMember_name});
            }
        }

        $(document).ready(function(){
            showDutchPayList(pr_idx)
            console.log(groupMemberArr)
            $("#room_name").val(room_name);
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave:groupMemberArr}));
            $("#pay-date").val(new Date().toISOString().substring(0, 10));
            $("#memberList").html($("#member-list-tmpl").tmpl({mList:groupMemberArr}));

            $("#btn-close-modal").on("click", function(){
                var isRetrieveInfo = ($("#retrieve-pay-id").val().length > 0);
                if( payArr.length > 0 && !isRetrieveInfo){
                    // 더치페이 내용 저장 (새로 생성한 더치페이)
                    if($("#pay_name").val().trim().length <= 0){
                        let today = new Date();

                        let month = (today.getMonth() + 1).toString().padStart(2, "0");  // 월
                        let date = (today.getDate()).toString().padStart(2, "0");  // 날짜
                        let hours = today.getHours(); // 시
                        let minutes = today.getMinutes();  // 분
                        let seconds = today.getSeconds();  // 초

                        $("#pay_name").val(month + date + "_"+ hours + ':' + minutes + ':' + seconds);
                    }
                    console.log(payArr, $("#pay_name").val(), $("#allPrice").val(), $("#cutPrice").val(), $("#pay-date").val(), $("#due-date").val())
                    var ducthPayObj = {
                        "pr_idx": pr_idx,
                        "dutchPayName": $("#pay_name").val(),
                        "totalPay":$("#allPrice").val().replaceAll(",", ""),
                        "option":$("#cutPrice").val()
                    }
                    var pay_date = $("#pay-date").val().trim();
                    var due_date = $("#due-date").val().trim();
                    if (pay_date.length > 0){
                        ducthPayObj.createDate = pay_date;
                    }
                    if (due_date.length > 0){
                        ducthPayObj.dueDate = due_date;
                    }
                    payArr.forEach((v, index) => {
                        ducthPayObj['payList['+index+'].p_name'] = v.sn;
                        ducthPayObj['payList['+index+'].price'] = v.spp.replaceAll(",", "");
                        ducthPayObj['payList['+index+'].payMember.prgm_idx'] = v.spp2.prgm_idx;
                        ducthPayObj['payList['+index+'].payMember.payMember_name'] = v.spp2.payMember_name;
                        v.spp3.forEach((v2, index2) => {
                            ducthPayObj['payList['+index+'].participants['+index2+'].prgm_idx'] = v2.prgm_idx;
                            ducthPayObj['payList['+index+'].participants['+index2+'].payMember_name'] = v2.payMember_name;
                        })
                    });
                    console.log(ducthPayObj)

                    $.ajax({
                        url:"/pay/new",
                        type:"POST",
                        data: ducthPayObj,
                        success:function (data){
                            console.log(data);
                            showDutchPayList(pr_idx);
                        },
                        error:function (x,i,e){
                            console.log(e);
                        }
                    })
                } else if (isRetrieveInfo) { // retrieve한 내용 수정 한 이후, 닫기

                }

                // modal 안보이도록 css 변경
                $(".modal").removeClass("show");
                // 현재까지 저장되어있던 정보 삭제
                payArr = [];

                $("#pay_name").val("");
                $("#allPrice").val(0);
                $("#cutPrice").val("0");
                $("#pay-date").val("");
                $("#due-date").val("");
                $("#bill").val("");
                $("#retrieve-pay-id").val("");
                $("#payList").html("<tr>"+
                    "<th>결제 목록</th>"+
                "<th>결제 금액</th>"+
                "<th>결제자</th>"+
                "<th>참여인원</th>"+
                "<th></th>"+
                "</tr>");

            });
            $("#btn-open-modal").on("click", function(){
                $(".modal").addClass("show");
            });

            // 방생성 modal open 이후
          /*  // 1. memberList에서 일부 member 삭제
            $(document).on("click", ".btn-member-delete", function(){


                console.log("memberList에서 member 삭제")
                let index = $(this).attr("data-idx");
                groupMemberArr.splice(index,1); // groupMemberArr 에서 member 삭제
                $(this).parent().remove(); // html에서 해당 member span 태그 삭제
                console.log("[END] index:", index, ", groupMemberArr:", groupMemberArr);
            });*/
        });


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
                        accountArr = [];
                        for (let x of groupMemberArr) {
                            if(x.payMember_account != null || x.payMember_bank != null){
                                accountArr.push({"prgm":x.prgm_idx,"sb":x.payMember_bank,"sn":x.payMember_account,"so":x.payMember_name});
                            }
                        }
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
        };


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
    <script type="text/html" id="new-dutch-tmpl">
        <tr style="color: #888888" id="new-dutch-form">
            <td>\${today}</td>
            <td><input type="text" id="new-dutch-name"></td>
            <td>0</td>
            <td><button onclick="createNewDutchCfm()">확인</button></td>
        </tr>
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
                <div id="new-pay-participants-form" style="display: none">
                    {{each(index, p) pr}}
                    <input type="checkbox" class="new-pay-participants-check" value="\${p.prgm_idx}" data-prgm-name="\${p.payMember_name}" checked>\${p.payMember_name}
                    {{/each}}
                    <button type="button" onclick="changeParticipantsNumber()">OK</button>
                </div>
            </td>
            <td><button id="btn-update-pay" type="button" onclick="return saveNewPay()">저장</button></td>
        </tr>
    </script>

    <!--입력한 결제 저장 template-->
    <script type="text/html" id="save-pay-tmpl">
        <tr>
            <th>결제 목록</th>
            <th>결제 금액</th>
            <th>결제자</th>
            <th>참여인원</th>
            <th></th>
        </tr>
        {{each(index, p) pSave}}
        <tr style="color: #888888" class="save-pay-form\${index}">
            <td id="save-name">\${p.sn}</td>
            <td id="save-price">\${p.spp}</td>
            <td id="save-payer">\${p.spp2.payMember_name}</td>
            <td id="save-participants">{{each(index, prgm) p.spp3}}
                \${prgm.payMember_name}
                {{/each}}
            </td>
            <td>
                <button id="btn-delete-pay" class="btn-delete-pay" data-idx="\${index}" onclick="deleteSavePay($(this))">삭제</button>
            </td>
        </tr>
        {{/each}}
    </script>
    <script type="text/html" id="retrieve-pay-tmpl">
        <tr>
            <th>결제 목록</th>
            <th>결제 금액</th>
            <th>결제자</th>
            <th>참여인원</th>
            <th></th>
        </tr>
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
                <button id="btn-delete-pay-ajax" class="btn-delete-pay" data-idx="\${p.p_idx}" onclick="deleteSavePay($(this))">삭제</button>
            </td>
        </tr>
        {{/each}}
    </script>

    <script type="text/html" id="show-dutch-list-tmpl">
        <tr>
            <th>결제일</th>
            <th>제목</th>
            <th>총 결제금액</th>
            <th>정산현황</th>
        </tr>
        {{each(index, p) dList}}
        <tr>
                <td>\${p.createDate}</td>
                <td><a href="javascript:showDutchPayInfo(\${p.dp_idx})"> \${p.dutchPayName}</a></td>
                <td>\${p.totalPay}</td>
                <td>정산현황</td>
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
        <tr>
            <th>은행</th>
            <th>계좌번호</th>
            <th>이름</th>
            <th></th>
        </tr>
            {{each(index, p) pSave}}
                {{if p.payMember_account != null}}
        <tr style="color: #888888" class="save-account-form\${index}">

            <td id="save-bank">\${p.payMember_bank}</td>
            <td id="save-number">\${p.payMember_account}</td>
            <td id="save-owner">\${p.payMember_name}</td>
            <td>
                <input type="hidden" id="prgm_idx" value="\${p.prgm_idx}">
                <button id="btn-delete-account" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</button>
            </td>
        </tr>
                {{/if}}
            {{/each}}
    </script>

<h1>여기는 PAY방입니다<h1><br>
    <button>취소</button>
    <h2>Pedal</h2>
<button>등록</button><br>
방이름 : <input type="text" name="room_name" id="room_name" readonly><br>
방멤버 : <input type="text" name="groupMember" id="groupMember" ><button onclick="memberListOne()">추가</button><br>
<div id="memberList"></div>
<button>1개만</button><button>여러개</button><br>
<span>결제 목록</span><button id="btn-open-modal" <%--onclick="createNewDutch()"--%>>추가</button><br>
<table id="dutchList">

</table>

<span>계좌 목록</span><button id="btn-account-plus" onclick="createNewAccount()">추가</button><br>
<table id="accountList">
    <tr>
        <th>은행</th>
        <th>계좌번호</th>
        <th>이름</th>
        <th></th>
    </tr>
</table>
<%--    <c:forEach items="${payList}" var="payDTO">
        <tr>
            <td>${payDTO.createDate}</td>
            <td>${payDTO.title}</td> <!-- payDTO에 title이 없었..-->
            <td>${payDTO.totalPay}</td>
            <td>${payDTO.???}</td> <!--정산현황은 어떻게 처리해야 할까유-->
        </tr>
    </c:forEach>--%>









    <div class="modal">
        <div class="modal_body">
            <button id="btn-close-modal">X</button>
            <input type="hidden" id="retrieve-pay-id">
            <hr>
            편집하기<br>

            <form id="pay-form">
<%--                <input type="text" name="payRoom" id="payRoom" value="${payRoom}">--%>
            <input type="text" id="pay_name" name="pay_name" placeholder="결제이름"> <button id="btn-pay-plus"onclick="return createNewPay()">+</button>
            <br>
            <table id="payList">
                <tr>
                    <th>결제 목록</th>
                    <th>결제 금액</th>
                    <th>결제자</th>
                    <th>참여인원</th>
                    <th></th>
                </tr>
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
            <input type="button" onclick="dataAjax($('#pay-form'))">결과 미리보기(정산하기)</input>
            </form>
        </div>
    </div>

<%--<div class="modal">
    <div class="modal_body">

    </div>
</div>--%>

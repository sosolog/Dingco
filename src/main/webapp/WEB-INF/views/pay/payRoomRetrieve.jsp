<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ssy04
  Date: 2022-04-27
  Time: 오후 3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
    <script src="/script/jquery-3.6.0.js"></script>
    <script src="/script/jquery.tmpl.js"></script>
    <script>


        let room_name = ${payRoom}.room_name;
        let groupMemberArr = ${payRoom}.groupMemberList;
        let memberNameArr = [];
        let payArr = [];
        let accountArr = [];

        for (let x of groupMemberArr) {
            memberNameArr.push(x.payMember_name);
            if(x.payMember_account != null || x.payMember_bank != null){
                accountArr.push({"prgm":x.prgm_idx,"sb":x.payMember_bank,"sn":x.payMember_account,"so":x.payMember_name});
            }
        }

        $(document).ready(function(){
            $("#room_name").val(room_name);
            $("#groupMember").val(memberNameArr.join(" "));
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave:accountArr}));

            $("#btn-close-modal").on("click", function(){
                // modal 안보이도록 css 변경
                $(".modal").removeClass("show");
                // 현재까지 저장되어있던 정보 삭제
                payArr = [];
                $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));

                $("#pay_name").val("");
                $("#allPrice").val(0);
                $("#cutPrice").val("0");
                $("#pay-date").val("");
                $("#due-date").val("");
                $("#bill").val("");

            });
            $("#btn-open-modal").on("click", function(){
                $(".modal").addClass("show");
            });
        });

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

        //
        function createNewDutchCfm(){
            console.log("새 더치페이 생성");
            console.log($("#new-dutch-name").val());

            let name = $("#new-dutch-name").val();
            location.href = "/pay/newtest/"+name;

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
            $("#btn-pay-plus").text("+");
            var savePayName = $("#new-pay-name").val();
            var savePayPrice = $("#new-pay-price").val();
            var savePayPayer = $(".new-pay-selector:selected").text();
            var savePayParticipants = $("#new-pay-participants").text();

            payArr.push({"sn":savePayName,"spp":savePayPrice,"spp2":savePayPayer,"spp3":savePayParticipants});

            console.log(payArr);
            $("#new-pay-form").remove();
            $("#payList").html($("#save-pay-tmpl").tmpl({pSave:payArr}));

            var allprice = uncomma($("#allPrice").val())*1;
            var addprice = uncomma(savePayPrice)*1;
            $("#allPrice").val(comma(allprice+addprice));

            return false;
        }

        //적은 계좌 텍스트로 저장해주고 PayGroupMember DB에 update 해주는 함수
        function saveNewAccount(){
            $("#btn-account-plus").text("+");
            var savebank = $("#new-account-bank").val();
            var saveNumber = $("#new-account-number").val();
            var saveOwner = $(".new-account-selector:selected").text();
            var gm_idx = $("#new-account-owner").val();
            accountArr.push({"prgm":gm_idx,"sb":savebank,"sn":saveNumber,"so":saveOwner});

            console.log(accountArr);
            $("#new-account-form").remove();
            $("#accountList").html($("#save-account-tmpl").tmpl({pSave:accountArr}));

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
            return false;
        }

        //저장된 결제 삭제하는 함수
        function deleteSavePay(tr){
            var allprice = uncomma($("#allPrice").val())*1;
            var delprice = uncomma($(tr).parent().parent().find("#save-price").text())*1;
            $("#allPrice").val(comma(allprice-delprice));

            let index = $(tr).attr("data-idx");
            payArr.splice(index,1);
            console.log(payArr);
            $(tr).parent().parent().remove();

        }

        //저장된 계좌 삭제하는 함수
        function deleteSaveAccount(tr){

            let index = $(tr).attr("data-idx");
            accountArr.splice(index,1);
            console.log(accountArr);
            $(tr).parent().parent().remove();

            let prgm_idx = $(tr).parent().find("#prgm_idx").val();
            console.log(prgm_idx);

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

            var pr_idx = ${payRoom}.pr_idx;
            var m_idx = ${payRoom}.m_idx;
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
            <td><button id="new-pay-participants" style="width: 50px"  onclick="return submitCancle()"> \${pr.length}명</button></td>
            <td><button id="btn-update-pay" onclick=" return saveNewPay()">저장</button></td>
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
            <td id="save-payer">\${p.spp2}</td>
            <td id="save-participants">\${p.spp3}</td>
            <td>
                <button id="btn-delete-pay" class="btn-delete-pay" data-idx="\${index}" onclick="deleteSavePay($(this))">삭제</button>
            </td>
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
                    <option class="new-account-selector" value="\${p.prgm_idx}">\${p.payMember_name}</option>
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
        <tr style="color: #888888" class="save-account-form\${index}">
            <td id="save-bank">\${p.sb}</td>
            <td id="save-number">\${p.sn}</td>
            <td id="save-owner">\${p.so}</td>
            <td>
                <input type="hidden" id="prgm_idx" value="\${p.prgm}">
                <button id="btn-delete-account" class="btn-delete-account" data-idx="\${index}" onclick="deleteSaveAccount($(this))">삭제</button>
            </td>
        </tr>
            {{/each}}
    </script>


</head>
<body>
<button>취소</button><h2>Pedal</h2>
<button>등록</button><br>
방이름<input type="text" name="room_name" id="room_name" readonly><br>
방멤버<input type="text" name="groupMember" id="groupMember" ><button onclick="">추가</button><br>
<button>1개만</button><button>여러개</button><br>
<span>결제 목록</span><button id="btn-open-modal" <%--onclick="createNewDutch()"--%>>추가</button><br>
<table id="dutchList">
    <tr>
        <th>결제일</th>
        <th>제목</th>
        <th>총 결제금액</th>
        <th>정산현황</th>
    </tr>
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
            절사옵션<select name="cutPrice" id="cutPrice">
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
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


<h4>비밀번호 찾기</h4>
<div style="color: #ac2925">
    <center>입력된 정보로 임시 비밀번호가 전송됩니다.</center>
</div>
<hr>
<form role="form">
    <label for="userEmail"><span class="glyphicon glyphicon-user"></span>email</label>
    <input type="text" id="userEmail" placeholder="가입시 이메일을 입력하세요.">
<br>
    <label for="userid"><span class="glyphicon glyphicon-eye-open"></span> name</label>
    <input type="text" id="userid" placeholder="아이디를 입력하세요.">
<br>
    <button type="button" id="checkEmail">OK</button>
</form>
<hr>
<div class="text-center small mt-2" id="checkMsg" style="color: red"></div>

<button onclick="history.back()">Cancel</button>


<script>

    $(document).ready(function () {

        $("#checkEmail").click(function () {
            var userEmail = $("#userEmail").val();
            var userid = $("#userid").val();

            $.ajax({
                url: "/check/findPw",
                type: "GET",
                data: {
                    "userEmail": userEmail,
                    "userid": userid
                },
                success: function (res) {
                    if (res['check']) {
                        console.log(res)

                        swal("발송 완료!", "입력하신 이메일로 임시비밀번호가 발송되었습니다.", "success").then((OK) => {
                                if (OK) {
                                    $.ajax({
                                        url: "/check/findPw/sendEmail",
                                        type: "PUT",
                                        data: {
                                            "userEmail": userEmail,
                                            "userid": userid
                                        }
                                    })
                                    window.location = "/login";
                                }

                            }
                        )
                        $('#checkMsg').html('<p style="color:darkblue"></p>');
                    } else {
                        $('#checkMsg').html('<p style="color:red">일치하는 정보가 없습니다.</p>');
                    }
                }
            })
        })
    })
</script>

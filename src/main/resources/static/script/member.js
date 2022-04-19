

// 마이페이지 비밀번호 재확인
function passwd_check(pagename){
    var passwd1 = $('#'+pagename+' input[name=passwd]').val();
    var passwd2 = $('#'+pagename+' input[name=passwd2]').val();
    $('#'+pagename+' input[name=chk_pw]').val(false);

    if (passwd1 != passwd2){
        $('#'+pagename+' .pw_check').text("비밀번호가 일치하지 않습니다");
    } else if (passwd1.length < 6 || passwd1.length > 30) {
        $('#'+pagename+' .pw_check').text("6자 이상, 30자 이하로 입력해주세요");
    } else if (letterCheck.checkKor.test(passwd1)) {
        $('#'+pagename+' .pw_check').text("비밀번호에는 한글을 입력할 수 없습니다");
    } else {
        $('#'+pagename+' .pw_check').text("비밀번호가 일치합니다");
        $('#'+pagename+' input[name=chk_pw]').val(true);
    }
}

// 마이페이지 정보 수정 - 유효성 검사
function editUserForm_submit(f){
    var mesg = "";
    var chk_pw = f.chk_pw.value;
    var email1 = f.email1.value;
    var email2 = f.email2.value;
    var snslogin = f.snslogin.value;

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 비밀번호
    if (chk_pw=="false" && snslogin!=""){ mesg = "비밀번호, "+mesg; }

    // 최종 결과
    if (mesg==""){
        f.submit();
        alert("회원 정보가 수정되었습니다");
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

//선택한 이메일 자동 insert
function f_emailSelect(obj){
    $('[name=email2]').val(obj.value);
}

<!-- 파일 크기 제한(ajax_3MB)-->
function checkFileSize(){
    if (this.files && this.files[0]) {
        var maxSize = 3 * 1024 * 1024;
        var fileSize = this.files[0].size;

        if(fileSize > maxSize){
            alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
            $(this).val('');
            return false;
        }
    }
}


<!-- 회원가입 아이디 유효성 체크 -->
function memberIdCheck() {
    var userid = $('#userid').val(); //id값이 "id"인 입력란의 값을 저장
    var str_space = /\s/; // 공백체크

    $.ajax({
        url: 'memberIdCheck', // Controller에서 인식할 주소나 메서드
        type: 'get', //GET 방식으로 전달
        data: {userid: userid}, // Controller에서  @RequestParam으로 들고옴
        datatype: 'text', // ex) text, html, json ...
        success: function (data) {
            if(data != 0) { //data가 0일 경우 -> 사용 가능한 아이디
                $("#idCheckResult").text("이미 사용중인 아이디입니다.");
                $("#idCheckHidden").val("false");

            } else {
                if (userid.length == 0) {
                    $("#idCheckResult").text("아이디는 필수 입력 값입니다.");
                    $("#idCheckHidden").val("false");
                } else if (str_space.exec(userid)) {
                    $("#idCheckResult").text("아이디에는 띄어쓰기를 하실 수 없습니다.");
                    $("#idCheckHidden").val("false");
                }else {
                    $("#idCheckResult").text("사용 가능한 아이디입니다.");
                    $("#idCheckHidden").val("true");
                }
            }
        }
        ,
        error: function () {
            alert("데이터베이스에 접근이 필요합니다!");
        }
    });
}

<!-- 회원가입 비밀번호 일치 여부 확인 -->
function memberPwCheck() {
    var passwd = $('#passwd').val();
    var passwd1 = $('#passwd1').val();4

    if(passwd == passwd1) {
        $("#pwCheckHidden").val("true");
        $("#pwCheckResult").text("");
    }else {
        $("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
        $("#pwCheckHidden").val("false");
    }
}

<!-- submit 제약조건(아이디 중복 확인, 비밀번호 중복 확인) -->
function joinSubmitCheck(event) {
    if($("#idCheckHidden").val()=='true' && $("#pwCheckHidden").val()=='true'){
        return true;
    }else{
        if($("#idCheckHidden").val()=='false' && $("#pwCheckHidden").val()=='false') {
            alert("아이디와 비밀번호 중복 확인이 필요합니다.");
            return false;
        }else if($("#idCheckHidden").val()=='false') {
            alert("아이디 중복 확인이 필요합니다.");
            return false;
        }else if($("#pwCheckHidden").val()=='false') {
            alert("비밀번호 중복 확인이 필요합니다.");
            return false;
        }
    }
}

// 명지 : 아이디찾기 유효성 검사
function finduserid(f){
    var check = true;
    if (f.username.value==""){
        check = false;
        $('.infoname').text("이름을 입력해주세요");
    } else { $('.infoname').text(""); }
    if (f.email1.value=="" || f.email2.value=="") {
        check = false;
        $('.infoemail').text("이메일을 입력해주세요");
    } else { $('.infoemail').text(""); }

    if (check == true){
        $.ajax({
            url: "/check/findId",
            type: "GET",
            data: {
                "username":f.username.value,
                "email1":f.email1.value,
                "email2":f.email2.value
            },
            success: function (res) {
                if (res==""){
                    $('.findidresult').text("일치하는 값이 없습니다.");
                } else {
                    $('.findidresult').text("회원님의 아이디는 "+res+"입니다");
                }
            }
        });
    }
}


<!-- 이메일, 아이디 DB에서 확인 -->
function findpasswd(f){
    console.log(f)
    var userid = f.userid.value;
    var userEmail = f.userEmail.value;
    console.log(userEmail,userid)

    $.ajax({
        url: "/check/findPw",
        type: "GET",
        data: {
            "userEmail": userEmail,
            "userid": userid
        },
        success: function (res) {
            if (res['check']) {

                swal("발송 완료!", "입력하신 이메일로 임시비밀번호가 발송되었습니다.", "success").then((OK) => {
                        if (OK) {
                            $.ajax({
                                url: "/check/findPw/sendEmail",
                                type: "PUT",
                                data: {
                                    "userEmail": userEmail,
                                    "userid": userid
                                }
                            });
                            location.href = "/login";
                        }

                    }
                )
                $('.findpwresult').html('<p style="color:darkblue"></p>');
            } else {
                $('.findpwresult').html('<p style="color:red">일치하는 정보가 없습니다.</p>');
            }
        }
    });
}

//비동기 로그인 체크
function loginValidCheck(){
    var userid = $("#userid").val();
    var passwd = $("#passwd").val();
    const f = $("#loginForm");

    if(userid.length==0){
        $("#result").text("아이디 입력 필수");
        $("#userid").focus();
        return false;
    }else if(passwd.length==0){
        $("#result").text("비밀번호 입력 필수");
        $("#passwd").focus();
        return false;
    }
    $.ajax({
        url:"/login/check",
        type:"post",
        data:{
            "userid":userid,
            "passwd":passwd
        },
        success:function (res){
            if(res){
                f.attr("action","/login");
                f.attr("method","POST");
                f.submit();
            }else{
                $("#result").text("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        },
        error:function (xhr,sta,e){
            location.href="/error/error";
        }

    });
    return false;

}

function onSuccess(googleUser) {
    console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
}
function onFailure(error) {
    console.log(error);
}
function renderButton() {
    gapi.signin2.render('my-signin2', {
        'scope': 'profile https://www.googleapis.com/auth/profile.emails.read',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });

}








<!-- 이메일 인중 -->
function emailValidationSend(){
    var email1 = $("#email1").val();
    var email2 = $("#email2").val();


    $.ajax({
        url: "emailValidationSend",
        type: "GET",
        data: {
            "email1": email1,
            "email2": email2
        },
        success: function (res) {
            alert("발송 완료!")
        }
    });
}

<!-- 이메일 인증 확인 -->
function emailValidationSend(){
    var emailValidationCheckNumber = $("#emailValidationCheckNumber").val();


    $.ajax({
        url: "emailValidationCheck",
        type: "GET",
        data: {
            "emailValidationCheckNumber": emailValidationCheckNumber
        },
        success: function (res) {
            alert("발송 완료!")
        }
    });
}




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
        $('#'+pagename+' .pw_check').text("비밀번호 일치 및 사용 가능합니다");
        $('#'+pagename+' input[name=chk_pw]').val(true);
    }
}

// 마이페이지 정보 수정 - 유효성 검사
function editUserForm_submit(f){
    var mesg = "";
    var chk_pw = f.chk_pw.value;
    var phone2 = f.phone2.value;
    var phone3 = f.phone3.value;
    var email1 = f.email1.value;
    var email2 = f.email2.value;

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 전화번호
    if ((phone2.length+phone3.length > 8) || (phone2=="" || phone3=="")
        || letterCheck.checkEngAll.test(phone2+phone3) || letterCheck.checkSpc.test(phone2+phone3)
        || letterCheck.checkKor.test(phone2+phone3)) {
        mesg = "전화번호, "+mesg;
    }

    // 비밀번호
    if (chk_pw=="false"){ mesg = "비밀번호, "+mesg; }

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



$(function () {

    <!-- 이메일 선택-->
    $("#url").on("change", function () {
        $("#email2").val($("#url option:selected").val());
    });

    <!-- 파일 크기 제한(ajax_3MB)-->
    $("input[name=file]").off().on("change", function(){
        if (this.files && this.files[0]) {

            var maxSize = 3 * 1024 * 1024;
            var fileSize = this.files[0].size;

            if(fileSize > maxSize){
                alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
                $(this).val('');
                return false;
            }
        }
    });

    <!-- 회원가입 아이디 유효성 체크 -->
    $("#userid").on("keyup", function () {
        var userid = $('#userid').val(); //id값이 "id"인 입력란의 값을 저장
        $.ajax({
            url:'memberIdCheck', // Controller에서 인식할 주소나 메서드
            type:'get', //GET 방식으로 전달
            data:{userid:userid}, // Controller에서  @RequestParam으로 들고옴
            datatype: 'text', // ex) text, html, json ...
            success:function(data){
                if(data != 0) { //data가 0일 경우 -> 사용 가능한 아이디
                    $("#idCheckResult").text("이미 사용중인 아이디입니다.");
                    $("#idCheckHidden").val("false");
                }else{
                    $("#idCheckResult").text("사용 가능한 아이디입니다.");
                    $("#idCheckHidden").val("true");
                }
            }
            ,
            error:function(){
                alert("데이터베이스에 접근이 필요합니다!");
            }
        });
    });

    <!-- 회원가입 비밀번호 일치 여부 확인 -->
    $("#passwd1").on("keyup", function () {
        var passwd = $('#passwd').val();
        var passwd1 = $('#passwd1').val();

        if(passwd == passwd1) {
            $("#pwCheckHidden").val("true");
            $("#pwCheckResult").text("");
        }else {
            $("#pwCheckResult").text("비밀번호가 일치하지 않습니다.");
            $("#pwCheckHidden").val("false");
        }
    });

    <!-- submit 제약조건(아이디 중복 확인, 비밀번호 중복 확인) -->
    $("form").on("submit", function () {
        if($("#idCheckHidden").val()=='true' && $("#pwCheckHidden").val()=='true'){
            return true
        }else{
            if($("#idCheckHidden").val()=='false' && $("#pwCheckHidden").val()=='false') {
                alert("아이디와 비밀번호 중복 확인이 필요합니다.")
                return false
            }else if($("#idCheckHidden").val()=='false') {
                alert("아이디 중복 확인이 필요합니다.")
                return false
            }else if($("#pwCheckHidden").val()=='false') {
                alert("비밀번호 중복 확인이 필요합니다.")
                return false
            }
        }
    });
});


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

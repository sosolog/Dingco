// 문자 구분 객체
var letterCheck = {
    'checkNum': /[0-9]/,
    'checkEngA': /[A-Z]/,
    'checkEnga': /[a-z]/,
    'checkEngAll': /[a-zA-Z]/,
    'checkSpc': /[~!@#$%^&*()+|<>?:{} ]/,
    'checkKor': /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/,
    'blank_pattern' : /[\s]/g
}

// 검색
function adminSearch(f){
    f.submit();
}

// 수정 & 저장
function saveEditForm(f){
    f.submit();
}

// 기존 아이디 사용
function returnID(){
    $("#userid").val($("#o_userid").val());
    $("#useridCheckResult").text("기존 아이디를 사용합니다.");
    $("#chk_id").val("true");
}

// 기존 이메일 사용
function returnEmail(){
    console.log($("#o_email1").val());
    $("#email1").val($("#o_email1").val());
    $("#email2").val($("#o_email2").val());
    $("#emailCheckResult").text("기존 이메일을 사용합니다.");
    $("#chk_email").val("true");
}

// 아이디 중복 확인 reset
function inputID(){
    $("#useridCheckResult").text("");
    $("#chk_id").val("false");
}

// 이메일 중복 확인 reset
function inputEmail(){
    $("#emailCheckResult").text("");
    $("#chk_email").val("false");
}

// 아이디 중복 확인
function dupliCheckID(){
    var userid = $('#userid').val(); // id값이 "id"인 입력란의 값을 저장
    var str_space = /\s/; // 공백체크

    $.ajax({
        url: '/admin/member/id/duplicate', // Controller에서 인식할 주소나 메서드
        type: 'get', // GET 방식으로 전달
        data: { userid: userid }, // Controller에서  @RequestParam으로 들고옴
        datatype: 'text', // ex) text, html, json ...
        success: function (data) {
            if(data != 0) { // data가 0일 경우 -> 사용 가능한 아이디
                $("#useridCheckResult").text("이미 사용중인 아이디입니다.");
                $("#chk_id").val("false");
            } else {
                if (userid.length == 0) {
                    $("#useridCheckResult").text("아이디는 필수 입력 값입니다.");
                    $("#chk_id").val("false");
                } else if (str_space.exec(userid)) {
                    $("#useridCheckResult").text("아이디에는 띄어쓰기를 하실 수 없습니다.");
                    $("#chk_id").val("false");
                } else {
                    $("#useridCheckResult").text("사용 가능한 아이디입니다.");
                    $("#chk_id").val("true");
                }
            }
        },
        error: function () {
            alert("데이터베이스에 접근이 필요합니다!");
        }
    });
}

// 이메일 중복 확인
function dupliCheckEmail(){
    var email1 = $('#email1').val();
    var email2 = $('#email2').val();
    var str_space = /\s/; // 공백체크

    $.ajax({
        url: '/admin/member/email/duplicate', // Controller에서 인식할 주소나 메서드
        type: 'get', // GET 방식으로 전달
        data: {
            email1: email1,
            email2: email2
        }, // Controller에서  @RequestParam으로 들고옴
        datatype: 'text', // ex) text, html, json ...
        success: function (data) {
            if(data != 0) { // data가 0일 경우 -> 사용 가능한 이메일
                $("#emailCheckResult").text("이미 사용중인 이메일입니다.");
                $("#chk_email").val("false");
            } else {
                if (email1.length == 0 || email2.length == 0) {
                    $("#emailCheckResult").text("이메일은 필수 입력 값입니다.");
                    $("#chk_email").val("false");
                } else if (str_space.exec(email1) || str_space.exec(email2)) {
                    $("#emailCheckResult").text("이메일에는 띄어쓰기를 하실 수 없습니다.");
                    $("#chk_email").val("false");
                } else {
                    $("#emailCheckResult").text("사용 가능한 이메일입니다.");
                    $("#chk_email").val("true");
                }
            }
        },
        error: function () {
            alert("데이터베이스에 접근이 필요합니다!");
        }
    });
}


// Member - User 정보 수정 폼 제출
function saveUserEditForm(f){
    var mode = f.mode.value;
    var isSNSUser = f.isSNSUser.value;
    var username = f.username.value;
    var userid = f.userid.value;
    var chk_id = f.chk_id.value;
    if (mode == '추가') { var passwd = f.passwd.value; }
    if (isSNSUser == 'X' || isSNSUser == '') {
        var email1 = f.email1.value;
        var email2 = f.email2.value;
        var chk_email = f.chk_email.value;
    }
    var result = true;

    // USERNAME
    if (letterCheck.checkSpc.test(username) || username.length < 2 || username.length > 10) {
        $('#usernameCheckResult').text('2~10자의 한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)');
        result = false;
    } else {
        $('#usernameCheckResult').text('');
    }

    // USERID
    if (letterCheck.checkSpc.test(userid) || userid.length < 5 || userid.length > 20) {
        $('#useridCheckResult').text('5~20자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.');
        result = false;
    } else {
        $('#useridCheckResult').text('');
    }

    // EMAIL
    if (isSNSUser == 'X' || isSNSUser == '') {
        if (letterCheck.checkSpc.test(email1) || email1.length < 1 || email1.length > 19
            || letterCheck.checkSpc.test(email2) || email2.length < 1 || email2.length > 19) {
            $('#emailCheckResult').text('이메일을 정확히 입력해주세요.');
            result = false;
        } else {
            $('#emailCheckResult').text('');
        }
    }

    // PASSWD
    if (mode == '추가' && (letterCheck.blank_pattern.test(passwd) || passwd.length < 7 || passwd.length > 15)) {
        $('#passwdCheckResult').text('필수 정보입니다.(빈값, 공백 사용불가) (7 ~ 15자)');
        result = false;
    } else {
        $('#passwdCheckResult').text('');
    }

    // DUPL - USERID
    if (chk_id == "false") {
        $('#useridCheckResult').text('아이디 중복확인이 필요합니다.');
        result = false;
    }

    // DUPL - EMAIL
    if (chk_email == "false") {
        $('#emailCheckResult').text('이메일 중복확인이 필요합니다.');
        result = false;
    }

    if (result) { f.submit(); }
}


// Member - Admin 정보 수정 폼 제출
function saveAdminEditForm(f){
    var mode = f.mode.value;
    var username = f.username.value;
    var userid = f.userid.value;
    var chk_id = f.chk_id.value;
    if (mode == '추가') { var passwd = f.passwd.value; }
    var result = true;

    // USERNAME
    if (letterCheck.checkSpc.test(username) || username.length < 2 || username.length > 10) {
        $('#usernameCheckResult').text('2~10자의 한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)');
        result = false;
    } else {
        $('#usernameCheckResult').text('');
    }

    // USERID
    if (letterCheck.checkSpc.test(userid) || userid.length < 5 || userid.length > 20) {
        $('#useridCheckResult').text('5~20자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.');
        result = false;
    } else {
        $('#useridCheckResult').text('');
    }

    // PASSWD
    if (mode == '추가' && (letterCheck.blank_pattern.test(passwd) || passwd.length < 7 || passwd.length > 15)) {
        $('#passwdCheckResult').text('필수 정보입니다.(빈값, 공백 사용불가) (7 ~ 15자)');
        result = false;
    } else {
        $('#passwdCheckResult').text('');
    }

    // DUPL - USERID
    if (chk_id == "false") {
        $('#useridCheckResult').text('아이디 중복확인이 필요합니다.');
        result = false;
    }

    if (result) { f.submit(); }
}


// Inquiry 검색 폼 제출
function submitSearchForm(f){
    f.submit();
}

// 업로드 이미지 파일 크기 제한(3MB)
function imageFileSizeCheck(file){
    var maxSize = 3 * 1024 * 1024;
    var fileSize = $("#"+file)[0].files[0].size;

    if(fileSize > maxSize){
        alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
        $("#"+file).val('');
        return false;
    }else {
        var file = event.target.files[0];

        var reader = new FileReader();
        reader.onload = function (e) {
            $("#preview").attr("src", e.target.result);
        }

        reader.readAsDataURL(file);
    }
}
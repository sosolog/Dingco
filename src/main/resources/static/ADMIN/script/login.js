// 비동기 로그인 체크
function loginValidCheck(f) {
    var userid = $("#userid").val();
    var passwd = $("#passwd").val();

    if (userid.length == 0) {
        $("#result").text("아이디를 입력해 주세요");
        $("#userid").focus();
        return false;
    } else if (passwd.length == 0) {
        $("#result").text("비밀번호를 입력해 주세요");
        $("#passwd").focus();
        return false;
    }
    $.ajax({
        url: "/admin/login/check",
        type: "get",
        data: {
            "userid": userid,
            "passwd": passwd
        },
        success: function (res) {
            if (res) {
                f.submit();
            } else {
                $("#result").text("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        },
        error: function (xhr, sta, e) {
            location.href = "/error/error";
        }

    });
    return false;

}
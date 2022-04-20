<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<script>

    // 네이버 로그인
    var naver_id_login = new naver_id_login("srtVLSBDQTIgJD7D65Ls", "http://localhost:9090/callback");

    // 네이버 사용자 프로필 조회
    naver_id_login.get_naver_userprofile("naverLoginCallback()");

    function naverLoginCallback() {
        var naver_idx = naver_id_login.getProfileData('id')
        $.ajax({
            url:"socialMemberNaverIdxCheck",
            type:"post",
            data:{"naver_idx":naver_idx,},
            success:function (data){
                if(data){
                    location.href = "main";
                }else {
                    $("#reg-form").css("visibility", "visible");

                    // userid 세팅
                    $("#username").val(naver_id_login.getProfileData('name'));

                    // naver_idx 세팅
                    $("#naver_idx").val(naver_idx);
                }
            },
            error:function (xhr,sta,e){
                location.href="/error/error";
            }
        });
    }

    //비동기 로그인 체크
    function socialLoginValidCheck(){
        const form = $("#socialMemberNaverLogin")

        var userid = $('#userid').val(); //id값이 "id"인 입력란의 값을 저장
        var str_space = /\s/; // 공백체크
        const regex = /[^a-zA-Z0-9]/g // 영문 대 소문자, 숫자

        $.ajax({
            url:"socialMemberIdCheck",
            type:"get",
            data:{"userid":userid,},
            success:function (data){
                if(data){
                    $("#idCheckResult").text("중복된 아이디가 존재합니다.");
                    $("#userid").val("");
                    $("#userid").focus();
                    return false;
                }else {
                    if (userid.length == 0) {
                        $("#idCheckResult").text("필수 입력 값입니다.");
                        $("#userid").val("");
                        $("#userid").focus();
                        return false;
                    }else if(userid.length >= 20 || userid.length <= 5){
                        $("#idCheckResult").text("5~20자리 안으로 입력해주세요.");
                        $("#userid").val("");
                        $("#userid").focus();
                        return false;
                    }else if (str_space.exec(userid)) {
                        $("#idCheckResult").text("띄어쓰기를 하실 수 없습니다.");
                        $("#userid").val("");
                        $("#userid").focus();
                        return false;
                    }else if (regex.exec(userid)) {
                        $("#idCheckResult").text("영문 대 소문자와 숫자만 가능합니다.");
                        $("#userid").val("");
                        $("#userid").focus();
                        return false;
                    }else {
                        form.attr("action","socialMemberAdd");
                        form.attr("method","POST");
                        form.submit();
                        return
                    }
                }
            },
            error:function (xhr,sta,e){
                location.href="/error/error";
            }
        });
    }

</script>

<div id="reg-form" style="visibility: hidden">

<h2>소셜 회원가입 페이지</h2>
<br>
<form id="socialMemberNaverLogin">
    <input type="hidden" id="naver_idx" name="naver_idx">
    <input type="hidden" id="socialIdCheckResult" value="false">
    * 아이디:<input type="text" id="userid" name="userid">
    <span id="idCheckResult"></span><br>
    * 이름:<input type="text" id= "username" name="username" readonly="readonly"><br>
    <br>
    <br>
    <input type="button" value="회원가입" onclick="return socialLoginValidCheck()"/>
</form>

</div>

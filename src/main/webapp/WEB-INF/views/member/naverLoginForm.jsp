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
            url:"/login/oauth/naver/duplicate",
            type:"post",
            data:{"naver_idx":naver_idx},
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
</script>

<div id="reg-form" style="visibility: hidden"></div>

<div id="join">
<form id="socialMemberNaverLogin" name="socialMemberNaverLogin" action="/join/naver.action" method="post">
    <input type="hidden" id="naver_idx" name="naver_idx">
    <input type="hidden" id="socialIdCheckResult" value="false">
    <table class="join_table">
        <tr>
            <td class="item_th"><span> 이름</span></td>
            <td class="item_box">
                <div><input type="text" id="username" name="username" value="${username}" readonly></div>
            </td>
        </tr>
        <tr>
            <td class="item_th"><span>아이디</span></td>
            <td class="item_box">
                <div><input type="text" id="userid" name="userid" value="" placeholder="아이디를 입력해주세요" onkeyup="memberIdDuplicateCheck()"></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td style="text-align: left;"><span id="idCheckResult" class="id_check"></span></td>
        </tr>
    </table>
    <a class="btn_login" onclick="socialLoginValidCheck(socialMemberNaverLogin)"><span>가입하기</span></a>
</form>
</div>

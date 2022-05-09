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

<div id="reg-form" style="visibility: hidden">

<h2>소셜 회원가입 페이지</h2>
<br>
<form id="socialMemberNaverLogin" name="socialMemberNaverLogin" action="/join/naver.action" method="post">
    <input type="hidden" id="naver_idx" name="naver_idx">
    <%--<input type="hidden" id="socialIdCheckResult" value="false">--%>
    * 아이디:<input type="text" id="userid" name="userid">
    <span id="idCheckResult"></span><br>
    * 이름:<input type="text" id= "username" name="username" readonly="readonly"><br>
    <br>
    <br>
    <input type="button" value="회원가입" onclick="socialLoginValidCheck(socialMemberNaverLogin)"/>
</form>

</div>

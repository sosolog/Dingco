<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>

    $(document).ready(function(){

        $("form").on("submit",function (){
            var userid = $("#userid").val()
            var passwd = $("#passwd").val()

            if(userid.length==0){
                alert("아이디 입력 필수")
                $("#userid").focus()
                event.preventDefault()
            }else if(passwd.length==0){
                alert("비밀번호 입력 필수")
                $("#userid").focus()
                event.preventDefault()
            }
        })

        $("#joinBtn").on("click",function (){
            location.href="join";
        })

        $("#find_ID_PWBtn").on("click",function (){
            location.href="find_ID_PW";
        })

        $("#upload").on("click",function (){
            location.href="upload";
        })

    })

</script>


<h2>로그인 페이지</h2>
<form action="login" method="post">
    * 아이디:<input type="text" name="userid" id="userid">
    <span id="result" style="color:red"></span><br>
    * 비밀번호:<input type="password" name="passwd" id="passwd" >
    <button>로그인</button>
</form>
    <button id="joinBtn">회원가입</button><br>
    <button id="find_ID_PWBtn">아이디/비밀번호 찾기</button><br>
    <button id="kakaoLoginBtn">카카오톡으로 로그인</button><br>
    <button id="googleLoginBtn">구글로 로그인</button><br>
    <button id="naverLoginBtn">네이버로 로그인</button><br>
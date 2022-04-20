<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>

    function socialLoginValidCheck(){
        const form = $("#socialMemberGoogleLogin")

        var userid = $('#userid').val(); //id값이 "id"인 입력란의 값을 저장
        var str_space = /\s/; // 공백체크
        const regex = /[^a-zA-Z0-9]/g // 영문 대 소문자, 숫자

        $.ajax({
            url:"http://localhost:9090/socialMemberIdCheck",
            type:"get",
            data:{"userid":userid},
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
                        form.attr("action","loginCheck");
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
    <h2>소셜 회원가입 페이지</h2>
    <br>

<form id="socialMemberGoogleLogin" >
    <input type="hidden" id="google_idx" name="google_idx" value="${google_idx}">
    * 아이디:<input type="text" id="userid" name="userid" value="${userid}">
    <span id="idCheckResult"></span><br>
    * 이름:<input type="text" id= "username" name="username" readonly="readonly" value="${username}"><br>
    <br>
    <br>
    <input type="button" value="회원가입" onclick="return socialLoginValidCheck()"/>
</form>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!-- 이메일 선택-->
<script>
    $(function () {
        $("#url").on("change", function () {
            $("#email2").val($("#url option:selected").val());
        });
    });
</script>

<!-- 파일 크기 제한(ajax_3MB)-->
<script>
    $(function () {
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
    });
</script>

<!-- 회원가입 아이디 유효성 체크 -->
<script>
    $(function () {
        $("#idCheck").on("click", function () {
            var userid = $('#userid').val(); //id값이 "id"인 입력란의 값을 저장
            $.ajax({
                url:'memberIdCheck', // Controller에서 인식할 주소나 메서드
                type:'get', //GET 방식으로 전달
                data:{userid:userid}, // Controller에서  @RequestParam으로 들고옴
                datatype: 'text', // ex) text, html, json ...
                success:function(data){
                    if(data != 0) { //cnt가 0일 경우 -> 사용 가능한 아이디
                        $('#userid').val("")
                        $("#idCheckResult").text("이미 사용중인 아이디입니다.");
                        $("#idCheckHidden").val("false");
                    }else{
                        $("#idCheckResult").text("사용 가능한 아이디입니다.");
                        $("#idCheckHidden").val("true");
                    }
                }
                ,
                error:function(){
                    alert("에러입니다!");
                }
            });
        });
    });
</script>

<!-- submit 제약조건(아이디 중복체크, 전화번호 인증_미정) -->
<script>
    $(function () {
        $("form").on("submit", function () {
            if($("#idCheckHidden").val()=='true'){
                return true
            }else{
                alert("아이디 중복확인이 필요합니다.")
                return false
            }
        });
    });
</script>

<h2>회원가입 페이지</h2>
<br>
<form action="memberAdd" id="memberAdd" method="post" enctype="multipart/form-data">
    <input type="hidden" value="false" id="idCheckHidden" name="idCheckHidden">
    * 아이디:<input type="text" id="userid" name="userid" value="${memberDTO.userid}">
    <input type="button" id="idCheck" value="아이디 중복확인"><br>
    <span id = "idCheckResult">
        <spring:bind path="memberDTO.userid">
            ${status.errorMessage }
        </spring:bind>
    </span><br>
    <br>
    * 비밀번호:<input type="password"  id="passwd" name="passwd" ><br><br>
    * 비밀번호 확인:<input type="password" id="passwd1" name="passwd1" onkeyup=""><br>
    <span>
        <spring:bind path="memberDTO.passwd">
            ${status.errorMessage }
        </spring:bind>
    </span><br>
    * 이름:<input type="text" id= "username" name="username" value="${memberDTO.username}"><br>
    <span>
        <spring:bind path="memberDTO.username">
                ${status.errorMessage}
        </spring:bind>
    </span><br>
    * 전화번호:<select name="phone1" value="${memberDTO.phone1}">
        <option value="010">010</option>
        <option value="011">011</option>
            </select>-
    <input type="text" name="phone2" value="${memberDTO.phone2}">-
    <input type="text" name="phone3" value="${memberDTO.phone3}">
    <button>휴대전화 인증</button>
    <br>
    <spring:bind path="memberDTO.phone2">
        ${status.errorMessage}
    </spring:bind>
    <spring:bind path="memberDTO.phone3">
        ${status.errorMessage}
    </spring:bind>
    <br>
    <br>
    <input id="file" name = "file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic"/><br>
    <br>프로필 사진 업로드(크기:2MB 이내, 확장자:gif,jpg,png,bmp,jpeg,heic)<br>
    <!-- accept: 지정한 확장자 이외에는 클릭 자체가 안됨-->

    <br>
    * 이메일:<input type="text" name="email1" id="email1" value="${memberDTO.email1}">@
    <input type="text" name="email2" id="email2" value="${memberDTO.email2}">
    <select id="url">
        <option value="daum.net">daum.net</option>
        <option value="naver.com">naver.com</option>
        <option value="gmail.com">gmail.com</option>
    </select>
    <br>
    <spring:bind path="memberDTO.email1">
        ${status.errorMessage }
    </spring:bind>
    <spring:bind path="memberDTO.email2">
        ${status.errorMessage }
    </spring:bind>
    <br>
    <br>
    <input type="submit" value="회원가입">
    <input type="reset" value="취소">
</form>

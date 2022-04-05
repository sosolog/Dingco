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

<!-- 파일 크기 제한(ajax_2MB)-->
<script>
    $(function () {
        $("input[name=file]").off().on("change", function(){
            if (this.files && this.files[0]) {

                var maxSize = 2 * 1024 * 1024;
                var fileSize = this.files[0].size;

                if(fileSize > maxSize){
                    alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.");
                    $(this).val('');
                    return false;
                }
            }
        });
    });
</script>


<h2>회원가입 페이지</h2>
<br>
<form action="memberAdd" id="memberAdd" method="post" enctype="multipart/form-data">
    * 아이디:<input type="text" id="userid" name="userid" value="${memberDTO.userid}">
    <button>아이디 중복확인</button><br>
    <span>
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
    <spring:bind path="memberDTO.phone1">
        ${status.errorMessage}
    </spring:bind>
    <spring:bind path="memberDTO.phone2">
        ${status.errorMessage}
    </spring:bind>
    <spring:bind path="memberDTO.phone3">
        ${status.errorMessage}
    </spring:bind>
    <br>
    <br>
    프로필 사진 업로드(크기:5MB 이내, 확장자:gif,jpg,png,bmp,jpeg,heic)<br>
    <!-- accept: 지정한 확장자 이외에는 클릭 자체가 안됨-->
    <input id="file" name = "file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic"/>
    <br>
    <c:if test = "${fn:contains(errors, 'size_limit')}">
        <span id = "errors_size_limit">${errors['size_limit']}</span>
    </c:if><br>

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

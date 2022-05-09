<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h4>아이디 찾기</h4>
<form name="findid">
    <div>
        <span>name</span>
        <input name="username" placeholder="이름을 입력하세요">
        <span class="infoname"></span>
    </div>
    <div>
        <span>email</span>
        <input name="email1">@
        <input name="email2">
        <span class="infoemail"></span>
    </div>
    <a onclick="finduserid(findid)" style="display:inline-block; margin-top:10px; width:100px; padding:5px 0px; border: 1px solid #555555; text-align: center; cursor:pointer;"><span>아이디 찾기</span></a>
    <span class="findidresult"></span>
    <div>------------------------------------------------------</div>
</form>


<h4>비밀번호 찾기</h4>
<div style="color: #ac2925">
    입력된 정보로 임시 비밀번호가 전송됩니다.
</div>
<form name="findpw">
    <div>
        <span>id</span>
        <input type="text" name="userid" placeholder="아이디를 입력하세요">
        <span class="infoid"></span>
    </div>
    <div>
        <span>email</span>
        <input type="email" name="userEmail" placeholder="가입시 이메일을 입력하세요">
        <span class="infoemail"></span>
    </div>
    <a onclick="findpasswd(findpw)" style="display:inline-block; margin-top:10px; width:100px; padding:5px 0px; border: 1px solid #555555; text-align: center; cursor:pointer;"><span>임시비밀번호 받기</span></a>
    <span class="findpwresult"></span><br>
    <input type="button" onclick="location.href='login'" value="취소">
</form>



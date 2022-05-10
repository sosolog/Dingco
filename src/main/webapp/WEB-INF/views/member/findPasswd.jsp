<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="findPasswd">
<form name="findpw">
    <table class="join_table">
        <tr>
            <td class="item_th"><span>아이디</span></td>
            <td class="item_box">
                <div><input type="text" id="userid" name="userid" placeholder="가입 시 아이디를 입력해주세요"></div>
            </td>
        </tr>
        <tr>
            <td class="item_th"><span>이메일</span></td>
            <td class="item_box">
                <div><input type="email" id="userEmail" name="userEmail" placeholder="가입 시 이메일을 입력해주세요"></div>
            </td>
        </tr>
    </table>
    <a class="btn_login" onclick="findpasswd(findpw)"><span>임시비밀번호 받기</span></a>
    <span class="result" id="result"></span>
</form>

</div>


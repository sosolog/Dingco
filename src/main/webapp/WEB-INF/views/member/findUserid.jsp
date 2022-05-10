<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="findUserid">
<form name="findid">
    <table class="join_table">
        <tr>
            <td class="item_th"><span>이름</span></td>
            <td class="item_box">
                <div><input type="text" id="username" name="username" placeholder="가입 시 이름을 입력해주세요"></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span class="infoname"></span></td>
        </tr>
        <tr>
            <td class="item_th"><span>이메일</span></td>
            <td class="td_email">
                <input type="text" name="email1" id="email1" value="${memberDTO.email1}" placeholder="앞자리 입력">
                <span class="link">@</span>
                <input type="text" name="email2" id="email2" value="${memberDTO.email2}" placeholder="직접 입력">
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span class="infoemail"></span></td>
        </tr>
    </table>
    <a class="btn_login" onclick="finduserid(findid)"><span>아이디 찾기</span></a>
    <span class="result" id="result"></span>
</form>
</div>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="join">
    <form id="socialMemberGoogleLogin" name="socialMemberGoogleLogin" action="/login/oauth/google.action" method="post">
        <input type="hidden" id="google_idx" name="google_idx" value="${google_idx}">
        <table class="join_table">
            <tr>
                <td class="item_th"><span>이름</span></td>
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
        <a class="btn_login" onclick="socialLoginValidCheck(socialMemberGoogleLogin)"><span>가입하기</span></a>

    </form>
</div>
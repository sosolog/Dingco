<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="join">
<c:set var="kakao_idx" value="${snsLoginDTO.kakao_idx}"/>
<c:set var="username" value="${snsLoginDTO.username}"/>

<form id="socialMemberKakaoLogin" name="socialMemberKakaoLogin" method="post" action="/login/kakao.action">
    <input type="hidden" id="kakao_idx" name="kakao_idx" value="${kakao_idx}">
    <%--* 아이디:<input type="text" id="userid" name="userid">
    <span id="idCheckResult"></span><br>
    * 이름:<input id= "username" name="username" readonly="readonly" value="${username}"><br>
    <br>
    <input type="button" value="회원가입" onclick="socialLoginValidCheck(socialMemberKakaoLogin)"/>--%>


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
    <a class="btn_login" onclick="socialLoginValidCheck(socialMemberKakaoLogin)"><span>가입하기</span></a>

</form>
</div>
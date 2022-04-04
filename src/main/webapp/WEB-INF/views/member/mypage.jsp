<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="m_idx" value="${userInfo.m_idx}"/>
<c:set var="userid" value="${userInfo.userid}"/>
<c:set var="passwd" value="${userInfo.passwd}"/>
<c:set var="username" value="${userInfo.username}"/>
<c:set var="phone1" value="${userInfo.phone1}"/>
<c:set var="phone2" value="${userInfo.phone2}"/>
<c:set var="phone3" value="${userInfo.phone3}"/>
<c:set var="email1" value="${userInfo.email1}"/>
<c:set var="email2" value="${userInfo.email2}"/>
<c:set var="authorities" value="${userInfo.authorities}"/>
<c:set var="joindate" value="${userInfo.joindate}"/>


<div id="wrap_editUser">
    <div id="main">
        <div class="title">
			<span>
				사용자 정보 수정
			</span>
        </div>
        <form action="/editMypage.action" id="editUser_form" name="editUserForm" method="post">
            <table class="editUser_table">
                <tr>
                    <td class="item_th">
                        <div><span>IDX</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly="readonly" id="m_idx" name="m_idx" value="${m_idx}"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>이름</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly="readonly" id="username" name="username" value="${username}"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>아이디</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly="readonly" id="userid" name="userid" value="${userid}"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>비밀번호</span></div>
                    </td>
                    <td class="item_box">
                        <div><input type="text" id="passwd" name="passwd" value="${passwd}" onkeyup="passwd_check('wrap_editUser')"
                                    placeholder="비밀번호를 입력하세요" autocomplete="off"></div>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>재확인</span></div>
                    </td>
                    <td class="item_box">
                        <div><input type="text" id="passwd2" name="passwd2" onkeyup="passwd_check('wrap_editUser')"
                                    placeholder="비밀번호를 다시 한번 입력하세요" autocomplete="off"></div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <input type="hidden" id="chk_pw" name="chk_pw" value="false">
                    <td><span class="pw_check">비밀번호를 입력해주세요</span></td>
                </tr>
                <tr>
                    <td class="item_th"><div>전화번호</div></td>
                    <td class="td_phone">
                        <select name="phone1">
                            <option value="010">010</option>
                            <option value="011">011</option>
                        </select>
                        <span class="link">-</span>
                        <input type="text" name="phone2" value="${phone2}" autocomplete="off">
                        <span class="link">-</span>
                        <input type="text" name="phone3" value="${phone3}" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td class="item_th"><div>이메일</div></td>
                    <td class="td_email">
                        <input type="text" name="email1" id="email1" value="${email1}" autocomplete="off">
                        <span class="link">@</span>
                        <input type="text" name="email2" id="email2" value="${email2}" autocomplete="off">
                        <select id="emailSelect" onchange="f_emailSelect(this)">
                            <option value="">직접입력</option>
                            <option value="daum.net">daum.net</option>
                            <option value="naver.com">naver.com</option>
                            <option value="gmail.com">gmail.com</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="item_th">
                        <div><span>가입일</span></div>
                    </td>
                    <td class="item_box">
                        <div><input readonly="readonly" id="joindate" name="joindate" value="${joindate}"></div>
                    </td>
                </tr>
            </table>
            <div class="wrap_btn">
                <a class="submit_box" onclick="editUserForm_submit(editUserForm)">수정하기</a>
            </div>
        </form>
    </div>
</div>


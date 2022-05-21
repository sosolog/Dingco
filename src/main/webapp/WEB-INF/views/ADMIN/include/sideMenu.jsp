<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Start: #sideMenu -->
<div id="sideMenu">
    <table class="side-table">
        <tr>
            <td class="box-logo">
                <span>Pedal</span>
                <span>Administration</span>
            </td>
            <td class="box-logininfo">
                <span>${loginMember.userid} 님, 환영합니다!</span>
            </td>
            <td class="box">
                <span class="category">회원 관리</span>
                <a href="/admin/member/userList" class="option <c:if test="${fn:contains(url, 'member/user')}">on</c:if>">사용자</a>
                <a href="/admin/member/adminList" class="option <c:if test="${fn:contains(url, 'member/admin')}">on</c:if>">관리자</a>
                <a href="/admin/inquiryList" class="option <c:if test="${fn:contains(url, 'inquiry')}">on</c:if>">문의 내역</a>
            </td>
            <td class="box">
                <span class="category">콘텐츠 관리</span>
                <a href="/admin/payList" class="option <c:if test="${fn:contains(url, 'pay')}">on</c:if>">더치페이 방 목록</a>
            </td>
            <td class="box">
                <span class="category">공지사항</span>
                <a href="/admin/noticeList" class="option <c:if test="${fn:contains(url, 'notice')}">on</c:if>">공지사항</a>
                <a href="/admin/faqList" class="option <c:if test="${fn:contains(url, 'faq')}">on</c:if>">자주 묻는 질문 (FAQ)</a>
            </td>
            <td class = "box">
                <a class = "category" href="/admin/logout">로그아웃 하기</a>
            </td>
        </tr>
    </table>
</div>
<!-- end: #sideMenu -->
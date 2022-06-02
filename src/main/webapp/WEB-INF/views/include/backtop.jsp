<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="header_box">
    <header id="headerTitle">
        <c:if test="${fn:contains(url, 'main')}">
            <div class="ico_lt">
                <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerLogo" href="/main"><span>Pedal</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'mypage')}">
            <div class="ico_lt">
                <a href="/main"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerText"><span>마이페이지</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'login')}">
            <div class="ico_lt">
                <a href="/main"><img src="/images/ico_back_01.png"></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'join') or fn:contains(url, 'oauth') or fn:contains(url, 'callback') or fn:contains(url, '/login/kakao')}">
            <div class="ico_lt">
                <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerText"><span>회원가입</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'find/userid')}">
            <div class="ico_lt">
                <a href="/login"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerText"><span>아이디 찾기</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'find/passwd')}">
            <div class="ico_lt">
                <a href="/login"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerText"><span>비밀번호 찾기</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'faq')}">
            <div class="ico_lt">
                <a href="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerLogo" href="/faq"><span>FAQ</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, 'notice')}">
            <div class="ico_lt">
                <a href="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerLogo" href="/notice"><span>NOTICE</span></a>
            </div>
        </c:if>
        <c:if test="${url == '/inquiry'}">
            <div class="ico_lt">
                <a href="/main"><img src="/images/ico_back_01.png"></a>
            </div>
            <div class="title">
                <a class="headerLogo"><span>1:1문의</span></a>
            </div>
            <div class="ico_rt">
                <a class="write" href="/inquiry/write"><span>등록</span></a>
            </div>
        </c:if>
    </header>
</div>
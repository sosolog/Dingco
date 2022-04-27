<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="header_box">
    <header id="headerTitle">
        <div class="ico_lt">
            <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
        </div>
        <div class="title">
        <c:if test="${fn:contains(url, 'main')}">
            <a class="headerLogo" href="/main"><span>Pedal</span></a>
        </c:if>
        <c:if test="${fn:contains(url, 'mypage')}">
            <a class="headerText"><span>마이페이지</span></a>
        </c:if>
        <c:if test="${fn:contains(url, 'join')}">
            <a class="headerText"><span>회원가입</span></a>
        </c:if>
        </div>
    </header>
</div>
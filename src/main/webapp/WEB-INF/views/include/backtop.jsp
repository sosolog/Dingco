<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="header_box">
    <header id="headerTitle">
        <div class="ico_lt">
            <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
        </div>
        <c:if test="${not fn:contains(url, '/login')}">
        <div class="title">
            <a class="headerLogo" href="/main"><span>Pedal</span></a>
        </div>
        </c:if>
    </header>
</div>
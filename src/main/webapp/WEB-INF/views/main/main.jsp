<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="main">
<c:if test="${loginMember == null}">
    <div class="unlogin">
        <span class="info">로그인 후 이용해 주세요</span>
        <a href="/login"><span>로그인</span></a>
    </div>
</c:if>
<c:if test="${loginMember != null}">
    <div class="listbox"></div>
    <a href="/pay/list" style="padding: 15px 30px; background-color:#333333;">
        <span style="color:#ffffff; font-size:30px;">방 생성 임시 버튼</span>
    </a>
</c:if>
</div>


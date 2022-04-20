<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h2>메인 화면(로그인)</h2>
<button onclick="location.href='/login/mypage'">
    마이페이지
</button>
<a href="/login/snsmypage">sns마이페이지</a>
<form action="logout" method="post">
<button>
    로그아웃
</button>
</form>

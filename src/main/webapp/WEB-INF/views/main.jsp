<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
</head>
    <body>
        <h2>메인 화면(로그인, 회원가입 테스트용)</h2>
        <button onclick="location.href='/join'">
            회원가입
        </button><br><br>
        <button onclick="location.href='/login'">
            로그인
        </button><br><br>
        <button onclick="location.href='/fileUpload'">
            파일 업로드 테스트 폼
        </button>
    </body>
</html>

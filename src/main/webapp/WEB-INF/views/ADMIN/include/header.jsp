<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Pedal</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=1920, user-scalable=no">
    <meta name="google-signin-client_id" content="165389155458-06oihucqpb37gsgqbvsuupqjjg47kjik.apps.googleusercontent.com">

   <%-- <meta name="google-site-verification" content="EBWVlt9UKAo5kb3JFlCqSdRF14f0cLXgws_kOjLW6gE" />
    <meta name="naver-site-verification" content="a954ae14ef5b21504685d492f4e3142a8c84efde" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta property="og:url" content="">

    <link rel="icon" href="/favicon.ico" type="image/x-icon">--%>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" type="text/css" href="/ADMIN/css/common.css" />
    <%--<link rel="preconnect" href="https://fonts.gstatic.com">--%>

    <script type="text/javascript" src="/script/jquery-3.6.0.js"></script>

    <script type="text/javascript" src="/ADMIN/script/common.js"></script>
    <script type="text/javascript" src="/ADMIN/script/login.js"></script>
    <%--<script type="text/javascript" src="/ADMIN/script/member.js"></script>
    <script type="text/javascript" src="/ADMIN/script/board.js"></script>--%>
    <script type="text/javascript" src="/ADMIN/script/pay.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">


</head>
<body>

<%-- ${url}??? ???????????? ???!! --%>
<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>






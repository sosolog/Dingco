<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Start: #aside -->
<div id="aside-black"></div>
<div id="aside">
    <div class="close_wrap">
        <a onclick="open_menu()" class="ico_wrap">
            <img src="images/aside/ico_close.png">
        </a>
        <div class="reset"></div>
    </div>
    <c:if test="${loginMember == null}">
        <a class="pro_box" href="/login">
            <div class="img_wrap"><img src="/images/aside/profile_no_image.png"></div>
            <div class="text_box">
                <div class="top">
                    <span>LOGIN</span>
                </div>
                <span class="userid1">로그인 후 이용해 주세요</span>
            </div>
        </a>
    </c:if>
    <c:if test="${loginMember != null}">
        <div class="pro_box">
            <div class="img_wrap">
                <c:if test="${loginMember.storeFileName == null}"><img src="/images/aside/profile_no_image.png"></c:if>
                <c:if test="${loginMember.storeFileName != null}"><img src="/files/member/${loginMember.storeFileName}"></c:if>
            </div>
            <div class="text_box">
                <div class="top">
                    <span>${loginMember.username}</span>
                    <a href="/mypage"><img src="/images/aside/ico_edit_02.png"></a>
                </div>
                <span class="userid2">${loginMember.userid}</span>
            </div>
        </div>
    </c:if>
    <nav class="menu">
        <div class="box_line"></div>
        <c:if test="${loginMember != null}">
            <div class="box_wrap">
                <span class="tit">내 활동</span>
                <div class="menu_detail">
                    <a href="/logout"><span class="logout">로그아웃</span></a>
                </div>
            </div>
            <div class="box_line"></div>
        </c:if>
        <div class="box_wrap">
            <span class="tit">고객센터</span>
            <div class="menu_detail">
                <a href="/notice"><span>공지사항</span></a>
                <a href="/faq"><span>FAQ</span></a>
                <c:if test="${loginMember != null}">
                    <a href="/inquiry"><span>1:1 문의</span></a>
                </c:if>
            </div>
        </div>
        <div class="box_line"></div>
    </nav>

    <!-------------------------------------------->
    <!-- #ajax_logout -->
    <div class="beforeAfterLogout"></div>
    <!-------------------------------------------->

</div>
<!-- end: #aside -->
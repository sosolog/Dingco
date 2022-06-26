<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="header_box">
    <header id="headerTitle">

        <div class="ico_lt">
            <a class="cancel" onclick="javascript:history.back()"><span>취소</span></a>
        </div>
        <div class="title">
            <c:if test="${fn:contains(url, 'inquiry')}">
                <a class="headerLogo"><span>1:1 문의</span></a>
            </c:if>
            <c:if test="${fn:contains(url, '/pay/')}">
                <a class="headerLogo"><span>Pedal</span></a>
            </c:if>
        </div>
        <c:if test="${fn:contains(url, 'inquiry') && fn:contains(url, 'write')}">
            <div class="ico_rt">
                <a class="save" onclick="submitInquiryForm(inquiryForm)"><span>등록</span></a>
            </div>
        </c:if>
        <c:if test="${(fn:contains(url, 'inquiry') && fn:contains(url, 'edit'))}">
            <div class="ico_rt">
                <a class="save" onclick="submitInquiryForm(inquiryForm, ${inquiryDTO.i_idx})"><span>저장</span></a>
            </div>
        </c:if>
        <c:if test="${fn:contains(url, '/pay/')}">
            <div class="ico_rt">
                <a class="save" href="javascript:history.back()"><span>저장</span></a>
            </div>
        </c:if>
    </header>
</div>
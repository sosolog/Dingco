<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--등록 모드-->
<c:if test="${empty memberDTO.m_idx}">
    <c:set var="mode" value="추가"/>
    <c:set var="m_idx" value=""/>
    <c:set var="isSNSUser" value=""/>
</c:if>
<!--수정 모드-->
<c:if test="${not empty memberDTO.m_idx}">
    <c:set var="mode" value="수정"/>
    <c:set var="m_idx" value="${memberDTO.m_idx}"/>
    <c:set var="username" value="${memberDTO.username}"/>
    <c:set var="userid" value="${memberDTO.userid}"/>
    <c:set var="passwd" value="${memberDTO.passwd}"/>
    <c:if test="${not empty memberDTO.email1}">
        <c:set var="email1" value="${memberDTO.email1}"/>
        <c:set var="email2" value="${memberDTO.email2}"/>
    </c:if>
    <c:set var="uploadFileName" value="${memberDTO.uploadFileName}"/>
    <c:set var="storeFileName" value="${memberDTO.storeFileName}"/>
    <c:set var="joindate" value="${memberDTO.joindate}"/>
    <c:if test="${not empty memberDTO.kakao_idx}">
        <c:set var="isSNSUser" value="카카오 (ID: ${memberDTO.kakao_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.google_idx}">
        <c:set var="isSNSUser" value="구글 (ID: ${memberDTO.google_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.naver_idx}">
        <c:set var="isSNSUser" value="네이버 (ID: ${memberDTO.naver_idx})"/>
    </c:if>
    <c:if test="${not empty memberDTO.email1}">
        <c:set var="isSNSUser" value="X"/>
    </c:if>
</c:if>

<form action="/admin/member/user/edit.action" name="editForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="mode" value="${mode}">
<input type="hidden" name="isSNSUser" value="${isSNSUser}">
<div id="main-box">
    <div id="top-menu">
        <span class="title">사용자 정보 ${mode}</span>
        <a class="btn-save" onclick="saveUserEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="history.back()"><span>취소</span></a>
    </div>
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${mode == '수정'}">
                <tr class="short-line">
                    <td>회원번호</td>
                    <td><input readonly="readonly" name="m_idx" value="${m_idx}"></td>
                </tr>
                </c:if>
                <c:if test="${mode == '수정' and isSNSUser == 'X' or isSNSUser == ''}">
                <tr class="short-line">
                    <td>프로필 사진</td> 
                    <td>
                        <span>
                            <c:if test="${storeFileName == null}">등록된 이미지가 없습니다</c:if>
                            <c:if test="${storeFileName != null}">${uploadFileName}</c:if>
                        </span>
                        <div class="profile">
                            <!-- Original -->
                            <input type="hidden" name="oUploadFileName" value="${uploadFileName}">
                            <input type="hidden" name="oStoreFileName" value="${storeFileName}">
                            <!-- New -->
                            <label class="image" for="file"><a class="btn-file-upload">업로드</a></label>
                            <div class="imagebox">
                                <c:if test="${storeFileName == null}"><img id="preview" src="/images/join/profile_no_image.png"></c:if>
                                <c:if test="${storeFileName != null}"><img id="preview" src="/files/member/${storeFileName}"></c:if>
                            </div>
                            <input id="file" name="file" type="file" accept=".gif, .jpg, .png, .bmp, .jpeg, .heic" onchange="imageFileSizeCheck('file')"/>
                            <%--<span class="info">3MB 이내 (gif,jpg,png,bmp,jpeg,heic)</span>--%>
                        </div>
                    </td>
                </tr>
                </c:if>
                <tr class="short-line">
                    <td>이름</td>
                    <td>
                        <input name="username" placeholder="이름을 입력해주세요" value="${username}" style="width: 300px;">
                        <span id="usernameCheckResult" style="color: #E74133; display: inline-block;"></span>
                    </td>
                </tr>
                <tr class="short-line">
                    <td>아이디</td>
                    <td>
                        <input type="hidden" name="o_userid" id="o_userid" value="${userid}">
                        <input name="userid" id="userid" placeholder="아이디를 입력해주세요" value="${userid}" onkeyup="inputID()" style="width: 300px; margin-top: 6px;">
                        <span id="useridCheckResult" style="color: #E74133; display: inline-block;"></span>
                        <a onclick="dupliCheckID()" style="padding: 7px 13px; border: 1px solid #999999; float:right;"><span>중복확인</span></a>
                        <input name="chk_id" id="chk_id" type="hidden" value="false">
                        <c:if test="${mode == '수정'}">
                        <a onclick="returnID()" style="padding: 7px 13px; border: 1px solid #999999; float:right; margin-right: 10px;"><span>기존 아이디 사용</span></a>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${isSNSUser == '' or isSNSUser == 'X'}">
                <tr class="short-line">
                    <td>이메일</td>
                    <td>
                        <input type="hidden" name="o_email1" id="o_email1" value="${email1}">
                        <input type="hidden" name="o_email2" id="o_email2" value="${email2}">
                        <input name="email1" id="email1" placeholder="이메일 앞자리 (ex. abcde)" value="${email1}" style="width: 200px; margin-top: 6px;">
                        <span style="font-size:20px; color: #777777;">@</span>
                        <input name="email2" id="email2" placeholder="이메일 뒷자리 (ex. naver.com)" value="${email2}" style="width: 250px;">
                        <span id="emailCheckResult" style="color: #E74133; display: inline-block;"></span>
                        <a onclick="dupliCheckEmail()" style="padding: 7px 13px; border: 1px solid #999999; float:right;"><span>중복확인</span></a>
                        <input name="chk_email" id="chk_email" type="hidden" value="false">
                        <c:if test="${mode == '수정'}">
                            <a onclick="returnEmail()" style="padding: 7px 13px; border: 1px solid #999999; float:right; margin-right: 10px;"><span>기존 이메일 사용</span></a>
                        </c:if>
                    </td>
                </tr>
                </c:if>
                <c:if test="${mode == '추가'}">
                <tr class="short-line">
                    <td>비밀번호</td>
                    <td>
                        <input name="passwd" placeholder="비밀번호를 입력해주세요" value="${passwd}" style="width: 300px;">
                        <span id="passwdCheckResult" style="color: #E74133; display: inline-block;"></span>
                    </td>
                </tr>
                </c:if>
                <c:if test="${mode == '수정' and not empty memberDTO.email1}">
                <tr class="short-line">
                    <td>비밀번호</td>
                    <td><a style="padding: 7px 13px; border: 1px solid #999999;"><span>비밀번호 초기화</span></a></td>
                </tr>
                </c:if>
                <c:if test="${mode == '수정' and empty memberDTO.email1}">
                <tr class="short-line">
                    <td>SNS 회원</td>
                    <td><input readonly="readonly" name="" value="${isSNSUser}"></td>
                </tr>
                <tr class="short-line">
                    <td>가입일</td>
                    <td><input readonly="readonly" value="${joindate}"></td>
                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
</form>

<!--삭제 버튼-->
<c:if test="${mode == '수정'}">
    <a id="btn-delete" href="/admin/member/delete?idx=${m_idx}&role=USER"><img src="/ADMIN/images/remove.png"></a>
</c:if>
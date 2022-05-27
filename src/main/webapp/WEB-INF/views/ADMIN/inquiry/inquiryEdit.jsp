<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--수정 모드-->
<c:if test="${not empty inquiryDTO.i_idx}">
    <c:set var="mode" value="수정"/>
    <c:set var="i_idx" value="${inquiryDTO.i_idx}"/>
    <c:set var="m_idx" value="${inquiryDTO.m_idx}"/>
    <c:set var="category_id" value="${inquiryDTO.category_id}"/>
    <c:set var="title" value="${inquiryDTO.title}"/>
    <c:set var="content" value="${inquiryDTO.content}"/>
    <c:set var="upload_date" value="${inquiryDTO.upload_date}"/>
    <c:set var="last_updated_date" value="${inquiryDTO.last_updated_date}"/>
    <c:set var="status" value="${inquiryDTO.status}"/>
</c:if>
<%--[InquiryDTO(fileNames=[], i_idx=64, m_idx=117, category_id=건의사항, title=2345, content=2345,
            upload_date=05/18, last_updated_date=05/18, files=null, i_idx2=0, status=YET)--%>


<form action="" name="editForm" method="post" enctype="multipart/form-data">
<div id="main-box">
    <div id="top-menu">
        <span class="title">문의 ${i_idx} : ${title}</span>
        <a class="btn-save" onclick="saveEditForm(editForm)"><span>저장</span></a>
        <a class="btn-cancel" onclick="javascript:history.back()"><span>취소</span></a>
    </div>
    <input type="hidden" name="" value="${category_idx}">
    <div id="edit-table">
        <table>
            <tbody>
                <tr class="short-line">
                    <td>작성자</td>
                    <td><input readonly="readonly" name="" value="${m_idx}"></td>
                </tr>
                <tr class="short-line">
                    <td>상태</td>
                    <td><input readonly="readonly" name="" value="${status}"></td>
                </tr>
                <tr class="long-line">
                    <td>내용</td>
                    <td><textarea readonly="readonly" name="">${content}</textarea></td>
                </tr>
                <tr class="short-line">
                    <td>관련 이미지</td>
                    <td><input name="" value="${title}"></td>
                </tr>
                <tr class="short-line">
                    <td>작성일</td>
                    <td><input readonly="readonly" value="${upload_date}"></td>
                </tr>
                <tr class="long-line">
                    <td>댓글</td>
                    <td><textarea readonly="readonly" name="">${content}</textarea></td>
                </tr>
                <tr class="long-line">
                    <td>사용자의 대댓글</td>
                    <td><textarea readonly="readonly" name="">${content}</textarea></td>
                </tr>
                <tr class="long-line">
                    <td>관리자의 대댓글</td>
                    <td><textarea name="">${content}</textarea></td>
                </tr>
                <tr class="long-line">
                    <td>사용자의 대댓글2</td>
                    <td><textarea readonly="readonly" name="">${content}</textarea></td>
                </tr>
                <tr class="long-line">
                    <td>관리자의 대댓글2</td>
                    <td><textarea name="">${content}</textarea></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
</form>

<!--삭제 버튼-->
<c:if test="${mode == '수정'}">
    <a id="btn-delete" href="/admin/notice/delete?idx=${number_idx}"><img src="/static/ADMIN/images/remove.png"></a>
</c:if>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/script/jquery.tmpl.js"></script>
<script>
    let index = 0;
</script>

<script type="text/html" id="file-input-tmpl">
    <div>
        <input type="file" name="files" class="img_file" accept="image/*" required="required" data-idx="\${index}" hidden="hidden" onchange="readInputFile(this)">
    </div>
</script>
<script type="text/html" id="img-preview-tmpl">
    <li data-idx="\${index}">
        <a onclick="deleteImage(this)"><img src="/images/deleteUploadImg.png"></a>
        <div><img src="\${result}"></div>
    </li>
</script>

<div id="inquiryWrite">
    <%--@elvariable id="inquiryDTO" type="com.dingco.pedal.dto.InquiryDTO"--%>
    <form:form commandName="inquiryDTO" action="/inquiry" enctype="multipart/form-data" id="inquiry-form" name="inquiryForm" method="post">
        <div class="title">
            <input type="text" name="title" placeholder="제목을 입력해주세요" value="1:1문의-제목">
        </div>
        <select class="category" name="category_id">
            <option value="버그">버그</option>
            <option value="건의사항">건의사항</option>
            <option value="기타">기타</option>
        </select>
        <div class="content">
            <textarea name="content" placeholder="내용을 입력해주세요">버그가 이런 버그가 잇는뎁...</textarea>
        </div>
        <a class="uploadImage" href="javascript:addImage()">
            <img src="/images/imageupload.png">
            <span>이미지 파일은 최대 5개 업로드 가능합니다.</span>
            <div class="reset"></div>
        </a>
        <div id="file-input-list" style="display: none;"></div>

        <c:if test="${param.idx != null}">
            <input name="i_idx2" type="hidden" value="${param.idx}" readonly>
        </c:if>
        <%--<input type="button" value="작성 완료" id="formSubmit">--%>
    </form:form>
    <div class="imgPreview" id="img-preview-area">
        <ul>

        </ul>
    </div>
</div>

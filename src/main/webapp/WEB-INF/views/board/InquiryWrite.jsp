<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/script/jquery.tmpl.js"></script>
<script>
    let index = 0;
    let deleteImgIdx = [];
    let deleteImgName = [];
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
    <form:form modelAttribute="inquiryDTO" enctype="multipart/form-data" id="inquiry-form" name="inquiryForm" method="post">
        <div class="title">
            <form:input path="title" placeholder="제목을 입력해주세요"/>
        </div>
        <form:select path="category_id" class="category">
            <form:option value="건의사항">건의사항</form:option>
            <form:option value="버그">버그</form:option>
            <form:option value="기타">기타</form:option>
        </form:select>
        <div class="content">
            <form:textarea path="content" placeholder="내용을 입력해주세요"/>
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
        <c:if test="${inquiryDTO.i_idx > 0}">
            <input name="m_idx" type="hidden" value="${inquiryDTO.m_idx}" readonly>
            <input name="i_idx" type="hidden" value="${inquiryDTO.i_idx}" readonly>
            <input name="i_idx2" type="hidden" value="${inquiryDTO.i_idx2}" readonly>
            <input name="status" type="hidden" value="${inquiryDTO.status}" readonly>
        </c:if>
    </form:form>
    <div class="imgPreview" id="img-preview-area">
        <ul>
            <c:if test="${inquiryDTO.i_idx > 0}">
                <c:forEach var="files" items="${inquiryDTO.fileNames}" varStatus="status">
                    <li data-idx="${files.fileIdx}" data-filePath="${files.tableDir.directoryName}/${files.serverFileName}">
                        <a onclick="deleteImage(this, ${inquiryDTO.i_idx})"><img src="/images/deleteUploadImg.png"></a>
                        <div><img src="/files/${files.tableDir.directoryName}/${files.serverFileName}"></div>
                    </li>
                </c:forEach>
            </c:if>
        </ul>
    </div>
</div>
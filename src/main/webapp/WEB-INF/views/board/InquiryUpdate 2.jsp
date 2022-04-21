<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}/"></c:set>

<!DOCTYPE html>
<html>
<head>
    <script src="/script/jquery-3.6.0.js"></script>
    <script src="/script/jquery.tmpl.js"></script>
    <%--    <script src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>--%>
    <script type="text/html" id="file-input-tmpl">
        <div>
            <input type="file" name="files" class="img_file img_id_\${index}" accept="img/*" required="required" <%-- hidden="hidden"--%>>
        </div>
    </script>
    <script type="text/html" id="img-preview-tmpl">
        <div>
            <a class=img_id_\${index}>
                <img src=\${result} data-file=\${f_name} width="60" height="60"/>
            </a>
            <button class="minus">-</button>
        </div>
    </script>
</head>
<body>
<h1>1:1 문의사항 등록 화면</h1>
${inquiryDTO}
<%--@elvariable id="inquiryDTO" type="com.dingco.pedal.dto.InquiryDTO"--%>
<form:form modelAttribute="inquiryDTO" commandName="inquiryDTO" action="/inquiry/${inquiryDTO.i_idx}" enctype="multipart/form-data" id="inquiry-form" method="post">
    카테고리:
    <form:select path="category_id">
        <form:option value="건의사항">건의사항</form:option>
        <form:option value="버그">버그</form:option>
        <form:option value="기타">기타</form:option>
    </form:select><br>
    제목: <form:input path="title"/><br>
    내용: <form:input path="content"/><br>
    hidden: <input name="m_idx" type="text" value="${inquiryDTO.m_idx}" readonly="readonly"><br>
    hidden: <input name="i_idx" type="text" value="${inquiryDTO.i_idx}" readonly="readonly"><br>
    hidden: <input name="i_idx2" type="text" value="${inquiryDTO.i_idx2}" readonly="readonly"><br>
    hidden: <input name="status" type="text" value="${inquiryDTO.status}" readonly="readonly"><br>
<%--    hidden: ${inquiryDTO.fileNames}--%>
    <button id="img-add-btn" type="button">사진업로드</button>
    <span>이미지 파일은 최대 5개 업로드 가능합니다.</span> <br>
    <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->
    <div id="file-input-list"></div>
    <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->

    <input type="button" value="작성 완료" id="formSubmit">
</form:form>
<h3>이미지 미리보기</h3>
<div id="img-preview-area">
    <c:forEach var="files" items="${inquiryDTO.fileNames}" varStatus="status">
        <div>
            <a class=img_id_${files.fileIdx}>
                <img src="/files/${files.tableDir.directoryName}/${files.serverFileName}" style="max-width: 10%" height="auto">${fileCounts}
            </a>
            <button class="minus" data-fileIdx="${files.fileIdx}" data-tableDir="${files.tableDir.directoryName}" data-serverFileName="${files.serverFileName}">-</button>
        </div>
    </c:forEach>
</div>
<script>
    let index = 0
    $(document).on("click", ".minus",  function (){
        var isOk = true;
        if(confirm("정말로 삭제하시겠습니까. 한번 삭제하면 복원할 수 없습니다.")) {

            var className = $(this).siblings().first().attr("class");
            var fileIdx = $(this).attr("data-fileIdx");
            var tableDir = $(this).attr("data-tableDir");
            var serverFileName = $(this).attr("data-serverFileName");
            var filePath = tableDir + "\\" + serverFileName;
            if (fileIdx && tableDir && serverFileName) {
                $.ajax({
                    type: 'DELETE',
                    url: `/image/\${fileIdx}`,
                    contentType: 'application/json; charset=utf-8',
                    data: filePath,
                    dataType: 'json',
                    async: true,
                    success: function (result) {
                        console.log(result)
                        if(!result) {
                            isOk = false;
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR.status + ' ' + jqXHR.responseText);
                    }
                });
            }
            if (isOk) {
                $(this).parent().remove()
                $(`input.\${className}`).parent().remove()
            }
        }
    })
    $("#img-add-btn").on("click", function (){
        if (isMaximum()){
            alert("이미지 파일은 최대 5개 업로드 가능합니다.")
            return;
        }

        var file_input = $("#file-input-list").children().last().children("input");
        if(file_input.val() !== undefined && !file_input.val()){
            file_input.remove()
        }
        console.log("사진업로드")
        $("#file-input-tmpl").tmpl(index).appendTo( "#file-input-list" );
        $("#file-input-list").children().last().children("input").click()
    })
    $(document).on('change','.img_file', readInputFile);
    $("#img-preview-area").on('change', isMaximum);

    function isMaximum(){
        let count = $("#img-preview-area").children().length
        return count >= 5;
    }

    function readInputFile(e){
        console.log("readInputFIle")

        var files = e.target.files;
        var fileArr = Array.prototype.slice.call(files);

        fileArr.forEach(function(f){
            if(!f.type.match("image/.*")){
                alert("이미지 확장자만 업로드 가능합니다.");
                $(`.img_id_\${index}`).remove()
                return;
            };
            var reader = new FileReader();
            reader.onload = function(e){
                var data = {
                    "index":index,
                    "result":e.target.result,
                    "f_name":f.name
                }
                $("#img-preview-tmpl").tmpl(data).appendTo( "#img-preview-area" );
                index++;
            };
            reader.readAsDataURL(f);
        })
    }

    $("#formSubmit").on("click", function(){
        var file_input = $("#file-input-list").children().last().children("input");
        if(file_input.val() !== undefined && !file_input.val()){
            file_input.remove()
        }
        $("#inquiry-form").submit();
    })
</script>
</body>
</html>
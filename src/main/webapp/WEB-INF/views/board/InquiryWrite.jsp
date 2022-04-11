<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <a class="img_id_\${index}">
                <img src="\${result}" data-file="\${f_name}" width="60" height="60"/>
            </a>
            <button class="minus">-</button>
        </div>
    </script>
</head>
<body>

<h1>1:1 문의사항 등록 화면</h1>
<%--@elvariable id="inquiryDTO" type="com.dingco.pedal.dto.InquiryDTO"--%>
<form:form commandName="inquiryDTO" action="/inquiry" enctype="multipart/form-data" id="inquiry-form" method="post">
    카테고리:
    <select name="category_id">
        <option value="버그">버그</option>
        <option value="건의사항">건의사항</option>
        <option value="기타">기타</option>
    </select><br>
    제목: <input name="title" type="text" value="테스트용1"><br>
    내용: <input name="content" type="text" value="버그가 이런 버그가 잇는뎁..."><br>
    <button id="img-add-btn" type="button">사진업로드</button>
    <span>이미지 파일은 최대 5개 업로드 가능합니다.</span> <br>
    <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->
    <div id="file-input-list"></div>
    <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->
    <c:if test="${param.idx != null}">
        <input name="i_idx2" type="text" value="${param.idx}" readonly>
    </c:if>
    <input type="button" value="작성 완료" id="formSubmit">
</form:form>
<h3>이미지 미리보기</h3>
<div id="img-preview-area">
</div>
<script>
    let index = 0
    $(document).on("click", ".minus",  function (){
        var className = $(this).siblings().first().attr("class");
        $(this).parent().remove()
        $(`input.\${className}`).parent().remove()
        isMaximum()
    })
    $("#img-add-btn").on("click", function (){
        var file_input = $("#file-input-list").children().last().children("input");
        if(file_input.val() !== undefined && !file_input.val()){
            file_input.remove()
        }
        console.log("사진업로드")
        $("#file-input-tmpl").tmpl(index).appendTo( "#file-input-list" );
        $("#file-input-list").children().last().children("input").click()
    })
    $(document).on('change','.img_file', readInputFile);

    function isMaximum(){
        let count = $("#file-input-list").children().length
        console.log(count)
        if (count >= 5) {
            $("#img-add-btn").attr("hidden", "hidden");
        } else  {
            $("#img-add-btn").removeAttr("hidden");
        }
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
        isMaximum();
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
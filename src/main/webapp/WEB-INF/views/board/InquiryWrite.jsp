<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}/"></c:set>
<script src="/script/jquery.tmpl.js"></script>
<script type="text/html" id="file-input-tmpl">
    <div>
        <input type="file" name="files" class="img_file" accept="img/*" required="required" data-idx="\${idx}"<%-- hidden="hidden"--%>>
    </div>
</script>
<script type="text/html" id="img-preview-tmpl">
    <%--<div>
        <a class="img_id_\${index}">
            <img src="\${result}" data-file="\${f_name}" width="60" height="60"/>
        </a>
        <button class="minus">-</button>
    </div>--%>
    <li data-idx="\${idx}">
        <a onclick="deleteImage(this)"><img src="/images/deleteUploadImg.png"></a>
        <div><img src="\${result}" data-file="\${f_name}"></div>
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
        <a class="uploadImage" id="img-add-btn">
            <img src="/images/imageupload.png">
            <span>이미지 파일은 최대 5개 업로드 가능합니다.</span>
            <div class="reset"></div>
        </a>
        <%--<button id="img-add-btn" type="button">사진업로드</button>--%>
        <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->
        <div id="file-input-list" style="display: none;"></div>
        <!-- 이부분은 나중에 hidden 처리 될 예정입니다. -->
        <c:if test="${param.idx != null}">
            <input name="i_idx2" type="text" value="${param.idx}" readonly>
        </c:if>
        <%--<input type="button" value="작성 완료" id="formSubmit">--%>
    </form:form>
    <div class="imgPreview" id="img-preview-area">
        <ul>

        </ul>
    </div>
</div>
<script>
    let index = 0
    function deleteImage(btn) {
        console.log($(btn).siblings().first());
        // 삭제할 미리보기 이미지의 class 명 가져오기
        var className = $(btn).siblings().first().attr("class");
        // 선택한 미리보기 이미지 삭제
        // $(btn).parent().remove();
        // $(`input.\${className}`).parent().remove()
        // isMaximum()
    }
    $(document).on("click", ".minus",  function (){
        var className = $(this).siblings().first().attr("class");
        var tmpImg = $(this).parent();
        var idx = tmpImg.attr("data-idx");
        tmpImg.remove();
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
                $("#img-preview-tmpl").tmpl(data).appendTo( "#img-preview-area ul" );
                index++;
            };
            reader.readAsDataURL(f);
        })
        isMaximum();
    }

    /*$("#formSubmit").on("click", function(){
        var file_input = $("#file-input-list").children().last().children("input");
        if(file_input.val() !== undefined && !file_input.val()){
            file_input.remove()
        }
        $("#inquiry-form").submit();
    })*/
</script>

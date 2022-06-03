<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<%--<script type="text/javascript">--%>
<%--    $(document).ready(function(){--%>

<%--        $("#deletePost").on("click", function (){--%>
<%--            var data_nidx = $(this).attr("data-nidx");--%>
<%--            console.log({number_idx:data_nidx});--%>
<%--            if(confirm("정말로 삭제하시겠습니까?")){--%>

<%--                $.ajax({--%>
<%--                    type: 'DELETE',--%>
<%--                    url: `/notice/${faqDTO.number_idx}`,--%>
<%--                    datatype: "json",--%>
<%--                    success: function (result) {--%>
<%--                        confirm("삭제되었습니다.")--%>
<%--                        console.log("success")--%>
<%--                        console.log(result)--%>
<%--                        if(result) {--%>
<%--                            location.href="/notice";--%>
<%--                        }--%>
<%--                    },--%>
<%--                    error: function (jqXHR, textStatus, errorThrown) {--%>
<%--                        console.log(jqXHR.status + ' ' + jqXHR.responseText);--%>
<%--                    }--%>
<%--                });--%>
<%--            }--%>

<%--        });--%>



<%--        $("#updatePost").on("click",function (){--%>
<%--            var title = $("#title").val();--%>
<%--            var category_idx = $("#category_idx").val();--%>
<%--            var content = $("#content").val();--%>
<%--            var data_nidx = $(this).attr("data-nidx");--%>
<%--            console.log(title,category_idx,content, data_nidx)--%>

<%--            if (title.length == 0) {--%>
<%--                alert("제목을 입력하시오")--%>
<%--                $("#title").focus()--%>
<%--                event.preventDefault()--%>
<%--            } else if (content.length == 0) {--%>
<%--                alert("내용을 입력하시오")--%>
<%--                $("#content").focus()--%>
<%--                event.preventDefault()--%>
<%--            } else if (title.length != 0 && content.length != 0) {--%>
<%--                if(confirm("정말 수정하시겠습니까?")){--%>
<%--                    $.ajax({--%>
<%--                        type: 'PUT',--%>
<%--                        url: `/notice/${faqDTO.number_idx}`,--%>
<%--                        datatype: "json",--%>
<%--                        data: {--%>
<%--                            title: title,--%>
<%--                            category_idx: category_idx,--%>
<%--                            content: content,--%>
<%--                            number_idx: data_nidx--%>
<%--                        },--%>
<%--                        success: function (result) {--%>
<%--                            confirm("수정되었습니다.")--%>
<%--                            console.log("success")--%>
<%--                            console.log(result)--%>
<%--                            if(result) {--%>
<%--                                location.href="/notice";--%>
<%--                            }--%>
<%--                        },--%>
<%--                        error: function (jqXHR, textStatus, errorThrown) {--%>
<%--                            console.log(jqXHR.status + ' ' + jqXHR.responseText);--%>
<%--                        }--%>
<%--                    });--%>
<%--                }--%>
<%--            }--%>
<%--        });--%>

<%--    });--%>

<%--</script>--%>


<div id="noticeRetrieve">
    <div class="title"><span>${faqDTO.title}</span></div>
    <div class="info">
        <span>
            ${faqDTO.writeday}
        </span>
    </div>
    <div class="content">
        <span>${faqDTO.content}</span>
    </div>
    <div class="imgPreview">
        <ul>
            <c:forEach var="files" items="${dto.fileNames}">
                <li><div><img src="/files/${files.tableDir.directoryName}/${files.serverFileName}"></div></li>
            </c:forEach>
        </ul>
    </div>
    <div class="blueLine"></div>
</div>


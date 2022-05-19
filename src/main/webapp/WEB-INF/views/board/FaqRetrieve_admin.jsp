<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

        $("#deletePost").on("click", function (){
            var data_nidx = $(this).attr("data-nidx");
            console.log({number_idx:data_nidx});
            if(confirm("정말로 삭제하시겠습니까?")){

                $.ajax({
                    type: 'DELETE',
                    url: `/faq/${faqDTO.number_idx}`,
                    datatype: "json",
                    success: function (result) {
                        confirm("삭제되었습니다.")
                        console.log("success")
                        console.log(result)
                        if(result) {
                            location.href="/faq";
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR.status + ' ' + jqXHR.responseText);
                    }
                });
            }

        });



        $("#updatePost").on("click",function (){
            var title = $("#title").val();
            var category_idx = $("#category_idx").val();
            var content = $("#content").val();
            var data_nidx = $(this).attr("data-nidx");
            console.log(title,category_idx,content, data_nidx)

            if (title.length == 0) {
                alert("제목을 입력하시오")
                $("#title").focus()
                event.preventDefault()
            } else if (content.length == 0) {
                alert("내용을 입력하시오")
                $("#content").focus()
                event.preventDefault()
            } else if (title.length != 0 && content.length != 0) {
                if(confirm("정말 수정하시겠습니까?")){
                    $.ajax({
                        type: 'PUT',
                        url: `/faq/${faqDTO.number_idx}`,
                        datatype: "json",
                        data: {
                            title: title,
                            category_idx: category_idx,
                            content: content,
                            number_idx: data_nidx
                        },
                        success: function (result) {
                            confirm("수정되었습니다.")
                            console.log("success")
                            console.log(result)
                            if(result) {
                                location.href="/faq";
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(jqXHR.status + ' ' + jqXHR.responseText);
                        }
                    });
                }
            }
        });

    });

</script>

<%--<h3>상세글 화면</h3>--%>
<%--<form name="retrieve1">--%>
<%--    제목:<input type="text" name="title" id="title" value="${faqDTO.title}"><br>--%>
<%--</form>--%>
<%--<form:form  name="retrieve2" modelAttribute="category">--%>
<%--    카테고리:--%>
<%--    <form:select id="catSel" path="category">--%>
<%--            <form:option value="${category_idx}">${category_name}</form:option>--%>
<%--    </form:select><br>--%>
<%--</form:form>--%>
<%--<form name="retrieve3">--%>
<%--    작성자:<input type="text" name="username" id="username" value="${memberDTO.username}" readonly><br>--%>
<%--    내용:<textarea rows="10" cols="10" name="content" id="content" value="${faqDTO.content}">${faqDTO.content}</textarea><br>--%>
<%--    <input type="button" id="updatePost"  data-nidx="${faqDTO.number_idx}"  value="수정">--%>
<%--    <input type="button" id="deletePost"  data-nidx="${faqDTO.number_idx}" value="삭제">--%>
<%--    <input type="hidden" name="m_idx" id="m_idx" value="${memberDTO.m_idx}">--%>
<%--</form>--%>


<h3>상세글 화면</h3>
<form name="retrieve">
    제목:<input type="text" name="title" id="title" value="${faqDTO.title}"><br>
    카테고리:
        <c:forEach var="cat" items="${category}" varStatus="status">
            <input type="text" id="${cat.category_idx}" value="${cat.category_name}" readonly>
            <input type="hidden"  id="category_idx" value="${cat.category_idx}"><br>
        </c:forEach>

    작성자:<input type="text" name="username" id="username" value="${memberDTO.username}" readonly><br>
    내용:<textarea rows="10" cols="10" name="content" id="content" value="${faqDTO.content}">${faqDTO.content}</textarea><br>
    <input type="button" id="updatePost"  data-nidx="${faqDTO.number_idx}"  value="수정">
    <input type="button" id="deletePost"  data-nidx="${faqDTO.number_idx}" value="삭제">
    <input type="hidden" name="m_idx" id="m_idx" value="${memberDTO.m_idx}">
</form>


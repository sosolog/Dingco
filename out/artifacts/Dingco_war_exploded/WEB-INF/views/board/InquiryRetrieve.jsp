<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="dto" value="${inquiryDTO}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="/script/jquery-3.6.0.js"></script>
    <script src="/script/jquery.tmpl.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#deletePost").on("click", function (){
                if(confirm("정말로 삭제하시겠습니까? 한번 삭제하면 다시 복원할 수 없습니다.")){

                    $.ajax({
                        type: 'DELETE',
                        url: `/inquiry/${dto.i_idx}`,
                        success: function (result) {
                            console.log(result)
                            if(result) {
                                location.href="/inquiry";
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(jqXHR.status + ' ' + jqXHR.responseText);
                        }
                    });
                }
            });

            getComments();
        });
    </script>
    <script type="text/html" id="comment-list-tmpl">
        <div>
            <table>
                <tr>
                    <td>작성자</td>
                    <td>\${m_idx}</td>
                    <td>작성일</td>
                    <td>\${post_date}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td colspan="3"><span id="comment_\${c_idx}">\${comment}</span></td>
                </tr>
            </table>
            <div id="btn-\${c_idx}-default">
                <button class="upd-comment" data-cidx="\${c_idx}" data-comment="\${comment}">수정</button>
                <button class="del-comment" data-cidx="\${c_idx}">삭제</button>
                <button class="re-comment" data-cidx="\${c_idx}">대댓글 작성</button>
            </div>
            <div id="btn-\${c_idx}-update" style="display: none">
                <button class="confirm-upd" data-cidx="\${c_idx}">확인</button>
            </div>
            <div id="form-\${c_idx}-recomment" style="display:none;">
                <input type="text" id="recomment_\${c_idx}">
                <button class="confirm-recomment" data-cidx="\${c_idx}">확인</button>
            </div>
            {{if count_sub > 0}}
            \${count_sub}개의 대댓글 존재
            <button class="show-re-comment" data-cidx="\${c_idx}" data-status="0">대댓글 보기</button>
            <div id="recomment-list-\${c_idx}"></div>
            {{/if}}
        </div>
    </script>
    <script type="text/html" id="comment-recomment-list-tmpl">
        <div>
            <table>
                <tr>
                    <td>L </td>
                    <td>작성자</td>
                    <td>\${m_idx}</td>
                    <td>작성일</td>
                    <td>\${post_date}</td>
                </tr>
                <tr>
                    <td> </td>
                    <td>내용</td>
                    <td colspan="3"><span id="comment_\${c_idx}">\${comment}</span></td>
                </tr>
            </table>
            <div id="btn-\${c_idx}-default">
                <button class="upd-comment" data-cidx="\${c_idx}" data-comment="\${comment}">수정</button>
                <button class="del-comment" data-cidx="\${c_idx}">삭제</button>
            </div>
            <div id="btn-\${c_idx}-update" style="display: none">
            <button class="confirm-upd" data-cidx="\${c_idx}">확인</button>
        </div>
        </div>
    </script>
</head>
<body>
<h2>게시판 세부사항 보기</h2>
<table border="1">
    <tr>
        <th>문의번호</th>
        <th>작성자번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>내용</th>
        <th>업로드 날짜</th>
        <th>수정 날짜</th>
        <th>상위 문의 고유번호</th>
        <th>문의 상태</th>
    </tr>


    <tr>
        <td>${dto.i_idx}</td>
        <td>${dto.m_idx}</td>
        <td>${dto.category_id}</td>
        <td>${dto.title}</td>
        <td>${dto.content}</td>
        <td>${dto.upload_date}</td>
        <td>${dto.last_updated_date}</td>
        <td>${dto.i_idx2}</td>
        <td>${dto.status.message}, ${dto.status == 'YET'}</td>
    </tr>
</table>
<div>
<c:forEach var="files" items="${dto.fileNames}">
    <img src="/files/${files.tableDir.directoryName}/${files.serverFileName}" style="max-width: 10%" height="auto">

</c:forEach>
</div>
<c:if test="${dto.i_idx2 != 0}">
    <h3>관련 문의 보기_기존</h3>
    <a href="/inquiry/${dto.i_idx2}">상위문의 확인하기</a>
</c:if>


<br>
<hr>
<c:if test="${dto.status == 'YET'}">
    <c:if test="${memberDTO.authorities == 'USER'}">
        <a href="${dto.i_idx}/update">글 수정</a>
    </c:if>
    <button id="deletePost">글 삭제</button>
</c:if>
<c:if test="${memberDTO.authorities == 'ADMIN'}">
    <form:form modelAttribute="inquiryDTO" action="">
        <form:radiobutton path="status" value="YET" label="답변 대기 중"/>
        <form:radiobutton path="status" value="IN_PROCESS" label="답변 완료"/>
        <form:radiobutton path="status" value="DONE" label="문의 종료"/>
        <button type="button" id="inq_status_update">문의상태 변경하기</button>
    </form:form>
</c:if>
<c:if test="${memberDTO.authorities == 'USER'}">
    <c:if test="${dto.status == 'IN_PROCESS'}">
        <button type="button" id="inq_terminate">문의 종료하기</button>
    </c:if>
    <c:if test="${dto.status != 'YET'}">
        <button type="button" id="re_inq">재문의하기</button>
    </c:if>
</c:if>

<hr>
<h3>댓글</h3>
<div>
    <input type="text" id="comment">
    <input type="button" value="댓글 입력" id="new-comment">
</div>
<div id="comment-list"></div>
<script type="text/javascript">
    $("#inq_terminate").on("click", function(){
        if(confirm("정말로 문의를 종료하시겠습니까?")){
            chageInqStatus('DONE');
        }
    });
    $("#re_inq").on("click", function(){
        if(confirm("문의를 종료하고, 재문의를 진행하시겠습니까?")){
            chageInqStatus('RE_INQUIRY', function (result){
                location.href = "/inquiry/write?idx="+${dto.i_idx};
            });
        }
    });
    $("#inq_status_update").on("click", function(){
        var v_status = $("input[type='radio'][name='status']:checked").val();
        chageInqStatus(v_status, );
    });
    function chageInqStatus(v_status, success_fn=function (result) {
        console.log(result)
        location.reload();
    }){
        $.ajax({
            type: 'POST',
            url: `/inquiry/${dto.i_idx}/status`,
            data: {
                status: v_status
            },
            success: success_fn,
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    }
    $("#new-comment").on("click", function (){
        var comment = $("#comment").val().trim();
        if(comment.length >= 2){
            let comment_data = {
                comment: comment
            }
            writeComments(comment_data);
        } else {
            alert("2자 이상 입력해주세요!")
        }
        $("#comment").val("")
    })

    $(document).on("click", ".re-comment", function (){
        var c_idx = $(this).attr("data-cidx");
        $(`#form-\${c_idx}-recomment`).show();
    })
    $(document).on("click", ".confirm-recomment", function (){
        var c_idx = $(this).attr("data-cidx");
        var comment = $(`#recomment_\${c_idx}`).val();
        console.log(comment)
        if(comment.length >= 2){
            let comment_data = {
                comment: comment,
                c_idx2: c_idx
            }
            writeComments(comment_data);
        } else {
            alert("2자 이상 입력해주세요!")
        }
        $("#comment").val("")
    })

    function writeComments(comment_data){
        $.ajax({
            type: 'POST',
            url: `/inquiry/${dto.i_idx}/comment/${memberDTO.m_idx}`,
            data: comment_data,
            success: function (result) {
                console.log(result)
                if(result) {
                    getComments();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    }
    $(document).on("click", ".show-re-comment",function (){
        var c_idx = $(this).attr("data-cidx");
        var status = $(this).attr("data-status");
        // $("[id^='recomment-list-']").each(function(){
        //     $(this).empty();
        // })
        if(status == 0) {
            $(this).attr("data-status", "1");
            $(this).html("대댓글창 닫기")
            $.ajax({
                type: 'get',
                url: `/inquiry/${dto.i_idx}/comment/\${c_idx}`,
                success: function (result) {
                    console.log(result);
                    // $(`#recomment-list-\${c_idx}`).empty();
                    $("#comment-recomment-list-tmpl").tmpl(result).appendTo( `#recomment-list-\${c_idx}` );
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.status + ' ' + jqXHR.responseText);
                }
            })
        } else {
            $(this).html("대댓글 보기")
            $(this).attr("data-status", "0");
            $(`#recomment-list-\${c_idx}`).empty();
        }

    })
    $(document).on("click", ".upd-comment",function (){
        console.log("upd-comment")
        var c_idx = $(this).attr("data-cidx");
        var comment = $(this).attr("data-comment");
        $(`#btn-\${c_idx}-default`).hide();
        $(`#btn-\${c_idx}-update`).show();
        $("#comment_"+c_idx).html(`<input type='text' id='comment_input_\${c_idx}' value='\${comment}'>`)

    })

    $(document).on("click", ".confirm-upd",function (){
        console.log("confirm-upd")
        var c_idx_data = $(this).attr("data-cidx");
        var comment_data = $(`#comment_input_\${c_idx_data}`).val();
        $(`#btn-\${c_idx_data}-update`).hide();
        $.ajax({
            type: 'PUT',
            url: `/inquiry/${dto.i_idx}/comment/${memberDTO.m_idx}`,
            data: {
                c_idx: c_idx_data,
                comment: comment_data
            },
            success: function (result) {
                console.log(result)
                getComments();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    })

    $(document).on("click", ".del-comment",function (){
        console.log("confirm-upd")
        var c_idx_data = $(this).attr("data-cidx");
        $.ajax({
            type: 'DELETE',
            url: `/inquiry/${dto.i_idx}/comment/\${c_idx_data}`,
            success: function (result) {
                console.log(result)
                getComments();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    })
    function getComments(){
        $.ajax({
            type: 'get',
            url: `/inquiry/${dto.i_idx}/comment`,
            success: function (result) {
                console.log(result);
                $('#comment-list').empty();
                $("#comment-list-tmpl").tmpl(result).appendTo( "#comment-list" );
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    }
</script>
</body>
</html>




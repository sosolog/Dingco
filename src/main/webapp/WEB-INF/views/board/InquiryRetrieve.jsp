<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
<%-- Start : 댓글 --%>
<script type="text/html" id="comment-list-tmpl">
    <div id="commentOne\${c_idx}" class="commentOne">
        <div class="top">
            <span>관리자 \${m_idx}</span>
            <span>(\${post_date})</span>
            <a onclick="openCommentOption(\${c_idx})"><img src="/images/commentOption.png"></a>
        </div>
        <div class="commentContent">
            <span id="comment_\${c_idx}">\${comment}</span>
        </div>

        <div id="btn-\${c_idx}-default">
            <button class="upd-comment" data-cidx="\${c_idx}" data-comment="\${comment}">수정</button>
            <button class="del-comment" data-cidx="\${c_idx}">삭제</button>
            <button class="re-comment" data-cidx="\${c_idx}">대댓글 작성</button>
        </div>
        <div id="btn-\${c_idx}-update" class="writeComment" style="display: none">
            <a class="confirm-upd" data-cidx="\${c_idx}"><span>수정</span></a>
            <div class="reset"></div>
        </div>
        {{if count_sub > 0}}
        <a class="show-re-comment" data-cidx="\${c_idx}" data-status="0"><span>답글 \${count_sub}개</span></a>
            <%--<button class="show-re-comment" data-cidx="\${c_idx}" data-status="0">대댓글 보기</button>--%>
            <div id="recomment-list-\${c_idx}"></div>
        {{/if}}
        <div id="form-\${c_idx}-recomment" class="writeComment" style="display:none;">
            <textarea id="recomment_\${c_idx}"></textarea>
            <a class="confirm-recomment" data-cidx="\${c_idx}"><span>등록</span></a>
            <div class="reset"></div>
        </div>
    </div>
</script>
<%-- End : 댓글 --%>
<%-- Start : 대댓글 --%>
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
<%-- End : 대댓글 --%>

<%----------------------- HTML -----------------------%>
<header>
    <c:set var="dto" value="${inquiryDTO}"></c:set>
    <c:if test="${fn:contains(url, '/inquiry/')}">
        <div class="ico_lt">
            <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
        </div>
        <div class="title">
            <a class="headerLogo"><span>1:1문의</span></a>
        </div>
        <div class="ico_rt">
            <c:if test="${dto.status == 'YET'}"><a class="rewrite" href="${dto.i_idx}/update"><span>수정</span></a></c:if>
        </div>
    </c:if>
</header>
<div id="inquiryRetrieve">
    <div class="title"><span>${dto.title}</span></div>
    <div class="info">
        <span>
            ${dto.upload_date},
            <c:if test="${dto.i_idx2 != 0}">재문의</c:if>
            <c:if test="${dto.i_idx2 == 0}">최초문의</c:if>
        </span>
        <c:if test="${dto.i_idx2 != 0}"><a href="/inquiry/${dto.i_idx2}"><span>>> 최초 문의 보러가기</span></a></c:if>
    </div>
    <div class="content">
        <span>${dto.content}</span>
    </div>
    <div class="imgPreview">
        <ul>
            <c:forEach var="files" items="${dto.fileNames}">
                <li><div><img src="/files/${files.tableDir.directoryName}/${files.serverFileName}"></div></li>
            </c:forEach>
        </ul>
    </div>
    <c:if test="${dto.status == 'YET'}">
        <a class="deletePost" id="deletePost"><span>글 삭제</span></a>
    </c:if>
    <div class="blueLine"></div>
    <c:if test="${memberDTO.authorities == 'ADMIN'}">
        <form:form modelAttribute="inquiryDTO" action="">
            <form:radiobutton path="status" value="YET" label="답변 대기 중"/>
            <form:radiobutton path="status" value="IN_PROCESS" label="답변 완료"/>
            <form:radiobutton path="status" value="DONE" label="문의 종료"/>
            <button type="button" id="inq_status_update">문의상태 변경하기</button>
        </form:form>
        <div class="whitegrayLine"></div>
    </c:if>

    <c:if test="${memberDTO.authorities == 'USER'}">
        <c:if test="${dto.status == 'IN_PROCESS'}">
            <button type="button" id="inq_terminate">문의 종료하기</button>
        </c:if>
        <c:if test="${dto.status != 'YET'}">
            <button type="button" id="re_inq">재문의하기</button>
        </c:if>
        <div class="grayLine"></div>
    </c:if>

    <div class="commentBox">
        <div id="comment-list"></div>
    </div>
    <div class="grayLine"></div>
    <div class="writeComment">
        <textarea id="comment" placeholder="댓글을 입력해주세요"></textarea>
        <a id="new-comment"><span>등록</span></a>
    </div>

</div>


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
        $("#comment_"+c_idx).html(`<div class="writeComment"><textarea type='text' id='comment_input_\${c_idx}' class='comment_input_update' placeholder='내용을 입력해주세요'>\${comment}</textarea></div>`)

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





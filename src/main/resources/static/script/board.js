
function openFAQ(idx) {
    $('#openFAQ'+idx).css("display", "");
    $('#flipFAQ'+idx+' .btn_openFAQ').css("display", "none");
    $('#flipFAQ'+idx+' .btn_flipFAQ').css("display", "block");
}

function flipFAQ(idx) {
    $('#openFAQ'+idx).css("display", "none");
    $('#flipFAQ'+idx+' .btn_openFAQ').css("display", "block");
    $('#flipFAQ'+idx+' .btn_flipFAQ').css("display", "none");
}


// START : InquiryRetrieve 용
// 글 삭제
function deletePost() {
    if(confirm("정말로 삭제하시겠습니까? 한번 삭제하면 다시 복원할 수 없습니다.")){
        $.ajax({
            type: 'DELETE',
            url: `/inquiry/${i_idx}`,
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
}

// USER 용 - (답변 완료 후) 문의 종료하기
function terminateInquiry() {
    if(confirm("정말로 문의를 종료하시겠습니까?")){
        changeInqStatus('DONE');
    }
}

// USER 용 - (답변 완료 후) 재문의하기
function processReInquiry() {
    if(confirm("문의를 종료하고, 재문의를 진행하시겠습니까?")){
        changeInqStatus('RE_INQUIRY', function (result){
            location.href = `/inquiry/write?idx=${i_idx}`;
        });
    }
}

// ADMIN 용 - 문의상태 변경하기
function changeInquiryStatus() {
    var v_status = $("input[type='radio'][name='status']:checked").val();
    changeInqStatus(v_status);
}

// 공통 : 문의상태 변경 작업
function changeInqStatus(v_status, success_fn=function (result) {
    console.log(result)
    location.reload();
}){
    $.ajax({
        type: 'POST',
        url: `/inquiry/${i_idx}/status`,
        data: {
            status: v_status
        },
        success: success_fn,
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }
    })
}

// 현재 글 댓글 가져오기
function getComments(){
    $.ajax({
        type: 'get',
        url: `/inquiry/${i_idx}/comment`,
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

// 새 댓글 작성
function createNewComment(m_idx) {
    var comment = $("#comment").val().trim();
    if(comment.length >= 2){ // 유효성 검사
        let comment_data = {
            comment: comment
        }
        writeComments(comment_data, m_idx);
    } else {
        alert("2자 이상 입력해주세요!")
    }
    $("#comment").val("")
}

// 대댓글 폼 보여주기
function showReCommentForm(c_idx) {
    $(`#form-${c_idx}-recomment`).show();
}

// 대댓글 작성하기 : 대댓글 폼 -> 확인 버튼 클릭
function createReComment(c_idx, m_idx){
    var comment = $(`#recomment_${c_idx}`).val();
    console.log(comment)
    if(comment.length >= 2){ // 유효성 검사
        let comment_data = {
            comment: comment,
            c_idx2: c_idx
        }
        writeComments(comment_data, m_idx);
    } else {
        alert("2자 이상 입력해주세요!")
    }
    $("#comment").val("")
}

// 댓글/대댓글 작성 -> Ajax 작업
function writeComments(comment_data, m_idx){
    $.ajax({
        type: 'POST',
        url: `/inquiry/${i_idx}/comment/${m_idx}`,
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

// 대댓글 보여주기 - 토글
function toggleShowReCommentList(btn, c_idx) {
    var status = $(btn).attr("data-status");
    console.log(status)
    if(status == 0) {
        $(btn).attr("data-status", "1");
        $(btn).children().html("대댓글창 닫기");
        $.ajax({
            type: 'get',
            url: `/inquiry/${i_idx}/comment/${c_idx}`,
            success: function (result) {
                console.log(result);
                $("#comment-recomment-list-tmpl").tmpl(result).appendTo( `#recomment-list-${c_idx}` );
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        })
    } else {
        var comment_ctn = $(`#recomment-list-${c_idx}`).children().length;
        $(`#recomment-list-${c_idx}`).empty();
        $(btn).attr("data-status", "0");
        $(btn).children().html(`답글 ${comment_ctn}개`);
    }
}

// (대)댓글 수정 폼 보여주기
// TODO: 현재 접속중인 사용자의 댓글인 경우에만 수정/삭제 버튼 생기도록 변경해야 함.
function showUpdateCommentForm(c_idx) {
    var comment = $(`#comment_${c_idx}`).text();
    console.log(comment);
    $(`#btn-${c_idx}-default`).hide();
    $(`#btn-${c_idx}-update`).show();
    $(`#comment_${c_idx}`).html(`<div class="writeComment"><textarea type='text' id='comment_input_${c_idx}' placeholder='내용을 입력해주세요'>${comment}</textarea></div>`)
}


// 댓글/대댓글 수정 -> Ajax 작업
function updateComment(c_idx, m_idx){
    var comment_data = $(`#comment_input_${c_idx}`).val();
    console.log(c_idx, m_idx, comment_data);

    $(`#btn-${c_idx}-update`).hide();
    $.ajax({
        type: 'PUT',
        url: `/inquiry/${i_idx}/comment/${m_idx}`,
        data: {
            c_idx: c_idx,
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
}

// 댓글 삭제하기
// TODO: 현재 접속중인 사용자의 댓글인 경우에만 수정/삭제 버튼 생기도록 변경해야 함.
// TODO: 다른 사람의 댓글도 현제 삭제 가능...
function deleteComment(c_idx) {
    $.ajax({
        type: 'DELETE',
        url: `/inquiry/${i_idx}/comment/${c_idx}`,
        success: function (result) {
            console.log(result)
            getComments();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }
    })
}

// END : InquiryRetrieve

// START : InquiryWrite
// 업로드 대기중인(미리보기) 이미지 삭제
function deleteImage(btn) {
    var idx = $(btn).parent().attr("data-idx");
    $(btn).parent().remove();
    $(`input[data-idx='\${idx}']`).parent().remove();
}

// 파일이 등록되지 않은 파일 태그 삭제하기
function deleteEmptyFileForm() {
    var file_input = $("#file-input-list").children().last().children("input");
    if(file_input.val() !== undefined && !file_input.val()){
        file_input.parent().remove();
    }
}

// 이미지 -> 파일 폼(태그)에 저장하기
function addImage(){
    deleteEmptyFileForm();

    // 5개 이상 등록 막기(유효성 검사)
    if ($("#img-preview-area").children().children().length >= 5){
        alert("이미지 파일은 최대 5개 업로드 가능합니다.");
    } else {
        $("#file-input-tmpl").tmpl(index).appendTo( "#file-input-list" );
        $("#file-input-list").children().last().children("input").click()
    }
}

// 이미지 폼태그에 저장하기
function readInputFile(input){
    var file = input.files[0];

    // 파일 타입 확인 (유효성 검사)
    if(!file.type.match("image/.*")){
        alert("이미지 확장자만 업로드 가능합니다.");
        $(input).parent().remove();
        return false;
    };

    var reader = new FileReader();
    reader.onload = function(e){
        var data = {
            "index":index,
            "result":e.target.result
        }
        $("#img-preview-tmpl").tmpl(data).appendTo( "#img-preview-area ul" );
        index++;
    };
    reader.readAsDataURL(file);
}
function submitInquiryForm(f){
    deleteEmptyFileForm();
    f.submit();
}
// END : InquiryWrite
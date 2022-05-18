
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
function getComments(recomment=false, c_idx=0){
    $.ajax({
        type: 'get',
        url: `/inquiry/${i_idx}/comment`,
        success: function (result) {
            console.log(result);
            result.forEach(c => c.auth = auth);
            $('#comment-list').empty();
            $("#comment-list-tmpl").tmpl(result).appendTo( "#comment-list" );
            if(recomment){
                $(`#commentOne${c_idx} > .show-re-comment`).click();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }
    })
}

// 새 댓글 작성
function createNewComment() {
    var comment = $("#comment").val().trim();
    if(comment.length >= 2){ // 유효성 검사
        let comment_data = {
            comment: comment
        }
        writeComments(comment_data);
    } else {
        alert("2자 이상 입력해주세요!")
    }
    $("#comment").val("")
}

// 대댓글 폼 보여주기
function showReCommentForm(c_idx) {
    $(`#form-${c_idx}-recomment`).toggle();
}

// 대댓글 작성하기 : 대댓글 폼 -> 확인 버튼 클릭
function createReComment(c_idx){
    var comment = $(`#recomment_${c_idx}`).val();
    console.log(comment)
    if(comment.length >= 2){ // 유효성 검사
        let comment_data = {
            comment: comment,
            c_idx2: c_idx
        }
        writeComments(comment_data, true);
    } else {
        alert("2자 이상 입력해주세요!")
    }
    $("#comment").val("")
}

// 댓글/대댓글 작성 -> Ajax 작업
function writeComments(comment_data, recomment=false){
    $.ajax({
        type: 'POST',
        url: `/inquiry/${i_idx}/comment`,
        data: comment_data,
        success: function (result) {
            console.log(result)
            if(recomment) {
                var idx = $(`#btn-${comment_data.c_idx2}-default`).parents(".commentOne").attr("id").substr(10);
                getComments(recomment, Number.parseInt(idx));
            } else{
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
    $(`#form-${c_idx}-recomment`).hide();
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
                result.forEach(c => c.auth = auth);
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
function updateComment(c_idx, recomment=false){
    var comment_data = $(`#comment_input_${c_idx}`).val();
    $(`#btn-${c_idx}-update`).hide();
    $.ajax({
        type: 'PUT',
        url: `/inquiry/${i_idx}/comment/${c_idx}`,
        data: {
            comment: comment_data
        },
        success: function (result) {
            console.log(result)
            if(recomment) {
                var idx = $(`#btn-${c_idx}-default`).parents(".commentOne").attr("id").substr(10);
                getComments(recomment, Number.parseInt(idx));
            } else{
                getComments();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }
    })
}

// 댓글 삭제하기
function deleteComment(c_idx, recomment=false) {
    $.ajax({
        type: 'DELETE',
        url: `/inquiry/${i_idx}/comment/${c_idx}`,
        success: function (result) {
            console.log(result)
            if(recomment) {
                var idx = $(`#btn-${c_idx}-default`).parents(".commentOne").attr("id").substr(10);
                getComments(recomment, Number.parseInt(idx));
            } else{
                getComments();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }
    })
}

// END : InquiryRetrieve

// START : InquiryWrite(Update)

// 이미지 삭제
function deleteImage(btn) {
    var li = $(btn).parent();
    var idx = li.attr("data-idx");
    var filePath = li.attr("data-filePath");

    if(filePath){ // DB에 저장된 이미지 중 삭제할 이미지 목록에 넣기
        deleteImgIdx.push(idx);
        deleteImgName.push(filePath);
    } else { // 업로드 대기중인(미리보기) 이미지 삭제
        $(`input[data-idx='${idx}']`).parent().remove();
    }
    li.remove();
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

// 폼태그에 저장된 이미지 -> 미리보기
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

// 글 등록/수정 폼 제출
function submitInquiryForm(f, i_idx){
    deleteEmptyFileForm();

    // 유효성 체크
    if ($("#title").val().trim().length < 2) {
        alert("제목을 2자 이상 입력해주세요.");
        return false;
    } else if ($("#content").val().trim().length < 2) {
        alert("내용을 2자 이상 입력해주세요.");
        return false;
    }

    // 수정후, 저장시 삭제할 이미지 목록 삭제하기
    if (deleteImgName.length > 0 && deleteImgIdx.length > 0) {
        $.ajax({
            type: 'DELETE',
            url: `/inquiry/${i_idx}/image`,
            data: {
                pathList: deleteImgName,
                idxList: deleteImgIdx
            },
            dataType: 'json',
            async: true,
            success: function (result) {
                console.log(result)
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }

    // 폼 새로 등록/수정 에 따라 action값 변경
    if(i_idx) {
        f.action = `/inquiry/${i_idx}`;
    } else {
        f.action = `/inquiry`;
    }
    f.submit();
}

// END : InquiryWrite(Update)

// START : InquiryList
// 검색어 입력 후, 검색하기
function go_search(){
    searchWord = $("#searchKey").val().trim();
    if(searchWord.length <= 0){
        searchWord = null;
    }
    getInquiryList(1, searchWord);
}

// 검색창 유효성 검사
function searchFormValidation(keyCode) {
    searchWord = $("#searchKey").val().trim();
    // 엔터키 누르면, 검색 로직 실행
    if(keyCode === 13) {
        go_search()
    } else if (searchWord.length <= 0 && keyCode == 32) {
        // 첫번째 입력하는 키가 스페이스키면, 해당 내용 삭제
        $("#searchKey").val('');
    }
}

// 문의글 정보 가져오기
function getInquiryList(page = 1, searchKey, success_fn = resetList){
    // 전체 페이지보다 넘어가면, 더이상 데이터 가져오지 않는다.
    if (page > totalPage) {
        curPage = page--;
        return false;
    }

    var sendData = {
        pg : page
    };

    // 검색어가 존재하지 않으면 Ajax 시 쿼리스트링으로 넘기지 않음.
    if(searchKey) {
        sendData.sch = searchKey;
    }

    $.ajax({
        url:"/inquiry/list",
        type:"GET",
        data:sendData,
        success:success_fn,
        error:function (xhr,sta,error){
            console.log(error);
        }
    })
}

// START: AJAX 성공시, 실행되는 함수
// 처음 화면 랜더링 & 검색어 입력 후, 처음으로 리스트 가져올 시
function resetList(data) {
    console.log()
    curPage = 1;
    totalPage = Math.ceil(data.totalRecord / data.perPage);
    $("#inqList").html($("#inquiry-list-tmpl").tmpl(data));
}

// 스크롤하여 추가로 데이터 가져올 시
function appendList(data) {
    $("#inquiry-list-tmpl").tmpl(data).appendTo("#inqList");
}
// END: AJAX 성공시, 실행되는 함수

// START : InquiryList


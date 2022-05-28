// 검색
function adminSearch(f){
    f.submit();
}

// 수정 & 저장
function saveEditForm(f){
    f.submit();
}

// Inquiry 검색 폼 제출
function submitSearchForm(f){
    f.submit();
}

// 업로드 이미지 파일 크기 제한(3MB)
function imageFileSizeCheck(file){

    var maxSize = 3 * 1024 * 1024;
    var fileSize = $("#"+file)[0].files[0].size;

    if(fileSize > maxSize){
        alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
        $("#"+file).val('');
        return false;
    }else {
        var file = event.target.files[0];

        var reader = new FileReader();
        reader.onload = function (e) {
            $("#preview").attr("src", e.target.result);
        }

        reader.readAsDataURL(file);
    }
}

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

function editInquiryForm_submit(f){
    var file_input = $("#file-input-list").children().last().children("input");
    if(file_input.val() !== undefined && !file_input.val()){
        file_input.remove()
    }
    f.submit();
}
// 문자 구분 객체
var letterCheck = {
    'checkNum': /[0-9]/,
    'checkEngA': /[A-Z]/,
    'checkEnga': /[a-z]/,
    'checkEngAll': /[a-zA-Z]/,
    'checkSpc': /[~!@#$%^&*()_+|<>?:{}]/,
    'checkKor': /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/
}


var menu = false;
function open_menu(){

    // css 효과
    if (!menu){
        $("#aside-black").css("display", "block");
        $("#aside-black ").fadeTo('500', "50%");
        $("#aside").animate({
            "left" : "0px"
        }, 400);
        menu = true;
    } else {
        $("#aside-black ").fadeTo('slow', "0%");
        $("#aside-black ").css("display", "none");
        $("#aside").animate({
            "left" : "-1000px"
        }, 400);
        menu = false;
    }
}

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="/script/jquery-3.6.0.js"></script>



<html>
    <body>
        <form name="myform">
            Pedal MT
            <table border="1">
                <th>누가</th>
                <th>누구에게</th>
                <th>얼마를?</th>
                <th>정산여부</th>
                <tr><!-- 첫 번째 줄 시작 -->
                    <td>
                        <input type="text" readonly="readonly" id="give1" value="명지">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="take1" value="민욱">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="giveMoney1" value="15,000">
                    </td>
                    <td>
                        <input type="checkbox">
                    </td>
                </tr><!-- 첫 번째 줄 끝 -->
                <tr><!-- 두 번째 줄 시작 -->
                    <td>
                        <input type="text" readonly="readonly" id="give2" value="주황">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="take2" value="소현">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="giveMoney2" value="10,000">
                    </td>
                    <td>
                        <input type="checkbox">
                    </td>
                </tr><!-- 두 번째 줄 끝 -->
                <tr><!-- 세 번째 줄 시작 -->
                    <td>
                        <input type="text" readonly="readonly" id="give3" value="이름">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="take3" value="이름">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="giveMoney3" value="0">
                    </td>
                    <td>
                        <input type="checkbox">
                    </td>
                </tr><!-- 세 번째 줄 끝 -->
            </table><br>
            계좌정보
            <table border="1">
                <th>은행</th>
                <th>계좌번호</th>
                <th>이름</th>
                <tr><!-- 첫 번째 줄 시작 -->

                    <td>
                        <input type="text" readonly="readonly" id="bankName1" name="bankName1" value="신한은행">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="bankNumber1" name="bankNumber1" value="123-456-789012">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="userName1" name="userName1" value="총무">
                    </td>
                </tr><!-- 첫 번째 줄 끝 -->
                <tr><!-- 두 번째 줄 시작 -->
                    <td>
                        <input type="text" readonly="readonly" id="bankName2" name="bankName2" value="카카오뱅크">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="bankNumber2" name="bankNumber2" value="3333-12-3456789">
                    </td>
                    <td>
                        <input type="text" readonly="readonly" id="userName2" name="userName2" value="명지">
                    </td>
                </tr><!-- 두 번째 줄 끝 -->
            </table><br>
            결제일 <input id="startDate" type="date"><br>
            마감일 <input id="endDate" type="date"><br><br>

            <a href="#" onclick="clip(); return false;">URL주소복사</a>
            <a href="#" onclick="copyClip(); return false;">클립보드로 복사</a>

        </form>
    </body>
</html>
<span class="button gray medium"></span>
<script type="text/javascript">

    function clip(){

        var url = '';


        var textarea = document.createElement("textarea"); // Document.createElement("생성할 요소의 유형") 메서드는 지정한 tagName의 HTML 요소를 만들어 반환합니다.

        document.body.appendChild(textarea); // body에 'textarea' 내용을 주입
        url = window.document.location.href; // URL 주소
        textarea.value = url;
        textarea.select();
        document.execCommand("copy"); // 텍스트를 클립 보드에 복사
        document.body.removeChild(textarea);

        alert("URL이 복사되었습니다.")
    }

    function copyClip() {
        var clipText = '';

        const header = $("#startDate").val() + " 모임 정산 내용입니다.\n\n"

        const content = "정산내용\n" +
            $("#give1").val() + "님은 " + $("#take1").val() + "님에게 " + $("#giveMoney1").val() + "원 입금 부탁드립니다.\n" +
            $("#give2").val() + "님은 " + $("#take2").val() + "님에게 " + $("#giveMoney2").val() + "원 입금 부탁드립니다.\n\n";

        const footer = "계좌정보\n" +
            $("#userName1").val() + "에게 보내실 분은 " + $("#bankName1").val() + $("#bankNumber1").val() + "로 보내주세요.\n" +
            $("#userName2").val() + "에게 보내실 분은 " + $("#bankName2").val() + $("#bankNumber2").val() + "로 보내주세요.\n" +
            "계좌가 없는 인원은 카카오톡으로 보내주세요.";

        try {
            clipText += header;
            clipText += content;
            clipText += footer;

            console.log(clipText);
            navigator.clipboard.writeText(clipText).then(function() {
                window.alert("클립보드로 복사되었습니다.")//클립보드로 복사
                    })
        } catch (err) {
            console.log("복사할 수 없습니다!");
        }
    }
</script>

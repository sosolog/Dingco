<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<a id="btnTwitter" class="link-icon twitter" href="javascript:shareTwitter();">트위터</a>
<a id="btnFacebook" class="link-icon facebook" href="javascript:shareFacebook();">페이스북</a>
<a id="btnKakao" class="link-icon kakao" href="javascript:shareKakao();">카카오톡</a>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>


<script>
    function shareKakao() {

        // 사용할 앱의 JavaScript 키 설정
        Kakao.init('26859d6bf4c050091f95cdb46c224768');

        // 카카오링크 버튼 생성
        Kakao.Link.createDefaultButton({
            container: '#btnKakao', // 카카오공유버튼ID
            objectType: 'feed',
            content: {
                title: "개발새발", // 보여질 제목
                description: "블로그입니다", // 보여질 설명
                imageUrl: "https://velog.io/@uo3641493", // 콘텐츠 URL
                link: {
                    mobileWebUrl: "https://velog.io/@uo3641493",
                    webUrl: "https://velog.io/@uo3641493"
                }
            }
        });
    }

</script>
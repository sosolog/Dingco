<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/script/jquery.tmpl.js"></script>


<script>
    let curPage = 1;
    let totalPage = 1;
    let searchKey = null;

    $(document).ready(function(){
        getNOTICEList();
    });

    $(window).scroll(function(){   //스크롤이 최하단 으로 내려가면 리스트를 조회하고 page를 증가시킨다.

        if($(window).scrollTop() +10 >= $(document).height() - $(window).height()){
            getNOTICEList(++curPage, searchKey, appendnoticeList);
        }
    });
</script>

<script type="text/html" id="notice-list-tmpl">
    {{each(index, dto) dtoList}}
    <tr class="list">
        <td class="idx"><span>{{= totalRecord - (curPage-1) * criteriaOfPage - index}}</span></td>
        <td class="title">
            <a href="notice/\${dto.number_idx}">
                <span> \${dto.title} </span>
            </a>
        </td>
        <td class="writeday">
<%--            <a href="notice/\${dto.number_idx}">--%>
                <span> \${dto.writeday} </span>
            </a>
        </td>

    </tr>
    {{/each}}
</script>


<!-- 스크롤 페이징 폼 -->

<div id="inquiryList">
    <div class="sch">
        <div class="sch_wrap">
            <div class="sch_input_wrap">
                <input type="text" data-role="none" id="searchKey" class="sch_input"
                       placeholder="검색어를 입력하세요" onkeydown="go_search_notice_enter(event.keyCode)">
            </div>
            <div class="sch_ico_rt">
                <a onclick="go_search_notice_click()"><img src="/images/ico_search_02.png"></a>
            </div>
        </div>
    </div>
    <div class="wrap_table">
        <table>
            <thead>
            <tr>
                <td class="idx" style="width: 12%;"> No </td>
                <td class="title"> 제목 </td>
                <td class="writeday"> 작성일 </td>
            </tr>
            </thead>

            <!-- 레코드가 들어갈 공간 -->
            <tbody id="noticeList" class="infinite"></tbody>
        </table>
    </div>
</div>



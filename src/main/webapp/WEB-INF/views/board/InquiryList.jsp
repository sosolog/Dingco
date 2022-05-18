<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/script/jquery.tmpl.js"></script>
<script>
    let curPage = 1;
    let totalPage = 1;
    let searchWord = null;
    $(document).ready(function(){
        getInquiryList();
    });

    $(window).scroll(function(){   //스크롤이 최하단 으로 내려가면 리스트를 조회하고 page를 증가시킨다.
        if($(window).scrollTop() +10 >= $(document).height() - $(window).height()){
            getInquiryList(++curPage, searchWord, appendList);
        }
    });
</script>
<script type="text/html" id="inquiry-list-tmpl">
    {{each(index, dto) dtoList}}
    <tr class="list">
        <td class="idx"><span>{{= totalRecord - (curPage-1) * criteriaOfPage - index}}</span></td>
        <td class="title">
            <a href="inquiry/\${dto.i_idx}">
                <span> \${dto.title} </span>
                {{if dto.i_idx2 > 0}}
                <img src="/images/request.png">
                {{/if}}
            </a>
        </td>
        <td class="writeday">
            <a href="inquiry/\${dto.i_idx}">
                <span> \${dto.upload_date} </span>
            </a>
        </td>
        <td class="answer">
            <a href="inquiry/\${dto.i_idx}">
                {{if dto.status == 'YET'}}
                <img src="/images/beforeAnswer.png">
                {{else}}
                <img src="/images/afterAnswer.png">
                {{/if}}
            </a>
        </td>
    </tr>
    {{/each}}
</script>
<div id="inquiryList">
    <div class="sch">
        <div class="sch_wrap">
            <div class="sch_input_wrap">
                <input type="text" data-role="none" id="searchKey" class="sch_input"
                       placeholder="검색어를 입력하세요" onkeyup="searchFormValidation(event.keyCode)">
            </div>
            <div class="sch_ico_rt">
                <a onclick="go_search(searchForm)"><img src="/images/ico_search_02.png"></a>
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
                <td class="answer"> 답변 </td>
            </tr>
            </thead>
            <tbody id="inqList" class="infinite">

            </tbody>
            <tfoot class="paginaiton"></tfoot>
        </table>
    </div>
    <!-- 페이지 번호 출력 -->
<%--    <%@ include file="../page.jsp" %>--%>
    <!-- 페이지 번호 출력 -->
    <br>
</div>
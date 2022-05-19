<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/script/jquery.tmpl.js"></script>


<script>
  let curPage = 1;
  let totalPage = 1;
  let searchKey = null;

  $(document).ready(function(){
    getFAQList();
  });

  $(window).scroll(function(){   //스크롤이 최하단 으로 내려가면 리스트를 조회하고 page를 증가시킨다.

    if($(window).scrollTop() +10 >= $(document).height() - $(window).height()){
      getFAQList(++curPage, searchKey, appendfaqList);
    }
  });
</script>

<script type="text/html" id="faq-list-tmpl">
  {{each(index, dto) dtoList}}
  <tr class="list">
    <td class="idx"><span>{{= totalRecord - (curPage-1) * criteriaOfPage - index}}</span></td>
    <td class="title">
      <a href="faq/\${dto.number_idx}">
        <span> \${dto.title} </span>
      </a>
    </td>
    <td class="writeday">
      <a href="faq/\${dto.number_idx}">
        <span> \${dto.writeday} </span>
      </a>
    </td>
    <td class="btn_openFAQ">
      <a onclick="openFAQ(\${dto.number_idx})" id="btn_openFAQ\${dto.number_idx}">
        <img src="/images/openFAQ.png">
      </a>
      <a onclick="flipFAQ(\${dto.number_idx})" id="btn_flipFAQ\${dto.number_idx}" style="display: none">
        <img src="/images/flipFAQ.png">
      </a>
    </td>
  </tr>
  <tr id="openFAQ\${dto.number_idx}" class="openFAQ" style="display: none;">
    <td class="content" colspan="100%">
      <div class="open_title">
        <span>Q. </span>
        <span>\${dto.title}</span>
      </div>
      <div class="open_content">
        <span>A. </span>
        <span>\${dto.content}</span>
      </div>
      <div class="open_writeday">
        <span>\${dto.writeday}</span><br>
      </div>
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
               placeholder="검색어를 입력하세요" onkeydown="go_search_faq_enter(event.keyCode)">
      </div>
      <div class="sch_ico_rt">
        <a onclick="go_search_faq_click()"><img src="/images/ico_search_02.png"></a>
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
            <td class="open"></td>
            <td class="flip"></td>
        </tr>
      </thead>

      <!-- 레코드가 들어갈 공간 -->
      <tbody id="faqList" class="infinite"></tbody>
    </table>
  </div>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageDTO" value="${pageDTO}"></c:set>
<c:set var="totalRecord" value="${pageDTO.totalRecord}"/>

<div id="faqList">
  <form class="sch" method="get" action="/faq/search" name="searchForm">
    <div class="sch_wrap">
      <div class="sch_input_wrap">
        <input type="text" data-role="none" class="sch_input" id = "searchKey" name="searchKey" placeholder="검색어를 입력하세요" onkeydown="if(event.keyCode === 13) $(this).submit();">
      </div>
      <div class="sch_ico_rt">
        <input type="image" src="/images/ico_search_02.png" alt="submit">
      </div>
    </div>
  </form>
  <div class="wrap_table">
    <table>
      <thead>
        <tr>
          <td class="idx" style="width: 12%;"> No </td>
          <td class="title"> 제목 </td>
          <td class="btn_openflip" style="width: 10%;"></td>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="dto" items="${pageDTO.dtoList}" varStatus="status">
        <tr id="flipFAQ${dto.number_idx}" class="flipFAQ">
          <td class="idx">
            <span>${totalRecord - ((pageDTO.curPage-1) * pageDTO.criteriaOfPage) - status.index}</span>
          </td>
          <td class="title">
            <span> ${dto.title} </span>
          </td>
          <td class="btn_openFAQ">
            <a onclick="openFAQ(${dto.number_idx})">
              <img src="/static/images/openFAQ.png">
            </a>
          </td>
          <td class="btn_flipFAQ">
            <a onclick="flipFAQ(${dto.number_idx})">
              <img src="/static/images/flipFAQ.png">
            </a>
          </td>
        </tr>
        <tr id="openFAQ${dto.number_idx}" class="openFAQ" style="display: none;">
          <td class="content" colspan="100%">
            <div class="open_title">
              <span>Q. </span>
              <span>${dto.title}</span>
            </div>
            <div class="open_content">
              <span>A. </span>
              <span>${dto.content}</span>
            </div>
            <div class="open_writeday">
             <span>${dto.writeday}</span><br>
            </div>
          </td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>


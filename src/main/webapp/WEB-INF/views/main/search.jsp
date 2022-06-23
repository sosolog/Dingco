<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="/script/jquery.tmpl.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/moment@2.29.3/moment.min.js"></script>

<!--더치페이 리스트 목록 템플릿-->
<script type="text/html" id="payRoom-list-tmpl">
    {{each(index, p) pList}}
    <div class="wrap_payRoom">
        <a class="box" id="pList_\${index}" href="/pay/\${p.pr_idx}">
            <span class="tit">\${p.room_name}</span>
            <span class="date">
                \${p.create_date}
                <a id="btn-delete-account-ajax" class="btn-delete-account" data-idx="\${index}" onclick="deleteOnePayRoom(\${p.pr_idx},$(this))">삭제</a>
            </span>
        </a>

    </div>
    {{/each}}
</script>


<div id="main">
    <div id="payRoomList">
        <div class="list_top">
            <span class="tit">검색 결과</span>
        </div>
        <div id="payRoomList_data"></div>
    </div>
</div>


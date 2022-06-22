<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
    /*let payRoomArr = ${payRoomList};*/

    function searchPayRoom(f){
        $("#payRoomList_data").html("");
        $.ajax({
            url:"/search.do",
            type:"GET",
            data: { "sch": f.searchKey.value },
            success:function(res){
                console.log(res.searchPayRooms);
                $("#payRoom-list-tmpl").tmpl(res).appendTo("#payRoomList_data");
            },
            error:function (xhr,sta,error){
                console.log(error);
            }
        })
    }
</script>

<div id="header_box">
    <header id="headerTitle">
        <form class="sch" method="get" name="searchForm">
            <div class="sch_wrap">
                <div class="sch_ico_lt">
                    <a href="javascript:history.back()" style="display: inline-block; position: absolute; top: 30px; left: 25px;"><img src="/images/btn_back.png"></a>
                </div>
                <div class="sch_input_wrap">
                    <input type="text" data-role="none" name="searchKey" class="sch_input"
                           placeholder="찾고 계신 더치페이 방을 검색해보세요" onkeydown="if(event.keyCode === 13) searchPayRoom(searchForm);">
                </div>
                <div class="sch_ico_rt">
                    <a onclick="searchPayRoom(searchForm)" class="ui-link"><img src="/images/ico_search_02.png" style="width: 60px; margin-top: 5px;"></a>
                </div>
            </div>
        </form>
    </header>
</div>
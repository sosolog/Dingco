<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="/script/jquery-3.6.0.js"></script>
</head>
<body>
<h2>게시판 세부사항 보기</h2>
<table border="1">
    <tr>
        <th>문의번호</th>
        <th>작성자번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>내용</th>
        <th>업로드 날짜</th>
        <th>수정 날짜</th>
        <th>상위 문의 고유번호</th>
        <th>문의 상태</th>
    </tr>
    <c:set var="dto" value="${inquiryDTO}"></c:set>

    <tr>
        <td>${dto.i_idx}</td>
        <td>${dto.m_idx}</td>
        <td>${dto.category_id}</td>
        <td>${dto.title}</td>
        <td>${dto.content}</td>
        <td>${dto.upload_date}</td>
        <td>${dto.last_updated_date}</td>
        <td>${dto.i_idx2}</td>
        <td>${dto.status.message}, ${dto.status == 'YET'}</td>
    </tr>
</table>
<div>
<c:forEach var="files" items="${dto.fileNames}">
    <img src="/files/${files.tableDir.directoryName}/${files.serverFileName}" style="max-width: 10%" height="auto">

</c:forEach>
</div>
<c:if test="${dto.i_idx2 != 0}">
    <h3>관련 문의 보기_기존</h3>
    <a href="/inquiry/${dto.i_idx2}">하ㅏㅏㅏㅏ</a>
</c:if>


<br>
<hr>
<c:if test="${dto.status == 'YET'}">
    <a href="${dto.i_idx}/update">글 수정</a>
    <button id="deletePost">글 삭제</button>
<script type="text/javascript">
    $("#deletePost").on("click", function (){
        if(confirm("정말로 삭제하시겠습니까? 한번 삭제하면 다시 복원할 수 없습니다.")){

            $.ajax({
                type: 'DELETE',
                url: `/inquiry/${dto.i_idx}`,
                success: function (result) {
                    console.log(result)
                    if(result) {
                        location.href="/inquiry";
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.status + ' ' + jqXHR.responseText);
                }
            });
        }
    });
</script>

</c:if>

</body>
</html>




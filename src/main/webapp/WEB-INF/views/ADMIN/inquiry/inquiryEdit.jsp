<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!--수정 모드-->
<c:if test="${not empty inquiryDTO.i_idx}">
    <c:set var="mode" value="수정"/>
    <c:set var="i_idx" value="${inquiryDTO.i_idx}"/>
    <%--<c:set var="m_idx" value="${inquiryDTO.m_idx}"/>--%>
    <c:set var="m_idx2" value="${inquiryDTO.m_idx}"/>
    <c:set var="category_id" value="${inquiryDTO.category_id}"/>
    <c:set var="title" value="${inquiryDTO.title}"/>
    <c:set var="content" value="${inquiryDTO.content}"/>
    <c:set var="upload_date" value="${inquiryDTO.upload_date}"/>
    <c:set var="last_updated_date" value="${inquiryDTO.last_updated_date}"/>
    <c:set var="status" value="${inquiryDTO.status}"/>
</c:if>

<script src="/script/jquery.tmpl.js"></script>
<script type="text/javascript">
    let m_idx2 = ${inquiryDTO.m_idx};
</script>

<form action="" name="editForm" method="post" enctype="multipart/form-data">
<div id="main-box">
    <div id="top-menu">
        <span class="title">문의 ${i_idx} : ${title}</span>
        <%--<a class="btn-save" onclick="saveEditForm(editForm)"><span>저장</span></a>--%>
        <a class="btn-cancel" onclick="javascript:history.back()"><span>뒤로가기</span></a>
    </div>
    <input type="hidden" name="category_idx" value="${category_idx}">
    <div id="edit-table">
        <table>
            <tbody>
                <c:if test="${inquiryDTO.i_idx2 != ''}">
                    <tr class="short-line">
                        <td>최초문의 보러가기</td>
                        <td><a href="/admin/inquiry/edit?idx=${inquiryDTO.i_idx2}" class="mini-btn3"><span>최초 문의 보러가기</span></a></td>
                    </tr>
                </c:if>
                <tr class="short-line">
                    <td>작성자</td>
                    <td><input readonly="readonly" name="m_idx" value="${m_idx2}"></td>
                </tr>
                <tr class="short-line">
                    <td>상태</td>
                    <td><input readonly="readonly" name="status" value="${status}"></td>
                </tr>
                <tr class="long-line">
                    <td>내용</td>
                    <td><textarea readonly="readonly" name="content">${content}</textarea></td>
                </tr>
                <tr class="short-line">
                    <td>관련 이미지</td>
                    <td><input name="title" value="${title}"></td>
                </tr>
                <tr class="short-line">
                    <td>작성일</td>
                    <td><input readonly="readonly" name="upload_date" value="${upload_date}"></td>
                </tr>
                <tr class="short-line">
                    <td>문의상태 변경</td>
                    <td>
                    <form:form modelAttribute="inquiryDTO" action="">
                        <form:radiobutton path="status" value="YET" label="답변 대기 중"/>
                        <form:radiobutton path="status" value="IN_PROCESS" label="답변 완료"/>
                        <form:radiobutton path="status" value="DONE" label="문의 종료"/>
                        <form:radiobutton path="status" value="RE_INQUIRY" label="재문의"/>
                        <a onclick="changeInquiryStatus(${i_idx})" class="mini-btn"><span>문의상태 변경하기</span></a>
                    </form:form>
                    </td>
                </tr>
                <c:forEach var="dto" items="${commentDTO}" varStatus="status">
                    <tr class="long-line">
                        <%-- START : 상위 댓글 좌측 타이틀 --%>
                        <td>
                            <span style="display: block; margin-bottom: 10px;">
                                <c:if test="${m_idx2 != dto.m_idx}">관리자의 댓글${status.count}</c:if>
                                <c:if test="${m_idx2 == dto.m_idx}">사용자의 댓글${status.count}</c:if>
                            </span>
                            <c:if test="${dto.count_sub > 0}">
                                <a class="show-re-comment" data-status="0" onclick="toggleShowReCommentList(this, ${dto.c_idx}, ${dto.i_idx})"
                                   style="display:block; margin-top: 10px; color:#88AEFF; text-decoration:underline;">
                                    <span>답글 ${dto.count_sub}개</span>
                                </a>
                            </c:if>
                            <c:if test="${dto.count_sub == 0 && dto.comment != '' && m_idx2 == dto.m_idx}">
                                <a id="span-create-${dto.c_idx}-re-comment" onclick="showReCommentForm(${dto.c_idx})" style="color:#666666; text-decoration:underline;">
                                    <span>대댓글 달기</span>
                                </a>
                            </c:if>
                        </td>
                        <%-- END : 상위 댓글 좌측 타이틀 --%>

                        <%-- START : 상위 댓글 내용 보기 --%>
                        <td>
                            <c:if test="${dto.comment == ''}">
                            <textarea readonly="readonly" name="content" id="comment_input_${dto.c_idx}" class="commentArea" style="color:#999999;">(삭제된 댓글입니다.)</textarea>
                            </c:if>
                            <c:if test="${dto.comment != '' && m_idx2 != dto.m_idx}"> <%--관리자의 댓글--%>
                            <textarea name="content" id="comment_input_${dto.c_idx}" class="commentArea">${dto.comment}</textarea>
                            </c:if>
                            <c:if test="${dto.comment != '' && m_idx2 == dto.m_idx}"> <%--사용자의 댓글--%>
                                <textarea readonly="readonly" name="content" id="comment_input_${dto.c_idx}" class="commentArea">${dto.comment}</textarea>
                            </c:if>
                            <c:if test="${dto.comment != '' && m_idx2 != dto.m_idx}">
                                <a onclick="updateComment(${dto.i_idx}, ${dto.c_idx})" class="mini-btn"><span>수정</span></a>
                                <a onclick="deleteComment(${dto.i_idx}, ${dto.c_idx})" class="mini-btn2"><span>삭제</span></a>
                            </c:if>
                        </td>
                        <%-- END : 상위 댓글 내용 보기 --%>
                    </tr>

                    <%-- START : 대댓글 작성 --%>
                    <tr id="form-${dto.c_idx}-recomment" class="writeComment" style="display:none;">
                        <td>
                            └ 대댓글 작성
                            <a onclick="hideReCommentForm(${dto.c_idx})" style="display:block; margin-top: 10px; color:#666666; text-decoration:underline;">
                                <span>접기</span>
                            </a>
                        </td>
                        <td>
                            <textarea id="recomment_${dto.c_idx}" style="height: 70px;"></textarea>
                            <a onclick="createReComment(${dto.i_idx}, ${dto.c_idx})" class="mini-btn"><span>등록</span></a>
                            <div class="reset"></div>
                        </td>
                    </tr>
                    <%-- END : 대댓글 작성 --%>

                    <%-- START : 대댓글 보기 --%>
                    <tr>
                        <td colspan="2" class="tmpl-parents" style="display:none; padding:0px;">
                            <table id="recomment-list-${dto.c_idx}" style="border: 0px;"></table>
                        </td>
                    </tr>
                    <%-- END : 대댓글 보기 --%>
                </c:forEach>
            </tbody>
        </table>
        <div class="writeComment" style="border:1.5px solid #6C6C6C; border-radius: 5px; padding: 10px; margin: 5px;">
            <textarea id="comment" class="commentArea" placeholder="새로운 댓글을 추가해주세요."></textarea>
            <%--<a onclick="createReComment(${dto.i_idx}, ${dto.c_idx})" class="mini-btn"><span>등록</span></a>--%>
            <a onclick="createNewComment(${i_idx})" class="mini-btn"><span>등록</span></a>
            <div class="reset"></div>
        </div>
    </div>
</div>

</form>

<!--삭제 버튼-->
<%--<c:if test="${mode == '수정'}">
    <a id="btn-delete" href="/admin/notice/delete?idx=${number_idx}"><img src="/ADMIN/images/remove.png"></a>
</c:if>--%>


<%-- Start : 대댓글 --%>
<script type="text/html" id="comment-recomment-list-tmpl">
    <tr class="long-line">
        <td>
            <span>
                {{= m_idx2 != m_idx ? '└ 관리자' : '└ 사용자'}}
            </span>
        </td>
        <td>
            {{if (comment == '' && m_idx2 == m_idx)}}
            <textarea readonly="readonly" name="content" id="comment_input_\${c_idx}" class="commentArea" style="background-color: #FCFCFC">(삭제된 댓글입니다.)</textarea>
            {{/if}}
            {{if (comment != '' && m_idx2 != m_idx)}} <%--관리자의 댓글--%>
            <textarea name="content" id="comment_input_\${c_idx}" class="commentArea" style="background-color: #FCFCFC">\${comment}</textarea>
            {{/if}}
            {{if (comment != '' && m_idx2 == m_idx)}} <%--사용자의 댓글--%>
            <textarea readonly="readonly" name="content" id="comment_input_\${c_idx}" class="commentArea" style="background-color: #FCFCFC">\${comment}</textarea>
            {{/if}}
            {{if (m_idx2 != m_idx)}}
            <a onclick="updateComment(\${i_idx}, \${c_idx})" class="mini-btn"><span>수정</span></a>
            <a onclick="deleteComment(\${i_idx}, \${c_idx})" class="mini-btn2"><span>삭제</span></a>
            {{/if}}
        </td>
    </tr>
</script>
<%-- End : 대댓글 --%>


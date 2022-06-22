<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="dto" value="${inquiryDTO}"></c:set>

<script src="/script/jquery.tmpl.js"></script>
<script type="text/javascript">
    let i_idx = ${dto.i_idx};
    let auth = ${memberDTO.authorities == 'ADMIN'};

    $(document).ready(function(){
        getComments();
    });

</script>
<%-- Start : 댓글 --%>
<script type="text/html" id="comment-list-tmpl">
    <div id="commentOne\${c_idx}" class="commentOne">
        <div class="top">
            <span>{{= m_idx == 0 ? '관리자' : (auth ? '사용자'+m_idx : '작성자')}}</span>
            <span>(\${post_date})</span>
            <div id="btn-\${c_idx}-default">
                {{if comment }}
                {{if (m_idx != 0 && !auth) || (m_idx == 0 && auth)}}
                <button onclick="showUpdateCommentForm(\${c_idx})" class="mini-btn">수정</button>
                <button onclick="deleteComment(\${c_idx})" class="mini-btn" style="color:#E74133; border-color: #E74133;">삭제</button>
                {{/if}}
                {{if (m_idx == 0 && !auth)}}
                <button onclick="showReCommentForm(\${c_idx})" class="mini-btn">대댓글 작성</button>
                {{/if}}
                {{/if}}
            </div>
        </div>
        <div class="commentContent">
            <span id="comment_\${c_idx}">{{= comment ? comment : '(삭제된 댓글)'}}</span>
        </div>
        <div id="btn-\${c_idx}-update" class="writeComment" style="display: none">
            <a onclick="updateComment(\${c_idx})"><span>수정</span></a>
            <div class="reset"></div>
        </div>
        {{if count_sub > 0}}
        <a class="show-re-comment" data-status="0" data-cidx="\${c_idx}" onclick="toggleShowReCommentList(this, \${c_idx})"><span>답글 \${count_sub}개</span></a>
            <div id="recomment-list-\${c_idx}"></div>
        {{/if}}
        <div id="form-\${c_idx}-recomment" class="writeComment" style="display:none;">
            <textarea id="recomment_\${c_idx}" placeholder="대댓글을 입력해주세요"></textarea>
            <a onclick="createReComment(\${c_idx})"><span>등록</span></a>
            <div class="reset"></div>
        </div>
    </div>
</script>
<%-- End : 댓글 --%>
<%-- Start : 대댓글 --%>
<script type="text/html" id="comment-recomment-list-tmpl">
    <div>
        <table>
            <tr>
                <td>
                    <span style="font-size: 20px; float: left;">L</span>
                    <div class="top" style="padding: 0px 15px; text-align: left;">
                        <span>{{= m_idx == 0 ? '관리자' : (auth ? '사용자'+m_idx : '작성자')}}</span>
                        <span>(\${post_date})</span>
                        <div id="btn-\${c_idx}-default">
                            {{if comment }}
                                <%--{{if (m_idx != 0 && !auth) || (m_idx == 0 && auth)}}
                                <button onclick="showUpdateCommentForm(\${c_idx})" class="mini-btn">수정</button>
                                <button onclick="deleteComment(\${c_idx})" class="mini-btn" style="color:#E74133; border-color: #E74133;">삭제</button>
                                {{/if}}--%>
                                {{if (m_idx != 0 && !auth) || (m_idx == 0 && auth)}}
                                    <div id="btn-\${c_idx}-default" style="padding-left: 10px;">
                                        <button onclick="showUpdateCommentForm(\${c_idx})" class="mini-btn">수정</button>
                                        <button onclick="deleteComment(\${c_idx}, true)" class="mini-btn" style="color:#E74133; border-color: #E74133; margin-left: 5px;">삭제</button>
                                    </div>
                                    <%--<div id="btn-\${c_idx}-update" style="display: none">
                                        <button onclick="updateComment(\${c_idx}, true)">확인</button>
                                    </div>--%>
                                {{/if}}
                            {{/if}}
                        </div>
                    </div>
                </td>
            <tr>
                <td>
                    <div class="commentContent" style="padding-left: 30px; text-align: left;">
                        <span id="comment_\${c_idx}">{{= comment ? comment : '(삭제된 댓글)'}}</span>
                    </div>
                </td>
                <%--<td>작성자</td>
                <td>{{= m_idx == 0 ? '관리자' : (auth ? '사용자'+m_idx : '작성자')}}</td>
                <td>작성일</td>
                <td>\${post_date}</td>--%>
            </tr>
            <%--<tr>
                <td> </td>
                <td>내용</td>
                <td colspan="3"><span id="comment_\${c_idx}">\${comment}</span></td>
            </tr>--%>
        </table>


        {{if (m_idx != 0 && !auth) || (m_idx == 0 && auth)}}
        <%--<div id="btn-\${c_idx}-default">
            <button onclick="showUpdateCommentForm(\${c_idx})">수정</button>
            <button onclick="deleteComment(\${c_idx}, true)">삭제</button>
        </div>--%>
        <div id="btn-\${c_idx}-update" style="display: none">
        <a onclick="updateComment(\${c_idx}, true)" class="mini-btn3"><span>확인</span></a>
            <div class="reset"/>
        </div>
        {{/if}}
    </div>
</script>
<%-- End : 대댓글 --%>

<%----------------------- HTML -----------------------%>
<header>

    <c:if test="${fn:contains(url, '/inquiry/')}">
        <div class="ico_lt">
            <a onclick="javascript:history.back()"><img src="/images/ico_back_01.png"></a>
        </div>
        <div class="title">
            <a class="headerLogo"><span>1:1문의</span></a>
        </div>
        <div class="ico_rt">
            <c:if test="${dto.status == 'YET'}"><a class="rewrite" href="${dto.i_idx}/edit"><span>수정</span></a></c:if>
        </div>
    </c:if>
</header>
<div id="inquiryRetrieve">
    <div class="title"><span>${dto.title}</span></div>
    <div class="info">
        <span>
            ${dto.upload_date},
            <c:if test="${dto.i_idx2 != 0}">재문의</c:if>
            <c:if test="${dto.i_idx2 == 0}">최초문의</c:if>
        </span>
        <c:if test="${dto.i_idx2 != 0}"><a href="/inquiry/${dto.i_idx2}"><span>>> 최초 문의 보러가기</span></a></c:if>
    </div>
    <div class="content">
        <span>${dto.content}</span>
    </div>
    <div class="imgPreview">
        <ul>
            <c:forEach var="files" items="${dto.fileNames}">
                <li><div><img src="/files/${files.tableDir.directoryName}/${files.serverFileName}"></div></li>
            </c:forEach>
        </ul>
    </div>
    <c:if test="${dto.status == 'YET'}">
        <a class="deletePost" href="javascript:deletePost()"><span>글 삭제</span></a>
    </c:if>
    <div class="blueLine"></div>
    <c:if test="${memberDTO.authorities == 'ADMIN'}">
        <form:form modelAttribute="inquiryDTO" action="">
            <form:radiobutton path="status" value="YET" label="답변 대기 중"/>
            <form:radiobutton path="status" value="IN_PROCESS" label="답변 완료"/>
            <form:radiobutton path="status" value="DONE" label="문의 종료"/>
            <button type="button" onclick="changeInquiryStatus()">문의상태 변경하기</button>
        </form:form>
        <div class="whitegrayLine"></div>
    </c:if>

    <c:if test="${memberDTO.authorities != 'ADMIN'}">
        <c:if test="${dto.status != 'YET' && dto.status != 'RE_INQUIRY'}">
            <button type="button" onclick="processReInquiry()" class="mini-btn2">재문의하기</button>
        </c:if>
        <c:if test="${dto.status == 'IN_PROCESS'}">
            <button type="button" onclick="terminateInquiry()" class="mini-btn2">문의 종료하기</button>
        </c:if>
        <c:if test="${dto.status == 'IN_PROCESS' || (dto.status != 'YET' && dto.status != 'RE_INQUIRY')}">
            <div class="grayLine"></div>
        </c:if>
    </c:if>

    <div class="commentBox">
        <div id="comment-list"></div>
    </div>
    <div class="grayLine"></div>
    <div class="writeComment">
        <textarea id="comment" placeholder="댓글을 입력해주세요"></textarea>
        <a href="javascript:createNewComment()"><span>등록</span></a>
    </div>
</div>
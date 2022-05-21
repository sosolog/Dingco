package com.dingco.pedal.ADMIN.MEMBER.sevice;

import com.dingco.pedal.ADMIN.MEMBER.dao.AdminMemberDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;

@Service("AdminMemberService")
@RequiredArgsConstructor
public class AdminMemberSerivceImpl implements AdminMemberService {

    @Autowired
    AdminMemberDAO adminMemberDAO;

    /**
     * 페이징 구분 기준 세팅(상수)
     * perPage = 한 페이지에 보여줄 게시글 수
     * pagesPerBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int perPage = 10;
    private static final int pagesPerBlock = 5;

    /**
     * 사용자 회원 목록 가져오기
     * @author 명지
     * @param cp : curPage, 현재 페이지
     * @param sch : searchKey, 검색어
     * @return 사용자 회원 목록 리스트
     */
    @Override
    public PageDTO selectAllUser(int cp, String sch) throws Exception {

        int offset = (cp - 1) * perPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("sch", sch);
        map.put("offset", offset);

        int totalRecord = adminMemberDAO.cntAllUser(map); // Notice 전체 레코드 개수 조회
        List<MemberDTO> dtolist = adminMemberDAO.selectAllUser(map);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<MemberDTO> pageDTO = new PageDTO<MemberDTO>(dtolist, perPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(pagesPerBlock);
        return pageDTO;
    }
}

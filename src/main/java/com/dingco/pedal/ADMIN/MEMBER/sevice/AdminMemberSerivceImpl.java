package com.dingco.pedal.ADMIN.MEMBER.sevice;

import com.dingco.pedal.ADMIN.MEMBER.dao.AdminMemberDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("AdminMemberService")
@RequiredArgsConstructor
public class AdminMemberSerivceImpl implements AdminMemberService {

    private final AdminMemberDAO mDAO;

    private final int perPage = 10;
    private final int pagesPerBlock = 5;

    /**
     * 사용자 회원 목록 가져오기
     * @author 명지
     * @param cp : curPage, 현재 페이지
     * @param sch : searchKey, 검색어
     * @return 사용자 회원 목록 리스트
     */
    @Override
    public PageDTO selectUserPaging(int cp, String sch) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("curPage", cp);
        map.put("searchKey", sch);

//        int limit = perPage;
//        int offset = (cp - 1) * perPage;
//        int totalRecord = countTotalInquiries(dto, sch);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("m_idx", dto.getM_idx());
//        map.put("authorities", dto.getAuthorities());
//        map.put("searchKey", sch);
//        List<InquiryDTO> inquiryList = mDAO.selectUserPaging(map);
//
//                session.selectList("com.config.InquiryMapper.showUserInquiry", map, new RowBounds(offset, limit));
//        PageDTO<InquiryDTO> pageDTO = new PageDTO<>(inquiryList, perPage, totalRecord, curPage);
//
//        pageDTO.setPageListInBlock(pagesPerBlock);
//
//        return mDao.selectUserPaging(map);
        return null;
    }
}

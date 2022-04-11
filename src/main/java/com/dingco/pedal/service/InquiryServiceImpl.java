package com.dingco.pedal.service;

import com.dingco.pedal.dao.FileDAO;
import com.dingco.pedal.dao.InquiryDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.util.FileName;
import com.dingco.pedal.util.TableDir;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("inquiryService")
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDAO inquiryDAO;
    private final FileDAO fileDAO;

//    @Override
//    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
//        return dao.showUserInquiry(dto);
//    }

    @Override
    public PageDTO<InquiryDTO> showUserInquiry(MemberDTO dto, int curPage) throws Exception {
        return inquiryDAO.showUserInquiry(dto, curPage);
    }

    @Override
    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception {
        InquiryDTO inquiryDTO = inquiryDAO.showOneUserInquiry(i_idx);

        HashMap<String, Object> map = new HashMap<>();
        map.put("tableDir", TableDir.INQUIRY);
        map.put("idx", inquiryDTO.getI_idx());

        List<FileName> fileNames = fileDAO.showImages(map);
        inquiryDTO.setFileNames(fileNames);
        return inquiryDTO;
    }

    @Override
    @Transactional
    public int writeUserInquiry(InquiryDTO dto) throws Exception {
        int result = inquiryDAO.writeUserInquiry(dto);
        if (dto.getFiles() != null){
            result = fileDAO.uploadImages(dto.getFileNames());
        }
        return result;
    }

    @Override
    public int updateUserInquiry(InquiryDTO dto) throws Exception {
        int result = inquiryDAO.updateUserInquiry(dto);
        if (dto.getFiles() != null){
            result = fileDAO.uploadMoreImages(dto.getFileNames(), dto.getI_idx());
        }
        return result;
    }

    @Override
    public int updateUserInquiryStatus(InquiryDTO dto) throws Exception {
        return inquiryDAO.updateUserInquiryStatus(dto);
    }

    @Override
    public int deleteUserInquiry(int i_idx) throws Exception {
        int result = fileDAO.deleteAllImagesInPost(i_idx, TableDir.INQUIRY);
        result = inquiryDAO.deleteUserInquiry(i_idx);
        return result;
    }

    @Override
    public int deleteImage(int img_idx) throws Exception {
        return fileDAO.deleteImage(img_idx);
    }
}

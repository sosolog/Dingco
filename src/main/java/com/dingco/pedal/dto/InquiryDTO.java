package com.dingco.pedal.dto;

import com.dingco.pedal.util.FileName;
import com.dingco.pedal.util.InquiryStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class InquiryDTO {
    private List<FileName> fileNames = new ArrayList<>();

    private int i_idx;          // 문의 고유번호
    private int m_idx;          // 작성자 고유번호
    private String userid;      // 작성자 아이디
    private String username;    // 작성자 이름
    private String category_id; // 카테고리
    private String title;       // 제목
    private String content;     // 내용
    private String upload_date; // 업로드 날짜
    private String last_updated_date; // 마지막 수정 날짜
    private List<MultipartFile> files;
    private int i_idx2;         // 상위 문의 고유번호
    private InquiryStatus status; // 문의 상태

}

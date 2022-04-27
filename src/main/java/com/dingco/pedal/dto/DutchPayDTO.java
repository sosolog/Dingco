package com.dingco.pedal.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public class DutchPayDTO {
    private List<PayDTO> payList;
    private int totalPay; // 총 결제금액
    // 더치페이 계산해주는 로직을 거친후 결과 저장
    private List<DutchPayResultDTO> dutchpayResultList;// 더치페이 결과...
    private String option; // 절사옵션 (10원, 100원, 1000원)
    // 더치페이결과
    // 언제 정산할 건지...
    //
    private String createDate; // 정산하기 한 날
    private String dueDate; // 정산 마감일

    private List<MultipartFile> imageFile;
}

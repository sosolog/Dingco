package com.dingco.pedal.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Data
public class DutchPayDTO {
    private int pr_idx;
    private int dp_idx;
    private String dutchPayName;
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

    public List<DutchPayResultDTO> calculateDutchPay() {
        List<PayDTO> payList = this.getPayList();
        System.out.println("payList = " + payList);

        // TODO: 여기서 payList로 지지고 볶고 해서 로직 완성해주세욤 ㅎㅎㅎ


        return null;
    }
}

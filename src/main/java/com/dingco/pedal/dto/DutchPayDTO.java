package com.dingco.pedal.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        System.out.println("payList = " + payList);

        // TODO: 여기서 payList로 지지고 볶고 해서 로직 완성해주세욤 ㅎㅎㅎ
        return null;
    }

    public List<DutchPayResultDTO> calculateDutchPay_WJH() {
            List<PayDTO> payList = this.getPayList();
//            System.out.println("payList = " + payList);

            // TODO: 여기서 payList로 지지고 볶고 해서 로직 완성해주세욤 ㅎㅎㅎ
            Map<PayGroupMemberDTO, Map<PayGroupMemberDTO, Float>> calcMap = new HashMap<>();
            List<DutchPayResultDTO> dutchPayResultDTOList = new ArrayList<>();

            PayGroupMemberDTO maxPayGroupMember = null;
            int max = 0;

            for (PayDTO paydto: payList) {
                boolean check = false;
                float price = paydto.getPrice()/paydto.getParticipants().size();
                PayGroupMemberDTO payer = paydto.getPayMember();

//                System.out.println(paydto.getPrice());
//                System.out.println(payer);

                if(paydto.getParticipants().contains(paydto.getPayMember())){
                    check = true;
                }

                for (PayGroupMemberDTO nameDTO: paydto.getParticipants()) {

                    PayGroupMemberDTO parname = nameDTO;

//                    System.out.println(parname);

                    if(parname.getPrgm_idx()!=payer.getPrgm_idx()){

                        if(calcMap.get(parname)==null){

                            Map<PayGroupMemberDTO,Float> map = new HashMap<>();
                            map.put(payer,price);
                            calcMap.put(parname,map);

                        }else{

                            if(calcMap.get(parname).get(payer)==null){
                                calcMap.get(parname).put(payer,price);

                            }else{

                                calcMap.get(parname).put(payer,calcMap.get(parname).get(payer)+price);

                            }

                        }

                        if(calcMap.get(payer)!=null){
                            if(calcMap.get(payer).get(parname)!=null){

                                if(calcMap.get(payer).get(parname)<calcMap.get(parname).get(payer)){
                                    calcMap.get(parname).put(payer,calcMap.get(parname).get(payer)-calcMap.get(payer).get(parname));
                                    calcMap.get(payer).remove(parname);
                                }else if(calcMap.get(payer).get(parname)>calcMap.get(parname).get(payer)){
                                    calcMap.get(payer).put(parname,calcMap.get(payer).get(parname)-calcMap.get(parname).get(payer));
                                    calcMap.get(parname).remove(payer);
                                }else{
                                    calcMap.get(parname).remove(payer);
                                    calcMap.get(payer).remove(parname);
                                }
                            }
                        }
                        if(calcMap.get(parname).size()>max){
                            maxPayGroupMember = parname;
                            max = calcMap.get(parname).size();
                        }
                    }


                }

//                System.out.println(check);
//                System.out.println(price);
//                System.out.println("---------------------------------------");
            }

            for (PayGroupMemberDTO key: calcMap.keySet()) {

                if(key.getPrgm_idx()!=maxPayGroupMember.getPrgm_idx()){

                    float allCost = 0;
                    for (PayGroupMemberDTO paykey: calcMap.get(key).keySet()){
                        allCost += calcMap.get(key).get(paykey);
                        if(paykey.getPrgm_idx()!=maxPayGroupMember.getPrgm_idx()){

                            if(calcMap.get(maxPayGroupMember).get(paykey)!=null){
                                calcMap.get(maxPayGroupMember).put(paykey,calcMap.get(maxPayGroupMember).get(paykey)+calcMap.get(key).get(paykey));
                            }else{
                                calcMap.get(maxPayGroupMember).put(paykey,calcMap.get(key).get(paykey));
                            }
                        }
//                        calcMap.get(key).remove(paykey);
                    }
                    if(allCost!=0){
                    calcMap.get(key).put(maxPayGroupMember,allCost);

                    DutchPayResultDTO dto = new DutchPayResultDTO();
                    dto.setRecipient(maxPayGroupMember);
                    dto.setPaid(false);
                    dto.setSender(key);
                    dto.setAmount((int) allCost);
                    dutchPayResultDTOList.add(dto);
                    }

                }
            }

        for (PayGroupMemberDTO payMember: calcMap.get(maxPayGroupMember).keySet()) {
            DutchPayResultDTO dto = new DutchPayResultDTO();
            dto.setPaid(false);
            dto.setSender(maxPayGroupMember);
            dto.setRecipient(payMember);
            float cc = calcMap.get(maxPayGroupMember).get(payMember);
            dto.setAmount(Math.round(cc));
            dutchPayResultDTOList.add(dto);
        }
//            System.out.println(calcMap);
        for (DutchPayResultDTO d:dutchPayResultDTOList) {
            System.out.println(d.getSender().getPayMember_name()+" to "+d.getRecipient().getPayMember_name()+" 가격: "+d.getAmount());
        }
            return dutchPayResultDTOList;
        }
}


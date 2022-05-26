package com.dingco.pedal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class DutchPayDTO {
    private int pr_idx;
    private int dp_idx;

    @JsonProperty("name")
    private String dutchPayName;

    @JsonProperty("pay_list")
    private List<PayDTO> payList;

    @JsonProperty("total")
    private int totalPay; // 총 결제금액
    // 더치페이 계산해주는 로직을 거친후 결과 저장
    @JsonProperty("result_list")
    private List<DutchPayResultDTO> dutchpayResultList;// 더치페이 결과...
    private String option; // 절사옵션 (10원, 100원, 1000원)
    // 더치페이결과
    // 언제 정산할 건지...
    //
    @JsonProperty("create_date")
    private String createDate; // 정산하기 한 날
    @JsonProperty("due_date")
    private String dueDate; // 정산 마감일

    private List<MultipartFile> imageFile;
  
        


    public List<DutchPayResultDTO> calculateDutchPay(List<PayGroupMemberDTO> payRoomMembers) {
        List<PayDTO> payList = this.getPayList();

        // TODO: 아래 메소드들을 주석처리/해제 해서 알고리즘 결과 확인하세요!
        List<DutchPayResultDTO> dutchPayResultList = calculateDutchPay_PSH(payRoomMembers); // 소현 계산 알고리즘
//        List<DutchPayResultDTO> dutchPayResultList = calculateDutchPay_WJH(); // 주황 계산 알고리즘
        setDutchpayResultList(dutchPayResultList);
        return dutchPayResultList;
    

    }

    private List<DutchPayResultDTO> calculateDutchPay_WJH() {
            List<PayDTO> payList = this.getPayList();
//            System.out.println("payList = " + payList);

            /*
            calcMap : {a(주는 사람)={b(받는 사람)=1000(가격)} ...} 형식으로 설계
            dutchPayResultDTOList : calcMap의 정보를 이용해서 리스트로 만든 다음 리턴
             */
            Map<PayGroupMemberDTO, Map<PayGroupMemberDTO, Float>> calcMap = new HashMap<>();
            List<DutchPayResultDTO> dutchPayResultDTOList = new ArrayList<>();

            /*
            maxPayGroupMember : 보내야 할 사람이 가장 많은 사람의 정보
            max : 보내야 할 사람(=calcMap.size())
             */
            PayGroupMemberDTO maxPayGroupMember = null;
            int max = 0;

            /*
            결제 목록에 있는 payDTO들을 하나씩 꺼내서 정보를 calcMap에다 담기위한 과정
             */
            for (PayDTO paydto: payList) {

                /*
                price : 하나의 결제에 가격과 참여한 인원을 나눈 값을 float으로 저장해둔다
                payer : 결제를 한 사람
                 */
                float price = paydto.getPrice()/paydto.getParticipants().size();
                PayGroupMemberDTO payer = paydto.getPayMember();

//                System.out.println(paydto.getPrice());
//                System.out.println(payer);

                /*
                하나의 결제에 있는 결제참여자들의 정보를 하나씩 빼서 payer에게 price를 줘야한다는 정보를 calcMap에다 넣는 과정
                 */
                for (PayGroupMemberDTO parname: paydto.getParticipants()) {

//                    System.out.println(parname);



                     /*
                            결제 참여자가 payer일 수도 있을 경우를 염두하기
                            같을 경우 자기자신한테 돈을 보내지 않아도 됨
                            */
                    if (parname.getPrgm_idx() != payer.getPrgm_idx()) {

                            /*
                            한 사람의 결제 참여자(parname)가 calcMap에 없는 경우 (payer,price)로 map을 만들어서
                            (parname,map)으로 calcMap에 넣고,
                            있는 경우 map에서 받을 사람이 있는지 확인하고, 있으면 price를 더하고, 없으면 (payer,price)를 새로 만들어서 put한다.
                             */

                        // calcMap에 parname이 없는 경우
                        if (calcMap.get(parname) == null) {

                            Map<PayGroupMemberDTO, Float> map = new HashMap<>();
                            map.put(payer, price);
                            calcMap.put(parname, map);

                            // calcMap에 parname이 있는 경우
                        } else {
                            // map에 payer가 없는 경우
                            if (calcMap.get(parname).get(payer) == null) {
                                calcMap.get(parname).put(payer, price);

                                // map에 payer가 있는 경우
                            } else {

                                calcMap.get(parname).put(payer, calcMap.get(parname).get(payer) + price);

                            }

                        }

                      /*
                        calcMap에 parname에 대한 정보를 넣기 전 결제 참여자(parname)가 payer였을 때,
                        기존 payer가 결제 참여자였을 때 지불해야 할 가격과 비교하는 과정
                         */
                        if (calcMap.get(payer) != null) {
                            if (calcMap.get(payer).get(parname) != null) {

                                if (calcMap.get(payer).get(parname) < price) {
                                    calcMap.get(parname).put(payer, price - calcMap.get(payer).get(parname));
                                    calcMap.get(payer).remove(parname);
                                } else if (calcMap.get(payer).get(parname) > price) {
                                    calcMap.get(payer).put(parname, calcMap.get(payer).get(parname) - price);
                                } else {
                                    calcMap.get(payer).remove(parname);
                                }
                            }

                        }

                        //보내야 할 횟수가 가장 많은 사람을 구하는 함수
                        if (calcMap.get(parname).size() > max) {
                            maxPayGroupMember = parname;
                            max = calcMap.get(parname).size();
                        }
                    }


                }//end : 결제목록 하나

//                System.out.println(check);
//                System.out.println(price);
//                System.out.println("---------------------------------------");

            }//end : 결제목록 전체


            // 자기가 여러 사람에게 보내야 할 총금액을 보내야 할 횟수가 가장 많은 사람에게 보내주고 그 사람이 보내야 할 돈을 합산시키는 코드

            for (PayGroupMemberDTO key: calcMap.keySet()) {

                if(key.getPrgm_idx()!=maxPayGroupMember.getPrgm_idx()){
                    //자기가 여러 사람에게 보내야 할 총금액
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

                    //리턴해야 할 DutchPayResultDTO에 정보 넣는 코드
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


        //리턴해야 할 List<DutchPayResultDTO>에 정보 넣는 코드
        for (PayGroupMemberDTO payMember: calcMap.get(maxPayGroupMember).keySet()) {
            DutchPayResultDTO dto = new DutchPayResultDTO();
            dto.setPaid(false);
            dto.setSender(maxPayGroupMember);
            dto.setRecipient(payMember);
            float cc = calcMap.get(maxPayGroupMember).get(payMember);
            dto.setAmount(Math.round(cc));
            dutchPayResultDTOList.add(dto);
        }
            System.out.println(calcMap);

            return dutchPayResultDTOList;
    }


    private int adjustCuttingOption(int amount){
        String option = getOption();
        if (option != null) {
            double tenth = Math.pow(10, option.length() - 1);
            long roundedAmount = Math.round(amount / tenth);
            return (int) (roundedAmount * tenth);
        }
        return amount;
    }

    private List<DutchPayResultDTO> calculateDutchPay_PSH(List<PayGroupMemberDTO> payRoomMembers) {
        List<PayDTO> payList = this.getPayList();

        // 페이방 멤버 리스트
        List<Integer> payRoomMembersIdx = new ArrayList<>();
        List<String> payRoomMembersName = new ArrayList<>();
        payRoomMembers.stream().forEach(p -> {
            payRoomMembersIdx.add(p.getPrgm_idx());
            payRoomMembersName.add(p.getPayMember_name());
        });

        // 멤버 idx -> graph 에서의 해당 멤버 idx 변환
        HashMap<Integer, Integer> graphIdxMapper = makeGraphIdxMapperFromPrgmIdx(payRoomMembersIdx);
        System.out.println("graphIdxMapper = " + graphIdxMapper);

        int size = payRoomMembersIdx.size();
        int[][] graph = makeGraph(size, payList, graphIdxMapper);
        showGraphInClear(graph, payRoomMembersName);

        HashMap<Integer, HashMap<Integer, Integer>> reducedGraph = reduceGraph(graph);
//        showGraphInClear(graph, payRoomMembersName);
        return makeDutchPayResultDTOList(reducedGraph, payRoomMembersIdx, payRoomMembersName);
    }

    private void showGraphInClear(int[][] graph, List<String> payRoomMembersName) {
        int j = 0;
        System.out.printf("%s", "구분");
        for (String name : payRoomMembersName) {
            System.out.printf("%8s ", name);
        }
        System.out.println();
        for (int[] arr: graph) {
            System.out.printf("%s ", payRoomMembersName.get(j++));
            for (int a : arr) {
                System.out.printf("%10d ", a);
            }
            System.out.println();
        }
    }

    private HashMap<Integer, Integer> makeGraphIdxMapperFromPrgmIdx(List<Integer> payRoomMembersIdx) {
        HashMap<Integer, Integer> graphIdxMapper = new HashMap<>();
        for (int i = 0; i < payRoomMembersIdx.size(); i++) {
            graphIdxMapper.put(payRoomMembersIdx.get(i), i);
        }
        return graphIdxMapper;
    }

    private int[][] makeGraph(int graphSize, List<PayDTO> payList, HashMap<Integer, Integer> mapper){
        int[][] graph = new int[graphSize][graphSize];

        for (PayDTO payDTO : payList) {
            int payer_idx = payDTO.getPayMember().getPrgm_idx(); // 돈 낸 사람 idx
            int num_parties = payDTO.getParticipants().size(); // 참여자 명 수

            int n_bbang = payDTO.getPrice() / num_parties; // N 빵 금액
            int n_bbang_remain = payDTO.getPrice() % num_parties; // N 빵 안되는 나머지

            // 참여자 순회
            for (PayGroupMemberDTO m: payDTO.getParticipants()) {
                int x = mapper.get(m.getPrgm_idx());
                int y = mapper.get(payer_idx);
                graph[x][y] += n_bbang;

                // N빵으로 안 나눠지는 나머지는 차례대로 1원씩 가지기
                if (n_bbang_remain > 0) {
                    graph[x][y] += 1;
                    n_bbang_remain--;
                }
            }
        }

        // 서로 주고 받는 경우, 더 큰 금액 쪽으로 몰기
        for (int i = 0; i < graph.length; i++) {
            graph[i][i] = 0;
            for (int k = i+1; k < graph[i].length; k++) {
                if (graph[i][k] >= graph[k][i]){
                    graph[i][k] -= graph[k][i];
                    graph[k][i] = 0;
                } else {
                    graph[k][i] -= graph[i][k];
                    graph[i][k] = 0;
                }
            }
        }
        return graph;
    }
    
    private HashMap<Integer, HashMap<Integer, Integer>> reduceGraph(int[][] arr){
        
        // sender 가 돈을 보내야하는 recipient 수가 많은 순서대로 sender 를 정렬해주는 reverse_temp_amountarator
        Comparator<Map.Entry<Integer, HashMap<Integer, Integer>>> hasMoreRecipient = new Comparator<Map.Entry<Integer, HashMap<Integer, Integer>>>() {
            @Override
            public int compare(Map.Entry<Integer, HashMap<Integer, Integer>> o1, Map.Entry<Integer, HashMap<Integer, Integer>> o2) {
                return o1.getValue().size() < o2.getValue().size() ? 1 : -1;
            }
        };

        // 특정 sender 가 recipient 에게 보내야하는 금액이 큰 순서대로 recipient 정렬 
        Comparator<Map.Entry<Integer, Integer>> higherAmountOfDebt = new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() < o2.getValue() ? 1 : -1;
            }
        };

        HashMap<Integer, HashMap<Integer, Integer>> senderToRecipient = new HashMap<>();
        for (int sender = 0; sender < arr.length; sender++) {
            HashMap<Integer, Integer> recipientToAmount = new HashMap<>();
            for (int recipient = 0; recipient < arr[sender].length; recipient++) {
                int amount = arr[sender][recipient];
                if(amount != 0){
                    recipientToAmount.put(recipient, arr[sender][recipient]);
                }
            }
            senderToRecipient.put(sender, recipientToAmount);
        }

        // 아래 과정을 최대 5회 반복
        for (int test = 0; test < 5; test++) {
            List<Map.Entry<Integer, HashMap<Integer, Integer>>> entryList_sTOr = new ArrayList<>(senderToRecipient.entrySet());
            entryList_sTOr.sort(hasMoreRecipient); // sender 가 돈을 보내야하는 recipient 수가 많은 순서대로 sender 를 정렬

            System.out.println("entryList_sTOr = " + entryList_sTOr);

            boolean isAllHasLessThanOneRecipients = true;

            for (int i = 0; i < entryList_sTOr.size(); i++) {
                // sender_num = s_num (보내야하는 사람)
                Integer s_num = entryList_sTOr.get(i).getKey();
                HashMap<Integer, Integer> recipient_amount = entryList_sTOr.get(i).getValue();

                if(recipient_amount.size() > 1){ // 해당 sender 가 보내야하는 recipient 가 2명 이상인 경우
                    isAllHasLessThanOneRecipients = false;

                    // 해당 sender 가 보내야하는 (recipient = amount)의 리스트
                    List<Map.Entry<Integer, Integer>> entryList_rTOa = new ArrayList<>(recipient_amount.entrySet());
                    entryList_rTOa.sort(higherAmountOfDebt); // recipient 에게 보내야하는 금액이 큰 순서대로 recipient 정렬

                    // sender 가 보내야하는 금액이 가장 큰 recipient (현재 sender 에서 기준 recipient)
                    // base_recipient_num = base_r_num
                    int base_r_num = entryList_rTOa.get(0).getKey();

                    for (int j = 1; j < entryList_rTOa.size(); j++) {
                        // (s_num -> base_r_num)에게 보내야하는 금액 : 기준 recipient 에게 보내야 하는 금액! 
                        int base_amount = senderToRecipient.get(s_num).get(base_r_num);

                        // 기준 recipient 를 제외한 나머지 recipient 중 한명(받아야하는 금액 큰 순서대로)
                        // 다음 상위 recipient : next_recipient_num = next_r_num
                        Integer next_r_num = entryList_rTOa.get(j).getKey();
                        // (s_num -> next_r_num)에게 보내야하는 금액
                        Integer next_amount = senderToRecipient.get(s_num).get(next_r_num);

                        System.out.println("base_r_num = "+ base_r_num + " base_amount = " + base_amount);
                        System.out.println("next_r_num = "+ next_r_num + " next_amount = " + next_amount);


                        // 원래 (s_num -> next_r_num) 보내야 하는 것을 (s_num -> base_r_num) 보낸다면, [하고자 하는 행위! (타겟 행위)]
                        // 해당 금액(next_amount)을 (base_r_num -> next_r_num) 보내야 함
                        // 1. 만약 현재 (base_r_num -> next_r_num) 보낼 금액(temp_amount)이 있다고 하면, temp_amount += next_amount

                        // 2. 만약 현재 (base_r_num -> next_r_num) 보낼 금액(temp_amount)이 없다고 하면, 
                        // -- 다른 말로하면, (next_r_num -> base_r_num) 보낼 금액이 있다면!

                        // 2-1. (next_r_num -> base_r_num) 보낼 금액(reverse_temp_amount)이 있고 & reverse_temp_amount - next_amount >= 0 이면,
                        //      reverse_temp_amount -= next_amount
                        // 2-2. (next_r_num -> base_r_num) 보낼 금액(reverse_temp_amount)이 없거나, (OR) reverse_temp_amount - next_amount < 0 이면,
                        //      원래 (s_num -> next_r_num) 보내야 하는 것을 (s_num -> base_r_num) 로 보내는 것 안한다!

                        // 그런데, 원래 금액에서 - 해가는 것이 더 좋은 방법! => 2번 조건을 먼저 체크한다!

                        boolean isProcessed = false;

                        // 2번 조건 :
                        // sender = next_recipient , recipient = base_r_num
                        HashMap<Integer, Integer> candidateOfRecipient = senderToRecipient.get(next_r_num);
                        if (candidateOfRecipient != null) { // sender(next_r_num) 가 보내야 하는(보낼 수 있는) recipient 존재 유무 체크
                            // 원래 (next_r_num -> base_r_num) 보낼 금액 [없을 수도 있기 때문에 체크 필요]
                            Integer reverse_temp_amount = candidateOfRecipient.get(base_r_num);
                            System.out.println("reverse_temp_amount = " + reverse_temp_amount);

                            // 2-1 번 조건
                            if(reverse_temp_amount != null && reverse_temp_amount >= next_amount){
                                // [타겟 행위] 실행 : (s_num -> next_r_num) 금액 을 (s_num -> base_r_num) 금액 에 더해주기
                                senderToRecipient.get(s_num).put(base_r_num, base_amount + next_amount);
                                senderToRecipient.get(s_num).remove(next_r_num);  // (s_num -> next_r_num) 금액은 삭제

                                // 원래 (next_r_num -> base_r_num) 금액 에서 (s_num -> next_r_num) 금액 빼주기
                                senderToRecipient.get(next_r_num).put(base_r_num, reverse_temp_amount - next_amount);
                                isProcessed = true;
                            }
                            // 2-2 번 조건 : 아무 것도 안함!

                        }

                        // 2번 조건에서 프로세스가 진행 안된 경우, 1번 조건 진행
                        if (!isProcessed) {
                            // 1번 조건 :
                            // sender = base_r_num , recipient = next_r_num
                            candidateOfRecipient = senderToRecipient.get(base_r_num);

                            if(candidateOfRecipient != null){ // sender(base_r_num) 가 보내야 하는(보낼 수 있는) recipient 존재 유무 체크
                                // 원래 (base_r_num -> next_r_num) 보낼 금액 [없을 수도 있기 때문에 체크 필요]
                                Integer temp_amount = senderToRecipient.get(base_r_num).get(next_r_num);
                                System.out.println("temp_amount = " + temp_amount);

                                if(temp_amount != null) {
                                    // [타겟 행위] 실행 : (s_num -> next_r_num) 금액 을 (s_num -> base_r_num) 금액 에 더해주기
                                    senderToRecipient.get(s_num).put(base_r_num, base_amount + next_amount);
                                    senderToRecipient.get(s_num).remove(next_r_num); // (s_num -> next_r_num) 금액은 삭제

                                    // 원래 (base_r_num -> next_r_num) 금액 에서 (s_num -> next_r_num) 금액 더해주기
                                    senderToRecipient.get(base_r_num).put(next_r_num, temp_amount + next_amount);
                                }
                            }
                        }
                        System.out.println("senderToRecipient = " + senderToRecipient);

                    }
                }
            }

            if(isAllHasLessThanOneRecipients){
                break;
            }
        }
        return senderToRecipient;
    }

    private List<DutchPayResultDTO> makeDutchPayResultDTOList(HashMap<Integer, HashMap<Integer, Integer>> reducedGraph,
                                                              List<Integer> payRoomMembersIdx,
                                                              List<String> payRoomMembersName){
        List<DutchPayResultDTO> dutchPayResultList = new ArrayList<>();

        Set<Map.Entry<Integer, HashMap<Integer, Integer>>> entries = reducedGraph.entrySet();
        Iterator<Map.Entry<Integer, HashMap<Integer, Integer>>> iterator = entries.iterator();
        for (;iterator.hasNext();){
            Map.Entry<Integer, HashMap<Integer, Integer>> next = iterator.next();
            Integer senderIdx = next.getKey();
            PayGroupMemberDTO sender = new PayGroupMemberDTO();
            sender.setPrgm_idx(payRoomMembersIdx.get(senderIdx));
            sender.setPayMember_name(payRoomMembersName.get(senderIdx));

            Set<Map.Entry<Integer, Integer>> recipientEntries = next.getValue().entrySet();
            Iterator<Map.Entry<Integer, Integer>> recipientIterator = recipientEntries.iterator();
            for (;recipientIterator.hasNext();){
                Map.Entry<Integer, Integer> nextRecipient = recipientIterator.next();
                Integer recipientIdx = nextRecipient.getKey();

                PayGroupMemberDTO recipient = new PayGroupMemberDTO();
                recipient.setPrgm_idx(payRoomMembersIdx.get(recipientIdx));
                recipient.setPayMember_name(payRoomMembersName.get(recipientIdx));

                Integer amount = nextRecipient.getValue();

                DutchPayResultDTO dutchPayResultDTO = new DutchPayResultDTO();
                dutchPayResultDTO.setSender(sender);
                dutchPayResultDTO.setRecipient(recipient);
                dutchPayResultDTO.setAmount(adjustCuttingOption(amount));

                dutchPayResultList.add(dutchPayResultDTO);
            }
        }

        return dutchPayResultList;
    }
    
}


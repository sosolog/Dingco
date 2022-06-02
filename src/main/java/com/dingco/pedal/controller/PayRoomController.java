package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.*;
import com.dingco.pedal.service.PayRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class PayRoomController {

    @Autowired
    PayRoomService payRoomService;

    /**
     * payRoom(페이방) 세부 사항 보기 UI
     * @param pr_idx payRoom 번호
     * @param memberDTO 세션에 저장된 로그인 정보
     * @return payRoom 이 현재 로그인된 회원의 payRoom 이면, 세부 화면으로
     *         아니라면, 메인 화면으로
     * @throws Exception
     */
    @GetMapping("/pay/{pr_idx}")
    public String payRoomRetrieve(@PathVariable("pr_idx") int pr_idx,@Login MemberDTO memberDTO, Model model) throws Exception {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("pr_idx", pr_idx);
        map.put("m_idx", memberDTO.getM_idx());
        PayRoomDTO payRoomDTO = payRoomService.selectPayRoomRetrieve(map);

        String next = "";
        if(payRoomDTO == null){
            next = "redirect:/main";
        }else{
            ObjectMapper mapper = new ObjectMapper();
            String payRoomJson = mapper.writeValueAsString(payRoomDTO);
            model.addAttribute("payRoom",payRoomJson);
            next = "payRoomRetrieve";
        }
        return next;
    }

//    /** 사용 X
//     * payRoom(페이방) 목록 보기
//     */
//    @GetMapping("/pay/list")
//    public String payRoomList(Model model,@Login MemberDTO memberDTO) throws Exception {
//        int m_idx = memberDTO.getM_idx();
//        List<PayRoomDTO> list = payRoomService.selectPayRoom(m_idx);
//        log.info("payRoomService.selectPayRoom(m_idx) = " + payRoomService.selectPayRoom(m_idx));
//
//        ObjectMapper mapper = new ObjectMapper();
//        String payRoomJson = mapper.writeValueAsString(list);
//        log.info("payRoomJson = " + payRoomJson);
//        model.addAttribute("payRoomList",payRoomJson);
//
//        return "payRoomList";
//    }

    /**
     * payRoom 생성 (이름, 참여자 목록 insert)
     * @param roomName 생성할 페이방 이름
     * @param memberList 생성할 페이방의 참여자 목록
     * @param memberDTO 세션에 저장된 로그인 정보
     * @return 생성된 payRoom 의 번호
     * @throws Exception
     */
    @PostMapping("/pay/room")
    @ResponseBody
    public int payRoom(@RequestParam("roomName") String roomName,
                       @RequestParam("memberList[]") List<String> memberList,
                       @Login MemberDTO memberDTO) throws Exception{

        int m_idx = memberDTO.getM_idx();

        List<PayGroupMemberDTO> payGroupMemberDTOList = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            PayGroupMemberDTO payGroupMemberDTO = new PayGroupMemberDTO();
            payGroupMemberDTO.setPayMember_name(memberList.get(i));
            payGroupMemberDTOList.add(payGroupMemberDTO);
        }

        PayRoomDTO payRoomDTO = new PayRoomDTO();
        payRoomDTO.setRoom_name(roomName);
        payRoomDTO.setM_idx(m_idx);
        payRoomDTO.setGroupMemberList(payGroupMemberDTOList);

        int pr_idx = payRoomService.roomInfo(payRoomDTO);
        return pr_idx;
    }


    // START : 페이방 참여자 CRUD & 참여자 계좌정보 CRUD
    /**
     * 페이방 참여자 1명 추가
     * @param payGroupMemberDTO 참여자 정보 (pr_idx, payMember_name)
     * @return 추가한 참여자 정보 (pr_idx, payMember_name, prgm_idx)
     * @throws Exception
     */
    @PostMapping("/pay/membercheck")
    @ResponseBody
    public PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        return payRoomService.memberAdd(payGroupMemberDTO);
    }

    /**
     * 페이방 참여자 1명의 더치페이(결제내역) 참여 여부 확인
     * @param payGroupMemberDTO 페이방 참여자 정보 (pr_idx, prgm_idx 확인)
     * @return 결제 내역의 결제자/참석자 에 포함된 이력이 있으면 true 반환, 아니면 false 반환
     * @throws Exception
     */
    @GetMapping("/pay/membercheck")
    @ResponseBody
    public boolean memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        return payRoomService.memberCheck(payGroupMemberDTO);
    }

    /**
     * 페이방 참여자 1명 삭제
     * @param prgm_idx 참여자 번호
     * @throws Exception
     */
    @DeleteMapping("/pay/membercheck")
    @ResponseBody
    public int memberDelete(@RequestParam int prgm_idx) throws Exception{
        return payRoomService.memberDelete(prgm_idx);
    }

    /**
     * 페이방 참여자 1명의 계좌정보 보기
     * @param prgm_idx 참여자 번호
     * @return 참여자 계좌 정보 반환
     * @throws Exception
     */
    @GetMapping("/pay/accountInfo/{prgm_idx}")
    @ResponseBody
    public PayGroupMemberDTO getAccountInfo(@PathVariable int prgm_idx) throws Exception {
        return payRoomService.selectAccount(prgm_idx);
    }

    /**
     * 페이방 참여자 1명의 계좌정보 수정
     * @param groupMemberDTO 수정할 참여자 정보
     * @throws Exception
     */
    @PutMapping("/pay/accountInfo")
    @ResponseBody
    public void putAccountInfo(@RequestBody PayGroupMemberDTO groupMemberDTO) throws Exception {
        log.debug("groupMemberDTO = {}", groupMemberDTO);
        int num = payRoomService.updateAccount(groupMemberDTO);
    }

//    /** 사용 X (계좌정보 수정로직으로 통합)
//     * 페이방 참여자 1명의 계좌정보 삭제, 실제로는 해당 부분만 null로 update!
//     */
//    @PutMapping("/pay/accountNull")
//    @ResponseBody
//    public void accountNull(@RequestParam int prgm_idx) throws Exception {
//       int num = payRoomService.accountNull(prgm_idx);
//    }

    // END : 페이방 참여자 CRUD & 참여자 계좌정보 CRUD


    /**
     * payRoom 내 더치페이 목록 보기
     * @param pr_idx 페이방 번호
     * @return 더치페이 목록 담은 list
     * @throws Exception
     */
    @GetMapping("/pay/{pr_idx}/dutch/list")
    @ResponseBody
    public List<DutchPayDTO> showDutchpayList(@PathVariable int pr_idx) throws Exception {
        return payRoomService.dutchPayListInfo(pr_idx);
    }

    /**
     * payRoom 내 더치페이 생성
     * @param dutchPayDTO 생성할 더치페이 정보
     * @return 생성된 더치페이 번호
     * @throws Exception
     */
    @PostMapping("/pay/new")
    @ResponseBody
    public int makeDutchpay(@RequestBody DutchPayDTO dutchPayDTO) throws Exception {
        return payRoomService.insertDutchPay(dutchPayDTO);
    }

    /**
     * 더치페이 1개 정보 보기
     * @param pr_idx 페이방 번호
     * @param dp_idx 더치페이방 번호
     * @return
     * @throws Exception
     */
    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public DutchPayDTO showDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception {
        return payRoomService.dutchPayInfo(pr_idx, dp_idx);
    }

    /**
     * 더치페이 내역 1개 삭제
     * @param pr_idx 페이방 번호
     * @param dp_idx 더치페이 번호
     * @throws Exception
     */
    @DeleteMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public int deleteOneDutpay(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        return payRoomService.deleteOneDutchpay(dp_idx);
    }

    /**
     * 더치페이 정보 수정 (결제 목록 추가/수정/삭제, 기본 정보 수정 포함)
     * @param changes 더치페이의 수정항목 정보
     * @throws Exception
     */
    @PostMapping("/pay/RetrieveInfo")
    @ResponseBody
    public int postRetrieveInfo(@RequestBody DutchPayChangesDTO changes) throws Exception {
        return payRoomService.updateDutchPay(changes.getInsertPayList(), changes.getUpdatePayList(), changes.getDeletePayList(), changes.getDutchInfo());
    }


    /**
     * 더치페이 기본 정보 + 결제 목록을 바탕으로 더치페이 결과 계산하기
     * @param pr_idx 페이방 번호
     * @param dp_idx 더치페이 번호
     * @return 더치페이 계산 결과를 담은 더치페이 정보
     * @throws Exception
     */
    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/result")
    @ResponseBody
    public DutchPayDTO getDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        DutchPayDTO dutchPayDTO = payRoomService.dutchPayInfo(pr_idx, dp_idx);
        List<PayGroupMemberDTO> payGroupMemberDTOS = payRoomService.showPayRoomGroupMember(pr_idx);

        List<DutchPayResultDTO> dutchPayResult = dutchPayDTO.calculateDutchPay(payGroupMemberDTOS);
        dutchPayDTO.setDutchpayResultList(dutchPayResult);

        // 정보 콘솔에 출력
        dutchPayResult.stream().forEach(d -> {
            log.info(d.getSender().getPayMember_name() + " -> " + d.getRecipient().getPayMember_name() + " = " + d.getAmount());
        });

        return dutchPayDTO;
    }

    /**
     * 더치페이 결과 가져오기
     * @param pr_idx 페이방 번호
     * @param dp_idx 더치페이 번호
     * @return 저장된 더치페이 결과 있으면 더치페이 정보(결과+기본정보) 반환
     *          없으면 null 반환
     * @throws Exception
     */
    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/result/chk")
    @ResponseBody
    public DutchPayDTO hasDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        return payRoomService.showDutchPayResultInfo(pr_idx, dp_idx);
    }

    /**
     * 더치페이 결과 저장 (정산 여부 등)
     * @param pr_idx 페이방 번호
     * @param dp_idx 더치페이 번호
     * @param resultList 더치페이 결과 리스트
     * @throws Exception
     */
    @PostMapping("/pay/{pr_idx}/dutch/{dp_idx}/result")
    @ResponseBody
    public int saveDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx, @RequestBody List<DutchPayResultDTO> resultList) throws Exception{
        return payRoomService.saveDutchPayResult(dp_idx, resultList);
    }


//    /** 사용 X
//     * 결제 정보 1개 보기
//     */
//    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
//    @ResponseBody
//    public PayDTO showOnePayInfo(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx) throws Exception {
//        return payRoomService.showOnePayInfo(p_idx);
//    }

//    /** 사용 X
//     * 더치페이 내 결제 정보 1개 입력
//     */
//    @PostMapping("/pay/{pr_idx}/dutch/{dp_idx}")
//    @ResponseBody
//    public int addPayIntoDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, PayDTO payDTO) throws Exception {
//        return payRoomService.insertPayIntoDutch(payDTO);
//    }

//    /** 사용 X
//     * 더치페이 내 결제 정보 1개 수정
//     */
//    @PutMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
//    @ResponseBody
//    public int updateOnePayInDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx, PayDTO payDTO) throws Exception {
//        return payRoomService.updateOnePayInDutchpay(payDTO);
//    }

//    /** 사용 X
//     * 더치페이 내 결제 정보 1개 삭제
//     */
//    @DeleteMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
//    @ResponseBody
//    public int deleteOnePayInDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx) throws Exception {
//        return payRoomService.deleteOnePayInDutchpay(p_idx);
//    }

//    /** 사용 X
//     * 페이방 멤버 목록 가져오기
//     */
//    @GetMapping("/pay/{pr_idx}/member")
//    @ResponseBody
//    public List<PayGroupMemberDTO> showPayRoomGroupMember(@PathVariable int pr_idx) throws Exception{
//        return payRoomService.showPayRoomGroupMember(pr_idx);
//    }

//    /** 사용 X
//     * 더치페이 기본정보 수정 (결제 목록 제외)
//     */
//    @PutMapping("/pay/{pr_idx}/dutch/{dp_idx}")
//    @ResponseBody
//    public int updateDutchPay(@PathVariable int pr_idx, @PathVariable int dp_idx, DutchPayDTO dutchPayDTO) throws Exception{
//        return payRoomService.updateDutchPay(dutchPayDTO);
//    }

//    /** 사용 X
//     * 더치페이 정보에 저장된 결제목록 한번에 수정
//     */
//    @PutMapping("/pay/RetrieveInfo")
//    @ResponseBody
//        public int putRetrieveInfo(DutchPayDTO updateDTO) throws Exception {
//        if(updateDTO.getPayList()!=null){
//            for (PayDTO paydto: updateDTO.getPayList()) {
//                payRoomService.updateOnePayInDutchpay(paydto);
//            }
//        }
//        return 1;
//    }

//    /** 사용 X
//     * 더치페이 정보에 저장된 결제목록 한번에 삭제
//     */
//    @DeleteMapping("/pay/RetrieveInfo")
//    @ResponseBody
//    public int deleteRetrieveInfo(@RequestParam("deleteArr[]") List<Integer> deleteArr) throws Exception {
//        System.out.println("deleteArr = " + deleteArr);
//        int num = 0;
//        if(deleteArr.size()!=0){
//            for (int delete:deleteArr) {
//                num = payRoomService.deleteOnePayInDutchpay(delete);
//            }
//        }
//        return num;
//    }
}

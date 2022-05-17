package com.dingco.pedal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class PageDTO<T> {
    private final List<T> dtoList;  // 현재 페이지 내의 레코드 리스트

    private final int criteriaOfPage;     // 페이지 구분 기준(몇 개의 레코드로 다음 페이지로 넘어갈 것인가?)
    private int criteriaOfBlock;      // 블럭 구분 기준(몇 개의 페이지로 다음 블럭으로 넘어갈 것인가?)

    private final int totalRecord;  // 전체 레코드 개수
    private int totalPage; // 전체 페이지 수
    private int totalBlock; // 전체 블럭 수

    private final int curPage;     // 현재 페이지 수
    private Integer curBlock;    // 현재 블럭

    private Integer firstPageInNextBlock;   // 다음 블럭의 첫 번째 페이지
    private Integer lastPageInPrevBlock;    // 이전 블럭의 마지막 페이지

    private Integer firstPageInCurBlock;    // 현재 블럭의 첫 번째 페이지
    private Integer lastPageInCurBlock;    // 현재 블럭의 마지막 페이지

    private List<Integer> pageListInBlock;  // 현재 블럭의 페이지 리스트

    // - int와 integer의 차이
    // 리스트에 넣기 위해 wrapper class 이용

    public void setPageListInBlock(int criteriaOfBlock) {

        this.criteriaOfBlock = criteriaOfBlock;


        this.totalPage = (int) Math.ceil((double) totalRecord / criteriaOfPage);
        this.totalBlock = (int) Math.ceil((double) totalPage / criteriaOfBlock);


        /**
         * 전체 레코드 개수가 0보다 크면 현재 블럭의 페이지 리스트 생성(ArrayList)
         *  현재 블럭의 페이지 리스트에 현재 블럭의 첫 번째 페이지에서 현재 블럭의 마지막 페이지의 수를 추가
         */
        if (totalRecord > 0) {
            this.curBlock = (int) Math.ceil((double) curPage / criteriaOfBlock);

            /**
             * <조건식>
             * 현재 블럭과 전체 블럭이 같다면 다음 블럭의 첫 번째 페이지는 null(=다음 블럭이 존재하지 않음)
             * 현재 블럭과 전체 블럭이 다르다면 다음 블럭의 첫 번째 페이지는 (현재 블럭 * 블럭 구분 기준) + 1
             * 현재 블럭이 1이라면 지난 블럭의 마지막 페이지는 지난 블럭의 마지막 페이지는 null(=지난 블럭이 존재하지 않음)
             * 현재 블럭이 1이 아니라면 지난 블럭의 마지막 페이지는 (현재 블럭 -1) * 블럭 구분 기준
             */
            this.firstPageInNextBlock = curBlock.equals(totalBlock) ? null : curBlock * criteriaOfBlock + 1;
            this.lastPageInPrevBlock = curBlock.equals(1) ? null : (curBlock - 1) * criteriaOfBlock;

            /**
             * <조건식>
             * 지난 블럭의 마지막 페이지가 null(=지난 블럭이 존재 X)이라면 현재 블럭의 첫 번째 페이지는 1
             * 지난 블럭의 마지막 페이지가 null(=지난 블럭이 존재 X)이 아니라면 현재 블럭의 첫 번째 페이지는 지난 블럭의 마지막 페이지 + 1
             * 다음 블럭의 마지막 페이지가 null(=다음 블럭이 존재 X)이라면 현재 블럭의 마지막 페이지 = 전체 페이지 수
             * 다음 블럭의 마지막 페이지가 null(=다음 블럭이 존재 X)이 아니라면 현재 블럭의 마지막 페이지 = 다음 블럭의 첫 번째 페이지 - 1
             */
            this.firstPageInCurBlock = lastPageInPrevBlock == null ? 1 : lastPageInPrevBlock + 1;
            this.lastPageInCurBlock = firstPageInNextBlock == null ? totalPage : firstPageInNextBlock - 1;
            this.pageListInBlock = new ArrayList<Integer>();

            for (Integer pageListNumber = firstPageInCurBlock; pageListNumber <= lastPageInCurBlock; pageListNumber++) {
                pageListInBlock.add(pageListNumber);
            }
        }
        /**
         * 전체 레코드 개수가 0이면 현재 블럭의 페이지 리스트 생성(ArrayList)
         */
        else {
            this.curBlock = 1;

            this.lastPageInPrevBlock = null;
            this.firstPageInNextBlock = null;
            this.pageListInBlock = new ArrayList<Integer>();
        }
    }
}
// 페이지 블럭을 1 ~ 10 까지 있는 페이지 중에 1~ 5 페이지만 링크로 보여주겠다.
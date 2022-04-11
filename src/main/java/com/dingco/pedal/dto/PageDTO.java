package com.dingco.pedal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class PageDTO<T> {
    private final List<T> dtoList;
    private final int totalRecord;
    private final int curPage;
    private final int perPage;
    private int pagesPerBlock;
    private Integer firstPageInNextBlock;
    private Integer lastPageInPrevBlock;
    private Integer curBlock;
    private List<Integer> pageListInBlock;

    public void setPageBlock(int pagesPerBlock){
        this.pagesPerBlock = pagesPerBlock;
        if(totalRecord > 0){

            int totalPage = (int) Math.ceil((double) totalRecord / perPage);
            // firstPageBlock = 1 로 본다.
            int totalBlock = (int) Math.ceil((double) totalPage / pagesPerBlock);
            this.curBlock = (int) Math.ceil((double) curPage / pagesPerBlock);
            this.lastPageInPrevBlock = curBlock.equals(1) ? null : (curBlock - 1) * pagesPerBlock;
            this.firstPageInNextBlock = curBlock.equals(totalBlock) ? null : curBlock * pagesPerBlock + 1;
            this.pageListInBlock = new ArrayList<Integer>();
            for (int pageNum = lastPageInPrevBlock == null ? 1 : lastPageInPrevBlock + 1;
                 pageNum <= (firstPageInNextBlock == null ? totalPage : firstPageInNextBlock - 1);
                 pageNum++){
                pageListInBlock.add(pageNum);
            }
        } else {
            this.curBlock = 1;
            this.lastPageInPrevBlock = null;
            this.firstPageInNextBlock = null;
            this.pageListInBlock = new ArrayList<Integer>();
        }
    }
}

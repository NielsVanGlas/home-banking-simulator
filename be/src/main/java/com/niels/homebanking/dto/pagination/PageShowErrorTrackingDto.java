package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.error.ShowErrorTracking;

import java.util.List;

public class PageShowErrorTrackingDto extends PageDto {

    private List<ShowErrorTracking> item;

    public PageShowErrorTrackingDto(List<ShowErrorTracking> showTransactionDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showTransactionDto;
    }

    @JsonProperty("item")
    public List<ShowErrorTracking> getItem() {
        return item;
    }

    public void setItem(List<ShowErrorTracking> item) {
        this.item = item;
    }

}

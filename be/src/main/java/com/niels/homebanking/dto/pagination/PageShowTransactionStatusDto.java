package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;

import java.util.List;

public class PageShowTransactionStatusDto extends PageDto {

    private List<ShowTransactionStatusDto> item;

    public PageShowTransactionStatusDto(List<ShowTransactionStatusDto> showTransactionStatusDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showTransactionStatusDto;
    }

    @JsonProperty("item")
    public List<ShowTransactionStatusDto> getItem() {
        return item;
    }

    public void setItem(List<ShowTransactionStatusDto> item) {
        this.item = item;
    }

}

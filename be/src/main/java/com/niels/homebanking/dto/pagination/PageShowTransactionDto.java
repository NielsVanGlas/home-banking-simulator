package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;

import java.util.List;

public class PageShowTransactionDto extends PageDto {

    private List<ShowTransactionDto> item;

    public PageShowTransactionDto(List<ShowTransactionDto> showTransactionDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showTransactionDto;
    }

    @JsonProperty("item")
    public List<ShowTransactionDto> getItem() {
        return item;
    }

    public void setItem(List<ShowTransactionDto> item) {
        this.item = item;
    }

}

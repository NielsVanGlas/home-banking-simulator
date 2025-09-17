package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;

import java.util.List;

public class PageShowBankAccountDto extends PageDto {

    private List<ShowBankAccountDto> item;

    public PageShowBankAccountDto(List<ShowBankAccountDto> showBankAccountDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showBankAccountDto;
    }

    @JsonProperty("item")
    public List<ShowBankAccountDto> getItem() {
        return item;
    }

    public void setItem(List<ShowBankAccountDto> item) {
        this.item = item;
    }

}

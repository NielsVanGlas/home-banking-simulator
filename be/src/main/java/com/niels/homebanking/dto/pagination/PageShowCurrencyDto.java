package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.currency.ShowCurrencyDto;

import java.util.List;

public class PageShowCurrencyDto extends PageDto {

    private List<ShowCurrencyDto> item;

    public PageShowCurrencyDto(List<ShowCurrencyDto> showCurrencyDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showCurrencyDto;
    }

    @JsonProperty("item")
    public List<ShowCurrencyDto> getItem() {
        return item;
    }

    public void setItem(List<ShowCurrencyDto> item) {
        this.item = item;
    }

}

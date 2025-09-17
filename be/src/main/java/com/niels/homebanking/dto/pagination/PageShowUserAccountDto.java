package com.niels.homebanking.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;

import java.util.List;

public class PageShowUserAccountDto extends PageDto {

    private List<ShowUserAccountDto> item;

    public PageShowUserAccountDto(List<ShowUserAccountDto> showUserAccountDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showUserAccountDto;
    }

    @JsonProperty("item")
    public List<ShowUserAccountDto> getItem() {
        return item;
    }

    public void setItem(List<ShowUserAccountDto> item) {
        this.item = item;
    }

}

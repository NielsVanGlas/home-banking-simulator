package com.niels.homebanking.dto.pagination;

public class PageDto {

    private int current;
    private long totalItems;
    private int totalPages;

    public PageDto() {
    }

    public PageDto(int current, long totalItems, int totalPages) {
        this.current = current;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

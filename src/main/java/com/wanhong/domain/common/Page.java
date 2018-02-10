package com.wanhong.domain.common;

/**
 * @author wangmeng247
 * @date 2018-02-09 16:25
 */
public class Page<T>{
    public static final int DEFAULT_PAGESIZE = 10;
    private int pageSize;
    private int index;
    private int totalItem;
    private int totalPage;
    private int startRow;
    private int endRow;
    private T data;
    public Page(T data) {
        this.data = data;
    }

    public boolean isFirstPage() {
        return this.index <= 1;
    }

    public boolean isMiddlePage() {
        return !this.isFirstPage() && !this.isLastPage();
    }

    public boolean isLastPage() {
        return this.index >= this.totalPage;
    }

    public boolean isNextPageAvailable() {
        return !this.isLastPage();
    }

    public boolean isPreviousPageAvailable() {
        return !this.isFirstPage();
    }

    public int getNextPage() {
        return this.isLastPage() ? this.totalItem : this.index + 1;
    }

    public int getPreviousPage() {
        return this.isFirstPage() ? 1 : this.index - 1;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.repaginate();
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.repaginate();
    }

    public int getTotalItem() {
        return this.totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
        if (this.totalItem <= 0) {
            this.totalPage = 0;
            this.index = 1;
            this.startRow = 0;
        }

        this.repaginate();
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getStartRow() {
        return (this.index - 1) * this.pageSize;
    }

    public int getEndRow() {
        return this.index * this.pageSize;
    }

    private void repaginate() {
        if (this.pageSize < 1) {
            this.pageSize = 10;
        }

        if (this.index < 1) {
            this.index = 1;
        }

        if (this.totalItem > 0) {
            this.totalPage = this.totalItem / this.pageSize + (this.totalItem % this.pageSize > 0 ? 1 : 0);
            if (this.index > this.totalPage) {
                this.index = this.totalPage;
            }

            this.startRow = (this.index - 1) * this.pageSize;
        }

    }
}

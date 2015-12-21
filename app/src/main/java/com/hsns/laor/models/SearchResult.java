package com.hsns.laor.models;

/**
 * Created by otdom on 12/21/15.
 */
public class SearchResult {
    private String searchKey;

    public SearchResult(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}

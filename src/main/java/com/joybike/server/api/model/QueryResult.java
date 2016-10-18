package com.joybike.server.api.model;

import java.util.List;

/**
 * Created by 58 on 2016/10/12.
 */
public class QueryResult<T> {

    private List<T> data;
    private int total;

    public QueryResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

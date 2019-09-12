package com.eltyl.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationResult<T> {
    private Long total;
    private List<T> list = new ArrayList<T>();
    public PaginationResult(Long total, List<T> list) {
        this.list = list;
        this.total = total;
    }
}

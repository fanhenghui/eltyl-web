package com.eltyl.model.qo;

import lombok.Data;

@Data
public class UserQuery {
    private Integer pageNo;
    private Integer pageSize;
    private String username;
}

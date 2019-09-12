package com.eltyl.model.qo;

import lombok.Data;

@Data
public class PermissionQuery {
    private Integer pageNo;
    private Integer pageSize;
    private String permissionName;
}

package com.eltyl.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class SysPermission {
    @Accessors(chain = true)
    private Integer id;
    private Integer pid;
    private String name;
    private String code;
}

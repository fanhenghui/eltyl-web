package com.eltyl.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class SysRole {
    @Accessors(chain = true)
    private Integer id;
    private String name;
    private String description;
    private String permissionIds;
}

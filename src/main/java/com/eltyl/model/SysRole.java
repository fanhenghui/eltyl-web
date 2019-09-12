package com.eltyl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String code;
    private String description;
    private String permissionIds;
    @TableField(exist = false)
    private String operate;

    @TableField(exist = false)
    private String permissionStr;
}

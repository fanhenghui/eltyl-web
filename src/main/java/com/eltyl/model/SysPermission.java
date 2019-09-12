package com.eltyl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private String code;
    @TableField(exist = false)
    private String parent;
    @TableField(exist = false)
    private String operate;
    @TableField(exist = false)
    private List<SysPermission> children;
}

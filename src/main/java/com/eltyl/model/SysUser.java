package com.eltyl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"password"})
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String mobile;
    private String email;
    private Integer status = 0;
    private Long createTime;
    private Long updateTime;
    private Long lastLoginTime;
    private String roleIds;
    @TableField(exist = false)
    private String operate;
    @TableField(exist = false)
    private String roleNameList;
    @TableField(exist = false)
    private List<String> roleCodeList;
    @TableField(exist = false)
    private String createTimeStr;
    @TableField(exist = false)
    private String updateTimeStr;
    @TableField(exist = false)
    private String lastLoginTimeStr;
    @TableField(exist = false)
    private String roleStr;
}

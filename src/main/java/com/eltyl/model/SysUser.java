package com.eltyl.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class SysUser {
    @Accessors(chain = true)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String mobile;
    private String email;
    private Integer status = 0;
    private Integer createTime;
    private Integer updateTime;
    private Integer lastLoginTime;
    private String roleIds;
}

package com.eltyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eltyl.model.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> findPermissionByUserId(Integer userId);
    /*
    * select role_ids from sys_user where id=userId;
    *
    * select code from sys_permission where id in(select id from)
    * */
}

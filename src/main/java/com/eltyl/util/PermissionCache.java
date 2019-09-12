package com.eltyl.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eltyl.mapper.SysPermissionMapper;
import com.eltyl.mapper.SysRoleMapper;
import com.eltyl.model.SysPermission;
import com.eltyl.model.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionCache {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    public List<SysPermission> roleData=new ArrayList<SysPermission>();
    //之前定时更新的策略有问题，所以改为有修改就更新
    public void refreshPermissionData(){
        roleData.clear();
        List<SysPermission> data=sysPermissionMapper.selectList(new QueryWrapper<>());
        for(SysPermission hos:data){
            roleData.add(hos);
        }
    }
    public String getPermissionNameById(Integer roleId){
        if(roleData.size()<=0)refreshPermissionData();
        for(SysPermission hos:roleData){
            if(hos.getId()==roleId){
                return hos.getName();
            }
        }
        return "";
    }
}

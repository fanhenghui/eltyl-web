package com.eltyl.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eltyl.mapper.SysRoleMapper;
import com.eltyl.model.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleCache {
    @Autowired
    private SysRoleMapper roleMapper;
    public List<SysRole> roleData=new ArrayList<SysRole>();
    //之前定时更新的策略有问题，所以改为有修改就更新
    public void refreshRoleData(){
        roleData.clear();
        List<SysRole> data=roleMapper.selectList(new QueryWrapper<>());
        for(SysRole hos:data){
            roleData.add(hos);
        }
    }
    public String getRoleNameById(Integer roleId){
        if(roleData.size()<=0)refreshRoleData();
        for(SysRole hos:roleData){
            if(hos.getId()==roleId){
                return hos.getName();
            }
        }
        return "";
    }
    public String getRoleList(String ids){
        String ret="";
        String[] idList=ids.split(",");
        for(String id:idList){
            if(!StringUtils.isEmpty(id)&&Integer.parseInt(id)>0){
                ret+=getRoleNameById(Integer.parseInt(id))+",";
            }
        }
        if(ret.indexOf(",")>0)ret=ret.substring(0,ret.length()-1);
        return ret;
    }
    public String getRoleCodeById(Integer roleId){
        if(roleData.size()<=0)refreshRoleData();
        for(SysRole hos:roleData){
            if(hos.getId()==roleId){
                return hos.getCode();
            }
        }
        return "";
    }
    public List<String> getRoleCodeList(String ids){
        List<String> ret=new ArrayList<>();
        String[] idList=ids.split(",");
        for(String id:idList){
            if(!StringUtils.isEmpty(id)&&Integer.parseInt(id)>0){
                ret.add(getRoleCodeById(Integer.parseInt(id)));
            }
        }
        return ret;
    }
    public List<SysRole> getRoleData(){
        if(roleData.size()<=0)refreshRoleData();
        return roleData;
    }
}

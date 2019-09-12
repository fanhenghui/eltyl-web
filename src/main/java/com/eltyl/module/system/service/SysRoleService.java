package com.eltyl.module.system.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eltyl.exception.BusinessException;
import com.eltyl.mapper.SysPermissionMapper;
import com.eltyl.mapper.SysRoleMapper;
import com.eltyl.model.SysPermission;
import com.eltyl.model.SysRole;
import com.eltyl.model.qo.RoleQuery;
import com.eltyl.model.vo.PaginationResult;
import com.eltyl.model.vo.Permission;
import com.eltyl.util.RoleCache;
import com.eltyl.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private RoleCache roleCache;

    //添加角色
    public void addRole(SysRole role)throws BusinessException {
        if(StringUtils.isEmpty(role.getName())||role.getName().length()>64/3||role.getDescription().length()>512/3){
            throw new BusinessException("参数错误");
        }
        if(sysRoleMapper.insert(role)<0){
            throw new BusinessException("角色插入失败");
        }
        roleCache.refreshRoleData();
    }
    //查询角色列表
    public PaginationResult findRolesByPage(RoleQuery query)throws BusinessException{
        int pageNo = 1;
        int pageSize = 10;
        if (query.getPageNo()!=null) {
            pageNo = query.getPageNo();
        }
        if (query.getPageSize()!=null&&pageSize>0&&pageSize<200) {
            pageSize = query.getPageSize();
        }
        QueryWrapper<SysRole> wrapper=new QueryWrapper<SysRole>();
        if(!StringUtils.isEmpty(query.getRoleName()))wrapper.like("name",query.getRoleName());
        IPage<SysRole> result=sysRoleMapper.selectPage(new Page<SysRole>(pageNo, pageSize),wrapper);
        for(SysRole role:result.getRecords()){
            role.setOperate("<a class=\"editItem\" itemId=\""+role.getId()+"\">编辑</a>  <a class=\"delItem\"  itemId=\""+role.getId()+"\">删除</a>");
        }
        return new PaginationResult(result.getTotal(),result.getRecords());
    }
    public List<Permission> selectUserTree(String ids){
        String[] arr= ids.split(",");
        List<Integer> idList=new ArrayList<>();
        for(String item:arr){
            if(!StringUtils.isEmpty(item)&&Integer.parseInt(item)>0){
                idList.add(Integer.parseInt(item));
            }
        }
        List<SysPermission> data=sysPermissionMapper.selectList(new QueryWrapper<>());
        List<Permission> list=new ArrayList<>();
        for(SysPermission item:data){
            Permission p=new Permission();
            p.setId(item.getId());
            p.setPid(item.getPid());
            p.setTitle(item.getName());
            if(idList.contains(item.getId()))p.setChecked(true);
            else p.setChecked(false);
            list.add(p);
        }
        return TreeUtil.getChildPerms(list,0);
    }
    public SysRole findRoleById(Integer id)throws BusinessException{
        //查询权限信息
        SysRole role=sysRoleMapper.selectById(id);
        String treeData= JSON.toJSONString(selectUserTree(role.getPermissionIds()));
        role.setPermissionStr(treeData);
        return role;
    }
    public void updateRoleById(SysRole role)throws BusinessException{
        if(StringUtils.isEmpty(role.getName())||role.getName().length()>64/3||role.getDescription().length()>512/3){
            throw new BusinessException("参数错误");
        }
        sysRoleMapper.updateById(role);
        roleCache.refreshRoleData();
    }
    public void deleteById(Integer id)throws BusinessException{
        sysRoleMapper.deleteById(id);
        roleCache.refreshRoleData();
    }
}

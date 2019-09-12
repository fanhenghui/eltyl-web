package com.eltyl.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eltyl.exception.BusinessException;
import com.eltyl.mapper.SysPermissionMapper;
import com.eltyl.model.SysPermission;
import com.eltyl.model.qo.PermissionQuery;
import com.eltyl.model.vo.PaginationResult;
import com.eltyl.model.vo.Permission;
import com.eltyl.util.PermissionCache;
import com.eltyl.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private PermissionCache permissionCache;
    //添加权限
    public void addPermission(SysPermission permission)throws BusinessException {
        if(StringUtils.isEmpty(permission.getName())||permission.getName().length()>64/3){
            throw new BusinessException("参数错误");
        }
        if(sysPermissionMapper.insert(permission)<0){
            throw new BusinessException("权限插入失败");
        }
        permissionCache.refreshPermissionData();
    }
    //查询权限列表
    public PaginationResult findPermissionsByPage(PermissionQuery query)throws BusinessException{
        int pageNo = 1;
        int pageSize = 10;
        if (query.getPageNo()!=null) {
            pageNo = query.getPageNo();
        }
        if (query.getPageSize()!=null&&pageSize>0&&pageSize<200) {
            pageSize = query.getPageSize();
        }
        QueryWrapper<SysPermission> wrapper=new QueryWrapper<SysPermission>();
        if(!StringUtils.isEmpty(query.getPermissionName()))wrapper.like("name",query.getPermissionName());
        IPage<SysPermission> result=sysPermissionMapper.selectPage(new Page<SysPermission>(pageNo, pageSize),wrapper);
        for(SysPermission permission:result.getRecords()){
            permission.setParent(permissionCache.getPermissionNameById(permission.getPid()));
            permission.setOperate("<a class=\"editItem\" itemId=\""+permission.getId()+"\">编辑</a>  <a class=\"delItem\"  itemId=\""+permission.getId()+"\">删除</a>");
        }
        return new PaginationResult(result.getTotal(),result.getRecords());
    }
    public SysPermission findPermissionById(Integer id)throws BusinessException{
        return sysPermissionMapper.selectById(id);
    }
    public void updatePermissionById(SysPermission permission)throws BusinessException{
        if(StringUtils.isEmpty(permission.getName())||permission.getName().length()>64/3){
            throw new BusinessException("参数错误");
        }
        sysPermissionMapper.updateById(permission);
        permissionCache.refreshPermissionData();
    }
    public void deleteById(Integer id)throws BusinessException{
        sysPermissionMapper.deleteById(id);
        permissionCache.refreshPermissionData();
    }
    public List<Permission> selectTree(){
        List<SysPermission> data=sysPermissionMapper.selectList(new QueryWrapper<>());
        List<Permission> list=new ArrayList<>();
        for(SysPermission item:data){
            Permission p=new Permission();
            p.setId(item.getId());
            p.setPid(item.getPid());
            p.setTitle(item.getName());
            p.setChecked(false);
            p.setOpen(true);
            list.add(p);
        }
        return TreeUtil.getChildPerms(list,0);
    }
}

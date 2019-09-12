package com.eltyl.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eltyl.exception.BusinessException;
import com.eltyl.mapper.SysUserMapper;
import com.eltyl.model.SysRole;
import com.eltyl.model.SysUser;
import com.eltyl.model.qo.UserQuery;
import com.eltyl.model.vo.PaginationResult;
import com.eltyl.util.DateTimeUtil;
import com.eltyl.util.RoleCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleCache roleCache;
    //添加用户
    public void addUser(SysUser user)throws BusinessException {
        if(StringUtils.isEmpty(user.getUsername())){
            throw new BusinessException("参数错误");
        }
        user.setCreateTime(new Date().getTime()/1000);
        user.setUpdateTime(new Date().getTime()/1000);
        if(sysUserMapper.insert(user)<0){
            throw new BusinessException("用户插入失败");
        }
    }
    //查询用户列表
    public PaginationResult findUsersByPage(UserQuery query)throws BusinessException{
        int pageNo = 1;
        int pageSize = 10;
        if (query.getPageNo()!=null) {
            pageNo = query.getPageNo();
        }
        if (query.getPageSize()!=null&&pageSize>0&&pageSize<200) {
            pageSize = query.getPageSize();
        }
        QueryWrapper<SysUser> wrapper=new QueryWrapper<SysUser>();
        if(!org.apache.commons.lang3.StringUtils.isEmpty(query.getUsername()))wrapper.like("username",query.getUsername());
        IPage<SysUser> result=sysUserMapper.selectPage(new Page<SysUser>(pageNo, pageSize),wrapper);
        for(SysUser user:result.getRecords()){ ;
            user.setCreateTimeStr(DateTimeUtil.timeToStr(user.getCreateTime()));
            user.setUpdateTimeStr(DateTimeUtil.timeToStr(user.getUpdateTime()));
            user.setLastLoginTimeStr(DateTimeUtil.timeToStr(user.getLastLoginTime()));
            user.setRoleNameList(roleCache.getRoleList(user.getRoleIds()));
            user.setOperate("<a class=\"editItem\" itemId=\""+user.getId()+"\">编辑</a>  <a class=\"delItem\"  itemId=\""+user.getId()+"\">删除</a>  <a class=\"resetItem\"  itemId=\""+user.getId()+"\">重置</a>");
        }
        return new PaginationResult(result.getTotal(),result.getRecords());
    }
    public SysUser findUserById(Integer id)throws BusinessException{
        SysUser user=sysUserMapper.selectById(id);
        String[] arr=user.getRoleIds().split(",");
        List<Integer> idList=new ArrayList<>();
        for(String item:arr){
            if(!StringUtils.isEmpty(item)&&Integer.parseInt(item)>0){
                idList.add(Integer.parseInt(item));
            }
        }
        //查询角色信息
        String roleStr="<select name=\"role\" xm-select=\"roleSelect\" xm-select-skin=\"primary\"><option value=\"\"></option>";
        List<SysRole> roleList=roleCache.getRoleData();
        for(SysRole item:roleList){
             if(idList.contains(item.getId()))roleStr+="<option value=\""+item.getId()+"\" selected=\"selected\">"+item.getName()+"</option>";
             else roleStr+="<option value=\""+item.getId()+"\">"+item.getName()+"</option>";
        }
        roleStr+="</select>";
        user.setRoleStr(roleStr);
        return user;
    }
    public String getRoleData(){
        String roleStr="<select name=\"role\" xm-select=\"roleSelect\" xm-select-skin=\"primary\"><option value=\"\"></option>";
        List<SysRole> roleList=roleCache.getRoleData();
        for(SysRole item:roleList){
            roleStr+="<option value=\""+item.getId()+"\">"+item.getName()+"</option>";
        }
        roleStr+="</select>";
        return roleStr;
    }
    public void updateUserById(SysUser user)throws BusinessException{
        if(StringUtils.isEmpty(user.getUsername())){
            throw new BusinessException("参数错误");
        }
        user.setUpdateTime(new Date().getTime()/1000);
        sysUserMapper.updateById(user);
    }
    public void deleteById(Integer id)throws BusinessException{
        sysUserMapper.deleteById(id);
    }
    //修改用户的最后登录时间
    public void updateLastLoginTime(Integer uid,Long lastLoginTime){
        SysUser user=new SysUser();
        user.setId(uid);
        user.setLastLoginTime(lastLoginTime);
        sysUserMapper.updateById(user);
    }
    public void updatePasswordById(SysUser user)throws BusinessException{
        if(StringUtils.isEmpty(user.getPassword())||user.getId()<=0||user.getPassword().length()<6){
            throw new BusinessException("参数错误");
        }
        user.setUpdateTime(new Date().getTime()/1000);
        user.setPassword(encoder.encode(user.getPassword()));
        sysUserMapper.updateById(user);
    }
}

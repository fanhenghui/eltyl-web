package com.eltyl.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eltyl.exception.LoginAccessException;
import com.eltyl.mapper.SysPermissionMapper;
import com.eltyl.mapper.SysRoleMapper;
import com.eltyl.mapper.SysUserMapper;
import com.eltyl.model.MyUser;
import com.eltyl.model.SysPermission;
import com.eltyl.model.SysRole;
import com.eltyl.model.SysUser;
import com.eltyl.util.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    public List<SysPermission> findPermissionByRoleIds(String roleIds)
    {
        List<SysRole> roleList=sysRoleMapper.selectBatchIds(ConvertUtil.getIntegerList(roleIds));
        List<Integer> permissionIdList=new ArrayList<Integer>();
        for(SysRole role:roleList){
            permissionIdList.addAll(ConvertUtil.getIntegerList(role.getPermissionIds()));
        }
        return sysPermissionMapper.selectBatchIds(permissionIdList);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.手动添加固定用户
        //User user=new User("admin","123456", AuthorityUtils.commaSeparatedStringToAuthorityList("Index,List"));
        if(StringUtils.isBlank(username))throw new UsernameNotFoundException("用户名不可以为空");
        //2.从数据库查询用户
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser user=sysUserMapper.selectOne(wrapper);
        User myUser=null;
        if(user!=null){
            //3.查询用户权限
            List<SysPermission> permissionList=findPermissionByRoleIds(user.getRoleIds());
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

            for(SysPermission permission:permissionList){
                authorityList.add(new SimpleGrantedAuthority(permission.getCode()));
            }
            myUser = new User(username,user.getPassword(),true,true,true,true,authorityList);
        }else{
            throw new UsernameNotFoundException("用户不存在");
        }
        return myUser;
    }

}

package com.eltyl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eltyl.annotation.MyAnno;
import com.eltyl.mapper.SysPermissionMapper;
import com.eltyl.mapper.SysRoleMapper;
import com.eltyl.mapper.SysUserMapper;
import com.eltyl.model.SysPermission;
import com.eltyl.model.SysRole;
import com.eltyl.model.SysUser;
import com.eltyl.model.qo.RoleQuery;
import com.eltyl.model.vo.Permission;
import com.eltyl.util.ConvertUtil;
import com.eltyl.util.DateUtil;
import com.eltyl.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
@RunWith(SpringRunner.class)
@SpringBootTest
public class Web2ApplicationTests {
    @Autowired
    private JwtUtil jwtUtil;
    @Test
    public void contextLoads() {
    }
    @Test
    @Ignore
    public void testOther(){
        //Assert.assertEquals("123",u.getPwd());
        //assertThat(u).isNotNull();
        //userMapper.updateById(new User().setId(2).setName("ab@c.c"));
        //userMapper.delete(new QueryWrapper<User>().lambda().eq(User::getPwd,"123"));

        /*UpdateWrapper<User> uw=new UpdateWrapper<User>();
        uw.eq("name","aaa");
        User user=new User();
        user.setPwd("121");
        userMapper.update(user,uw);
        List<String> a= Arrays.asList("aaa","bbb","qqq");*/
        DateUtil d=new DateUtil();
        Method[] ms = DateUtil.class.getMethods();
        Annotation[] annotation;
        for (Method m : ms) {
            annotation = m.getAnnotations();
            for (Annotation tag : annotation) {
                System.out.println("value is:"+((MyAnno)tag).value());
                System.out.println("sex is:"+((MyAnno)tag).sex());
                System.out.println("age is:"+((MyAnno)tag).age());
            }
        }
    }
    @Test
    @Ignore
    public void testJwt(){
        String jwt=jwtUtil.createToken("123","15235389368","user");
        Claims claims=jwtUtil.parseJWT(jwt);
        System.out.println("roles is:"+claims.get("roles"));
    }
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Test
    @Ignore
    public void testPer()
    {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username","1256174071");
        SysUser user=sysUserMapper.selectOne(wrapper);
        List<SysRole> roleList=sysRoleMapper.selectBatchIds(ConvertUtil.getIntegerList(user.getRoleIds()));
        List<Integer> permissionIdList=new ArrayList<Integer>();
        for(SysRole role:roleList){
            permissionIdList.addAll(ConvertUtil.getIntegerList(role.getPermissionIds()));
        }
        List<SysPermission> permissionList=sysPermissionMapper.selectBatchIds(permissionIdList);
        permissionList.forEach(System.out::println);
    }
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Test
    @Ignore
    public void updatePassword(){
        String aaa=passwordEncoder.encode("123456");
        System.out.println(aaa);
        //System.out.println(passwordEncoder.matches("123456",aaa));
        System.out.println(AnonymousAuthenticationToken.class.isAssignableFrom(RememberMeAuthenticationToken.class));
        System.out.println(RememberMeAuthenticationToken.class.isAssignableFrom(RememberMeAuthenticationToken.class));
    }
    @Test
    @Ignore
    public void testPage(){
        RoleQuery query=new RoleQuery();
        query.setPageNo(1);
        query.setPageSize(1);
        //,new QueryWrapper<SysRole>().like("name","员")
        IPage<SysRole> result=sysRoleMapper.selectPage(new Page<SysRole>(query.getPageNo(), query.getPageSize()),null);
        System.out.println("getTotal is:"+result.getTotal());//总记录数
        System.out.println("getPages is:"+result.getPages());//总页数
        System.out.println("getRecords is:"+result.getRecords());
    }
    @Test
    public void testJson(){
        String json="[{\"checked\":false,\"id\":1,\"pid\":0,\"title\":\"管理商品\",\"children\":[{\"checked\":false,\"id\":5,\"pid\":1,\"title\":\"查看商品\"}]}]";
        List<Permission> list=(List<Permission>)JSON.parse(json);
        System.out.println(list);
        System.out.println(list.get(0).getTitle());
    }
}

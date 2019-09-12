package com.eltyl.module.system.controller;

import com.alibaba.fastjson.JSON;
import com.eltyl.exception.BusinessException;
import com.eltyl.model.SysPermission;
import com.eltyl.model.SysRole;
import com.eltyl.model.SysUser;
import com.eltyl.model.qo.PermissionQuery;
import com.eltyl.model.qo.RoleQuery;
import com.eltyl.model.qo.UserQuery;
import com.eltyl.model.vo.AjaxResponse;
import com.eltyl.model.vo.PaginationResult;
import com.eltyl.model.vo.Permission;
import com.eltyl.module.system.service.SysPermissionService;
import com.eltyl.module.system.service.SysRoleService;
import com.eltyl.module.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    //用户相关
    @RequestMapping("/user")
    public String user(ModelMap model){
        String roleData= sysUserService.getRoleData();
        roleData=roleData.replace("\"","\\\"");
        model.addAttribute("roleData",roleData);
        return "system/user_list";
    }
    @RequestMapping("/user/add")
    @ResponseBody
    public AjaxResponse<Integer> addUser(SysUser user){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysUserService.addUser(user);
            result.setCode(200);
            result.setMsg("用户增加成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/user/list")
    @ResponseBody
    public AjaxResponse<List<SysUser>> loadUserList(UserQuery query){
        AjaxResponse<List<SysUser>> result=new AjaxResponse();
        try {
            PaginationResult ret=sysUserService.findUsersByPage(query);
            result.setCode(200);
            result.setMsg("用户查询成功");
            result.setTotal(ret.getTotal());
            result.setData(ret.getList());
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/user/loaditem")
    @ResponseBody
    public AjaxResponse<SysUser> loadUserItem(Integer id){
        AjaxResponse<SysUser> result=new AjaxResponse();
        try {
            SysUser ret=sysUserService.findUserById(id);
            result.setCode(200);
            result.setMsg("用户查询成功");
            result.setData(ret);
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/user/update")
    @ResponseBody
    public AjaxResponse<Integer> updateUser(SysUser user){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysUserService.updateUserById(user);
            result.setCode(200);
            result.setMsg("用户修改成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/user/delete")
    @ResponseBody
    public AjaxResponse<Integer> deleteUser(Integer id){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysUserService.deleteById(id);
            result.setCode(200);
            result.setMsg("用户删除成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/user/reset")
    @ResponseBody
    public AjaxResponse<Integer> resetUser(SysUser user){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysUserService.updatePasswordById(user);
            result.setCode(200);
            result.setMsg("密码修改成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    //角色相关
    @RequestMapping("/role")
    public String role(ModelMap model){
        String treeData= JSON.toJSONString(sysPermissionService.selectTree());
        treeData=treeData.replace("\"","\\\"");
        model.addAttribute("treeData",treeData);
        return "system/role_list";
    }
    @RequestMapping("/role/add")
    @ResponseBody
    public AjaxResponse<Integer> addRole(SysRole role){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysRoleService.addRole(role);
            result.setCode(200);
            result.setMsg("角色增加成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/role/list")
    @ResponseBody
    public AjaxResponse<List<SysRole>> loadRoleList(RoleQuery query){
        AjaxResponse<List<SysRole>> result=new AjaxResponse();
        try {
            PaginationResult ret=sysRoleService.findRolesByPage(query);
            result.setCode(200);
            result.setMsg("角色查询成功");
            result.setTotal(ret.getTotal());
            result.setData(ret.getList());
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/role/loaditem")
    @ResponseBody
    public AjaxResponse<SysRole> loadRoleItem(Integer id){
        AjaxResponse<SysRole> result=new AjaxResponse();
        try {
            SysRole ret=sysRoleService.findRoleById(id);
            result.setCode(200);
            result.setMsg("角色查询成功");
            result.setData(ret);
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/role/update")
    @ResponseBody
    public AjaxResponse<Integer> updateRole(SysRole role){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysRoleService.updateRoleById(role);
            result.setCode(200);
            result.setMsg("角色修改成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/role/delete")
    @ResponseBody
    public AjaxResponse<Integer> deleteRole(Integer id){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysRoleService.deleteById(id);
            result.setCode(200);
            result.setMsg("角色删除成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    //权限相关
    @RequestMapping("/permission")
    public String permission(){
        return "system/permission_list";
    }
    @RequestMapping("/permission/add")
    @ResponseBody
    public AjaxResponse<Integer> addPermission(SysPermission permission){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysPermissionService.addPermission(permission);
            result.setCode(200);
            result.setMsg("权限增加成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/permission/list")
    @ResponseBody
    public AjaxResponse<List<SysPermission>> loadPermissionList(PermissionQuery query){
        AjaxResponse<List<SysPermission>> result=new AjaxResponse();
        try {
            PaginationResult ret=sysPermissionService.findPermissionsByPage(query);
            result.setCode(200);
            result.setMsg("权限查询成功");
            result.setTotal(ret.getTotal());
            result.setData(ret.getList());
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/permission/loaditem")
    @ResponseBody
    public AjaxResponse<SysPermission> loadPermissionItem(Integer id){
        AjaxResponse<SysPermission> result=new AjaxResponse();
        try {
            SysPermission ret=sysPermissionService.findPermissionById(id);
            result.setCode(200);
            result.setMsg("权限查询成功");
            result.setData(ret);
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/permission/update")
    @ResponseBody
    public AjaxResponse<Integer> updatePermission(SysPermission permission){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysPermissionService.updatePermissionById(permission);
            result.setCode(200);
            result.setMsg("权限修改成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping("/permission/delete")
    @ResponseBody
    public AjaxResponse<Integer> deletePermission(Integer id){
        AjaxResponse<Integer> result=new AjaxResponse();
        try {
            sysPermissionService.deleteById(id);
            result.setCode(200);
            result.setMsg("权限删除成功");
        }catch (BusinessException e){
            result.setCode(415);
            result.setMsg(e.getMessage());
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("服务器发生错误");
            e.printStackTrace();
        }
        return result;
    }
    //父级权限选择数据
    @RequestMapping("/permission/parentSelectJson")
    @ResponseBody
    public List<Permission> parentSelectJson(){
        return sysPermissionService.selectTree();
    }
}

package com.ll.admin.controller;

import com.ll.admin.common.utils.PageTableRequest;
import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.RoleDto;
import com.ll.admin.entity.Role;
import com.ll.admin.entity.User;
import com.ll.admin.service.RoleService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:21
 */
@Controller
@RequestMapping("/api/role")
@Api(tags = "角色相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('role:list')")
    public String index(){
        return "system/role/role";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "分页返回角色列表")
    @PreAuthorize("hasAnyAuthority('role:list')")
    @Log("查询角色")
    public Result roleList(PageTableRequest request,Role role) {
        request.countOffset();
        return roleService.getFuzzyRolesByPage(request.getOffset(), request.getLimit(),role);
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "修改角色页面")
    @PreAuthorize("hasAnyAuthority('role:edit')")
    public String editRole(Model model, Role role) {
        model.addAttribute("MyRole",roleService.getRoleById(role.getRoleId()));
        return "system/role/role-edit";
    }

    @GetMapping(value = "/edit/dataScope")
    @ApiOperation(value = "修改角色页面")
    @PreAuthorize("hasAnyAuthority('role:edit')")
    public String editRoleDataScope(Model model, Role role) {
        model.addAttribute("MyRole",roleService.getRoleById(role.getRoleId()));
        return "system/role/role-dataScope";
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改角色")
    @PreAuthorize("hasAnyAuthority('role:edit')")
    @Log("修改角色")
    public Result updateRole(@RequestBody RoleDto roleDto) {

        return roleService.update(roleDto);
    }

    @PutMapping(value = "/authDataScope")
    @ResponseBody
    @ApiOperation(value = "修改角色数据权限")
    public Result updateauthDataScope(@RequestBody RoleDto roleDto) {

        return roleService.authDataScope(roleDto);
    }

    @GetMapping(value = "/add")
    @ApiOperation(value = "添加角色页面")
    @PreAuthorize("hasAnyAuthority('role:add')")
    public String addRole(Model model) {
        model.addAttribute("MyRole",new Role());
        return "/system/role/role-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加角色")
    @PreAuthorize("hasAnyAuthority('role:add')")
    @Log("添加角色")
    public Result saveRole(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除角色")
    @PreAuthorize("hasAnyAuthority('role:del')")
    @Log("删除角色")
    public Result<Role> deleteRole(RoleDto roleDto) {
        return roleService.delete(roleDto.getRoleId());
    }

    @GetMapping("/all")
    @ResponseBody
    @ApiOperation(value = "角色列表")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public Result<Role> getAll(){
        return roleService.getAllRoles();
    }
}

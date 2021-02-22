package com.ll.admin.controller;

import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.DeptDto;
import com.ll.admin.entity.Dept;
import com.ll.admin.service.DeptService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 13:48
 */
@Controller
@RequestMapping("/api/dept")
@Api(tags = "系统：部门管理")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("index")
    @ApiOperation(value = "返回部门页面")
    public String index(){
        return "system/dept/dept";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "部门列表")
    @PreAuthorize("hasAnyAuthority('dept:edit')")
    @Log("查询部门")
    public Result getDeptAll(Dept myDept){
        return Result.ok().data(deptService.getDeptAll(myDept)).code(ResultCode.TABLE_SUCCESS);
    }


    @GetMapping("build")
    @ResponseBody
    @ApiOperation(value = "绘制部门树")
    @PreAuthorize("hasAnyAuthority('dept:add','dept:edit')")
    @Log("绘制部门树")
    public Result buildDeptAll(DeptDto deptDto){
        List<DeptDto> deptAll =deptService.buildDeptAll(deptDto);
        return Result.ok().data(deptAll);
    }

    @GetMapping("/add")
    @ApiOperation(value = "添加部门页面")
    @PreAuthorize("hasAnyAuthority('dept:add')")
    public String addJob(Model model){
        model.addAttribute("myDept",new Dept());
        return "/system/dept/dept-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加部门")
    @PreAuthorize("hasAnyAuthority('dept:add')")
    @Log("添加部门")
    public Result<Dept> savePermission(@RequestBody Dept dept) {

        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals( deptService.checkDeptNameUnique(dept))) {
            return Result.error().message("新增岗位'" + dept.getDeptName() + "'失败，岗位名称已存在");
        }
        int i = deptService.insertDept(dept);
        return Result.judge(i,"添加");
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "修改部门页面")
    @PreAuthorize("hasAnyAuthority('dept:edit')")
    public String editPermission(Model model, Dept dept) {
        model.addAttribute("myDept",deptService.getDeptById(dept.getDeptId()));
        return "system/dept/dept-edit";
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改部门")
    @PreAuthorize("hasAnyAuthority('dept:edit')")
    @Log("修改部门")
    public Result updateMenu(@RequestBody Dept dept) {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals( deptService.checkDeptNameUnique(dept))) {
            return Result.error().message("更新岗位'" + dept.getDeptName() + "'失败，岗位名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return Result.error().message("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }else if (dept.getStatus().toString().equals(UserConstants.DEPT_DISABLE)
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0)
        {
            return Result.error().message("该部门包含未停用的子部门！");
        }
        int i = deptService.updateDept(dept);
        return Result.judge(i,"修改");
    }

    /**
     * 用户状态修改
     */
    @Log("修改部门状态")
    @PutMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "修改部门状态")
    @PreAuthorize("hasAnyAuthority('dept:edit')")
    public Result changeStatus(@RequestBody Dept myDept)
    {

        return Result.judge(deptService.changeStatus(myDept),"修改");
    }
    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除部门")
    @PreAuthorize("hasAnyAuthority('dept:del')")
    @Log("删除部门")
    public Result<Dept> deleteRole(Integer deptId) {
        if (deptService.selectDeptCount(deptId) > 0){
            return Result.error().message("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return Result.error().message("部门存在用户,不允许删除");
        }
        int i = deptService.deleteDeptById(deptId);
        return Result.judge(i,"删除");
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/ebuild/{roleId}")
    @ResponseBody
    @ApiOperation(value = "通过id绘制部门树")
    @PreAuthorize("hasAnyAuthority('role:add','role:edit')")
    @Log("通过id绘制部门树")
    public Result deptTreeData(@PathVariable Integer roleId)
    {
        List<DeptDto> deptDtos = deptService.roleDeptTreeData(roleId);
        return Result.ok().data(deptDtos);
    }
}

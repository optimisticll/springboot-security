package com.ll.admin.controller;

import com.ll.admin.common.utils.PageTableRequest;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.dto.JwtUserDto;
import com.ll.admin.dto.MenuDto;
import com.ll.admin.dto.MenuIndexDto;
import com.ll.admin.entity.Menu;
import com.ll.admin.entity.User;
import com.ll.admin.service.MenuService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:21
 */
@Controller
@RequestMapping("/api/menu")
@Api(tags = "系统:菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("index")
    @PreAuthorize("hasAnyAuthority('menu:list')")
    @ApiOperation(value = "返回菜单页面")
    public String index(){
        return "system/menu/menu";
    }


    @GetMapping
    @ResponseBody
    @ApiOperation(value = "菜单列表")
    @PreAuthorize("hasAnyAuthority('menu:list')")
    @Log("查询菜单")
    public Result getMenuAll(String queryName,Integer queryType){
        return Result.ok().data(menuService.getMenuAll(queryName,queryType)).code(ResultCode.TABLE_SUCCESS);
    }

    @GetMapping("/build")
    @ResponseBody
    @ApiOperation(value = "绘制菜单树")
    @PreAuthorize("hasAnyAuthority('menu:add','menu:edit')")
    @Log("绘制菜单树")
    public Result buildMenuAll(){
        List<MenuDto> menuAll =menuService.buildMenuAll();
        return Result.ok().data(menuAll);
    }

    @GetMapping("/ebuild/{roleId}")
    @ResponseBody
    @ApiOperation(value = "通过id绘制菜单树")
    @PreAuthorize("hasAnyAuthority('role:add','role:edit')")
    @Log("通过id绘制菜单树")
    public Result buildMenuAllByRoleId(@PathVariable Integer roleId){
        List<MenuDto> menuAll =menuService.buildMenuAllByRoleId(roleId);
        return Result.ok().data(menuAll);
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "修改菜单页面")
    @PreAuthorize("hasAnyAuthority('menu:edit')")
    public String editPermission(Model model, Menu menu) {
        model.addAttribute("myMenu",menuService.getMenuById(menu.getMenuId()));
        return "system/menu/menu-edit";
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改菜单")
    @PreAuthorize("hasAnyAuthority('menu:edit')")
    @Log("修改菜单")
    public Result updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }


    @GetMapping(value = "/add")
    @ApiOperation(value = "添加菜单页面")
    @PreAuthorize("hasAnyAuthority('menu:add')")
    public String addMenu(Model model) {
        model.addAttribute("myMenu",new Menu());
        return "system/menu/menu-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加菜单")
    @PreAuthorize("hasAnyAuthority('menu:add')")
    @Log("添加菜单")
    public Result<Menu> savePermission(@RequestBody Menu menu) {
        return menuService.save(menu);
    }


    /**
     * @param menuId
     * @return
     */
    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除菜单")
    @PreAuthorize("hasAnyAuthority('menu:del')")
    @Log("删除菜单")
    public Result deleteMenu(Integer menuId) {
        return menuService.delete(menuId);
    }
}

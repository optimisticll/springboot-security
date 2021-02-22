package com.ll.admin.service.impl;

import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.dto.MenuDto;
import com.ll.admin.dto.MenuIndexDto;
import com.ll.admin.entity.Menu;
import com.ll.admin.mapper.MenuMapper;
import com.ll.admin.mapper.RoleMenuMapper;
import com.ll.admin.service.MenuService;
import com.ll.admin.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:18
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<Menu> getMenuAll(String queryName,Integer queryType) {

        return menuMapper.getFuzzyMenu(queryName,queryType);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }

    @Override
    public List<MenuDto> buildMenuAll() {
        return menuMapper.buildAll();
    }

    @Override
    public Result updateMenu(Menu menu) {
        menu.setIcon("layui-icon "+menu.getIcon());
        return (menuMapper.update(menu) > 0) ? Result.ok().message("修改成功") : Result.error().message("修改失败");

    }

    @Override
    public Result<Menu> save(Menu menu) {
        menu.setIcon("layui-icon "+menu.getIcon());
        return (menuMapper.save(menu) > 0) ? Result.ok().message("添加成功") : Result.error().message("添加失败");

    }



    /**
     * 如果这里删除了菜单树的父节点，把它的子节点一并删除
     * @param id
     * @return
     */
    @Override
    public Result delete(Integer id) {
        Integer count = roleMenuMapper.countRoleMenuByRoleId(id);
        if (count == 0){
            menuMapper.deleteById(id);
            List<Integer> list = menuMapper.selectByParentId(id);
            if(list.size()>0){
                for (Integer parentId: list){
                    menuMapper.deleteByParentId(parentId);
                }
                menuMapper.deleteByParentId(id);
            }
            return Result.ok().message("删除成功");
        }
        else {
            return Result.error().message("已经有角色分配该菜单，无法删除");
        }

    }

    @Override
    public List<MenuDto> buildMenuAllByRoleId(Integer roleId) {
        List<MenuDto> listByRoleId = menuMapper.listByRoleId(roleId);
        List<MenuDto> permissionDtos = menuMapper.buildAll();
        List<MenuDto> tree = TreeUtil.menutree(listByRoleId, permissionDtos);
        return tree;
    }

    @Override
    public List<MenuIndexDto> getMenu(Integer userId) {
        List<MenuIndexDto> list = menuMapper.listByUserId(userId);
        List<MenuIndexDto> result = TreeUtil.parseMenuTree(list);
        return result;
    }
}

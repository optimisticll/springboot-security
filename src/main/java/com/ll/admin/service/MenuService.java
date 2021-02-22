package com.ll.admin.service;

import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.MenuDto;
import com.ll.admin.dto.MenuIndexDto;
import com.ll.admin.entity.Menu;
import com.ll.admin.entity.User;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:17
 */
public interface MenuService {

    /**
     * 返回菜单
     * @param queryName 用户名
     * @param queryType 类型
     * @return
     */
    List<Menu> getMenuAll(String queryName,Integer queryType);

    /**
     * 获取菜单信息
     * @param id
     * @return
     */
    Menu getMenuById(Integer id);

    /**
     * 菜单树
     * @return
     */
    List<MenuDto> buildMenuAll();

    /**
     * 跟新菜单
     * @param menu
     * @return
     */
    Result updateMenu(Menu menu);

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    Result<Menu> save(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    Result delete(Integer id);

    /**
     * 通过权限id绘制菜单树
     * @param roleId
     * @return
     */
    List<MenuDto> buildMenuAllByRoleId(Integer roleId);

    /**
     * 通过用户id获取菜单
     * @param userId
     * @return
     */
    List<MenuIndexDto> getMenu(Integer userId);
}

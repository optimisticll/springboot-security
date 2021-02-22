package com.ll.admin.mapper;

import com.ll.admin.dto.MenuDto;
import com.ll.admin.dto.MenuIndexDto;
import com.ll.admin.entity.Menu;
import com.ll.admin.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:13
 */
@Mapper
public interface MenuMapper {
    /**
     * 模糊查询菜单
     * @param queryName 查询的表题
     * @param queryType 查询类型
     * @return
     */
    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    List<Menu> getFuzzyMenu(String queryName,Integer queryType);

    /**
     * 通过id查询菜单
     * @param menuId
     * @return
     */
    @Select("select m.menuid,m.parentid,m.menuname,m.icon,m.url,m.permission,m.sort,m.type,m.createtime,m.updatetime from menu m where m.menuid = #{menuId}")
    Menu getMenuById(Integer menuId);

    /**
     * 菜单树
     * @return
     */
    @Select("select m.menuid,m.parentid,m.menuname from menu m")
    @Result(property = "title",column = "menuname")
    @Result(property = "id",column = "menuid")
    List<MenuDto> buildAll();

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    int update(Menu menu);

    /**
     * 新建菜单
     * @param menu
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "menuId")
    @Insert("insert into menu(parentid, menuname, icon, url, permission, sort, type, createtime, updatetime)values(#{parentId}, #{menuName}, #{icon}, #{url}, #{permission}, #{sort}, #{type}, now(), now())")
    int save(Menu menu);

    /**
     * 通过id删除菜单
     * @param menuId
     * @return
     */
    @Delete("delete from menu where menuid = #{menuId}")
    int deleteById(Integer menuId);

    /**
     * 通过父节点删除子菜单
     * @param parentId
     * @return
     */
    @Delete("delete from menu where parentid = #{parentId}")
    int deleteByParentId(Integer parentId);

    /**
     * 通过父节点返回字节点
     * @param parentId
     * @return
     */
    @Select("select m.menuid from menu m where parentid = #{parentId}")
    List<Integer> selectByParentId(Integer parentId);

    /**
     * 通过角色id返回菜单
     * @param roleId
     * @return
     */
    @Select("select m.menuid,m.parentid,m.menuname from menu m inner join role_menu rm on m.menuid = rm.menuid where rm.roleid = #{roleId}")
    @Result(property = "title",column = "menuname")
    @Result(property = "id",column = "menuid")
    List<MenuDto> listByRoleId(Integer roleId);

    /**
     * 通过用户id返回菜单
     * @param userId
     * @return
     */
    List<MenuIndexDto> listByUserId(@Param("userId")Integer userId);

}

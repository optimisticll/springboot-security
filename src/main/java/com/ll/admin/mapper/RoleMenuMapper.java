package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:22
 */
@Mapper
public interface RoleMenuMapper {
    /**
     * 通过id删除rolemenu
     * @param roleId
     * @return
     */
    @Delete("delete from role_menu where roleid = #{roleId}")
    int deleteRoleMenu(Integer roleId);

    /**
     * 新建角色与menu的联系
     * @param roleId
     * @param menuIds
     */
    void save(@Param("roleId")Integer roleId, @Param("menuIds") List<Integer> menuIds);

    /**
     * 通过role_id计算权限数量
     * @param id
     * @return
     */
    @Select("select count(*) from role_menu t where t.menuid = #{menuId}")
    Integer countRoleMenuByRoleId(Integer id);
}

package com.ll.admin.mapper;

import com.ll.admin.dto.RoleDto;
import com.ll.admin.entity.Role;
import com.ll.admin.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:11
 */
@Mapper
public interface RoleMapper {
    /**
     * 分页模糊查询角色
     * @param role
     * @return
     */
    List<Role> getFuzzyRolesByPage(Role role);

    /**
     * 通过id查询角色
     * @param roleId
     * @return
     */
    @Select("select r.roleid,r.rolename,r.datascope,r.description,r.createtime,r.updatetime from role r where r.roleid = #{roleId}")
    Role getRoleById(Integer roleId);

    /**
     * 更新角色
     * @param roleDto
     * @return
     */
    int update(RoleDto roleDto);

    /**
     * 新建角色
     * @param roleDto
     * @return
     */
    int saveRole(RoleDto roleDto);

    /**
     * 通过id删除角色
     * @param roleId
     * @return
     */
    @Delete("delete from role where roleid = #{roleId}")
    int delete(Integer roleId);

    /**
     * 返回所有角色
     * @return
     */
    @Select("select r.roleid,r.rolename,r.description from role r")
    List<Role> getAllRoles();
}

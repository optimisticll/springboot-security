package com.ll.admin.service;

import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.RoleDto;
import com.ll.admin.entity.Role;
import com.ll.admin.entity.User;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:16
 */
public interface RoleService {
    /**
     * 返回角色
     * @param startPosition
     * @param limit
     * @param myRole
     * @return
     */
    Result<Role> getFuzzyRolesByPage(Integer startPosition, Integer limit,Role myRole);

    /**
     * 通过id获得角色信息
     * @param roleId
     * @return
     */
    Role getRoleById(Integer roleId);

    /**
     * 更新角色
     * @param roleDto
     * @return
     */
    Result update(RoleDto roleDto);

    /**
     * 数据权限
     * @param roleDto
     * @return
     */
    Result authDataScope(RoleDto roleDto);
    /**
     * 新建角色
     * @param roleDto
     * @return
     */
    Result save(RoleDto roleDto);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    Result<Role> delete(Integer roleId);

    /**
     * 获取全部角色
     * @return
     */
    Result<Role> getAllRoles();
}

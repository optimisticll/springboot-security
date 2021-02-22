package com.ll.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.annotation.DataPermission;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.RoleDto;
import com.ll.admin.entity.Role;
import com.ll.admin.entity.RoleMenu;
import com.ll.admin.entity.RoleUser;
import com.ll.admin.mapper.RoleDeptMapper;
import com.ll.admin.mapper.RoleMapper;
import com.ll.admin.mapper.RoleMenuMapper;
import com.ll.admin.mapper.RoleUserMapper;
import com.ll.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 14:17
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private RoleDeptMapper roleDeptMapper;

    @Override
    @DataPermission(deptAlias = "d")
    public Result<Role> getFuzzyRolesByPage(Integer offectPosition, Integer limit,Role myRole) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<Role> fuzzyRolesByPage = roleMapper.getFuzzyRolesByPage(myRole);
        return Result.ok().count(page.getTotal()).data(fuzzyRolesByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    @Override
    public Result update(RoleDto roleDto) {
        List<Integer> menuIds = roleDto.getMenuIds();
        menuIds.remove(0L);
        //1、更新角色权限之前要删除该角色之前的所有权限
        roleMenuMapper.deleteRoleMenu(roleDto.getRoleId());
        //2、判断该角色是否有赋予权限值，有就添加"
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMenuMapper.save(roleDto.getRoleId(), menuIds);
        }
        //3、更新角色表
        int countData = roleMapper.update(roleDto);
        if(countData > 0){
            return Result.ok().message("更新成功");
        }else{
            return Result.error().message("更新失败");
        }
    }

    @Override
    public Result authDataScope(RoleDto roleDto) {
        if (roleDto.getDataScope().equals(UserConstants.DATA_SCOPE_CUSTOM)){
            List<Integer> deptIds = roleDto.getDeptIds();
            deptIds.remove(0L);
            roleDeptMapper.deleteRoleDept(roleDto.getRoleId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                roleDeptMapper.save(roleDto.getRoleId(), deptIds);
            }
            roleMapper.update(roleDto);
        }else {
            roleMapper.update(roleDto);
            roleDeptMapper.deleteRoleDept(roleDto.getRoleId());
        }
        return Result.ok().message("更新成功");
    }

    @Override
    public Result save(RoleDto roleDto) {
        roleDto.setDataScope("1");
        //1、先保存角色"
        roleMapper.saveRole(roleDto);
        List<Integer> menuIds = roleDto.getMenuIds();
        //移除0,permission id是从1开始
        //2、保存角色对应的所有权限
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMenuMapper.save(roleDto.getRoleId(), menuIds);
        }
        return Result.ok().message("插入成功");
    }

    @Override
    public Result<Role> delete(Integer id) {
        List<RoleUser> tbRoleUsers = roleUserMapper.listAllRoleUserByRoleId(id);
        if(tbRoleUsers.size() <= 0){
            roleMenuMapper.deleteRoleMenu(id);
            roleMapper.delete(id);
            roleDeptMapper.deleteRoleDept(id);
            return Result.ok().message("删除成功");
        }
        return Result.error().message("该角色已经关联,无法删除");
    }

    @Override
    public Result<Role> getAllRoles() {
        return Result.ok().data(roleMapper.getAllRoles());
    }
}

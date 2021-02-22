package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:22
 */
@Mapper
public interface RoleDeptMapper {
    /**
     * 通过id删除与部门关联
     * @param roleId
     * @return
     */
    @Delete("delete from role_dept where roleid = #{roleId}")
    int deleteRoleDept(Integer roleId);

    /**
     * 新建角色与部门的联系
     * @param id
     * @param deptIds
     */
    void save(@Param("roleId")Integer id, @Param("deptIds") List<Integer> deptIds);
}

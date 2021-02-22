package com.ll.admin.mapper;

import com.ll.admin.dto.DeptDto;
import com.ll.admin.entity.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 13:49
 */
@Mapper
public interface DeptMapper {
    /**
     * 模糊查询部门
     * @param myDept 查询的名称
     * @return
     */
    List<Dept> getFuzzyDept(Dept myDept);


    /**
     * 部门树
     * @param deptDto
     * @return
     */
    List<DeptDto> buildAll(DeptDto deptDto);

    /**
     * 校验部门名称
     * @param deptName 岗位名称
     * @param parentId
     * @return
     */
    Dept checkDeptNameUnique(@Param("deptName")String deptName, @Param("parentId") Integer parentId);


    /**
     * 新增部门信息
     * @param dept 岗位信息
     * @return 结果
     */
    @Insert("INSERT INTO dept(parentid,ancestors,deptname,sort,status, createtime, updatetime) values(#{parentId},#{ancestors},#{deptName},#{sort},#{status}, now(), now())")
    int insertDept(Dept dept);
    /**
     * 根据部门ID查询信息
     * @param deptId 部门ID
     * @return 部门信息
     */
    Dept selectDeptById(Integer deptId);

    /**
     * 通过id查询部门信息
     * @param deptId
     * @return
     */
    @Select("select d.deptid,d.parentid,d.ancestors,d.deptname,d.sort,d.status,d.createtime,d.updatetime from dept d where d.deptid = #{deptId}")
    Dept getDeptById(Integer deptId);


    /**
     * 根据ID查询所有子部门
     *
     * @param id 部门ID
     * @return 部门列表
     */
    List<Dept> selectChildrenDeptById(Integer id);


    /**
     * 根据角色ID查询部门
     *
     * @param id 角色ID
     * @return 部门列表
     */
    List<DeptDto> selectRoleDeptTree(Integer id);
    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts")List<Dept> depts);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(Dept dept);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    void updateDeptStatus(Dept dept);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(Integer deptId);
    /**
     * 查询部门人数
     *
     * @param dept 部门信息
     * @return 结果
     */
    int selectDeptCount(Dept dept);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int checkDeptExistUser(Integer deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Integer deptId);
}

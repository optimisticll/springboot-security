package com.ll.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ll.admin.annotation.DataPermission;
import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.DeptDto;
import com.ll.admin.entity.Dept;
import com.ll.admin.mapper.DeptMapper;
import com.ll.admin.service.DeptService;
import com.ll.admin.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 13:53
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    @DataPermission(deptAlias = "d")
    public List<Dept> getDeptAll(Dept myDept) {
        return deptMapper.getFuzzyDept(myDept);
    }

    @Override
    @DataPermission(deptAlias = "d")
    public List<DeptDto> buildDeptAll(DeptDto deptDto) {
        return deptMapper.buildAll(deptDto);
    }

    @Override
    @DataPermission(deptAlias = "d")
    public List<DeptDto> roleDeptTreeData(Integer roleId) {
        List<DeptDto> selectRoleDeptTree = deptMapper.selectRoleDeptTree(roleId);
        DeptDto deptDto = new DeptDto();
        List<DeptDto> buildAll = deptMapper.buildAll(deptDto);
        List<DeptDto> tree = TreeUtil.deptTree(selectRoleDeptTree, buildAll);
        return tree;
    }

    @Override
    public int insertDept(Dept dept) {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        if (UserConstants.DEPT_DISABLE.equals(info.getStatus().toString()))
        {
            throw new MyException(ResultCode.ERROR,"部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    @Override
    public String checkDeptNameUnique(Dept dept) {
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(),dept.getParentId());
        if (ObjectUtil.isNotEmpty(info) && !info.getDeptId().equals(dept.getDeptId())){
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;

    }

    @Override
    public Dept selectDeptById(Integer deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public Dept getDeptById(Integer deptId) {
        return deptMapper.getDeptById(deptId);
    }

    @Override
    public int updateDept(Dept dept) {
        Dept parentInfo = deptMapper.selectDeptById(dept.getParentId());
        Dept oldInfo = selectDeptById(dept.getDeptId());
        if(ObjectUtil.isNotEmpty(parentInfo) &&ObjectUtil.isNotEmpty(oldInfo)){
            String newAncestors = parentInfo.getAncestors() + "," + parentInfo.getDeptId();
            String oldAncestors = oldInfo.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result =deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus().toString()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    @Override
    public int selectNormalChildrenDeptById(Integer deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    @Override
    public int selectDeptCount(Integer parentId) {
        Dept dept =new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    @Override
    public boolean checkDeptExistUser(Integer deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    @Override
    public int deleteDeptById(Integer deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public int changeStatus(Dept myDept) {
        return deptMapper.updateDept(myDept);
    }

    /**
     * 修改子元素关系
     *
     * @param id 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Integer id, String newAncestors, String oldAncestors)
    {
        List<Dept> children = deptMapper.selectChildrenDeptById(id);
        for (Dept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept)
    {
        dept = deptMapper.selectDeptById(dept.getDeptId());;
        deptMapper.updateDeptStatus(dept);
    }
}

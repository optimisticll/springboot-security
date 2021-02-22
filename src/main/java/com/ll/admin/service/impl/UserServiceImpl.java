package com.ll.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.annotation.DataPermission;
import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.UserDto;
import com.ll.admin.entity.RoleUser;
import com.ll.admin.entity.User;
import com.ll.admin.entity.UserJob;
import com.ll.admin.mapper.RoleUserMapper;
import com.ll.admin.mapper.UserJobMapper;
import com.ll.admin.mapper.UserMapper;
import com.ll.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private UserJobMapper userJobMapper;

    @Override
    @DataPermission(deptAlias = "d", userAlias = "u")
    public Result<User> getAllUsersByPage(Integer offectPosition, Integer limit, User myUser) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<User> fuzzyUserByPage = userMapper.getFuzzyUserByPage(myUser);
        return Result.ok().count(page.getTotal()).data(fuzzyUserByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public void checkUserAllowed(User user) {
        if (!StringUtils.isEmpty(user.getUserId()) && user.isAdmin())
        {
            throw new MyException(ResultCode.ERROR,"不允许操作超级管理员用户");
        }
    }

    @Override
    public String checkPhoneUnique(User myUser) {
        Integer userId = ObjectUtil.isEmpty(myUser.getUserId()) ? -1: myUser.getUserId();
        User info = userMapper.checkPhoneUnique(myUser.getPhone());
        if (ObjectUtil.isNotEmpty(info) && !info.getUserId().equals(userId))
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkUserNameUnique(User user) {
        Integer userId = ObjectUtil.isEmpty(user.getUserId()) ? -1: user.getUserId();
        User info = userMapper.checkUsernameUnique(user.getUsername());
        if (ObjectUtil.isNotEmpty(info) && !info.getUserId().equals(userId))
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public Result<User> updateUser(User user, Integer roleId) {
        if (roleId!=null){
            userMapper.updateUser(user);
            RoleUser myRoleUser = new RoleUser();
            myRoleUser.setUserId(user.getUserId());
            myRoleUser.setRoleId(roleId);
            if(roleUserMapper.getRoleUserByUserId(user.getUserId())!=null){
                roleUserMapper.updateMyRoleUser(myRoleUser);
            }else {
                roleUserMapper.save(myRoleUser);
            }
            userJobMapper.deleteUserJobByUserId(user.getUserId());
            insertUserPost(user);
            return Result.ok().message("更新成功");
        }else {
            return Result.error().message("更新失败");
        }
    }

    @Override
    public int changeStatus(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Result<User> save(User user, Integer roleId) {
        if(roleId!= null){
            userMapper.save(user);
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(user.getUserId().intValue());
            roleUserMapper.save(roleUser);
            insertUserPost(user);
            return Result.ok().message("添加成功，初始密码123456");
        }

        return Result.error().message("添加失败");
    }

    @Override
    public int deleteUser(Integer userId) {
        checkUserAllowed(new User(userId));
        roleUserMapper.deleteRoleUserByUserId(userId);
        userJobMapper.deleteUserJobByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUser(userName);
    }


    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        Integer[] jobs = user.getJobids();

        if (ArrayUtil.isNotEmpty(jobs))
        {
            // 新增用户与岗位管理
            List<UserJob> list = new ArrayList<UserJob>();
            for (Integer jobId : jobs)
            {
                UserJob up = new UserJob();
                up.setUserId(user.getUserId());
                up.setJobId(jobId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userJobMapper.batchUserJob(list);
            }
        }
        return;
    }
}

package com.ll.admin.service;

import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.UserDto;
import com.ll.admin.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:18
 */

public interface UserService {
    /**
     * 返回用户列表
     * @param offectPosition
     * @param limit
     * @param myUser
     * @return
     */
    Result<User> getAllUsersByPage(Integer offectPosition, Integer limit, User myUser);

    /**
     * 根据id返回用户信息
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(User user);

    /**
     * 通过手机查询用户
     * @param user
     * @return
     */
    String checkPhoneUnique(User user);

    /**
     * 通过用户名查询用户
     * @param user
     * @return
     */
    String checkUserNameUnique(User user);

    /**
     * 更新用户
     * @param myUser
     * @param roleId
     * @return
     */
    Result<User> updateUser(User myUser, Integer roleId);

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    int changeStatus(User user);

    /**
     * 新建用户
     * @param myUser
     * @param roleId
     * @return
     */
    Result<User> save(User myUser, Integer roleId);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);
    /**
     *  根据用户名查询用户
     * @param userName
     * @return
     */
    User getUserByName(String userName);
}

package com.ll.admin.service;

import com.ll.admin.entity.RoleUser;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 11:13
 */
public interface RoleUserService {
    /**
     * 返回用户拥有的角色
     * @param userId
     * @return
     */
    List<RoleUser> getMyRoleUserByUserId(Integer userId);
}

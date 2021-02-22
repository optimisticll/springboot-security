package com.ll.admin.service.impl;

import com.ll.admin.entity.RoleUser;
import com.ll.admin.mapper.RoleUserMapper;
import com.ll.admin.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 15:05
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserMapper roleUserDao;
    @Override
    public List<RoleUser> getMyRoleUserByUserId(Integer userId) {
        return roleUserDao.getMyRoleUserByUserId(userId);

    }
}

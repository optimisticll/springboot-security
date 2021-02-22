package com.ll.admin.service.impl;

import com.ll.admin.dto.JwtUserDto;
import com.ll.admin.dto.MenuDto;
import com.ll.admin.dto.MenuIndexDto;
import com.ll.admin.entity.Role;
import com.ll.admin.entity.RoleUser;
import com.ll.admin.entity.User;
import com.ll.admin.mapper.MenuMapper;
import com.ll.admin.service.RoleService;
import com.ll.admin.service.RoleUserService;
import com.ll.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihaoxuan
 * @date 2020/12/25 13:52
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public JwtUserDto loadUserByUsername(String userName){
        // 根据用户名获取用户
        User user = userService.getUserByName(userName);
        if (user == null ){
            throw new BadCredentialsException("用户名或密码错误");
        }else if (user.getStatus().equals(User.Status.LOCKED)) {
            throw new LockedException("用户被锁定,请联系管理员解锁");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<MenuIndexDto> list = menuMapper.listByUserId(user.getUserId());
        List<String> collect = list.stream().map(MenuIndexDto::getPermission).collect(Collectors.toList());
        for (String authority : collect){
            if (!("").equals(authority) & authority !=null){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
                grantedAuthorities.add(grantedAuthority);
            }
        }
        //将用户所拥有的权限加入GrantedAuthority集合中
        JwtUserDto loginUser =new JwtUserDto(user,grantedAuthorities);
        loginUser.setRoleInfo(getRoleInfo(user));
        return loginUser;
    }


    public List<Role> getRoleInfo(User user) {
        User userByName = userService.getUserByName(user.getUsername());
        List<RoleUser> roleUserByUserId = roleUserService.getMyRoleUserByUserId(userByName.getUserId());
        List <Role> roleList = new ArrayList<>();
        for (RoleUser roleUser:roleUserByUserId){
            Integer roleId = roleUser.getRoleId();
            Role roleById = roleService.getRoleById(roleId);
            roleList.add(roleById);
        }
        return roleList;
    }

}

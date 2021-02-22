package com.ll.admin.mapper;

import com.ll.admin.dto.UserDto;
import com.ll.admin.entity.Menu;
import com.ll.admin.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:16
 */
@Mapper
public interface UserMapper {

    /**
     * 分页返回所有用户
     * @param myUser
     * @return
     */
    List<User> getFuzzyUserByPage(User myUser);

    //计算所有用户数量
    // @Select("select count(*) from My_user")
    // Long countAllUser();

    /**
     *
     * 通过id返回用户
     * @param userId
     * @return
     */
    @Select("select u.userid,u.deptid,u.username,u.password,u.nickname,u.phone,u.email,u.status,u.createtime,u.updatetime from user u where u.userid = #{userId}")
    User getUserById(Integer userId);

    /**
     * 通过手机返回用户
     * @param phone
     * @return
     */
    User checkPhoneUnique(String phone);

    /**
     * 通过用户名返回用户
     * @param userName
     * @return
     */
    User checkUsernameUnique(String userName);
    /**
     * 更新用户
     * @param myUser
     * @return
     */
    int updateUser(User myUser);



    /**
     * 插入用户
     * @param myUser
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into user(deptid,username, password, nickname, phone, email, status, createtime, updatetime) values(#{deptId},#{userName}, #{password}, #{nickName}, #{phone}, #{email}, #{status}, now(), now())")
    int save(User myUser);

    /**
     * 通过id删除用户
     * @param userId
     * @return
     */
    @Delete("delete from user where userid = #{userId}")
    int deleteUserById(Integer userId);



    /**
     *  根据用户名查询用户
     * @param userName
     * @return
     */
    @Select("select * from user t where t.username = #{userName}")
    User getUser(String userName);

}

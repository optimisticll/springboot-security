package com.ll.admin.mapper;

import com.ll.admin.entity.RoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:23
 */
@Mapper
public interface RoleUserMapper {
    /**
     * 通过角色id返回所有用户
     * @param id
     * @return
     */
    @Select("select * from role_user ru where ru.roleid = #{roleId}")
    List<RoleUser> listAllRoleUserByRoleId(Integer id);



    /**
     * 通过用户id查询权限id
     * @param userId
     * @return
     */
    @Select("select * from role_user ru where ru.userid = #{userId}")
    List<RoleUser> getMyRoleUserByUserId(Integer userId);

    /**
     * 通过用户id返回角色
     * @param intValue
     * @return
     */
    @Select("select * from role_user ru where ru.userid = #{userId}")
    RoleUser getRoleUserByUserId(int intValue);

    /**
     * 更新
     * @param myRoleUser
     * @return
     */
    int updateMyRoleUser(RoleUser myRoleUser);

    /**
     * 新建
     * @param myRoleUser
     * @return
     */
    @Insert("insert into role_user(userid, roleid) values(#{userId}, #{roleId})")
    int save(RoleUser myRoleUser);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from role_user where userid = #{userId}")
    int deleteRoleUserByUserId(Integer id);
}

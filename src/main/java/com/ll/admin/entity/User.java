package com.ll.admin.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author lihaoxuan
 * @date 2020/12/21 15:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {
    private static final long serialVersionUID = -6525908145032868837L;

    private Integer userId;

    private Integer deptId;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String email;

    private Integer status;

    public interface Status{
        int LOCKED = 0;
        int VALID = 1;
    }

    private Integer roleid;

    private Integer[] jobids;

    /**
     * 判断是否为admin用户
     * @return
     */
    public boolean isAdmin()
    {
        return isAdmin(this.getUserId());
    }

    public static boolean isAdmin(Integer userId)
    {
        return userId != null && 1L == userId;
    }

    public User(Integer userid){
        this.setUserId(userid);
    }

}

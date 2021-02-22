package com.ll.log.utils;

import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.dto.JwtUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @author lihaoxuan
 * @date 2020/12/28 9:46
 */
public class SecurityUtils {

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyException(ResultCode.UNAUTHORIZED, "当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
    /**
     * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
     * 无用
     */
    public static String getCurrentUserIp() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new MyException(ResultCode.UNAUTHORIZED, "当前登录状态过期");
        }
        Object details = authentication.getDetails();
        if (!(details instanceof WebAuthenticationDetails)) {
            return "";
        }
        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
        return webDetails.getRemoteAddress();
    }

    /**
     * 获取系统用户
     *
     * @return 系统用户名称
     */
    public static JwtUserDto getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyException(ResultCode.UNAUTHORIZED, "当前登录状态过期");
        }
        JwtUserDto userDetails = (JwtUserDto) authentication.getPrincipal();
        return userDetails;
    }
}

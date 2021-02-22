package com.ll.admin.controller;

import com.ll.admin.common.utils.PageTableRequest;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.UserDto;
import com.ll.admin.entity.User;
import com.ll.admin.service.JobService;
import com.ll.admin.service.UserService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:21
 */
@Controller
@RequestMapping("/api/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public String index(){
        return "system/user/user";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAnyAuthority('user:list')")
    @Log("查询用户")
    public Result<User> userList(PageTableRequest pageTableRequest, User user){
        pageTableRequest.countOffset();
        return userService.getAllUsersByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit(),user);
    }

    @GetMapping("/add")
    @ApiOperation(value = "添加用户页面")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String addUser(Model model){
        model.addAttribute("myUser",new User());
        model.addAttribute("jobs",jobService.selectJobAll());
        return "/system/user/user-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加用户")
    @PreAuthorize("hasAnyAuthority('user:add')")
    @Log("添加用户")
    public Result<User> saveUser(@RequestBody User user){
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))){
            return Result.error().message("手机号已存在");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUserNameUnique(user))){
            return Result.error().message("用户名已存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        return userService.save(user,user.getRoleid());
    }

    @GetMapping("edit")
    @ApiOperation(value = "修改用户界面")
    @PreAuthorize("hasAnyAuthority('user:edit')")
    public String editUser(Model model, User tbUser){
        model.addAttribute("myUser",userService.getUserById(tbUser.getUserId()));
        model.addAttribute("jobs",jobService.selectJobsByUserId(tbUser.getUserId()));
        return "/system/user/user-edit";
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAnyAuthority('user:edit')")
    @Log("修改用户")
    public Result updateUser(@RequestBody User user){
        User userById = userService.getUserById(user.getUserId());
        userService.checkUserAllowed(userById);
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))){

            return Result.error().message("手机号已存在");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUserNameUnique(user))){
            return Result.error().message("用户名已存在");
        }
        return userService.updateUser(user,user.getRoleid());
    }
    /**
     * 用户状态修改
     */
    @Log("修改用户状态")
    @PutMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "修改用户状态")
    @PreAuthorize("hasAnyAuthority('user:edit')")
    public Result changeStatus(@RequestBody User myUser)
    {
        userService.checkUserAllowed(myUser);
        userService.changeStatus(myUser);
        return Result.judge(userService.changeStatus(myUser),"修改成功");
    }

    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAnyAuthority('user:del')")
    @Log("删除用户")
    public Result deleteUser(Integer userId){
        int count = userService.deleteUser(userId);
        return Result.judge(count,"删除用户");
    }
}

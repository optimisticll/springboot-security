package com.ll.admin.task.controller;

import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.PageTableRequest;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Job;
import com.ll.admin.entity.Task;
import com.ll.admin.entity.User;
import com.ll.admin.service.TaskService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author lihaoxuan
 * @date 2020/12/30 10:28
 */
@Controller
@RequestMapping("/api/quartz")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/index")
    @ApiOperation(value = "返回定时任务页面")
    public String getTaskList(){
        return "system/task/task";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "定时任务列表")
    @PreAuthorize("hasAnyAuthority('task:list')")
    @Log("查询定时任务")
    public Result<Task> userList(PageTableRequest pageTableRequest, JobQueryDto task){
        pageTableRequest.countOffset();
        return taskService.getAllTaskByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit(),task);
    }

    @Log("修改定时任务状态")
    @PutMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "修改定时任务状态")
    @PreAuthorize("hasAnyAuthority('task:edit')")
    public Result changeStatus(@RequestBody Task task){
        int i = taskService.changeStatus(task);
        return Result.judge(i,"修改");
    }

    @GetMapping("/add")
    @ApiOperation(value = "添加定时任务页面")
    @PreAuthorize("hasAnyAuthority('task:add')")
    public String addJob(Model model){
        model.addAttribute("MyTask",new Task());
        return "/system/task/task-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加定时任务")
    @PreAuthorize("hasAnyAuthority('task:add')")
    @Log("添加定时任务")
    public Result saveJob(@RequestBody Task task){
        if (UserConstants.JOB_NAME_NOT_UNIQUE.equals(taskService.checkJobNameUnique(task))) {
            return Result.error().message("新增定时任务'" + task.getJobname() + "'失败，定时任务名称已存在");
        }
        return Result.judge(taskService.insertTask(task),"添加定时任务");
    }

    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除定时任务")
    @PreAuthorize("hasAnyAuthority('task:del')")
    @Log("删除定时任务")
    public Result deleteTask(String ids){
        try{
            taskService.deleteTask(ids);
            return Result.ok().message("删除成功");
        }catch (MyException e) {
            return Result.error().message(e.getMessage()).code(e.getCode());
        }
    }

    /*@PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "执行定时任务")
    @PreAuthorize("hasAnyAuthority('task:edit')")
    @Log("执行定时任务")
    public Result Immediately(String taskid){

    }*/
}

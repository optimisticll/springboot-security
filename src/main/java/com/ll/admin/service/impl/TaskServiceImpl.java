package com.ll.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Task;
import com.ll.admin.entity.User;
import com.ll.admin.enums.JobStatusEnum;
import com.ll.admin.mapper.TaskMapper;
import com.ll.admin.service.TaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/30 9:20
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void intiSchedule() throws SchedulerException {
        //获取任务信息数据
        List<Task> joblist = taskMapper.gettasklist();
        for(Task task : joblist){
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobstatus())) {

            }
        }
    }

    @Override
    public Result<Task> getAllTaskByPage(Integer offectPosition, Integer limit, JobQueryDto task) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<Task> fuzzyTaskByPage = taskMapper.getFuzzyTaskByPage(task.getQueryName(),task.getQueryStatus());
        return Result.ok().count(page.getTotal()).data(fuzzyTaskByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public int changeStatus(Task task) {
        return taskMapper.updateTask(task);
    }

    @Override
    public String checkJobNameUnique(Task task) {
        Task info = taskMapper.checkJobNameUnique(task.getJobname());
        if (ObjectUtil.isNotEmpty(info) && !info.getTaskid().equals (task.getTaskid())) {
            return UserConstants.TASK_NAME_NOT_UNIQUE;
        }
        return UserConstants.TASK_NAME_UNIQUE;
    }

    @Override
    public int insertTask(Task task) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setCreateuser(username);
        return taskMapper.insertTask(task);
    }

    @Override
    public int deleteTask(String ids) {
        Integer[] taskIds = Convert.toIntArray(ids);
        return taskMapper.deleteTaskbyid(taskIds);
    }

    /**
     * 暂停一个任务
     * */
    @Override
    public void pauseJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobname(),task.getJobgroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个任务
     * */
    @Override
    public void resumeJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobname(),task.getJobgroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个任务
     * */
    @Override
    public void deletejob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobname(),task.getJobgroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即触发一个任务
     * */
    @Override
    public void runJob(Task task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobname(),task.getJobgroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新cron表达式
     * */
    @Override
    public void updateCron(Task task) throws SchedulerException {
        TriggerKey triggerKey =TriggerKey.triggerKey(task.getJobname(),task.getJobgroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronexpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey,trigger);
    }
}

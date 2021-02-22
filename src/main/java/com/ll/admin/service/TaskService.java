package com.ll.admin.service;

import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Task;
import com.ll.admin.entity.User;
import org.quartz.SchedulerException;

/**
 * @author lihaoxuan
 * @date 2020/12/30 9:19
 */
public interface TaskService {

    public void intiSchedule() throws SchedulerException;

    /**
     * 返回定时列表
     * @param offectPosition
     * @param limit
     * @param myUser
     * @return
     */
    Result<Task> getAllTaskByPage(Integer offectPosition, Integer limit, JobQueryDto task);


    /**
     * 修改定时任务状态
     * */
    int changeStatus(Task task);

    /**
     * 校验定时任务是否存在
     * */
    String checkJobNameUnique(Task task);

    /**
     * 添加定时任务
     * */
    int insertTask(Task task);

    /**
     * 删除一个任务
     * */
    int deleteTask(String ids);


    /**
     * 暂停一个任务
     * */
    public void pauseJob(Task task) throws SchedulerException;

    /**
     * 恢复一个任务
     * */
    public void resumeJob(Task task) throws SchedulerException;

    /**
     * 删除一个任务
     * */
    public void deletejob(Task task) throws SchedulerException;

    /**
     * 立即触发一个任务
     * */
    public void runJob(Task task) throws SchedulerException;

    /**
     * 更新cron表达式
     * */
    public  void updateCron(Task task) throws SchedulerException;

}

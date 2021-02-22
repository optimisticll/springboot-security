package com.ll.admin.task;

import com.ll.admin.entity.Task;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lihaoxuan
 * @date 2020/12/30 9:49
 */
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    @SuppressWarnings("unchecked")
    public void addJob(Task task){
        try{
            //创建jobdetail实例、绑定job实现类
            //指明job的名称、所在组的名称、以及绑定job实现类
            Class<? extends Job> jobclass = (Class<? extends Job>)(Class.forName(task.getBeanclass()).newInstance().getClass());
            JobDetail jobDetail = JobBuilder.newJob(jobclass).withIdentity(task.getJobname(),task.getJobgroup()).build();
            //定义调度触发规则
            //使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobname(),task.getJobgroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronexpression())).startNow().build();
            //把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail,trigger);
            //启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.ll.admin.listener;

import com.ll.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author lihaoxuan
 * @date 2020/12/29 18:01
 */
@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    TaskService scheduleJobService;

    @Override
    public void run(String... args) throws Exception {
        try{
            scheduleJobService.intiSchedule();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

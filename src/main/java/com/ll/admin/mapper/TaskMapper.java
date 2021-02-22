package com.ll.admin.mapper;

import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/30 9:22
 */
@Mapper
public interface TaskMapper {

    @Select("select * from task")
    List<Task> gettasklist();

    List<Task> getFuzzyTaskByPage(String queryName,Integer queryStatus);

    int updateTask(Task task);

    Task checkJobNameUnique(String jobname);

    @Insert("insert into task(jobname,description,cronexpression,beanclass,jobstatus,jobgroup,createuser,createtime,updatetime) " +
            "values(#{jobname},#{description},#{cronexpression},#{beanclass},#{jobstatus},#{jobgroup},#{createuser},now(),now())")
    int insertTask(Task task);

    int deleteTaskbyid(Integer[] ids);
}

package com.ll.admin.mapper;

import com.ll.admin.entity.Job;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:20
 */
@Mapper
public interface JobMapper {
    /**
     * 模糊查询岗位
     * @param queryName 查询的名称
     * @param queryStatus 状态查询
     * @return
     */
    List<Job> getFuzzyJob(String queryName, Integer queryStatus);

    /**
     * 新增岗位信息
     * @param job 岗位信息
     * @return 结果
     */
    @Insert("INSERT INTO job(jobname,status,sort, createtime, updatetime) values(#{jobName},#{status},#{sort}, now(), now())")
    int insertDept(Job job);


    /**
     * 校验岗位名称
     * @param name 岗位名称
     * @return 结果
     */
    Job checkJobNameUnique(String name);
    /**
     * 通过id查询岗位信息
     * @param jobId
     * @return
     */
    @Select("select j.jobid,j.jobname,j.status,j.sort,j.createtime,j.updatetime from job j where j.jobid = #{jobId}")
    Job getJobById(Integer jobId);

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJobByIds(Integer[] ids);

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<Job> selectJobsByUserId(Integer userId);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<Job> selectJobAll();

    /**
     * 修改岗位信息
     *
     * @param myJob 岗位信息
     * @return 结果
     */
    int updateJob(Job myJob);
}

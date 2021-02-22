package com.ll.admin.service;

import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.Result;
import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Job;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:47
 */
public interface JobService {
    /**
     * 返回岗位
     * @param offectPosition
     * @param limit
     * @param jobQueryDto
     * @return
     */
    Result<Job> getJobAll(Integer offectPosition, Integer limit, JobQueryDto jobQueryDto);

    /**
     * 新增岗位信息
     * @param job 岗位信息
     * @return 结果
     */
    int insertJob(Job job);

    /**
     * 校验岗位名称
     *
     * @param job 岗位信息
     * @return 结果
     */
    String checkJobNameUnique(Job job);

    /**
     * 通过id获得岗位信息
     * @param jobId
     * @return
     */
    Job getJobById(Integer jobId);

    /**
     * 批量删除岗位信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws MyException 异常
     */
    int deleteJobByIds(String ids) throws MyException;

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param jobId 岗位ID
     * @return 结果
     */
    int countUserJobtById(Integer jobId);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<Job> selectJobAll();
    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<Job> selectJobsByUserId(Integer userId);

    /**
     * 修改保存岗位信息
     *
     * @param myJob 岗位信息
     * @return 结果
     */
    int updateJob(Job myJob);

    /**
     * 修改岗位状态
     * @param myUser
     * @return
     */
    int changeStatus(Job myUser);
}

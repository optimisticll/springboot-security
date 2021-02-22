package com.ll.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.dto.JobQueryDto;
import com.ll.admin.entity.Job;
import com.ll.admin.mapper.JobMapper;
import com.ll.admin.mapper.UserJobMapper;
import com.ll.admin.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:58
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private UserJobMapper userJobMapper;

    @Override
    public Result<Job> getJobAll(Integer offectPosition, Integer limit, JobQueryDto jobQueryDto) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<Job> fuzzyJob = jobMapper.getFuzzyJob(jobQueryDto.getQueryName(), jobQueryDto.getQueryStatus());
        return Result.ok().count(page.getTotal()).data(fuzzyJob).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public int insertJob(Job job) {
        return jobMapper.insertDept(job);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param job 岗位信息
     * @return 结果
     */
    @Override
    public String checkJobNameUnique(Job job) {
        Job info = jobMapper.checkJobNameUnique(job.getJobName());
        if (ObjectUtil.isNotEmpty(info) && !info.getJobId().equals (job.getJobId())){
            return UserConstants.JOB_NAME_NOT_UNIQUE;
        }
        return UserConstants.JOB_NAME_UNIQUE;
    }

    @Override
    public Job getJobById(Integer jobId) {
        return jobMapper.getJobById(jobId);
    }

    @Override
    public int deleteJobByIds(String ids) throws MyException {
        Integer[] jobIds = Convert.toIntArray(ids);
        for (Integer jobid:jobIds){
            Job job = getJobById(jobid);
            if (countUserJobtById(jobid)>0){
                throw new MyException(ResultCode.ERROR,job.getJobName()+ "已分配,不能删除");
            }
        }
        return jobMapper.deleteJobByIds(jobIds);
    }

    @Override
    public int countUserJobtById(Integer jobId) {
        return userJobMapper.countUserJobtById(jobId);
    }

    @Override
    public List<Job> selectJobAll() {
        return jobMapper.selectJobAll();
    }

    @Override
    public List<Job> selectJobsByUserId(Integer userId) {
        List<Job> userJobs = jobMapper.selectJobsByUserId(userId);
        List<Job>jobs =jobMapper.selectJobAll();
        for (Job job : jobs)
        {
            for (Job userJob : userJobs)
            {
                if (job.getJobId().equals(userJob.getJobId()))
                {
                    job.setFlag(true);
                    break;
                }
            }
        }
        return jobs;
    }

    @Override
    public int updateJob(Job myJob) {
        return jobMapper.updateJob(myJob);
    }

    @Override
    public int changeStatus(Job myJob) {
        return jobMapper.updateJob(myJob);
    }
}

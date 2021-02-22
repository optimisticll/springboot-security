package com.ll.admin.mapper;

import com.ll.admin.entity.UserJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:24
 */
@Mapper
public interface UserJobMapper {
    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param jobId 岗位ID
     * @return 结果
     */
    @Select("select count(1) from user_job where jobid=#{jobId}")
    int countUserJobtById(Integer jobId);

    /**
     * 批量新增用户岗位信息
     *
     * @param userJobList 用户角色列表
     * @return 结果
     */
    int batchUserJob(List<UserJob> userJobList);

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param id 用户ID
     * @return 结果
     */
    int deleteUserJobByUserId(Integer id);
}

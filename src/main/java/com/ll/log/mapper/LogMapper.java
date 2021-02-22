package com.ll.log.mapper;

import com.ll.log.dto.ErrorLogDto;
import com.ll.log.dto.LogDto;
import com.ll.log.dto.LogQuery;
import com.ll.log.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/28 16:11
 */
@Mapper
public interface LogMapper {
    /**
     * 保存日志
     * @param log
     */
    @Insert("insert into log (username,ip,description,params,type,exceptiondetail,browser,method,time,createtime) values (#{username},#{ip},#{description},#{params},#{type},#{exceptiondetail},#{browser},#{method},#{time},now())")
    void save(Log log);

    /**
     * 分页返回所有用户日志
     * @param logQuery 查询条件
     * @return
     */
    List<LogDto> getFuzzyLogByPage(@Param("logQuery") LogQuery logQuery);


    /**
     * 分页返回所有错误日志
     * @param logQuery 查询条件
     * @return
     */
    List<ErrorLogDto> getFuzzyErrorLogByPage(@Param("logQuery") LogQuery logQuery);


    /**
     * 删除所有日志
     * @param type 日志类型
     */
    @Delete("delete from my_log where type = #{type}")
    void delAllByInfo(String type);

}

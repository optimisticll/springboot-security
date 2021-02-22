package com.ll.log.service.impl;

import cn.hutool.json.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.log.dto.ErrorLogDto;
import com.ll.log.dto.LogDto;
import com.ll.log.dto.LogQuery;
import com.ll.log.entity.Log;
import com.ll.log.mapper.LogMapper;
import com.ll.log.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/28 16:14
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Result<LogDto> getFuzzyInfoLogByPage(Integer offectPosition, Integer limit, LogQuery logQuery) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<LogDto> fuzzyLogByPage = logMapper.getFuzzyLogByPage(logQuery);
        return Result.ok().count(page.getTotal()).data(fuzzyLogByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public Result<ErrorLogDto> getFuzzyErrorLogByPage(Integer offectPosition, Integer limit, LogQuery logQuery) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<ErrorLogDto> fuzzyErrorLogByPage = logMapper.getFuzzyErrorLogByPage(logQuery);
        return Result.ok().count(page.getTotal()).data(fuzzyErrorLogByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String userName, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.ll.log.annotation.Log myLog = method.getAnnotation(com.ll.log.annotation.Log.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(myLog.value());
        }
        assert log != null;
        log.setIp(ip);
        String loginPath = "login";
        if(loginPath.equals(signature.getName())){
            try {
                assert argValues != null;
                userName = new JSONObject(argValues[0]).get("userName").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUsername(userName);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logMapper.save(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logMapper.delAllByInfo("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logMapper.delAllByInfo("INFO");
    }
}

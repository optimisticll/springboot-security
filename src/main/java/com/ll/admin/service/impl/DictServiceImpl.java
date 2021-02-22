package com.ll.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.entity.Dict;
import com.ll.admin.mapper.DictDetailMapper;
import com.ll.admin.mapper.DictMapper;
import com.ll.admin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:55
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictDetailMapper dictDetailMapper;
    @Override
    public Result<Dict> getDictPage(Integer offectPosition, Integer limit, Dict myDict) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<Dict> fuzzyDictByPage = dictMapper.getFuzzyDictByPage(myDict);
        return Result.ok().count(page.getTotal()).data(fuzzyDictByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public Dict getDictByName(String dictName) {
        return dictMapper.getDictByName(dictName);
    }

    @Override
    public String checkDictNameUnique(Dict dict) {
        Dict info = dictMapper.getDictByName(dict.getDictName());
        if (ObjectUtil.isNotEmpty(info) && !info.getDictId().equals (dict.getDictId())){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertDict(Dict dict) {
        return dictMapper.insertDict(dict);
    }

    @Override
    public Dict getDictById(Integer dictId) {
        return dictMapper.getDictById(dictId);
    }

    @Override
    public int updateDict(Dict myDict) {
        return dictMapper.updateDict(myDict);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteDictByIds(String ids)  throws MyException {
        Integer[] dictIds = Convert.toIntArray(ids);
        for (Integer dictId : dictIds){
            dictDetailMapper.deleteDictDetailByDictId(dictId);
        }
        return dictMapper.deleteDictByIds(dictIds);
    }
}

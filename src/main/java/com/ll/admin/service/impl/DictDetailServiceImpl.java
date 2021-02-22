package com.ll.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.ResultCode;
import com.ll.admin.entity.Dict;
import com.ll.admin.entity.DictDetail;
import com.ll.admin.mapper.DictDetailMapper;
import com.ll.admin.service.DictDetailService;
import com.ll.admin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:50
 */
@Service
public class DictDetailServiceImpl implements DictDetailService {
    @Autowired
    private DictService dictService;

    @Autowired
    private DictDetailMapper dictDetailMapper;

    @Override
    public Result<DictDetail> getDictByName(Integer offectPosition, Integer limit, String dictName) {
        Dict dictByName =dictService.getDictByName(dictName);
        Integer dictId = dictByName.getDictId();
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<DictDetail> fuzzyDictDetailByPage = getDictDetail(dictId);
        return Result.ok().count(page.getTotal()).data(fuzzyDictDetailByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public List<DictDetail> getDictDetail(Integer dictId) {
        return dictDetailMapper.getDictDetail(dictId);
    }

    @Override
    public int insertDictDetail(DictDetail DictDetail) {
        return dictDetailMapper.insertDictDetail(DictDetail);
    }

    @Override
    public DictDetail getDictDetailById(Integer id) {
        return dictDetailMapper.getDictDetailById(id);
    }

    @Override
    public int updateDictDetail(DictDetail dictDetail) {
        return dictDetailMapper.updateDictDetail(dictDetail);
    }

    @Override
    public int deleteDictDetailByIds(String ids) {
        Integer[] dictDetailIds = Convert.toIntArray(ids);
        return dictDetailMapper.deleteDictDetailByIds(dictDetailIds);
    }

    @Override
    public int deleteDictDetailByDictId(Integer dictId) {
        return dictDetailMapper.deleteDictDetailByDictId(dictId);
    }
}

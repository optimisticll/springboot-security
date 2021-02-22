package com.ll.admin.service;

import com.ll.admin.common.utils.Result;
import com.ll.admin.entity.DictDetail;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:46
 */
public interface DictDetailService {
    /**
     * 通过字典名获取字典详情
     * @param dictName
     * @return
     */
    Result<DictDetail> getDictByName(Integer offectPosition, Integer limit, String dictName);


    /**
     * 根据字典id获取字典详情
     * @param dictId
     * @return
     */
    List<DictDetail> getDictDetail(Integer dictId);

    /**
     * 新增字典详情
     * @param myDictDetail
     * @return
     */
    int insertDictDetail(DictDetail myDictDetail);

    /**
     * 通过id获得字典信息
     * @param id
     * @return
     */
    DictDetail getDictDetailById(Integer id);

    /**
     * 修改保存字典详情信息
     *
     * @param myDictDetail 岗位信息
     * @return 结果
     */
    int updateDictDetail(DictDetail myDictDetail);

    /**
     * 批量删除字典详情信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws MyException 异常
     */
    int deleteDictDetailByIds(String ids);

    /**
     *
     * 根据字典id删除字典详情
     * @param dictId
     * @return
     */
    int deleteDictDetailByDictId(Integer dictId);
}

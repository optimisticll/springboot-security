package com.ll.admin.service;


import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.Result;
import com.ll.admin.entity.Dict;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:46
 */
public interface DictService {
    /**
     * 分页返回字典
     * @return
     */
    Result<Dict> getDictPage(Integer offectPosition, Integer limit, Dict myDict);

    /**
     * 通过字典名获取字典
     * @param dictName
     * @return
     */
    Dict getDictByName(String dictName);

    /**
     * 校验字典名称
     *
     * @param myDict 岗位信息
     * @return 结果
     */
    String checkDictNameUnique(Dict myDict);

    /**
     * 新增字典信息
     * @param myDict 岗位信息
     * @return 结果
     */
    int insertDict(Dict myDict);

    /**
     * 通过id获得字典信息
     * @param dictId
     * @return
     */
    Dict getDictById(Integer dictId);

    /**
     * 修改保存自带你信息
     *
     * @param myDict 岗位信息
     * @return 结果
     */
    int updateDict(Dict myDict);

    /**
     * 批量删除岗位信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws MyException 异常
     */
    int deleteDictByIds(String ids)throws MyException;
}

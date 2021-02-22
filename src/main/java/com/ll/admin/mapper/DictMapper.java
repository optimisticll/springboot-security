package com.ll.admin.mapper;

import com.ll.admin.entity.Dict;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据字典 Mapper 接口
 * @author lihaoxuan
 * @date 2020/12/29 14:08
 */
@Mapper
public interface DictMapper {
    /**
     * 模糊查询字典
     * @param myDict 状态查询
     * @return
     */
    List<Dict> getFuzzyDictByPage(Dict dict);

    /**
     * 通过字典名称获取字典信息
     * @param dictName
     * @return
     */
    Dict getDictByName(String dictName);

    /**
     * 插入字典
     * @param myDict
     * @return
     */
    @Insert("INSERT INTO dict(dictid,dictname,description, sort,createtime, updatetime)values(#{dictId},#{dictName},#{description},#{sort}, now(), now())")
    int insertDict(Dict myDict);

    /**
     * 通过id获得字典信息
     * @param dictId
     * @return
     */
    @Select("select di.dictid,di.dictname,di.description,di.sort,di.createtime,di.updatetime from dict di  where di.dictid = #{dictId}")
    Dict getDictById(Integer dictId);

    /**
     * 修改保存字典信息
     *
     * @param myDict 岗位信息
     * @return 结果
     */
    int updateDict(Dict myDict);

    /**
     * 批量删除岗位信息
     *
     * @param dictIds 需要删除的数据ID
     * @return 结果
     */
    int deleteDictByIds(Integer[] dictIds);
}

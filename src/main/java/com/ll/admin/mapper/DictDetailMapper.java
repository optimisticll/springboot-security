package com.ll.admin.mapper;

import com.ll.admin.entity.DictDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:18
 */
@Mapper
public interface DictDetailMapper {

    List<DictDetail> getDictDetail(Integer dictId);
    /**
     * 插入字典详情
     * @param myDictDetail
     * @return
     */
    @Insert("INSERT INTO dict_detail(dictid,label,value, sort,createtime, updatetime)values(#{dictId},#{label},#{value},#{sort}, now(), now())")
    int insertDictDetail(DictDetail DictDetail);

    /**
     * 通过id获得字典详情信息
     * @param id
     * @return
     */
    @Select("select did.id,did.dict_id,did.label,did.value,did.sort,did.createtime,did.updatetime from dict_detail did  where did.id = #{id}")
    DictDetail getDictDetailById(Integer id);

    /**
     * 修改保存字典详情信息
     *
     * @param myDictDetail 岗位信息
     * @return 结果
     */
    int updateDictDetail(DictDetail dictDetail);

    /**
     * 批量删除字典详情
     *
     * @param dictDetailIds 需要删除的数据ID
     * @return 结果
     */
    int deleteDictDetailByIds(Integer[] dictDetailIds);

    /**
     *
     * 根据字典id删除字典详情
     * @param id
     * @return
     */
    @Delete("DELETE from dict_detail where dictid = #{dictId}")
    int deleteDictDetailByDictId(Integer dictId);
}

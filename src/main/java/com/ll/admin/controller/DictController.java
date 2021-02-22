package com.ll.admin.controller;

import com.ll.admin.common.exception.MyException;
import com.ll.admin.common.utils.PageTableRequest;
import com.ll.admin.common.utils.Result;
import com.ll.admin.common.utils.UserConstants;
import com.ll.admin.entity.Dict;
import com.ll.admin.entity.Job;
import com.ll.admin.service.DictService;
import com.ll.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:07
 */
@Controller
@RequestMapping("/api/dict")
@Api(tags = "系统：字典管理")
public class DictController {
    @Autowired
    private DictService dictService;


    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('dict:list')")
    public String index(){
        return "system/dict/dict";
    }


    @GetMapping
    @ResponseBody
    @ApiOperation(value = "字典列表")
    @PreAuthorize("hasAnyAuthority('dict:list')")
    @Log("查询字典列表")
    public Result getDictAll(PageTableRequest pageTableRequest, Dict dict){
        pageTableRequest.countOffset();
        return dictService.getDictPage(pageTableRequest.getOffset(),pageTableRequest.getLimit(),dict);
    }


    @GetMapping("/add")
    @ApiOperation(value = "添加字典页面")
    @PreAuthorize("hasAnyAuthority('dict:add')")
    public String addDict(Model model){
        model.addAttribute("MyDict",new Dict());
        return "/system/dict/dict-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加字典")
    @PreAuthorize("hasAnyAuthority('dict:add')")
    @Log("添加字典")
    public Result saveDict(@RequestBody Dict dict){
        if (UserConstants.NOT_UNIQUE.equals(dictService.checkDictNameUnique(dict))) {
            return Result.error().message("新增字典'" + dict.getDictName() + "'失败，字典名称已存在");
        }
        return Result.judge(dictService.insertDict(dict),"添加字典");
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "修改字典页面")
    @PreAuthorize("hasAnyAuthority('dict:edit')")
    public String editDict(Model model, Dict dict) {
        model.addAttribute("MyDict",dictService.getDictById(dict.getDictId()));
        return "system/dict/dict-edit";
    }

    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改字典")
    @PreAuthorize("hasAnyAuthority('dict:edit')")
    @Log("修改字典")
    public Result updateDict(@RequestBody Dict dict){
        if (UserConstants.NOT_UNIQUE.equals(dictService.checkDictNameUnique(dict))) {
            return Result.error().message("修改字典'" + dict.getDictName() + "'失败，字典名称已存在");
        }
        return Result.judge(dictService.updateDict(dict),"修改字典");
    }

    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除字典")
    @PreAuthorize("hasAnyAuthority('dict:del')")
    public Result<Job> deleteDict(String ids) {
        try {
            dictService.deleteDictByIds(ids);
            return Result.ok().message("删除成功");
        }catch (MyException e){
            return Result.error().message(e.getMsg()).code(e.getCode());
        }
    }
}

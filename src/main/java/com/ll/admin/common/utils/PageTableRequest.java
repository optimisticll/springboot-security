package com.ll.admin.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:25
 */
@Data
public class PageTableRequest implements Serializable {
    private Integer page;
    private Integer limit;
    private Integer offset;

    public void countOffset(){
        if(null == this.page || null == this.limit){
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * limit;
    }
}

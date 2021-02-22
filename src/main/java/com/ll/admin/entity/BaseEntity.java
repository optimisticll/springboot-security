package com.ll.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lihaoxuan
 * @date 2020/12/21 15:23
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8925514045582235838L;

    private Date createtime=new Date();

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date updatetime=new Date();

    /** 请求参数 */
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public Map<String, Object> get1Params() {
        return params;
    }
}

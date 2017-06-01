package com.zjw.myframe.dao.bean.resource;

import com.zjw.myframe.dao.bean.PageBean;
import com.zjw.myframe.model.resource.Resource;

/**
 * URL资源列表参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public class ResourceListBean extends PageBean<Resource> {

    /**
     * 类型
     */
    private Integer type;
    /**
     * 名称
     */
    private String name;
    /**
     * URL
     */
    private String url;
    /**
     * 状态
     */
    private Integer status;

    /**
     * {@linkplain #type}
     */
    public Integer getType() {
        return type;
    }

    /**
     * {@linkplain #type}
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * {@linkplain #name}
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@linkplain #url}
     */
    public String getUrl() {
        return url;
    }

    /**
     * {@linkplain #url}
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@linkplain #status}
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * {@linkplain #status}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}

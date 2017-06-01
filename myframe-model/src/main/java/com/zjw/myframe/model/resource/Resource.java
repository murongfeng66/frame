package com.zjw.myframe.model.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * URL资源
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public class Resource {

    /**
     * 资源ID
     */
    private Long id;
    /**
     * 父ID
     */
    private Long pid;
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
     * 图标
     */
    private String icon;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 子节点
     */
    private List<Resource> children = new ArrayList<>();

    /**
     * {@linkplain #children}
     */
    public List<Resource> getChildren(){
        return children;
    }

    /**
     * {@linkplain #children}
     */
    public void setChildren(List<Resource> children){
        this.children = children;
    }

    /**
     * {@linkplain #id}
     */
    public Long getId(){
        return id;
    }

    /**
     * {@linkplain #id}
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * {@linkplain #pid}
     */
    public Long getPid(){
        return pid;
    }

    /**
     * {@linkplain #pid}
     */
    public void setPid(Long pid){
        this.pid = pid;
    }

    /**
     * {@linkplain #type}
     */
    public Integer getType(){
        return type;
    }

    /**
     * {@linkplain #type}
     */
    public void setType(Integer type){
        this.type = type;
    }

    /**
     * {@linkplain #name}
     */
    public String getName(){
        return name;
    }

    /**
     * {@linkplain #name}
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * {@linkplain #url}
     */
    public String getUrl(){
        return url;
    }

    /**
     * {@linkplain #url}
     */
    public void setUrl(String url){
        this.url = url;
    }

    /**
     * {@linkplain #icon}
     */
    public String getIcon(){
        return icon;
    }

    /**
     * {@linkplain #icon}
     */
    public void setIcon(String icon){
        this.icon = icon;
    }

    /**
     * {@linkplain #status}
     */
    public Integer getStatus(){
        return status;
    }

    /**
     * {@linkplain #status}
     */
    public void setStatus(Integer status){
        this.status = status;
    }

    /**
     * {@linkplain #sort}
     */
    public Integer getSort(){
        return sort;
    }

    /**
     * {@linkplain #sort}
     */
    public void setSort(Integer sort){
        this.sort = sort;
    }
}

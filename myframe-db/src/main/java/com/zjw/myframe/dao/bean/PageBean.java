package com.zjw.myframe.dao.bean;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 分页基础参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class PageBean<T> extends BaseBean {

    /**
     * 默认分页大小
     */
    protected static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 起始位置
     */
    private Integer start;
    /**
     * 结果数
     */
    private Integer count;
    /**
     * 总页数
     */
    private Integer totalPage = 1;
    /**
     * 分页大小
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 当前页
     */
    private Integer page = 1;
    /**
     * 结果集
     */
    private List<T> data;

    /**
     * 是否分页
     */
    private boolean isCut = true;

    public PageBean(){
        super();
    }

    public PageBean(int pageSize, int currentPage){
        super();
        this.page = currentPage >= 1 ? currentPage : 1;
        this.pageSize = pageSize > 0 ? pageSize : PageBean.DEFAULT_PAGE_SIZE;
        this.start = this.pageSize * (this.page - 1);
    }

    /**
     * {@linkplain #count}
     */
    public Integer getCount(){
        return count;
    }

    /**
     * {@linkplain #count}
     */
    public void setCount(Integer count){
        this.count = count;
        if(count == null){
            return;
        }
        if(this.isCut){
            this.totalPage = count % this.pageSize == 0 ? count / this.pageSize : count / this.pageSize + 1;
        }else{
            this.totalPage = 1;
            this.pageSize = this.count;
        }
    }

    /**
     * {@linkplain #data}
     */
    public List<T> getData(){
        return data;
    }

    /**
     * {@linkplain #data}
     */
    public void setData(List<T> data){
        this.data = data;
    }

    /**
     * {@linkplain #isCut}
     */
    public boolean getIsCut(){
        return isCut;
    }

    /**
     * {@linkplain #isCut}
     */
    public void setIsCut(boolean isCut){
        this.isCut = isCut;
    }

    /**
     * {@linkplain #page}
     */
    public Integer getPage(){
        return page;
    }

    /**
     * {@linkplain #page}
     */
    public void setPage(Integer page){
        this.page = page >= 1 ? page : 1;
    }

    /**
     * 获取分页结果
     */
    public PageResult<T> getPageResult(){
        PageResult<T> result = new PageResult<>();
        BeanUtils.copyProperties(this, result);
        return result;
    }

    /**
     * {@linkplain #pageSize}
     */
    public Integer getPageSize(){
        return pageSize;
    }

    /**
     * {@linkplain #pageSize}
     */
    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize > 0 ? pageSize : PageBean.DEFAULT_PAGE_SIZE;
    }

    /**
     * {@linkplain #start}
     */
    public Integer getStart(){
        return start;
    }

    /**
     * {@linkplain #start}
     */
    public void setStart(Integer start){
        this.start = start;
    }

    /**
     * {@linkplain #totalPage}
     */
    public Integer getTotalPage(){
        return totalPage;
    }

    /**
     * {@linkplain #totalPage}
     */
    public void setTotalPage(Integer totalPage){
        this.totalPage = totalPage;
    }

}

package com.zjw.myframe.dao.bean;

import java.util.List;

/**
 * ID列表参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public class IdsBean {

    private List<IdBean> ids;

    /**
     * {@linkplain #ids}
     */
    public List<IdBean> getIds(){
        return ids;
    }

    /**
     * {@linkplain #ids}
     */
    public void setIds(List<IdBean> ids){
        this.ids = ids;
    }
}

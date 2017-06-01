package com.zjw.myframe.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Mongodb封装方法
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Repository
public class MongodbBaseUtil {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void delete(Object object){
        mongoTemplate.remove(object);
    }

    public void save(Object object){
        mongoTemplate.save(object);
    }

}

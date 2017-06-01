package com.zjw.myframe.dao.redis.constant;

/**
 * Redis过期时间（单位：S）
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年8月24日
 */
public final class RedisExpires {

    /**
     * 用户信息和用户权限
     */
    public static final long USERINFO_PERMISSION = 1800;
    /**
     * 验证码
     */
    public static final long VERIFYCODE = 600;

}

package com.zjw.myframe.common.enums.base;

import java.util.LinkedList;
import java.util.List;

/**
 * 状态枚举类<p>
 * 采用二进制法表示是否有各状态
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum StatusEnum {

    /**
     * STATUS_1
     */
    STATUS_1(binaryValue(0), value(0), "STATUS_1"),
    /**
     * STATUS_2
     */
    STATUS_2(binaryValue(1), value(1), "STATUS_2", STATUS_1),
    /**
     * STATUS_3
     */
    STATUS_3(binaryValue(2), value(2), "STATUS_3", STATUS_2),
    /**
     * STATUS_4
     */
    STATUS_4(binaryValue(3), value(3), "STATUS_4", STATUS_3),
    /**
     * STATUS_5
     */
    STATUS_5(binaryValue(4), value(4), "STATUS_5", STATUS_4),
    /**
     * STATUS_6
     */
    STATUS_6(binaryValue(5), value(5), "STATUS_6", STATUS_5);

    /**
     * 不能跳转时的标志位
     */
    public static final int NOT_TO = 0;
    /**
     * 状态值
     */
    private int value;
    /**
     * 二进制值
     */
    private int binaryValue;
    /**
     * 状态描述
     */
    private String descirption;
    /**
     * 前置状态
     */
    private StatusEnum[] preStatus;

    StatusEnum(int binaryValue, int value, String descirption, StatusEnum... preStatus) {
        this.binaryValue = binaryValue;
        this.value = value;
        this.descirption = descirption;
        this.preStatus = preStatus;
    }

    /**
     * 计算二进制值
     */
    private static int binaryValue(int n) {
        return (int) Math.pow(2, n);
    }

    /**
     * 获取该状态值所有包含状态列表
     *
     * @param status 状态值
     */
    public static List<StatusEnum> getContainStatus(int status) {
        List<StatusEnum> excludes = new LinkedList<>();
        for (StatusEnum item : StatusEnum.values()) {
            if ((item.binaryValue & status) == item.binaryValue) {
                excludes.add(item);
            }
        }
        return excludes;
    }

    /**
     * 获取该状态值所有不包含状态列表
     *
     * @param status 状态值
     */
    public static List<StatusEnum> getExcludeStatus(int status) {
        List<StatusEnum> excludes = new LinkedList<>();
        for (StatusEnum item : StatusEnum.values()) {
            if ((item.binaryValue & status) != item.binaryValue) {
                excludes.add(item);
            }
        }
        return excludes;
    }

    /**
     * 计算状态值
     */
    private static int value(int n) {
        int result = 0;
        for (int i = 0; i <= n; i++) {
            result = (int) (result + Math.pow(2, i));
        }
        return result;
    }

    /**
     * 是否含有该状态
     *
     * @param status 状态值
     */
    public boolean containMe(int status) {
        return (this.binaryValue & status) == this.binaryValue;
    }

    /**
     * {@linkplain #binaryValue}
     */
    public int getBinaryValue() {
        return binaryValue;
    }

    /**
     * {@linkplain #descirption}
     */
    public String getDescirption() {
        return descirption;
    }

    /**
     * {@linkplain #preStatus}
     */
    public StatusEnum[] getPreStatus() {
        return preStatus;
    }

    /**
     * {@linkplain #value}
     *
     * @param exclude 排除状态数组，只能是该状态之前的状态
     * @return 该值为排除状态的状态值
     */
    public int getValue(StatusEnum... exclude) {
        int result = this.value;
        for (StatusEnum item : exclude) {
            if (this.binaryValue > item.binaryValue) {
                result -= item.binaryValue;
            }
        }
        return result;
    }

    /**
     * 跳转到该状态
     *
     * @param source  当前状态值
     * @param exclude 排除状态数组，只能是该状态之前的状态
     * @return 该状态的状态值，如果不满足前置状态则返回{@linkplain #NOT_TO}
     */
    public int toMe(int source, StatusEnum... exclude) {
        int fullSource = source;
        for (StatusEnum item : exclude) {
            if (this.binaryValue > item.binaryValue) {
                fullSource += item.binaryValue;
            }
        }
        for (StatusEnum item : this.preStatus) {
            if (item.value == fullSource) {
                return source + this.binaryValue;
            }
        }
        return NOT_TO;
    }

}

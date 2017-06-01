package com.zjw.myframe.common.verifycode;

import com.zjw.myframe.common.constant.ConstantObjects;
import com.zjw.myframe.common.exception.SystemParamException;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.Random;

/**
 * 验证码条件
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class Candition {

    /**
     * 验证码
     */
    private String verifyCode;
    /**
     * 字体大小<p>
     * 默认：50
     */
    private int fontSize = 50;
    /**
     * 干扰线数量<p>
     * 默认：验证码长度*2
     */
    private int interLin;
    /**
     * 验证码位置是否随机
     */
    private boolean randomLocation = true;
    /**
     * 水平偏移量（值越大，字符重叠越多）<p>
     * 默认：5，范围为1~10，包含1和10<p>
     * 只有当{@linkplain #randomLocation}为true时才有效
     */
    private int offsetsX = 5;
    /**
     * 背景色
     */
    private Color backColor = getRandomColor(200, 255);
    /**
     * 字体颜色
     */
    private Color fontColor = getRandomColor(0, 110);
    /**
     * 干扰线颜色
     */
    private Color lineColor = getRandomColor(0, 255);

    /**
     * 校验条件是否符合要求
     */
    public void checkCandition(){
        if(StringUtils.isEmpty(this.verifyCode)){
            throw new SystemParamException("验证码不能为空！");
        }
    }

    /**
     * {@linkplain #backColor}
     */
    public Color getBackColor(){
        return backColor;
    }

    /**
     * {@linkplain #backColor}
     */
    public void setBackColor(Color backColor){
        this.backColor = backColor;
    }

    /**
     * {@linkplain #fontColor}
     */
    public Color getFontColor(){
        return fontColor;
    }

    /**
     * {@linkplain #fontColor}
     */
    public void setFontColor(Color fontColor){
        this.fontColor = fontColor;
    }

    /**
     * {@linkplain #fontSize}
     */
    public int getFontSize(){
        return fontSize;
    }

    /**
     * {@linkplain #fontSize}
     */
    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }

    /**
     * 高度
     */
    public int getHeight(){
        return (int) (fontSize * 1.2);
    }

    /**
     * {@linkplain #interLin}
     */
    public int getInterLin(){
        return interLin;
    }

    /**
     * {@linkplain #interLin}
     */
    public void setInterLin(int interLin){
        this.interLin = interLin;
    }

    /**
     * {@linkplain #lineColor}
     */
    public Color getLineColor(){
        return lineColor;
    }

    /**
     * {@linkplain #lineColor}
     */
    public void setLineColor(Color lineColor){
        this.lineColor = lineColor;
    }

    /**
     * {@linkplain #offsetsX}
     */
    public int getOffsetsX(){
        return offsetsX;
    }

    /**
     * {@linkplain #offsetsX}
     */
    public void setOffsetsX(int offsetsX){
        if(offsetsX < 1 || offsetsX > 10){
            throw new SystemParamException("水平偏移量超出范围");
        }
        this.offsetsX = offsetsX;
    }

    /**
     * 生成随机颜色
     */
    private Color getRandomColor(int lower, int upper){
        Random random = new Random();
        lower = lower < 0 ? 0 : lower;
        upper = upper < 0 ? 0 : upper;
        lower = lower > 255 ? 255 : lower;
        upper = upper > 255 ? 255 : upper;
        if(lower >= upper){
            throw new SystemParamException("lower必须小于upper");
        }
        int r = lower + random.nextInt(upper - lower);
        int g = lower + random.nextInt(upper - lower);
        int b = lower + random.nextInt(upper - lower);
        return new Color(r, g, b);
    }

    /**
     * {@linkplain #randomLocation}
     */
    public boolean getRandomLocation(){
        return randomLocation;
    }

    /**
     * {@linkplain #randomLocation}
     */
    public void setRandomLocation(boolean randomLocation){
        this.randomLocation = randomLocation;
    }

    /**
     * {@linkplain #verifyCode}
     */
    public String getVerifyCode(){
        return verifyCode;
    }

    /**
     * {@linkplain #verifyCode}
     */
    public void setVerifyCode(String verifyCode){
        this.verifyCode = verifyCode.trim();
        this.interLin = this.verifyCode.length() * 2;
    }

    /**
     * 宽度
     */
    public int getWidth(){
        return (int) (fontSize * verifyCode.length() + fontSize * 0.4);
    }

    /**
     * 获取下一个偏移量
     */
    protected double nextOffsetsX(){
        return (!randomLocation ? 1 : (ConstantObjects.random.nextInt(this.offsetsX) + (10 - this.offsetsX)) * 0.1) * fontSize;
    }

}
package com.zjw.myframe.common.verifycode;

import com.zjw.myframe.common.constant.ConstantObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 验证码生成器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class ImageVerifyCodeFactory {

    private ImageVerifyCodeFactory(){
    }

    public static BufferedImage createImageCode(Candition candition){
        candition.checkCandition();
        BufferedImage bufferedImage = new BufferedImage(candition.getWidth(), candition.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(candition.getBackColor());
        graphics.fillRect(0, 0, candition.getWidth(), candition.getHeight());

        if(candition.getInterLin() > 0){
            int y, y1;
            for(int i = 0; i < candition.getInterLin(); i++){
                graphics.setColor(candition.getLineColor());
                y = ConstantObjects.random.nextInt(candition.getHeight());
                y1 = ConstantObjects.random.nextInt(candition.getHeight());
                graphics.drawLine(0, y, candition.getWidth(), y1);
            }
        }

        int fontX = candition.getHeight() - candition.getFontSize();
        int fontY = candition.getFontSize();
        graphics.setFont(new Font("Default", Font.PLAIN, candition.getFontSize()));
        for(int i = 0; i < candition.getVerifyCode().length(); i++){
            fontY = candition.getRandomLocation() ? (int) ((Math.random() * 0.3 + 0.6) * candition.getHeight()) : fontY;
            graphics.setColor(candition.getFontColor());
            graphics.drawString(Character.toString(candition.getVerifyCode().charAt(i)), fontX, fontY);
            fontX += candition.nextOffsetsX();
        }
        graphics.dispose();
        return bufferedImage;
    }

}
package com.zjw.myframe.common.verifycode;

import com.zjw.myframe.common.constant.ConstantObjects;

/**
 * 验证码类型
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum VerifyCodeType {
    /**
     * 验证码类型为仅数字,即0~9
     */
    TYPE_NUM_ONLY {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(10);
                if(null != excludeString && excludeString.contains(Integer.toString(t))){
                    continue;
                }
                verifyCode.append(t);
                length--;
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为仅字母,即大小写字母混合
     */
    TYPE_LETTER_ONLY {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(123);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 97 || (t >= 65 && t <= 90)){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为数字和大小写字母混合
     */
    TYPE_ALL_MIXED {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(123);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为数字和大写字母混合
     */
    TYPE_NUM_UPPER {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(91);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 65 || (t >= 48 && t <= 57)){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为数字和小写字母混合
     */
    TYPE_NUM_LOWER {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(123);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 97 || (t >= 48 && t <= 57)){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为仅大写字母
     */
    TYPE_UPPER_ONLY {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(91);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 65){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    },

    /**
     * 验证码类型为仅小写字母
     */
    TYPE_LOWER_ONLY {
        @Override
        public String getCode(int length, String excludeString){
            StringBuilder verifyCode = new StringBuilder();
            while(length > 0){
                int t = ConstantObjects.random.nextInt(123);
                if(null != excludeString && excludeString.contains(Integer.toString((char) t))){
                    continue;
                }
                if(t >= 97){
                    verifyCode.append((char) t);
                    length--;
                }
            }
            return verifyCode.toString();
        }
    };

    public abstract String getCode(int length, String excludeString);
}

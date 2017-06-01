package com.zjw.myframe.common.util;

import com.zjw.myframe.common.exception.SystemException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 反射工具
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class ReflectUtils {

    private ReflectUtils(){
    }

    /**
     * 获取指定对象里面的指定属性
     *
     * @param object       目标对象
     * @param fieldName 目标属性
     *
     * @return 目标字段
     */
    private static Field getField(Object object, String fieldName){
        List<Field> fields = getFieldsWithSupper(object);
        for(Field field : fields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }
        return null;
    }

    /**
     * 获取对象的所有属性，包含除Object的所有父类
     */
    public static List<Field> getFieldsWithSupper(Object object){
        if(object == null){
            throw new SystemException("object is null");
        }
        List<Field> fields = new ArrayList<>();
        for(Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * 获取对象的所有属性，不包含父类
     */
    public static List<Field> getFieldsWithOutSupper(Object object){
        if(object == null){
            throw new SystemException("object is null");
        }
        return Arrays.asList(object.getClass().getDeclaredFields());
    }

    /**
     * 获取指定对象的指定属性值
     *
     * @param object    目标对象
     * @param fieldName 目标属性
     *
     * @return 目标属性的值
     */
    public static Object getFieldValue(Object object, String fieldName){
        if(object == null){
            throw new SystemException("obj is null");
        }
        Object result = null;
        Field field = getField(object, fieldName);
        if(field != null){
            field.setAccessible(true);
            try{
                result = field.get(object);
            }catch(Exception e){
                throw new SystemException(e);
            }
        }
        return result;
    }

    /**
     * 设置指定对象的指定属性值
     *
     * @param object        目标对象
     * @param fieldName  目标属性
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object object, String fieldName, Object fieldValue){
        Field field = getField(object, fieldName);
        if(field != null){
            try{
                field.setAccessible(true);
                field.set(object, fieldValue);
            }catch(Exception e){
                throw new SystemException(e);
            }
        }
    }

    /**
     * 将对象转为Map
     */
    private static Map<String, Object> toMap(Object object, List<Field> fileds){
        Map<String, Object> map = new HashMap<>();
        try{
            for(Field field : fileds){
                field.setAccessible(true);
                Object value = field.get(object);
                if(value != null){
                    map.put(field.getName(), value);
                }
            }
        }catch(IllegalArgumentException | IllegalAccessException e){
            throw new SystemException(e);
        }
        return map;
    }

    /**
     * 将对象转为Map（包含父类）
     */
    public static Map<String, Object> toMapWithSupper(Object object){
        if(object == null){
            throw new SystemException("object is null");
        }
        List<Field> fileds = getFieldsWithSupper(object);
        return toMap(object, fileds);
    }

    /**
     * 将对象转为Map（不包含父类）
     */
    public static Map<String, Object> toMapWithOutSupper(Object object){
        if(object == null){
            throw new SystemException("object is null");
        }
        List<Field> fileds = getFieldsWithOutSupper(object);
        return toMap(object, fileds);
    }

    /**
     * 将Map转为对象
     */
    public static <T> T toObject(T t, Map<String, Object> map){
        List<Field> fileds = getFieldsWithSupper(t);
        try{
            for(Field field : fileds){
                String key = field.getName();
                field.setAccessible(true);
                if(map.containsKey(key)){
                    field.set(t, map.get(key));
                }
            }
        }catch(IllegalArgumentException | IllegalAccessException e){
            throw new SystemException(e);
        }

        return t;
    }

    /**
     * 复制对象（不包括父类）
     *
     * @param source      源对象
     * @param targetClass 目标对象类型
     * @param ignore      排除属性
     */
    public static Object copyWithOutSupper(Object source, Class targetClass, String... ignore){
        try{
            Object target = targetClass.newInstance();
            return copyWithOutSupper(source, target, ignore);
        }catch(Exception e){
            throw new SystemException(e);
        }
    }

    /**
     * 复制对象（不包括父类）
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignore 排除属性
     */
    public static Object copyWithOutSupper(Object source, Object target, String... ignore){
        try{
            List<Field> fileds = getFieldsWithOutSupper(source);
            for(Field sourceField : fileds){
                Object value = sourceField.get(source);
                if(value == null){
                    continue;
                }
                Field targetField = ReflectUtils.getField(target, sourceField.getName());
                if(targetField != null){
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }
            }
            return target;
        }catch(Exception e){
            throw new SystemException(e);
        }
    }
}
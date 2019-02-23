package com.druidelf.novelmain.common.utils;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@Data
public class UtilForMapTransformEntity<T> {
    private T t;
    private Map map;

    public static <T>UtilForMapTransformEntity EntityTransformerMap (T tObjcet ) throws InvocationTargetException, IllegalAccessException {
        Map map = new HashMap();
        UtilForMapTransformEntity<T> utilForMapTransformEntity = new UtilForMapTransformEntity();
        Method[] methods = tObjcet.getClass().getMethods();
        for (Method method : methods) {
            String key = method.getName();
            if (key.substring(0,2).equals("get")){
                Object value = method.invoke(tObjcet);
                map.put(key, value);
            }
        }
        utilForMapTransformEntity.setMap(map);
        return utilForMapTransformEntity;
    }
    /*public static DruidUser MapTransformerDruidUser ( Map mapData) {
        DruidUser.builder().
    }*/
}

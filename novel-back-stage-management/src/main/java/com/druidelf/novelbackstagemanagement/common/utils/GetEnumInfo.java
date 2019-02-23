package com.druidelf.novelbackstagemanagement.common.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;

@SuppressWarnings({"JavaDoc", "unused"})
@Log4j2
public class GetEnumInfo {
    /**
     * 枚举code码默认属性名称
     */
    private static String defaultCodeName = "statusCode";
    /**
     * 枚举name默认属性名称
     */
    private static String defaultName = "name";

    /**
     * 根据枚举反射类和传入code码，得到对应的枚举name
     * @param clazz
     * @param code
     * @return
     */
    public static String getNameByStatusCode( Class clazz, Object code ){
        return getNameByStatusCode(defaultCodeName, clazz, code, defaultName);
    }

    /**
     * 根据枚举反射类和传入code码以及实际CodeName的属性名称，得到对应的枚举name
     * @param reCodeName
     * @param clazz
     * @param code
     * @return
     */
    public static String getNameByStatusCode( String reCodeName, Class clazz, Object code ){
        return getNameByStatusCode(reCodeName, clazz, code, defaultName);
    }

    /**
     * 根据枚举反射类和传入code码以及实际name和CodeName的属性名称，得到对应的枚举name
     * @param clazz
     * @param code
     * @param reName
     * @return
     */
    public static String getNameByStatusCode( Class clazz, Object code, String reName ){
        return getNameByStatusCode(defaultCodeName, clazz, code, reName);
    }

    /**
     * 根据枚举反射类和传入code码，得到对应的枚举name
     * @param clazz
     * @param nameValue
     * @return
     */
    public static Integer getStatusCodeByName ( Class clazz, Object nameValue ){
        return getStatusCodeByName(defaultCodeName, clazz, nameValue, defaultName);
    }

    /**
     * 根据枚举反射类和传入code码以及实际CodeName的属性名称，得到对应的枚举name
     * @param reCodeName
     * @param clazz
     * @param nameValue
     * @return
     */
    public static Integer getStatusCodeByName( String reCodeName, Class clazz, Object nameValue ){
        return getStatusCodeByName(reCodeName, clazz, nameValue, defaultName);
    }

    /**
     * 根据枚举反射类和传入code码以及实际name和CodeName的属性名称，得到对应的枚举name
     * @param clazz
     * @param nameValue
     * @param reName
     * @return
     */
    public static Integer getStatusCodeByName( Class clazz, Object nameValue, String reName ){
        return getStatusCodeByName(defaultCodeName, clazz, nameValue, reName);
    }

    /**
     * 根据枚举反射类和传入code码以及实际name和的属性名称，得到对应的枚举name
     * @param clazz
     * @param code
     * @param reCodeName
     * @param reName
     * @return
     */

    @SuppressWarnings("WeakerAccess")
    public static String getNameByStatusCode ( String reCodeName, Class clazz, Object code, String reName ){

        if ( code == null ) return null;

        if ( clazz == null ) return null;

        Object[] objs = clazz.getEnumConstants();
        Field statusCode;
        try {
            statusCode = clazz.getField(reCodeName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reCodeName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }
        Field name;
        try {
            name = clazz.getField(reName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }

        for (Object object : objs ) {
            try {
                if ( statusCode !=null && statusCode.get(object).equals(code)) {
                    return name.get(object).toString();
                }
            } catch (IllegalAccessException e) {
                log.error("获取"+clazz.getSimpleName()+"实例值失败");
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    /**
     * 根据枚举反射类和传入code码以及实际name和的属性名称，得到对应的枚举code
     * @param clazz
     * @param nameValue
     * @param reCodeName
     * @param reName
     * @return
     */
    @SuppressWarnings("WeakerAccess")
    public static Integer getStatusCodeByName ( String reCodeName, Class clazz, Object nameValue, String reName ){

        if ( nameValue == null ) return null;

        if ( clazz == null ) return null;

        Object[] objs = clazz.getEnumConstants();
        Field statusCode;
        try {
            statusCode = clazz.getField(reCodeName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reCodeName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }
        Field name;
        try {
            name = clazz.getField(reName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }

        for (Object object : objs ) {
            try {
                if ( name !=null && name.get(object).equals(nameValue)) {
                    return (Integer) statusCode.get(object);
                }
            } catch (IllegalAccessException e) {
                log.error("获取"+clazz.getSimpleName()+"实例值失败");
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 根据枚举反射类和传入code码以及实际name和的属性名称,判断字符串是否相像，得到对应的枚举code
     * @param clazz
     * @param nameValue
     * @return
     */
    public static Integer getStatusCodeByLikeName ( Class clazz, Object nameValue ){
        return getStatusCodeByLikeName(defaultCodeName, clazz, nameValue, defaultName);
    }
    /**
     * 根据枚举反射类和传入code码以及实际name和的属性名称,判断字符串是否相像，得到对应的枚举code
     * @param clazz
     * @param nameValue
     * @param reCodeName
     * @param reName
     * @return
     */
    @SuppressWarnings("WeakerAccess")
    public static Integer getStatusCodeByLikeName ( String reCodeName, Class clazz, Object nameValue, String reName ){

        if ( nameValue == null ) return null;

        if ( clazz == null ) return null;

        Object[] objs = clazz.getEnumConstants();
        Field statusCode;
        try {
            statusCode = clazz.getField(reCodeName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reCodeName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }
        Field name;
        try {
            name = clazz.getField(reName);
        } catch (NoSuchFieldException e) {
            log.error(clazz.getSimpleName()+"枚举中得不到属性"+reName+"，你可以将枚举的属性名称改为"+defaultCodeName+"或者将你实际的枚举名称传入方法中进行重命名,注意访问修饰符为public");
            throw new RuntimeException(e);
        }

        for (Object object : objs ) {
            try {
                if ( name !=null && name.get(object).toString().contains(nameValue.toString())) {
                    return (Integer) statusCode.get(object);
                }
            } catch (IllegalAccessException e) {
                log.error("获取"+clazz.getSimpleName()+"实例值失败");
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

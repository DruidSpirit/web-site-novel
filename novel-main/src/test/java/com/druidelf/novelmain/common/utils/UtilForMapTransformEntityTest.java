package com.druidelf.novelmain.common.utils;

import com.druidelf.novelmain.entity.DruidUser;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class UtilForMapTransformEntityTest {

    @Test
    public void entityTransformerMap() throws InvocationTargetException, IllegalAccessException {
        DruidUser druidUser = DruidUser.builder().build();
        Map map = UtilForMapTransformEntity.EntityTransformerMap(DruidUser.builder().email("844748180@q.com")).getMap();
        System.out.println(map.toString());
    }
}
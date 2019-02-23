package com.druidelf.novelmain.service;


import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 通用接口，偏向于单表操作
 * @Description: CRUD 常见操作封装
 * @author <a href="smniuhe@gmail.com">smniuhe</a>
 */
@Service
public interface IService<T>{

    /**
     * 根据主键查询
     * @param key
     * @return
     */
    T selectByKey(Object key);

    /**
     * 保存实体
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 根据主键删除
     * @param key
     * @return
     */
    int delete(Object key);

    /**
     * 更新实体
     * @param entity
     * @return
     */
    int updateAll(T entity);

    /**
     * 更新实体-过滤null属性
     * @param entity
     * @return
     */
    int updateNotNull(T entity);

    /**
     * 条件查询
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    T selectByOne(T t);

    int updateByExampleSelective(T var1, Object var2);

    T selectOneByExample(Object example);

    List<T> selectAll();

    /**
     * 根据实体条件查询条数
     * @param t
     * @return
     */
    int selectCount(T t);

    /**
     * 插入数据
     * @param var1
     * @return
     */
    int insertList(List<? extends T> var1);

    /**
     * 根据实体类作为筛选条件，得到筛选列表
     * @param t
     * @return
     */
    List<T> select(T t);

    //TODO 其他...
}

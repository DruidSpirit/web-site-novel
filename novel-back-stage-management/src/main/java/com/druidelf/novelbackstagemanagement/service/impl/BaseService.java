/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.common.utils.extendFor.MyMapper;
import com.druidelf.novelbackstagemanagement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 基础的服务抽象类
 *
 * @author <a href="smniuhe@gmail.com">smniuhe</a>
 * @Description:
 */
public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected MyMapper<T> mapper;

    public MyMapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public T selectByOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public int updateByExampleSelective(T var1, Object var2) {
        return mapper.updateByExampleSelective( var1, var2 );
    }

    @Override
    public T selectOneByExample(Object example) {
        return mapper.selectOneByExample( example );
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * 根据实体条件查总条数
     *
     * @param t
     * @return
     */
    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    /**
     * 插入数据
     *
     * @param var1
     * @return
     */
    @Override
    public int insertList(List<? extends T> var1) {
        return mapper.insertList(var1);
    }

    /**
     * 根据实体类作为筛选条件，得到筛选列表
     *
     * @param t
     * @return
     */
    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    //TODO 其他...
}

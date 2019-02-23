package com.druidelf.novelbackstagemanagement.common.utils.extendFor;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
@RegisterMapper
public interface MyMapper<T> extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, InsertListMapper<T>,Marker {
}

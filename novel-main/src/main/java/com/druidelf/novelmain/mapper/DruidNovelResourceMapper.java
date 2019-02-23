package com.druidelf.novelmain.mapper;

import com.druidelf.novelmain.common.utils.extendFor.MyMapper;
import com.druidelf.novelmain.entity.DruidNovelResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DruidNovelResourceMapper extends MyMapper<DruidNovelResource> {
    List<DruidNovelResource> rankingListNovel(@Param("params") Map map);
}
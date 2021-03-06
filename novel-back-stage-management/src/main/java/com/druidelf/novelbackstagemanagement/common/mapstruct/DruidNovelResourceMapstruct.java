package com.druidelf.novelbackstagemanagement.common.mapstruct;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.vm.DruidNovelResourceVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DruidNovelResourceMapstruct {
    @Mappings({
            @Mapping(source = "enDruidNovelResource.id", target = "id")
    })
    DruidNovelResourceVm DruidNovelResourceTransform(DruidNovelResource enDruidNovelResource);

    List<DruidNovelResourceVm> DruidNovelResourceTransformLList(List<DruidNovelResource> enDruidNovelResourceList);
}
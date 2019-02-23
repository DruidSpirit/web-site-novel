package com.druidelf.novelstaticresource.common.mapstruct;

import com.druidelf.novelstaticresource.entity.DruidNovelResource;
import com.druidelf.novelstaticresource.response.vm.DruidNovelResourceVm;
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
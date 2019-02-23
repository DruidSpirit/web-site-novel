package com.druidelf.novelmain.common.mapstruct;

import com.druidelf.novelmain.entity.DruidNovelResource;
import com.druidelf.novelmain.response.vm.DruidNovelResourceVm;
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
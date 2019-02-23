package com.druidelf.novelmain.common.mapstruct;

import com.druidelf.novelmain.entity.DruidUser;
import com.druidelf.novelmain.entity.viewEntity.DruidUserVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DruidUserMapstruct {
    @Mappings({
            @Mapping(source = "enDruidUser.id", target = "id")
    })
    DruidUserVm DruidUserTransform(DruidUser enDruidUser);

    List<DruidUserVm> DruidUserTransformLList(List<DruidUser> enDruidUserList);
}
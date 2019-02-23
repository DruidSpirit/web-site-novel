package com.druidelf.novelstaticresource.common.mapstruct;

import com.druidelf.novelstaticresource.entity.DruidUser;
import com.druidelf.novelstaticresource.entity.viewEntity.DruidUserVm;
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
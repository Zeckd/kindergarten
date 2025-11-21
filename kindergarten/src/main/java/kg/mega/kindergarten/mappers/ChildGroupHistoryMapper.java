package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistorySaveDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChildGroupHistoryMapper {
    ChildGroupHistoryMapper INSTANCE = Mappers.getMapper(ChildGroupHistoryMapper.class);
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "childId", target = "child.id")

    ChildGroupHistory childGroupHistorySaveDtoToChildGroupHistory(ChildGroupHistorySaveDto childGroupHistorySaveDto);
    ChildGroupHistoryDto childGroupHistoryToChildGroupHistoryDto(ChildGroupHistory childGroupHistory);

}

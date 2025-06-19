package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChildGroupHistoryMapper {
    ChildGroupHistoryMapper INSTANCE = Mappers.getMapper(ChildGroupHistoryMapper.class);
    @Mapping(source = "group", target = "group.id")
    @Mapping(source = "child", target = "child.id")

    ChildGroupHistory childGroupHistoryCreateDtoToChildGroupHistory(ChildGroupHistoryCreateDto childGroupHistoryCreateDto);
    ChildGroupHistoryDto childGroupHistoryToChildGroupHistoryDto(ChildGroupHistory childGroupHistory);
    ChildGroupHistory childGroupHistoryDtoToChildGroupHistory(ChildGroupHistoryDto childGroupHistoryDto);
    List<ChildGroupHistoryDto> childGroupHistoryToChildGroupHistoryDto(List<ChildGroupHistory> childGroupHistory);

}

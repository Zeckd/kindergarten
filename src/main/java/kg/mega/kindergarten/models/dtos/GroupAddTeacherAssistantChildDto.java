package kg.mega.kindergarten.models.dtos;

public record GroupAddTeacherAssistantChildDto(
        Long groupId,
        Long teacherOrAssistantId,
        Long childId

){
}

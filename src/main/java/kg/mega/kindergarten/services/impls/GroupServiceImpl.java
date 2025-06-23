package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.mappers.GroupMapper;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.GroupCreateDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import kg.mega.kindergarten.repositories.ChildRepo;
import kg.mega.kindergarten.repositories.GroupRepo;
import kg.mega.kindergarten.services.AgeGroupService;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.GroupService;
import kg.mega.kindergarten.services.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service

public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepo;
    private final AgeGroupService ageGroupService;
    private final TeacherService teacherService;
    private final ChildRepo childRepo;

    public GroupServiceImpl(GroupRepo groupRepo, AgeGroupService ageGroupService, TeacherService teacherService, ChildRepo childRepo) {
        this.groupRepo = groupRepo;
        this.ageGroupService = ageGroupService;
        this.teacherService = teacherService;
        this.childRepo = childRepo;
    }

    @Override
    public GroupDto create(GroupCreateDto groupCreateDto) {
        AgeGroup ageGroup = ageGroupService.findById(groupCreateDto.ageGroupId());
        if (ageGroup == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Group group = GroupMapper.INSTANCE.groupCreateDtoToGroup(groupCreateDto);

        group.setAgeGroup(ageGroup);
        group = groupRepo.save(group);

        return GroupMapper.INSTANCE.groupToGroupDto(group);
    }

    @Override
    public List<Group> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return groupRepo.findAllList(pageable);

    }

    @Override
    public GroupDto delete(Long id) {
        Group group = groupRepo.findById(id).orElseThrow();
        groupRepo.deleteById(id);
        return GroupMapper.INSTANCE.groupToGroupDto(group) ;

    }

    @Override
    public GroupDto update(GroupDto groupDto, Delete delete) {
        Group group = GroupMapper.INSTANCE.groupDtoToGroup(groupDto);
        group.setDelete(delete);
        group = groupRepo.save(group);


        return GroupMapper.INSTANCE.groupToGroupDto(group);
    }

    @Override
    public Group findById(Long id) {
        return groupRepo.findByIdGroup(id);

    }

    @Override
    public GroupDto addTeacherOrAssistantAndChild(Long groupId, Long teacherOrAssistantId,Long childId) {
        Group group = groupRepo.findByIdGroup(groupId);
        if(teacherOrAssistantId != null) {
            Teacher teacherOrAssistant = teacherService.findById(teacherOrAssistantId);
            if (teacherOrAssistant.getPosition() == Position.TEACHER &&
                    groupRepo.existsByTeacher_Id(teacherOrAssistant.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher already assigned to another group");
            }

            if (teacherOrAssistant.getPosition() == Position.ASSISTANT &&
                    groupRepo.existsByAssistant_Id(teacherOrAssistant.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Assistant already assigned to another group");
            }

            if (teacherOrAssistant.getPosition() == Position.TEACHER) {
                group.setTeacher(teacherOrAssistant);
            } else if (teacherOrAssistant.getPosition() == Position.ASSISTANT) {
                group.setAssistant(teacherOrAssistant);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown position");
            }
        }
        if(childId != null) {
        Child child = childRepo.findByIdChild(childId);
            if (child == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found");
            }

            if (group.getChildren() == null) {
                group.setChildren(new ArrayList<>());
            }
            group.addChild(child);

            }
        group = groupRepo.save(group);
        return GroupMapper.INSTANCE.groupToGroupDto(group);
    }


}

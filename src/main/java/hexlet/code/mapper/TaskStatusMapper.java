package hexlet.code.mapper;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskStatusMapper {
    public abstract TaskStatus map(TaskStatusCreateDTO data);

    public abstract TaskStatusDTO map(TaskStatus data);

    public abstract TaskStatus map(TaskStatusDTO data);

    public abstract void update(TaskStatusUpdateDTO data, @MappingTarget TaskStatus model);
}

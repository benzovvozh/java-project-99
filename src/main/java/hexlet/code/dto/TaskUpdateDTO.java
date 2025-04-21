package hexlet.code.dto;

import hexlet.code.model.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {
    private JsonNullable<Integer> index; //?
    private JsonNullable<Long> assigneeId;
    @Size(min = 1)
    private JsonNullable<String> title;
    private JsonNullable<String> content;
    private JsonNullable<TaskStatus> status; // task status id
}

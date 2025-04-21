package hexlet.code.dto;

import hexlet.code.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskCreateDTO {
    private JsonNullable<Integer> index; //?
    private JsonNullable<Long> assigneeId;
    @NotBlank
    @Size(min = 1)
    private String title;
    private JsonNullable<String> content;
    @NotNull
    private String status;
}

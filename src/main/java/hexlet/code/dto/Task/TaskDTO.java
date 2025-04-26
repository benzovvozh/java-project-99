package hexlet.code.dto.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskDTO {
    private long id;
    private JsonNullable<Integer> index;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonProperty("assignee_id")
    private long assigneeId;
    private String title;
    private JsonNullable<String> content;
    private String status;
    private List<Long> taskLabelIds = new ArrayList<>();
}

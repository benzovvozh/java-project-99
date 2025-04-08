package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private long id;
    private JsonNullable<String> firstName;
    private JsonNullable<String> lastName;
    private String email;
    private LocalDateTime createdAt;
}

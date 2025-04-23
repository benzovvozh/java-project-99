package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Task implements BaseEntity{
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Include
    private int index;

    @NotBlank
    @ToString.Include
    @Size(min = 1)
    private String name;

    @ToString.Include
    private String description;

    @NotNull
    @ToString.Include
    @ManyToOne
    private TaskStatus taskStatus;

    @ToString.Include
    @ManyToOne
    private User assignee;

    @CreatedDate
    private LocalDateTime createdAt;



}

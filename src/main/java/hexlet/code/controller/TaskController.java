package hexlet.code.controller;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.utils.UserUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    UserUtils userUtils;
    private static final String ONLY_OWNER = """
                @userUtils.getCurrentUser().getEmail() == authentication.getName()
            """;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    @PreAuthorize(ONLY_OWNER)
    public ResponseEntity<List<TaskDTO>> index() {
        var tasks = taskRepository.findAll()
                .stream().map(taskMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasks.size()))
                .body(tasks);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize(ONLY_OWNER)
    public TaskDTO show(@PathVariable("id") long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        return taskMapper.map(task);
    }

    @PostMapping(path = "")
    @PreAuthorize(ONLY_OWNER)
    public TaskDTO create(@RequestBody @Valid TaskCreateDTO data) {
        var task = taskMapper.map(data);
        taskRepository.save(task);
        return taskMapper.map(task);
    }
}

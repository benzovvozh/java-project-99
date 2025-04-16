package hexlet.code.controller;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
public class TaskStatusController {
    @Autowired
    TaskStatusRepository repository;
    @Autowired
    UserUtils userUtils;
    @Autowired
    TaskStatusMapper taskStatusMapper;
    private static final String ONLY_OWNER = """
                @userUtils.getCurrentUser().getEmail() == authentication.getName()
            """;

    @GetMapping(path = "")
    ResponseEntity<List<TaskStatusDTO>> index() {
        var taskStatuses = repository.findAll();
        var result = taskStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(taskStatuses.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public TaskStatusDTO show(@PathVariable("id") long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task status with id " + id + " not found"));
        return taskStatusMapper.map(taskStatus);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(ONLY_OWNER)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreateDTO data) {
        var taskStatus = taskStatusMapper.map(data);
        repository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize(ONLY_OWNER)
    public TaskStatusDTO update(@PathVariable("id") long id, @RequestBody @Valid TaskStatusUpdateDTO data) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task status with id " + id + " not found"));
        taskStatusMapper.update(data, taskStatus);
        repository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize(ONLY_OWNER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") long id) {
        repository.deleteById(id);
    }
}

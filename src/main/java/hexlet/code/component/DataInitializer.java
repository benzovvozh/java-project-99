package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final TaskRepository taskRepository;

    private Task createTask(String name, String slug) {
        var task = new Task();
        var status = taskStatusRepository.findBySlug(slug).orElseThrow();
        List<Label> labels = new ArrayList<>();
        task.setName(name);
        task.setTaskStatus(status);
        task.setLabels(labels);
        taskRepository.save(task);
        return task;
    }

    private TaskStatus createTaskStatus(String name, String slug) {
        var taskStatus = new TaskStatus();
        taskStatus.setName(name);
        taskStatus.setSlug(slug);
        taskStatusRepository.save(taskStatus);
        return taskStatus;
    }

    private Label createLabel(String name) {
        var label = new Label();
        label.setName(name);
        labelRepository.save(label);
        return label;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            var userData = new User();
            userData.setEmail(email);
            userData.setPasswordDigest("qwerty");
            userService.createUser(userData);
        }

        if (taskStatusRepository.findAll().isEmpty()) {
            createTaskStatus("draft", "draft");
            createTaskStatus("to review", "to_review");
            createTaskStatus("to be fixed", "to_be_fixed");
            createTaskStatus("to publish", "to_publish");
            createTaskStatus("published", "published");
        }
        if (labelRepository.findAll().isEmpty()) {
            createLabel("bug");
            createLabel("feature");
        }
//        if (taskRepository.findAll().isEmpty()) {
//            createTask("Homework", "draft");
//            createTask("Cleaning", "to_review");
//            createTask("Sport", "to_be_fixed");
//        }

    }
}

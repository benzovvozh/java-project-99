package hexlet.code.component;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    private TaskStatus createTaskStatus(String name, String slug) {
        var taskStatus = new TaskStatus();
        taskStatus.setName(name);
        taskStatus.setSlug(slug);
        taskStatusRepository.save(taskStatus);
        return taskStatus;
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
            var draft = createTaskStatus("draft", "draft");
            var toReview = createTaskStatus("to review", "to_review");
            var toBeFixed = createTaskStatus("to be fixed", "to_be_fixed");
            var toPublish = createTaskStatus("to publish", "to_publish");
            var published =createTaskStatus("published", "published");
        }


    }
}

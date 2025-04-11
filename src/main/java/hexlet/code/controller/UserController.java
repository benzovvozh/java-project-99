package hexlet.code.controller;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ForbiddenException;
import hexlet.code.exception.UserNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUtils userUtils;

    private boolean checkUser(Long id) {
        var currentUserEmail = userUtils.getCurrentUser().getEmail();
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        if (currentUserEmail.equals(user.getEmail())) {
            return true;
        } else return false;
    }

    @GetMapping(path = "")
    public List<UserDTO> index() {
        var list = userRepository.findAll();
        var result = list.stream()
                .map(user -> userMapper.map(user)).toList();
        return result;
    }

    @GetMapping(path = "/{id}")
    public UserDTO show(@PathVariable("id") long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return userMapper.map(user);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO data) {
        var user = userMapper.map(data);
        userRepository.save(user);
        return userMapper.map(user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") long id) {
        if (checkUser(id)) {
            userRepository.deleteById(id);
        } else throw new ForbiddenException("You can't delete user with id " + id + " because you are not it");
    }

    @PutMapping(path = "/{id}")
    public UserDTO update(@PathVariable("id") long id, @RequestBody @Valid UserUpdateDTO data) {
        if (checkUser(id)) {
            var user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
            userMapper.update(data, user);
            userRepository.save(user);
            return userMapper.map(user);
        } else throw new ForbiddenException("You can't update user with id " + id + " because you are not it");
    }
}

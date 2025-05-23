package hexlet.code.controller;

import hexlet.code.dto.Label.LabelCreateDTO;
import hexlet.code.dto.Label.LabelDTO;
import hexlet.code.dto.Label.LabelUpdateDTO;
import hexlet.code.exception.NotFoundException;
import hexlet.code.exception.UnprocessableContentException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private LabelMapper labelMapper;

    @GetMapping(path = "")
    public ResponseEntity<List<LabelDTO>> index() {
        var labels = labelRepository.findAll()
                .stream().map(labelMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(labels);
    }

    @GetMapping(path = "/{id}")
    public LabelDTO show(@PathVariable("id") long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Label with " + id + " not found."));
        return labelMapper.map(label);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO data) {
        var label = labelMapper.map(data);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Label with " + id + " not found."));
        if (label.getTasks().isEmpty()) {
            labelRepository.deleteById(id);
        } else {
            throw new UnprocessableContentException("Нельзя удалить метку связанную с задачей");
        }
    }

    @PutMapping(path = "/{id}")
    public LabelDTO update(@PathVariable("id") long id, @RequestBody @Valid LabelUpdateDTO data) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Label with " + id + " not found."));
        labelMapper.update(data, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }
}

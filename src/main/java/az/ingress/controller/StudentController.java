package az.ingress.controller;

import az.ingress.model.common.PageCriteria;
import az.ingress.model.common.PageableResponse;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.StudentResponse;
import az.ingress.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class StudentController {
    StudentService studentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public StudentResponse create(@RequestBody StudentRequest request) {
        return studentService.save(request);
    }

    @GetMapping
    public PageableResponse<StudentResponse> retrieveAll(PageCriteria pageCriteria) {
        return studentService.getAll(pageCriteria);
    }

    @GetMapping("/{id}")
    public StudentResponse retrieveById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateById(@PathVariable Long id,
                           @RequestBody StudentRequest request) {
        studentService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
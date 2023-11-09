package az.ingress.service.concrete;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.repository.StudentRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.CustomPageRequest;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.PageableStudentResponse;
import az.ingress.model.response.StudentResponse;
import az.ingress.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.StudentMapper.STUDENT_MAPPER;
import static az.ingress.model.constant.ExceptionConstant.STUDENT_NOT_FOUND;
import static az.ingress.model.enums.EntityState.DELETED;
import static az.ingress.model.enums.EntityState.UPDATED;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class StudentServiceHandler implements StudentService {
    StudentRepository studentRepository;

    public StudentResponse save(StudentRequest request) {
        var student = STUDENT_MAPPER.mapRequestToEntity(request);
        var saved = studentRepository.save(student);
        return STUDENT_MAPPER.mapEntityToResponse(saved);
    }

    public StudentResponse getById(Long id) {
        var student = fetchIfExist(id);
        return STUDENT_MAPPER.mapEntityToResponse(student);
    }

    public PageableStudentResponse getAll(CustomPageRequest request) {
        var pageRequest = PageRequest.of(request.getPage(), request.getSize());
        var students = studentRepository.findAll(pageRequest);
        return STUDENT_MAPPER.buildPageableResponse(students);
    }

    public void updateById(Long id, StudentRequest request) {
        var student = fetchIfExist(id);
        var updated = update(student, request);
        studentRepository.save(updated);
    }

    public void deleteById(Long id) {
        var student = fetchIfExist(id);
        student.setStatus(DELETED);
        studentRepository.save(student);
    }

    private StudentEntity fetchIfExist(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
    }

    private StudentEntity update(StudentEntity entity, StudentRequest request) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setAge(request.getAge());
        entity.setStatus(UPDATED);
        return entity;
    }
}
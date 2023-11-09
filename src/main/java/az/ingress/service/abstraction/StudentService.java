package az.ingress.service.abstraction;

import az.ingress.model.request.CustomPageRequest;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.PageableStudentResponse;
import az.ingress.model.response.StudentResponse;


public interface StudentService {
    StudentResponse save(StudentRequest request);

    StudentResponse getById(Long id);

    PageableStudentResponse getAll(CustomPageRequest request);

    void updateById(Long id, StudentRequest request);

    void deleteById(Long id);
}
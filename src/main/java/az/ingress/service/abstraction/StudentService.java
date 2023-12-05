package az.ingress.service.abstraction;

import az.ingress.model.common.PageCriteria;
import az.ingress.model.common.PageableResponse;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.StudentResponse;


public interface StudentService {
    StudentResponse save(StudentRequest request);

    StudentResponse getById(Long id);

    PageableResponse<StudentResponse> getAll(PageCriteria pageCriteria);

    void updateById(Long id, StudentRequest request);

    void deleteById(Long id);
}
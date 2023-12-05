package az.ingress.mapper;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.common.PageableResponse;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.StudentResponse;
import org.springframework.data.domain.Page;

import static az.ingress.model.enums.EntityState.CREATED;

public enum StudentMapper {
    STUDENT_MAPPER;

    public StudentEntity mapRequestToEntity(StudentRequest request) {
        return StudentEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .status(CREATED)
                .build();
    }

    public StudentResponse mapEntityToResponse(StudentEntity student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .build();
    }

    public PageableResponse<StudentResponse> buildPageableResponse(Page<StudentEntity> students) {
        return PageableResponse.<StudentResponse>builder()
                .content(students.map(STUDENT_MAPPER::mapEntityToResponse).toList())
                .totalElements(students.getTotalElements())
                .totalPages(students.getTotalPages())
                .hasNextPage(students.hasNext())
                .build();
    }
}
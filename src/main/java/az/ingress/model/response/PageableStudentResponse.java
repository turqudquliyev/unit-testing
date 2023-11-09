package az.ingress.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableStudentResponse {
    List<StudentResponse> students;
    long totalElements;
    int totalPages;
    boolean hasNextPage;
}
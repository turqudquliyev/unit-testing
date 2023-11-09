package az.ingress.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    Long id;
    String firstName;
    String lastName;
    Integer age;
}
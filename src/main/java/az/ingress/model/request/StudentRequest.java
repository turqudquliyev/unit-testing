package az.ingress.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRequest {
    String firstName;
    String lastName;
    Integer age;
}
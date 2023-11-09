package az.ingress.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
@JsonInclude(NON_NULL)
public class ExceptionResponse {
    String code;
    List<ValidationException> validationExceptions;
}
package az.ingress.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(staticName = "of")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomPageRequest {
    Integer page;
    Integer size;
}
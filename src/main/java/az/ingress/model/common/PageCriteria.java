package az.ingress.model.common;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(staticName = "of")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageCriteria {
    @Min(0L) Integer page;
    @Min(1L) Integer size;
}
package org.maiyon.model.APIEntity;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommonResponse<T> {
    HttpStatus status;
    private T body;
}

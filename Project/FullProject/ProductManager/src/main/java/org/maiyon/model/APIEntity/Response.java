package org.maiyon.model.APIEntity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Response<T> {
    private ResponseStatus status;
    private String statusCode;
    private T content;
}
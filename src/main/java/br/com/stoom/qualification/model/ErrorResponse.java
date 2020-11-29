package br.com.stoom.qualification.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize(builder = ErrorResponse.GeneralErrorResponse.class)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(builderClassName = "GeneralErrorResponse", toBuilder = true)

public class ErrorResponse {

    private final String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GeneralErrorResponse {}
}

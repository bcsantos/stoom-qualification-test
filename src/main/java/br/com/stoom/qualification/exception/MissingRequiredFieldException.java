package br.com.stoom.qualification.exception;

import java.util.UUID;

public class MissingRequiredFieldException extends RuntimeException {

    public MissingRequiredFieldException(String fieldName) {
        super("The required field '" + fieldName + "' was not informed.");
    }

}

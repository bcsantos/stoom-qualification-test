package br.com.stoom.qualification.exception;

public class FieldValueExceededException extends AbstractMessageException {

    public FieldValueExceededException(String fieldName, int maxLength) {
        super("The value infomed for '" + fieldName + "' is too long. Maximum valid length: " + maxLength);
    }

}

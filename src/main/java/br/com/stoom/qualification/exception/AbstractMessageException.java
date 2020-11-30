package br.com.stoom.qualification.exception;

public abstract class AbstractMessageException extends RuntimeException {

    public AbstractMessageException(String message) {
        super(message);
    }

}

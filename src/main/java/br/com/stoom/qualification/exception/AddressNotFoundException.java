package br.com.stoom.qualification.exception;

import java.util.UUID;

public class AddressNotFoundException extends IllegalArgumentException {

    public AddressNotFoundException(UUID id) {
        super("No Address found for id " + id.toString() + '.');
    }

}

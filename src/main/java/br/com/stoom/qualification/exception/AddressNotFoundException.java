package br.com.stoom.qualification.exception;

import lombok.NoArgsConstructor;

public class AddressNotFoundException extends IllegalArgumentException {

    public AddressNotFoundException() {
        super("No Address found for the requested Id.");
    }

}

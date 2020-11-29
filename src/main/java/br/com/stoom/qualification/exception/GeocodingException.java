package br.com.stoom.qualification.exception;

public class GeocodingException extends IllegalArgumentException {

    public GeocodingException() {
        super("Failed to retrieve latitude and longitude from the address.");
    }

}

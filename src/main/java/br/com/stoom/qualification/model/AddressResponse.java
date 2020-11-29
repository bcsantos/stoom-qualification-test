package br.com.stoom.qualification.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder(toBuilder = true)
public class AddressResponse {

    private UUID id;

    private String streetName;

    private String number;

    private String complement;

    private String neighbourhood;

    private String city;

    private String state;

    private String country;

    private String zipcode;

    private String latitude;

    private String longitude;
}

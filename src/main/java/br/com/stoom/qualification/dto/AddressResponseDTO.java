package br.com.stoom.qualification.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder(toBuilder = true)
public class AddressResponseDTO {

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

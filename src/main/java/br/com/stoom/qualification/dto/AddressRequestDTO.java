package br.com.stoom.qualification.dto;

import lombok.*;
import org.springframework.lang.NonNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder(toBuilder = true)
public class AddressRequestDTO {

    private String streetName;

    @NonNull
    private String number;

    private String complement;

    @NonNull
    private String neighbourhood;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String country;

    @NonNull
    private String zipcode;

    private String latitude;

    private String longitude;
}

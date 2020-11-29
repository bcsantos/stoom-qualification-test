package br.com.stoom.qualification.model;

import br.com.stoom.qualification.entity.Address;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder(toBuilder = true)
public class AddressRequest {

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

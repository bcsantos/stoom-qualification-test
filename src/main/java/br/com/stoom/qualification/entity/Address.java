package br.com.stoom.qualification.entity;

import br.com.stoom.qualification.model.AddressRequest;
import br.com.stoom.qualification.model.AddressResponse;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder(toBuilder = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @Transient
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Address fromRequest(AddressRequest request) {
        return modelMapper.map(request, Address.class);
    }

    public AddressResponse toResponse() {
        return modelMapper.map(this, AddressResponse.class);
    }
}

package br.com.stoom.qualification.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressTest {

    @Test
    public void getMinimumAddress_shouldPass() {
        Address address = null;
        try {
            address = Address.builder()
                    .streetName("R. Comendador Araújo")
                    .number("1066")
                    .neighbourhood("Batel")
                    .city("Curitiba")
                    .state("PR")
                    .country("Brasil")
                    .zipcode("80420-000")
                    .build();
        } catch (Exception ignored) {
        }
        Assertions.assertThat((address != null) &&
                (address.getLatitude() != null) &&
                (address.getLongitude() != null))
                .isTrue();
    }

    @Test
    public void createWrongAddress_shouldFail() {
        Address address = null;
        try {
            address = Address.builder()
                    .streetName("Street Name")
                    .number("Number")
                    .complement("Complement")
                    .neighbourhood("Neighbourhood")
                    .city("City")
                    .state("State")
                    .country("Country")
                    .zipcode("12345678")
                    .build();
        } catch (Exception ignored) {
        }
        Assertions.assertThat(address == null).isTrue();
    }

    @Test
    public void createFieldTooLong_shouldFail() {
        Address address = null;
        try {
            address = Address.builder()
                    .streetName("Street Name")
                    .number("Number")
                    .complement("Complement")
                    .neighbourhood("Neighbourhood")
                    .city("City")
                    .state("State")
                    .country("Country")
                    .zipcode("12345678901")
                    .build();
        } catch (Exception ignored) {
        }
        Assertions.assertThat(address == null).isTrue();
    }

    @Test
    public void createBasicAddressWithLatLong() {
        Address address = null;
        try {
            address = Address.builder()
                    .streetName("Street Name")
                    .number("Number")
                    .neighbourhood("Neighbourhood")
                    .city("City")
                    .state("State")
                    .country("Country")
                    .zipcode("ZipCode")
                    .latitude("111")
                    .longitude("222")
                    .build();
        } catch (Exception ignored) {
        }
        Assertions.assertThat(address != null).isTrue();
    }

    @Test
    public void createCompleteAddress() {
        Address address = null;
        try {
            address = Address.builder()
                    .streetName("Street Name")
                    .number("Number")
                    .complement("Complement")
                    .neighbourhood("Neighbourhood")
                    .city("City")
                    .state("State")
                    .country("Country")
                    .zipcode("ZipCode")
                    .latitude("111")
                    .longitude("222")
                    .build();
        } catch(Exception ignore) {
        }
        Assertions.assertThat(address != null).isTrue();
    }
}
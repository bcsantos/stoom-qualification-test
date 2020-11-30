package br.com.stoom.qualification.repository;

import br.com.stoom.qualification.entity.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class AddressRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AddressRepository repository;

    @Test
    public void shouldSave() {
        var expected = repository.save(getCompleteAddress());

        var actual = testEntityManager.find(Address.class, expected.getId());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldFindById() {
        var expected = testEntityManager.persistAndFlush(getCompleteAddress());

        var actual = repository.findById(expected.getId()).orElseThrow();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldDelete() {
        var expected = testEntityManager.persistAndFlush(getCompleteAddress());
        repository.delete(expected);

        Assertions.assertThat(repository.findById(expected.getId()).isPresent()).isFalse();
    }

    private Address getCompleteAddress() {
        return Address.builder()
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
    }
}

package br.com.stoom.qualification.controller;

import br.com.stoom.qualification.entity.Address;
import br.com.stoom.qualification.model.AddressRequest;
import br.com.stoom.qualification.model.AddressResponse;
import br.com.stoom.qualification.repository.AddressRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Address Controller Test")
class AddressControllerTest {
    private final String REQUEST_BASE = "/stoom/address/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressRepository repository;


    // create: address ok

    @Test
    void createOkAddress_shouldSucceed() throws Exception {
        var request = getCompleteRequest();
        mockMvc.perform(post(REQUEST_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
        var address = repository.findAll().stream().findFirst().orElse(null);
        assertThat(address).extracting(Address::getStreetName).isEqualTo(request.getStreetName());
        getCompleteAddress();
    }

    // create: address missing required field

    @Test
    void createMissingRequiredField_shouldFail() throws Exception {
        var request = getCompleteRequest().toBuilder().number(null).build();
        mockMvc.perform(post(REQUEST_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // create: address missing latitude and longitude

    @Test
    void createMissingLatLong_shouldFindLatLongAndSucceed() throws Exception {
        var request = getCompleteRequest();
        mockMvc.perform(post(REQUEST_BASE).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(
                        request.toBuilder().latitude(null).longitude(null).build())))
                        .andExpect(status().isCreated())
                        .andExpect(header().exists("Location"));

        var address = repository.findAll().stream().findFirst().orElse(null);
        assertThat(address).extracting(Address::getLatitude, Address::getLongitude)
                .contains(request.getLatitude(), request.getLongitude());
    }


    // read all: empty

    @Test
    void findAllEmptyBase_shouldReturnList() throws Exception {
        String responseBody = mockMvc
                .perform(get(REQUEST_BASE)
                        .queryParam("field", "city")
                        .queryParam("value", "Some")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var addressModels = objectMapper.readValue(responseBody,new TypeReference<List<AddressRequest>>() { });
        assertThat(addressModels).hasSize(0);
    }

    // read: all ok

    @Test
    void findAll_shouldReturnList() throws Exception {
        String responseBody = mockMvc
                .perform(get(REQUEST_BASE)
                        .queryParam("field", "city")
                        .queryParam("value", "Some")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var addressModels =
                objectMapper.readValue(responseBody,new TypeReference<List<AddressRequest>>() { });
        assertThat(addressModels).size().isNotZero();
    }

    // read: invalid id

    @Test
    void findById_invalidId_shouldFail() throws Exception {
        String responseBody = mockMvc
                .perform(get(REQUEST_BASE)
                        .queryParam("field", "city")
                        .queryParam("value", "Some")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var addressModels =
                objectMapper.readValue(responseBody,new TypeReference<List<AddressRequest>>() { });
        assertThat(addressModels).size().isNotZero();
    }

    // read: existing id

    @Test
    void findById_existingId_shouldReturnAddress() throws Exception {
        Address address = getCompleteAddress();
        String responseBody = mockMvc
                .perform(get(REQUEST_BASE + address.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        AddressRequest addressModel = objectMapper.readValue(responseBody, AddressRequest.class);
        assertThat(addressModel)
                .extracting(AddressRequest::getCity, AddressRequest::getLatitude, AddressRequest::getLongitude)
                .contains("AlgumLugar", "-22.877083", "-47.048379");
    }

    // read: inexistent id

    @Test
    void findById_inexistentId_shouldFail() throws Exception {
        String responseBody = mockMvc
                .perform(get(REQUEST_BASE)
                        .queryParam("field", "city")
                        .queryParam("value", "Some")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var addressModels =
                objectMapper.readValue(responseBody,new TypeReference<List<AddressRequest>>() { });
        assertThat(addressModels).size().isNotZero();
    }


    // update: address ok

    @Test
    void update_existingIdAddressOk_shouldSucceed() throws Exception {
        var addressModel = getCompleteRequest();
        mockMvc.perform(put(REQUEST_BASE + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressModel)))
                .andExpect(status().isNotFound());
    }

    // update: address missing required field

    @Test
    void update_existingIdMissingRequiredField_shouldFail() throws Exception {
        var addressModel = getCompleteRequest().toBuilder().state(null).build();
        mockMvc.perform(put(REQUEST_BASE + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressModel)))
                .andExpect(status().isFound());
    }

    // update: address missing latitude and longitude

    @Test
    void updateExistingIdMissingLatLong_shouldFindLatLongAndSucceed() throws Exception {
        var addressModel = getCompleteRequest().toBuilder().state(null).build();
        mockMvc.perform(put(REQUEST_BASE + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressModel)))
                .andExpect(status().isFound());
    }

    // update: address missing latitude and longitude, fake address

    @Test
    void updateExistingIdMissingLatLongFakeAddress_shouldFindLatLongButFail() throws Exception {
        var request = getCompleteRequest();
        mockMvc.perform(post(REQUEST_BASE + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                        request.toBuilder().latitude(null).longitude(null).build())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        var address = repository.findAll().stream().findFirst().orElse(null);
        assertThat(address).extracting(Address::getLatitude, Address::getLongitude)
                .contains(request.getLatitude(), request.getLongitude());
    }


    // delete: id ok

    @Test
    void deleteExistigId_shouldSucceed() throws Exception {
        Address address = getCompleteAddress();
        // Given a simple GET request
        String responseBody = mockMvc
                .perform(delete(REQUEST_BASE + address.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn().getResponse().getContentAsString();
    }


    // ========================================================================
    //  Utilities
    // ========================================================================

    private String queryHttp(String url) {
        try {
            String responseBody = mockMvc.perform(get("/stoom/api/ecommerce").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private AddressRequest getCompleteRequest() {
        return AddressRequest.builder()
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
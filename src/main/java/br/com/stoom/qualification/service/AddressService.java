package br.com.stoom.qualification.service;

import br.com.stoom.qualification.entity.Address;
import br.com.stoom.qualification.exception.AddressNotFoundException;
import br.com.stoom.qualification.model.AddressRequest;
import br.com.stoom.qualification.model.AddressResponse;
import br.com.stoom.qualification.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private GoogleGeocodingService geocodingAPI;

    private Address find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    public List<AddressResponse> findAll() {
        return repository.findAll().stream().map(Address::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public AddressResponse findByID(UUID id) {
        return find(id).toResponse();
    }

    public AddressResponse create(AddressRequest request) {
        var address = Address.fromRequest(request);
        return repository.save(prepareAddress(address)).toResponse();
    }

    public AddressResponse update(UUID id, AddressRequest request) {
        find(id);
        Address address;
        try {
            address = Address.fromRequest(request);
        } catch (Exception e) {
            throw new RuntimeException("Erro: " + e.getClass().getName());
        }
        address.setId(id);
        return repository.save(prepareAddress(address)).toResponse();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Address prepareAddress(Address address) {
        if ((address.getLatitude() != null) && !address.getLatitude().isEmpty() &&
                (address.getLongitude() != null) && !address.getLongitude().isEmpty()) {
            return address;
        }

        return geocodingAPI.updateAddress(address);
    }
}

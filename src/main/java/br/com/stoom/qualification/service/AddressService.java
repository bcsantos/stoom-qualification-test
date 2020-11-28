package br.com.stoom.qualification.service;

import br.com.stoom.qualification.dto.AddressRequestDTO;
import br.com.stoom.qualification.dto.AddressResponseDTO;
import br.com.stoom.qualification.entity.Address;
import br.com.stoom.qualification.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();


    public List<AddressResponseDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ResponseEntity<AddressResponseDTO> create(AddressRequestDTO addressRequestDTO) {
        return new ResponseEntity<>(toDTO(repository.save(toEntity(addressRequestDTO))), HttpStatus.CREATED);
    }

    private Address toEntity(AddressRequestDTO addressRequestDTO) {
        return modelMapper.map(addressRequestDTO, Address.class);
    }

    private AddressResponseDTO toDTO(Address address) {
        return modelMapper.map(address, AddressResponseDTO.class);
    }

}

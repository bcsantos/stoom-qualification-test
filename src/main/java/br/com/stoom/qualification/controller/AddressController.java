package br.com.stoom.qualification.controller;

import br.com.stoom.qualification.dto.AddressRequestDTO;
import br.com.stoom.qualification.dto.AddressResponseDTO;
import br.com.stoom.qualification.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stoom/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // CRUD methods

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.create(addressRequestDTO);
    }

/*
    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return addressService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") UUID id, @RequestBody AddressResponseDTO addressDTO) {
        return addressService.findById(id)
                .map((record) -> {
                    record.setStreetName(addressDTO.getStreetName());
                    record.setNumber(addressDTO.getNumber());
                    record.setComplement(addressDTO.getComplement());
                    record.setNeighbourhood(addressDTO.getNeighbourhood());
                    record.setCity(addressDTO.getCity());
                    record.setState(addressDTO.getState());
                    record.setCountry(addressDTO.getCountry());
                    record.setZipcode(addressDTO.getZipcode());
                    record.setZipcode(addressDTO.getZipcode());
                    record.setLatitude(addressDTO.getLatitude());
                    record.setLongitude(addressDTO.getLongitude());

                    addressService.save(record);
                    return addressService;
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return addressService.findById(id)
                .map(record -> {
                    addressService.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
*/
}

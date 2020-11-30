package br.com.stoom.qualification.controller;

import br.com.stoom.qualification.exception.FieldValueExceededException;
import br.com.stoom.qualification.exception.MissingRequiredFieldException;
import br.com.stoom.qualification.model.AddressRequest;
import br.com.stoom.qualification.model.AddressResponse;
import br.com.stoom.qualification.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stoom/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Create

    @PostMapping
    public ResponseEntity<AddressResponse> create(@RequestBody AddressRequest request) {
        validateInputFields(request);
        return new ResponseEntity<>(addressService.create(request), HttpStatus.CREATED);
    }


    // Read

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(addressService.findByID(UUID.fromString(id)));
    }


    // Update

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressResponse> update(@PathVariable String id, @RequestBody AddressRequest request) {
        validateInputFields(request);
        return ResponseEntity.ok(addressService.update(UUID.fromString(id), request));
    }


    // Delete

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        addressService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }


    private void validateInputFields(AddressRequest request) {
        validateField("streetName", request.getStreetName(), true, 500);
        validateField("number", request.getNumber(), true, 100);
        validateField("complement", request.getComplement(), false, 500);
        validateField("neighbourhood", request.getNeighbourhood(), true, 100);
        validateField("city", request.getCity(), true, 100);
        validateField("state", request.getState(), true, 30);
        validateField("country", request.getCountry(), true, 50);
        validateField("zipcode", request.getZipcode(), true, 10);
        validateField("latitude", request.getLatitude(), false, 50);
        validateField("longitude", request.getLongitude(), false, 50);
    }

    private void validateField(String fieldName, String fieldValue, boolean required, int maxLength) {
        if (fieldValue == null) {
            if (required) {
                throw new MissingRequiredFieldException(fieldName);
            }
        } else {
            if (fieldValue.length() > maxLength) {
                throw new FieldValueExceededException(fieldName, maxLength);
            }
        }
    }
}

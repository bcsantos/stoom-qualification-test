package br.com.stoom.qualification.service;

import br.com.stoom.qualification.entity.Address;
import br.com.stoom.qualification.exception.GeocodingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Slf4j
@Service
public class GoogleGeocodingService {

    @Value("${google.api.base-url}")
    private String baseURL;

    @Value("${google.api.key}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpService httpService;

    public Address updateAddress(Address address) {
        // Query GoogleMaps API
        log.info("Longitude ou Longitude não informadas, consultando Google Maps.");
        var url = buildURL(address);
        log.info("URL: {}", url);
        var jsonResponse = httpService.run(url);
        if ((jsonResponse == null) || jsonResponse.isEmpty()) {
            throw new GeocodingException();
        }

        // Parse the response and extract latitude/longitude
        JsonNode node;
        try {
            node = mapper.readTree(jsonResponse)
                    .get("results").get(0).get("geometry").get("location");
        } catch (JsonProcessingException e) {
            throw new GeocodingException();
        }
        var latitude = node.get("lat").asText();
        var longitude = node.get("long").asText();

        // Update the Address entity and return
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        return address;
    }

    private String buildURL(Address address) {
        var fullAddress = address.getStreetName() + " " +
                address.getNumber() + ' ' +
                address.getCity() + ' ' +
                address.getState() + ' ' +
                address.getCountry() + ' ' +
                address.getZipcode();

        // Build the URI encoding parameters, to avoid injection
        try {
            return new URIBuilder(baseURL)
                    .addParameter("address", fullAddress)
                    .addParameter("key", apiKey)
                    .toString();
        } catch (URISyntaxException e) {
            log.error("Invalid URI Syntax.\n" +
                    "- baseURL: \"{}\"\n" +
                    "- address: \"{}\"",
                    baseURL,
                    fullAddress);
            throw new GeocodingException();
        }
    }
}

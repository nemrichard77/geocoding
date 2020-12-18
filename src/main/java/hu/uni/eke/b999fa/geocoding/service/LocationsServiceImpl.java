package hu.uni.eke.b999fa.geocoding.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.uni.eke.b999fa.geocoding.config.GeocodeConfiguration;
import hu.uni.eke.b999fa.geocoding.dao.LocationResultDao;
import hu.uni.eke.b999fa.geocoding.model.LocationResult;
import hu.uni.eke.b999fa.geocoding.service.exception.GeocodingApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationsServiceImpl implements LocationsService  {
    @Autowired
    private GeocodeConfiguration geocodeConfig;

    private final LocationResultDao dao;

    @Override
    public void getLocations(String[] addresses) throws IOException, InterruptedException, GeocodingApiException {
        for (String address : addresses) {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=hu&address=" + encodedAddress))
                    .header("x-rapidapi-key", geocodeConfig.getAPI_KEY())
                    .header("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonRoot = objectMapper.readTree(response.body());

            if (jsonRoot.isNull() || jsonRoot.get("status") == null) {
                if (jsonRoot.get("message") != null)
                    throw new GeocodingApiException("Error while get geolocations from google maps api: " + jsonRoot.get("message").asText());
                else
                    throw new GeocodingApiException("Unknown error while get geolocations from google maps api.");
            } else if (!jsonRoot.get("status").asText().equals("OK")) {
                throw new GeocodingApiException("Error while get geolocations from google maps api: STATUS=" + jsonRoot.get("status").asText());
            }

            for (JsonNode node : jsonRoot.get("results")) {
                dao.createLocationResult(new LocationResult(node.get("formatted_address").asText(),
                        node.get("geometry").get("location").get("lat").asText(),
                        node.get("geometry").get("location").get("lng").asText()));
            }
        }
    }

    @Override
    public void printLocations(String[] addresses) throws IOException, InterruptedException {
        try {
            getLocations(addresses);
        } catch (GeocodingApiException e) {
            log.info("Geocoding Api Exception: {}", e.getMessage());
        }
        System.out.println("");
        for (LocationResult locres : dao.readAll()){
            System.out.println(locres.formatted_address);
            System.out.println("Szélességi: " + locres.lat);
            System.out.println("Hosszúsági: " + locres.lng);
            System.out.println("");
        }
    }
}

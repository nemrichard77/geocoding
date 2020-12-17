package hu.uni.eke.b999fa.geocoding.service;

import hu.uni.eke.b999fa.geocoding.service.exception.GeocodingApiException;

import java.io.IOException;

public interface LocationsService {
    public void getLocations(String[] addresses) throws IOException, InterruptedException, GeocodingApiException;
    public void printLocations(String[] addresses) throws IOException, InterruptedException;
}

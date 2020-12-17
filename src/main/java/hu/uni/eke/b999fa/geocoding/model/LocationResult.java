package hu.uni.eke.b999fa.geocoding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationResult {
    public String formatted_address;
    public String lat;
    public String lng;
}

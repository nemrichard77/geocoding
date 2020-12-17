package hu.uni.eke.b999fa.geocoding.dao;

import hu.uni.eke.b999fa.geocoding.model.LocationResult;

import java.util.Collection;

public interface LocationResultDao {
    void createLocationResult(LocationResult locationResult);
    public Collection<LocationResult> readAll();
}

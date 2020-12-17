package hu.uni.eke.b999fa.geocoding.dao;

import hu.uni.eke.b999fa.geocoding.model.LocationResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class LocationResultDaoImpl implements LocationResultDao {
    private Collection<LocationResult> database;

    public LocationResultDaoImpl() {
        this(new HashSet<>());
    }

    protected  LocationResultDaoImpl(Collection<LocationResult> database){
        this.database = database;
    }

    @Override
    public void createLocationResult(LocationResult locationResult){
        this.database.add(locationResult);
    }

    @Override
    public Collection<LocationResult> readAll() {
        return new HashSet<>(database);
    }
}

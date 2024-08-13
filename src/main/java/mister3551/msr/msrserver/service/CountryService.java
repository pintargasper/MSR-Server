package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.Country;
import mister3551.msr.msrserver.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public ArrayList<Country> getCountries() {
        return (ArrayList<Country>) countryRepository.findAll();
    }
}
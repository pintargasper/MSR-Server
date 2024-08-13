package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.entity.Country;
import mister3551.msr.msrserver.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public ArrayList<Country> countries() {
        return countryService.getCountries();
    }
}
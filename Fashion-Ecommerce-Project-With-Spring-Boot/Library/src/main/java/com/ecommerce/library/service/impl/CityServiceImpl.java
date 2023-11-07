package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Country;
import com.ecommerce.library.repository.CityRepository;
import com.ecommerce.library.repository.CountryRepository;
import com.ecommerce.library.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {


    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> getCitiesByCountry(Country country) {
        return cityRepository.findCitiesByCountry(country);
    }
}

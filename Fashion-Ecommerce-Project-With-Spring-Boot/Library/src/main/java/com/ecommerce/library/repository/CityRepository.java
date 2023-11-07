package com.ecommerce.library.repository;

import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findCitiesByCountry(Country country);
}

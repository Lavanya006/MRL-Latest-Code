package com.mt_ag.bayer.cmc.persistence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mt_ag.bayer.cmc.persistence.entity.Substance;
import com.mt_ag.bayer.cmc.persistence.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt_ag.bayer.cmc.persistence.entity.Country;

import javax.annotation.PostConstruct;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository repository;

    @Autowired
    public CountryServiceImpl(CountryRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void save(Country country) {
        this.repository.save(country);
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        this.repository.findAll().forEach(countries::add);
        return countries;
    }

    @Override
    public Country find(Long id) {
        return this.repository.findById(id).get();
    }

    @Override
    public void delete(Country country) {
        this.repository.delete(country);
    }

    @Override
    public Country findByName(String name) {
        return this.repository.findByNameIgnoreCaseEquals(name);
    }

    @Override
    public List<Country> findByOtherNames(String anotherName) {
        return this.repository.findByOtherNamesIgnoreCaseEquals(anotherName);
    }

}

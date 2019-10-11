package com.altimetrik.countries.service;

import com.altimetrik.countries.model.Country;

import java.util.List;
import java.util.Set;

public interface CountryService {
    List<Country> getSimilarCountries(String countryCode, boolean regionCode, boolean incomeCode, boolean lendingCode);
}


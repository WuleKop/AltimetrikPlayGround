package com.altimetrik.countries.service;

import com.altimetrik.countries.model.Country;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private String url ="http://api.worldbank.org/v2/country?format=json&page=";
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Country> getSimilarCountries(String countryCode, boolean regionCode, boolean incomeCode, boolean lendingCode) {

        List<Country> similarCountries = new ArrayList<>();
        //Get the Total Number of pages
        JSONArray firstObj = new JSONArray(restTemplate.getForObject(url+1, String.class));
        int length=firstObj.getJSONObject(0).getInt("pages");

        //Get all Countries as a Json array
        List<JSONArray> allCountries = new ArrayList<>();
        for(int i=1;i<=length;i++) {
            JSONArray secondObj = new JSONArray(restTemplate.getForObject(url + i, String.class));
            JSONArray array = secondObj.getJSONArray(1);
            allCountries.add(array);
        }
        //Get the Entered Country
        Country enteredCountry=null;
        boolean b=false;
        for(JSONArray json:allCountries) {
           for(int i=0;i<json.length();i++) {
                JSONObject object = json.getJSONObject(i);
                if(countryCode.equals(object.getString("id"))) {
                    enteredCountry=getCountry(object);
                    b=true;
                    break;
                }
                if(b) break;
            }
        }
        //Get Matching Countries
        for(JSONArray json2:allCountries) {
            for(int i=0;i<json2.length();i++) {
                JSONObject object = json2.getJSONObject(i);
                if (!countryCode.equals(object.getString("id"))) {

                    String region= object.getJSONObject("region").getString("id");
                    String income = object.getJSONObject("incomeLevel").getString("id");
                    String lending = object.getJSONObject("lendingType").getString("id");

                if(!regionCode && !incomeCode && !lendingCode) {
                   return null;
                }
                else if (!regionCode && !incomeCode & lendingCode) {
                    if(enteredCountry.getLendingType().equals(lending)) {
                        Country foundCountry =getCountry(object);
                        similarCountries.add(foundCountry);
                    }

                } else if (!regionCode && incomeCode & !lendingCode) {
                    if(enteredCountry.getIncomeLevel().equals(income)) {
                        Country foundCountry =getCountry(object);
                        similarCountries.add(foundCountry);
                    }
                } else if (!regionCode && incomeCode & lendingCode) {
                    if(enteredCountry.getLendingType().equals(lending) &&
                            (enteredCountry.getIncomeLevel().equals(income))) {
                        Country foundCountry =getCountry(object);
                        similarCountries.add(foundCountry);
                    }

                } else if (regionCode && !incomeCode & !lendingCode) {
                    if(enteredCountry.getRegion().equals(region)) {
                        Country foundCountry = getCountry(object);
                        similarCountries.add(foundCountry);
                    }
                } else if (regionCode && !incomeCode & lendingCode) {
                    if(enteredCountry.getRegion().equals(region) &&
                            (enteredCountry.getLendingType().equals(lending))) {
                        Country foundCountry =getCountry(object);
                        similarCountries.add(foundCountry);
                    }

                } else if (regionCode && incomeCode & !lendingCode) {
                    if(enteredCountry.getRegion().equals(region) &&
                            (enteredCountry.getIncomeLevel().equals(income))) {
                        Country foundCountry =getCountry(object);
                        similarCountries.add(foundCountry);
                    }
                } else if (regionCode && incomeCode & lendingCode) {
                    if (enteredCountry.getRegion().equals(region) &&
                            (enteredCountry.getIncomeLevel().equals(income)) &&
                            (enteredCountry.getLendingType().equals(lending))) {
                        Country foundCountry = getCountry(object);
                        similarCountries.add(foundCountry);
                    }
                }
            }
        }
        }
        return similarCountries;
    }

    public Country getCountry(JSONObject object){
        return new Country(object.getString("id"),object.getString("name"),object.getString("capitalCity"),
                object.getJSONObject("region").getString("id"),object.getJSONObject("incomeLevel").getString("id"),
                object.getJSONObject("lendingType").getString("id"),object.getString("longitude"),
                object.getString("latitude"));

    }

}

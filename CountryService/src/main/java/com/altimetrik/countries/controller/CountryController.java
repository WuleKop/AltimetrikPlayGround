package com.altimetrik.countries.controller;

import com.altimetrik.countries.model.Country;
import com.altimetrik.countries.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="CountryController", description = "Similar Countries REST API",tags = "Country Controller")
@CrossOrigin(origins = {"http://localhost:4200"},
        maxAge = 4800, allowCredentials = "false")
@RequestMapping("api/countries")
@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/similar", produces = "application/json;charset=utf-8")
    @ApiOperation(value="Get Similar Countries", notes = "${CountryController.getAllCountries.notes}")
    public ResponseEntity<List<Country>> getAllCountries(@ApiParam(value = "Country Code", required = true)
                                                        @RequestParam String countryCode, @ApiParam(value = "Region")
                                                        @RequestParam(defaultValue = "false",required = false) boolean regionCode,
                                                        @ApiParam(value = "Income Level")
                                                        @RequestParam(defaultValue = "false",required = false) boolean incomeCode,
                                                        @ApiParam(value = "Lending Type")
                                                        @RequestParam(defaultValue = "false",required = false) boolean lendingCode) {

        try {
            List<Country> list = countryService.getSimilarCountries(countryCode, regionCode, incomeCode, lendingCode);
            System.out.println(list.size());
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}

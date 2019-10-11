package com.altimetrik.countries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Country {
    private String id;
    private String name;
    private String capitalCity;
    private String region;
    private String incomeLevel;
    private String lendingType;
    private String longitude;
    private String latitude;
}
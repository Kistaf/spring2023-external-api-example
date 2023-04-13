package com.stefanjen.apiusageexample.dtos.part2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
public class NameInfoResponse {
    private String name;
    private String gender;
    private int genderProbability;
    private int age;
    private int ageCount;
    private String country;
    private double countryProbability;

    public NameInfoResponse(String name, GenderizeResponse genderizeResponse, NationalizeResponse nationalizeResponse, AgifyResponse agifyResponse) {
        this.name = name;
        this.gender = genderizeResponse.getGender();
        this.genderProbability = genderizeResponse.getProbability();
        this.age = agifyResponse.getAge();
        this.ageCount = agifyResponse.getCount();

        Country country = nationalizeResponse.getCountry().stream().max(Comparator.comparing(Country::getProbability)).get();
        this.country = country.getCountry_id();
        this.countryProbability = country.getProbability();
    }
}

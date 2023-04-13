package com.stefanjen.apiusageexample.dtos.part2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NationalizeResponse {
    private List<Country> country;
}


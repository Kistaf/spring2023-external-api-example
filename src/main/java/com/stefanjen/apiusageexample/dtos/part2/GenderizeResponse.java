package com.stefanjen.apiusageexample.dtos.part2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenderizeResponse {
    private String gender;
    private int probability;
}

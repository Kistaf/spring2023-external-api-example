package com.stefanjen.apiusageexample.service;

import com.stefanjen.apiusageexample.dtos.part1.Gender;
import com.stefanjen.apiusageexample.dtos.part2.AgifyResponse;
import com.stefanjen.apiusageexample.dtos.part2.GenderizeResponse;
import com.stefanjen.apiusageexample.dtos.part2.NameInfoResponse;
import com.stefanjen.apiusageexample.dtos.part2.NationalizeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class ApiService {

    // The cache is just implemented as a hashmap for demonstration purposes.
    // The handling of the request would be the same if a database were to be used instead.
    private HashMap<String, NameInfoResponse> cache = new HashMap<>();

    public NameInfoResponse getApiResponse(String name) {
        String formattedName = name.toUpperCase();
        if (cache.containsKey(formattedName)) return cache.get(formattedName);

        Mono<GenderizeResponse> genderizeResponse = getGenderResponse(name);
        Mono<NationalizeResponse> nationalizeResponse = getNationalizeResponse(name);
        Mono<AgifyResponse> agifyResponse = getAgifyResponse(name);

        var rs = Mono.zip(genderizeResponse, nationalizeResponse, agifyResponse).map(t -> new NameInfoResponse(name, t.getT1(), t.getT2(), t.getT3()));
        NameInfoResponse nameInfoResponse = rs.block();
        cache.put(formattedName, nameInfoResponse);
        return nameInfoResponse;
    }

    private Mono<GenderizeResponse> getGenderResponse(String name) {
        WebClient client = WebClient.create();
        Mono<GenderizeResponse> genderizeResponseMono = client.get()
                .uri("https://api.genderize.io?name=" + name)
                .retrieve()
                .bodyToMono(GenderizeResponse.class);
        return genderizeResponseMono;
    }

    private Mono<NationalizeResponse> getNationalizeResponse(String name) {
        WebClient client = WebClient.create();
        Mono<NationalizeResponse> nationalizeResponseMono = client.get()
                .uri("https://api.nationalize.io?name=" + name)
                .retrieve()
                .bodyToMono(NationalizeResponse.class);
        return nationalizeResponseMono;
    }

    private Mono<AgifyResponse> getAgifyResponse(String name) {
        WebClient client = WebClient.create();
        Mono<AgifyResponse> agifyResponseMono = client.get()
                .uri("https://api.agify.io?name=" + name)
                .retrieve()
                .bodyToMono(AgifyResponse.class);
        return agifyResponseMono;
    }
}

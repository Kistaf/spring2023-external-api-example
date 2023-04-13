package com.stefanjen.apiusageexample.api;

import com.stefanjen.apiusageexample.dtos.part2.NameInfoResponse;
import com.stefanjen.apiusageexample.service.ApiService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final int SLEEP_TIME = 1000 * 3;

    ApiService apiService;

    public DemoController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping(value = "/random-string-slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
        return RandomStringUtils.randomAlphabetic(10);
    }

    @GetMapping("/name-info")
    public NameInfoResponse nameInfo(@RequestParam String name) {
        return apiService.getApiResponse(name);
    }

}

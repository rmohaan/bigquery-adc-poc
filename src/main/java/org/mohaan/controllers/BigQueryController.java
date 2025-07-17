package org.mohaan.controllers;

import org.mohaan.services.BigQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bigquery")
public class BigQueryController {

    private final BigQueryService bigQueryService;

    public BigQueryController(BigQueryService bigQueryService) {
        this.bigQueryService = bigQueryService;
    }

    @GetMapping("/query")
    public List<String> query(@RequestParam String q) {
        return bigQueryService.runQuery(q);
    }
}

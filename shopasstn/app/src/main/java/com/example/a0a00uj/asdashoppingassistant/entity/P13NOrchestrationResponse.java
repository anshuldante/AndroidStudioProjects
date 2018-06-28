package com.example.a0a00uj.asdashoppingassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class P13NOrchestrationResponse {
    private List<P13NViewItemResponse> results;

    public P13NOrchestrationResponse() {
        results = new ArrayList<>();
    }

    public List<P13NViewItemResponse> getResults() {
        return results;
    }

    public void setResults(List<P13NViewItemResponse> results) {
        this.results = results;
    }
}
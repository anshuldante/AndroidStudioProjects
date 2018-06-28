package com.example.a0a00uj.asdashoppingassistant.entity;

import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class P13NViewItemResponse {
    private List<ItemDTO> items;

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
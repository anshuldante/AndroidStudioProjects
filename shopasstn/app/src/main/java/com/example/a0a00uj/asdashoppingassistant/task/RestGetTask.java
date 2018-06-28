package com.example.a0a00uj.asdashoppingassistant.task;

import android.os.AsyncTask;

import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;
import com.example.a0a00uj.asdashoppingassistant.entity.P13NOrchestrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestGetTask extends AsyncTask<String, Void, List<ItemDTO>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected List<ItemDTO> doInBackground(String... urls) {
        P13NOrchestrationResponse response = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            response = objectMapper.readValue(urlConnection.getInputStream(), P13NOrchestrationResponse.class);
            return response.getResults().get(0).getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
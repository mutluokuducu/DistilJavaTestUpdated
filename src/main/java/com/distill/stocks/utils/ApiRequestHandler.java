package com.distill.stocks.utils;

import com.distill.stocks.dto.StockDataResponse;
import com.distill.stocks.dto.StockDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
public class ApiRequestHandler {

    public static final String API_URL = "https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/2023-01-09?adjusted=true&apiKey=U8qL6DQmwOLZMp5w5YHw3GJMJhdZjOvp";
    private static final String API_KEY = "?adjusted=true&apiKey=U8qL6DQmwOLZMp5w5YHw3GJMJhdZjOvp";
    private static final String API_BASE_URL = "https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/";

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    public List<StockDto> fetchStockData(List<String> tickers, LocalDate date) {
        StockDataResponse responseData = fetchData(date);

        assert responseData != null;
        return responseData.getStockDto().stream()
                .filter(result -> tickers.contains(result.getTrade()))
                .toList();
    }

    private StockDataResponse fetchData(LocalDate date) {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(API_BASE_URL + date.toString() + API_KEY, String.class);
        LOGGER.info("Api called  :{},{}", API_BASE_URL, date);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LOGGER.info("Api response to data ");
            return objectMapper.readValue(jsonResponse, StockDataResponse.class);
        } catch (JsonProcessingException e) {
            // Handle any JSON parsing errors here
            e.printStackTrace();
            LOGGER.error("Api response get error {} :", e);
            return null;
        }
    }
}

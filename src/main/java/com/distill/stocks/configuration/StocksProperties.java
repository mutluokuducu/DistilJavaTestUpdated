package com.distill.stocks.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stocks", ignoreUnknownFields = false)
public class StocksProperties {

    private int fetchDataForDays;

    public int getFetchDataForDays() {
        return fetchDataForDays;
    }

    public void setFetchDataForDays(int fetchDataForDays) {
        this.fetchDataForDays = fetchDataForDays;
    }

}

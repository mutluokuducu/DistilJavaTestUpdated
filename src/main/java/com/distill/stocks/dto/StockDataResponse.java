package com.distill.stocks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockDataResponse {
    @JsonProperty("adjusted")
    private boolean adjusted;
    @JsonProperty("queryCount")
    private int queryCount;
    @JsonProperty("results")
    private List<StockDto> stockDto;
    @JsonProperty("resultsCount")
    private int resultsCount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("count")
    private String count;
}

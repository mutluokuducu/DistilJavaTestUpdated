package com.distill.stocks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
    @JsonProperty("T")
    private String trade;
    @JsonProperty("c")
    private double c;
    @JsonProperty("h")
    private double h;
    @JsonProperty("l")
    private double l;
    @JsonProperty("n")
    private int n;
    @JsonProperty("o")
    private double o;
    @JsonProperty("t")
    private long timeUnit;
    @JsonProperty("v")
    private int v;
    @JsonProperty("vw")
    private double vw;

}

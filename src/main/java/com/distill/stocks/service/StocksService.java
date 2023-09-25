package com.distill.stocks.service;

import com.distill.stocks.model.Stock;

import java.time.LocalDate;
import java.util.List;

public interface StocksService {
    List<Stock> getTop10HighestValueStocksForPrevious7Days(LocalDate startDate, LocalDate endDate);

    List<Stock> getTop5CompaniesWithGreatestPositivePercentChange();

    List<Stock> getAllList();

}

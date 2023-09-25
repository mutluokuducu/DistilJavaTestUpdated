package com.distill.stocks.controller;

import com.distill.stocks.model.Stock;
import com.distill.stocks.service.StocksServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.distill.stocks.constant.Constants.*;

@RestController
public class StocksController {

    private final StocksServiceImpl stocksService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksController.class);

    @Autowired
    public StocksController(StocksServiceImpl stocksService) {
        this.stocksService = stocksService;
    }

    @GetMapping(STOCKS_GET_ALL_LIST)
    public ResponseEntity<List<Stock>> getAllList() {

        List<Stock> stockDailies = stocksService.getAllList();
        if (stockDailies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no data found.
        }
        // Call the service to fetch the top 10 highest value stocks
        return ResponseEntity.ok(stockDailies);
    }

    @GetMapping(STOCKS_TOP_10_HIGHEST_VALUE)
    public ResponseEntity<List<Stock>> getTop10HighestValueStocksForPrevious7Days() {
        LOGGER.info("STOCKS_TOP_10_HIGHEST_VALUE");


        // Calculate the date range for the previous 7 working days
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(endDate);
        List<Stock> stockDailies = stocksService.getTop10HighestValueStocksForPrevious7Days(startDate, endDate);
        if (stockDailies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no data found.
        }

        // Call the service to fetch the top 10 highest value stocks
        return ResponseEntity.ok(stockDailies);
    }

    @GetMapping(STOCKS_TOP_5_POSITIVE_PERCENT_CHANGE)
    public ResponseEntity<List<Stock>> getTop5CompaniesWithGreatestPositivePercentChange() {
        LOGGER.info("STOCKS_TOP_5_POSITIVE_PERCENT_CHANGE");
        List<Stock> top5Companies = stocksService.getTop5CompaniesWithGreatestPositivePercentChange();

        if (top5Companies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no data found.
        }

        return ResponseEntity.ok(top5Companies);
    }

    private LocalDate calculateStartDate(LocalDate endDate) {
        return endDate.minusDays(7);
    }
}

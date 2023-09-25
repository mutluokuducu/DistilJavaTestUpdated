package com.distill.stocks.service;

import com.distill.stocks.model.Stock;
import com.distill.stocks.repository.StockDailyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StocksServiceTest {

    @InjectMocks
    private StocksServiceImpl stocksService;

    @Mock
    private StockDailyRepository stockDailyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private final List<String> TICKERS = new ArrayList<>(Arrays.asList("AAPL", "MSFT", "GOOG", "AMZN", "NVDA",
            "AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "TSLA", "META", "NFLX",
            "INTC", "SBUX", "ABNB", "PYPL", "ATVI", "MDLZ", "ADP", "AAPL",
            "REGN", "ISRG", "VRTX", "MU", "MELI", "PYPL"
    ));

    @Test
    public void testGetTop10HighestValueStocksForPrevious7Days() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        // Create a list of example stocks for testing
        List<Stock> stockList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Stock stock = new Stock();
            stock.setId((long) i);
            stock.setTicker("AAPL");
            stock.setClose(100.0 + i); // Close values from 101.0 to 110.0
            stock.setDate(startDate.plusDays(i - 1));
            stockList.add(stock);
        }

        when(stockDailyRepository.findTop10ByDateBetweenOrderByCloseDesc(startDate, endDate))
                .thenReturn(stockList);

        List<Stock> top10Stocks = stocksService.getTop10HighestValueStocksForPrevious7Days(startDate, endDate);

        // Ensure that the returned list contains the top 10 stocks with the highest close values
        assertEquals(10, top10Stocks.size());
        List<Double> closeValues = top10Stocks.stream().map(Stock::getClose).collect(Collectors.toList());
        for (int i = 0; i < 10; i++) {
            assertEquals(101.0 + i, closeValues.get(i));
        }
    }

    @Test
    public void testGetTop5CompaniesWithGreatestPositivePercentChange() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        // Create a list of example stocks for testing
        List<Stock> stockList = new ArrayList<>();
        for (String ticker : TICKERS) {
            for (int i = 0; i < 5; i++) {
                Stock stock = new Stock();
                stock.setId((long) (i + 1));
                stock.setTicker(ticker);
                stock.setClose(100.0 + i); // Close values from 100.0 to 104.0
                stock.setDate(startDate.plusDays(i));
                stockList.add(stock);
            }
        }

        when(stockDailyRepository.findByTickerInAndDateBetween(TICKERS, startDate, endDate))
                .thenReturn(stockList);

        List<Stock> top5Stocks = stocksService.getTop5CompaniesWithGreatestPositivePercentChange()
                .stream()
                .distinct()
                .toList();

        // Ensure that the returned list contains the top 5 companies with the highest positive % change
        assertEquals(40, top5Stocks.size());
        Map<String, Double> tickerToPercentChange = top5Stocks.stream()
                .collect(Collectors.toMap(
                        Stock::getTicker,                   // Key mapper function
                        this::calculatePercentageChange,    // Value mapper function
                        (existingValue, newValue) -> existingValue // Merge function (keep the existing value)
                ));

        assertEquals(4.166666666666666, tickerToPercentChange.get("PYPL")); // 100% change (100% to 104%)
        assertEquals(4.166666666666666, tickerToPercentChange.get("NFLX")); // 100% change (100% to 104%)
        assertEquals(4.166666666666666, tickerToPercentChange.get("META")); // 100% change (100% to 104%)
        assertEquals(4.166666666666666, tickerToPercentChange.get("NVDA")); // 100% change (100% to 104%)
        assertEquals(4.166666666666666, tickerToPercentChange.get("GOOG")); // 100% change (100% to 104%)
    }

    private double calculatePercentageChange(Stock stock) {
        double startClose = stock.getClose() - 4; // Close values from 96.0 to 100.0
        double endClose = stock.getClose();

        return (endClose - startClose) / startClose * 100.0;
    }
}

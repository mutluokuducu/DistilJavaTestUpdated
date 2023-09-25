package com.distill.stocks.utils;

import com.distill.stocks.dto.StockDto;
import com.distill.stocks.model.Stock;
import com.distill.stocks.repository.StockDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final StockDailyRepository stockDailyRepository;

    private final ApiRequestHandler apiRequestHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private List<String> TICKERS = new ArrayList<>(Arrays.asList("AAPL", "MSFT", "GOOG", "AMZN", "NVDA",
            "AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "TSLA", "META", "NFLX",
            "INTC", "SBUX", "ABNB", "PYPL", "ATVI", "MDLZ", "ADP", "AAPL",
            "REGN", "ISRG", "VRTX", "MU", "MELI", "PYPL"
    ));

    @Autowired
    public DataLoader(
            StockDailyRepository stockDailyRepository, ApiRequestHandler apiRequestHandler) {
        this.stockDailyRepository = stockDailyRepository;
        this.apiRequestHandler = apiRequestHandler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LocalDate localDate = isWeekend(LocalDate.now().minusDays(1));
        TICKERS = TICKERS.parallelStream()
                .filter(ticker -> !stockDailyRepository.existsByTickerAndDate(ticker, localDate))
                .toList();
        processStockData(TICKERS, localDate);
        // TODO: Account for weekends...
    }

    private void processStockData(List<String> tickers, LocalDate date) {
        List<StockDto> list = apiRequestHandler.fetchStockData(tickers, date);
        List<Stock> stock = mapToStockDaily(list);
        stockDailyRepository.saveAll(stock);
        LOGGER.info("#####");
        LOGGER.info("Data saved in to DB :{}",stockDailyRepository.findAll());
        LOGGER.info("#####");
    }

    private List<Stock> mapToStockDaily(List<StockDto> stockResultsList) {
        return stockResultsList.stream()
                .map(result -> Stock.builder()
                        .ticker(result.getTrade())
                        .volume(result.getV())
                        .open(result.getO())
                        .low(result.getL())
                        .high(result.getH())
                        .close(result.getC())
                        .date(Instant.ofEpochMilli(result.getTimeUnit()).atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                .toList();
    }

    public LocalDate isWeekend(final LocalDate localDate) {
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        if (dayOfWeek == 6) {
            return localDate.minusDays(1);
        }
        if (dayOfWeek == 7) {
            return localDate.minusDays(2);
        }
        return localDate;
    }
}

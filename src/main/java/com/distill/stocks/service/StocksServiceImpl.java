package com.distill.stocks.service;

import com.distill.stocks.model.Stock;
import com.distill.stocks.repository.StockDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StocksServiceImpl implements StocksService {

    private final StockDailyRepository stockDailyRepository;

    private final List<String> TICKERS = new ArrayList<>(Arrays.asList("AAPL", "MSFT", "GOOG", "AMZN", "NVDA",
            "AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "TSLA", "META", "NFLX",
            "INTC", "SBUX", "ABNB", "PYPL", "ATVI", "MDLZ", "ADP", "AAPL",
            "REGN", "ISRG", "VRTX", "MU", "MELI", "PYPL"
    ));

    @Autowired
    public StocksServiceImpl(
            StockDailyRepository stockDailyRepository) {
        this.stockDailyRepository = stockDailyRepository;
    }

    @Override
    public List<Stock> getTop10HighestValueStocksForPrevious7Days(LocalDate startDate, LocalDate endDate) {

        List<Stock> top10Stocks = stockDailyRepository.findTop10ByDateBetweenOrderByCloseDesc(startDate, endDate);
        // Check if there are enough data points; if not, return an appropriate message.
        if (top10Stocks.size() < 10) {
            // Return a message or handle this case as needed.
            return Collections.emptyList(); // Example: Return an empty list for insufficient data.
        }

        return top10Stocks;
    }

    @Override
    public List<Stock> getTop5CompaniesWithGreatestPositivePercentChange() {
        // Fetch historical data for the specified tickers and date range.
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6); // 7 working days ago (excluding weekends)

        // Fetch historical data for the tickers within the date range.
        List<Stock> historicalData = fetchStockDailyForTickers(TICKERS, startDate, endDate);

        // Calculate and store the percentage change for each company.
        Map<String, List<Stock>> companyDataMap = new HashMap<>();
        for (String ticker : TICKERS) {
            List<Stock> companyData = historicalData.stream()
                    .filter(data -> ticker.equals(data.getTicker()))
                    .toList();

            companyDataMap.put(ticker, companyData);
        }

        // Calculate the percentage change for each company.
        Map<String, Double> companyPercentageChangeMap = new HashMap<>();
        for (String ticker : TICKERS) {
            List<Stock> companyData = companyDataMap.get(ticker);

            if (companyData != null) {
                double percentChange = calculatePercentageChange(companyData);
                companyPercentageChangeMap.put(ticker, percentChange);
            }
        }

        // Sort the companies by percentage change in descending order.
        List<Map.Entry<String, Double>> sortedCompanies = companyPercentageChangeMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .toList();

        // Get the top 5 companies with the greatest positive percentage change.
        List<String> top5Companies = sortedCompanies.stream()
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();

        // Fetch the full stock data for the top 5 companies.
        return historicalData.stream()
                .filter(data -> top5Companies.contains(data.getTicker()))
                .toList();
    }

    @Override
    public List<Stock> getAllList() {
        return stockDailyRepository.findAll();
    }

    // Helper method to calculate percentage change for a company's data.
    private double calculatePercentageChange(List<Stock> companyData) {
        double startClose = companyData.get(0).getClose();
        double endClose = companyData.get(companyData.size() - 1).getClose();

        return ((endClose - startClose) / startClose) * 100.0;
    }

    public List<Stock> fetchStockDailyForTickers(List<String> tickers, LocalDate startDate, LocalDate endDate) {
        // Assuming you have a repository that can fetch StockDaily data based on tickers and date range.
        return stockDailyRepository.findByTickerInAndDateBetween(tickers, startDate, endDate);
    }

}

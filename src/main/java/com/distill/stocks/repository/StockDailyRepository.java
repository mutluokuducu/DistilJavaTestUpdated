package com.distill.stocks.repository;

import com.distill.stocks.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface StockDailyRepository extends JpaRepository<Stock, Long> {

    boolean existsByTickerAndDate(String ticker, LocalDate date);

    List<Stock> findTop10ByDateBetweenOrderByCloseDesc(LocalDate startDate, LocalDate endDate);

    List<Stock> findByTickerInAndDateBetween(List<String> tickers, LocalDate startDate, LocalDate endDate);


}

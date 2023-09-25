package com.distill.stocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "stock_daily")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ticker", nullable = false)
    private String ticker;
    @Column(name = "volume")
    private int volume;
    @Column(name = "open")
    private double open;
    @Column(name = "low")
    private double low;
    @Column(name = "high")
    private double high;
    @Column(name = "close")
    private double close;
    @Column(name = "date")
    private LocalDate date;
}

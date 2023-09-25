package com.distill.stocks;

import com.distill.stocks.configuration.StocksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(StocksProperties.class)
@ComponentScan(basePackages = "com.distill.stocks")
@SpringBootApplication

public class StocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksApplication.class, args);
	}
}

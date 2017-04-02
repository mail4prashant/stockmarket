package com.jpmorgan.stockmarket.service.api;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.dto.Trade;

public interface ITradeCaptureService {

	Trade recordBuySideTrade(Stock stock, long noOfShares, double priceInPennies);
	
	Trade recordSellSideTrade(Stock stock, long noOfShares, double priceInPennies);
	
	List<Trade> getTrades(Stock stock);

	List<Trade> getRecentTrades(Stock stock, long time, ChronoUnit unit);
	
	void clearAllStockTrades();
	
}

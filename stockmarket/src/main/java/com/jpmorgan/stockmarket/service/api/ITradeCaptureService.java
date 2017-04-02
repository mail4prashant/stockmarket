package com.jpmorgan.stockmarket.service.api;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.dto.Trade;

/**
 * This service provides trade captures methods.  
 */
public interface ITradeCaptureService {

	/**
	 * Record buy side trade.
	 *
	 * @param stock the stock
	 * @param noOfShares the no of shares
	 * @param priceInPennies the price in pennies
	 * @return the trade
	 */
	Trade recordBuySideTrade(Stock stock, long noOfShares, double priceInPennies);
	
	/**
	 * Record sell side trade.
	 *
	 * @param stock the stock
	 * @param noOfShares the no of shares
	 * @param priceInPennies the price in pennies
	 * @return the trade
	 */
	Trade recordSellSideTrade(Stock stock, long noOfShares, double priceInPennies);
	
	/**
	 * Gets the trades.
	 *
	 * @param stock the stock
	 * @return the trades
	 */
	List<Trade> getTrades(Stock stock);

	/**
	 * Gets the recent trades.
	 *
	 * @param stock the stock
	 * @param time the time
	 * @param unit the unit
	 * @return the recent trades
	 */
	List<Trade> getRecentTrades(Stock stock, long time, ChronoUnit unit);
	
	/**
	 * Clear all stock trades.
	 */
	void clearAllStockTrades();
	
}

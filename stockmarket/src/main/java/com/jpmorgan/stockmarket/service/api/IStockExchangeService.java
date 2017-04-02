package com.jpmorgan.stockmarket.service.api;

/**
 * This service provides various methods to calculate important numbers in stock markets.  
 */
public interface IStockExchangeService {

	/**
	 * Gets the dividend yield for given stock and price.
	 *
	 * @param stockSymbol the stock symbol
	 * @param priceInPennies the price in pennies
	 * @return the dividend yield
	 */
	double getDividendYield(String stockSymbol, double priceInPennies);
	
	/**
	 * Gets the pe ratio for given stock and price.
	 *
	 * @param stockSymbol the stock symbol
	 * @param price the price in pennies
	 * @return the PE ratio
	 */
	double getPeRatio(String stockSymbol, double priceInPennies);
	
	/**
	 * Gets the volume weighted stock price for given stock.
	 *
	 * @param stockSymbol the stock symbol
	 * @return the volume weighted stock price
	 */
	double getVolumeWeightedStockPrice(String stockSymbol);
	
	/**
	 * Gets the all share index based on for all stocks.
	 *
	 * @return the all share index
	 */
	public double getAllShareIndex();
	
}

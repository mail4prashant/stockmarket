package com.jpmorgan.stockmarket.service.api;

import java.util.Collection;

import com.jpmorgan.stockmarket.dto.Stock;

/**
 * This service provides various methods related to maintain reference data in stock markets.  
 */
public interface IStockRefDataService {

	/**
	 * Adds the common stock.
	 *
	 * @param symbol the symbol
	 * @param lastDividendInPennies the last dividend in pennies
	 * @param parValueInPennies the par value in pennies
	 * @return the stock
	 */
	Stock addCommonStock(String symbol, double lastDividendInPennies, double parValueInPennies);

	/**
	 * Adds the preferred stock.
	 *
	 * @param symbol the symbol
	 * @param lastDividendInPennies the last dividend in pennies
	 * @param fixedDividendInPercentage the fixed dividend in percentage
	 * @param parValueInPennies the par value in pennies
	 * @return the stock
	 */
	Stock addPreferredStock(String symbol, double lastDividendInPennies, double fixedDividendInPercentage,
			double parValueInPennies);

	/**
	 * Gets the stock.
	 *
	 * @param stockSymbol the stock symbol
	 * @return the stock
	 */
	Stock getStock(String stockSymbol);

	/**
	 * Gets all stocks.
	 *
	 * @return the all stocks
	 */
	Collection<Stock> getAllStocks();

	/**
	 * Removes the stock.
	 *
	 * @param stockSymbol the stock symbol
	 */
	void removeStock(String stockSymbol);

	/**
	 * Removes the all stocks.
	 */
	void removeAllStocks();
}

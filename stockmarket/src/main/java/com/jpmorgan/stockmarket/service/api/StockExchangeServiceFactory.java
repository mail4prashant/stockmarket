package com.jpmorgan.stockmarket.service.api;

import com.jpmorgan.stockmarket.service.impl.StockExchangeAnalyticsService;
import com.jpmorgan.stockmarket.service.impl.StockRefDataService;
import com.jpmorgan.stockmarket.service.impl.TradeCaptureService;

/**
 * A factory for creating different StockExchangeServices.
 */
public class StockExchangeServiceFactory {

	/**
	 * Gets the stock reference data service.
	 *
	 * @return the stock reference data service
	 */
	public static IStockRefDataService getStockRefDataService() {
		return StockRefDataService.getInstance();
	}

	/**
	 * Gets the trade capture service.
	 *
	 * @return the trade capture service
	 */
	public static ITradeCaptureService getTradeCaptureService() {
		return TradeCaptureService.getInstance();
	}

	/**
	 * Gets the stock exchange analytics service.
	 *
	 * @return the stock exchange analytics service
	 */
	public static IStockExchangeAnalyticsService getStockExchangeAnalyticsService() {
		return new StockExchangeAnalyticsService(StockRefDataService.getInstance(), TradeCaptureService.getInstance());
	}
	
}

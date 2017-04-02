package com.jpmorgan.stockmarket.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;

import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.dto.Trade;
import com.jpmorgan.stockmarket.service.api.IStockExchangeService;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.service.api.ITradeCaptureService;

/**
 * Simple Implementation of {@link IStockExchangeService}.
 */
public class StockExchangeService implements IStockExchangeService {

	private static final long TIME_IN_MINUTES_TO_CALC_VOL_WGHTED_STOCK_PRICE = 5;

	private final IStockRefDataService stockRefDataService;
	private final ITradeCaptureService tradeCaptureService;

	public StockExchangeService(IStockRefDataService stockRefDataService, ITradeCaptureService tradeCaptureService) {
		this.stockRefDataService = stockRefDataService;
		this.tradeCaptureService = tradeCaptureService;
	}

	@Override
	public double getDividendYield(String stockSymbol, double priceInPennies) {
		return stockRefDataService.getStock(stockSymbol).getDividendYield(priceInPennies);
	}

	@Override
	public double getPeRatio(String stockSymbol, double priceInPennies) {
		return stockRefDataService.getStock(stockSymbol).getPeRatio(priceInPennies);
	}

	@Override
	public double getVolumeWeightedStockPrice(String stockSymbol) {
		return getVolumeWeightedStockPrice(stockRefDataService.getStock(stockSymbol));
	}

	@Override
	public double getAllShareIndex() {
		Collection<Stock> stocks = stockRefDataService.getAllStocks();
		double product = stocks.stream().mapToDouble(this::getVolumeWeightedStockPrice).reduce(1, (a, b) -> a * b);
		return Math.pow(product, 1.0 / stocks.size());
	}

	private double sumTrades(List<Trade> trades, ToDoubleFunction<Trade> function) {
		return trades.stream().mapToDouble(function).sum();
	}

	private double getVolumeWeightedStockPrice(Stock stock) {
		List<Trade> trades = tradeCaptureService.getRecentTrades(stock, TIME_IN_MINUTES_TO_CALC_VOL_WGHTED_STOCK_PRICE,
				ChronoUnit.MINUTES);

		ToDoubleFunction<Trade> priceQuantityMutiplicationFunction = trade -> trade.getPriceInPennies()
				* trade.getQuantity();
		ToDoubleFunction<Trade> quantityFunction = trade -> trade.getQuantity();
		return sumTrades(trades, priceQuantityMutiplicationFunction) / sumTrades(trades, quantityFunction);
	}

}

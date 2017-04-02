package com.jpmorgan.stockmarket.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.type.StockType;

public class StockRefDataService implements IStockRefDataService {

	private static class SingletonHolder {
		private static final StockRefDataService INSTANCE = new StockRefDataService();
	}

	private final Map<String, Stock> stockBySymbolMap;

	private StockRefDataService() {
		stockBySymbolMap = new ConcurrentHashMap<String, Stock>();
	}

	@Override
	public Stock addCommonStock(String symbol, double lastDividendInPennies, double parValueInPennies) {
		Stock stock = new Stock(symbol, StockType.COMMON, lastDividendInPennies, Double.NaN, parValueInPennies);
		return addStock(stock);
	}

	@Override
	public Stock addPreferredStock(String symbol, double lastDividendInPennies, double fixedDividendInPercentage,
			double parValueInPennies) {
		Stock stock = new Stock(symbol, StockType.PREFERRED, lastDividendInPennies, fixedDividendInPercentage,
				parValueInPennies);
		return addStock(stock);
	}

	private Stock addStock(Stock stock) {
		stockBySymbolMap.putIfAbsent(stock.getSymbol(), stock);
		return stock;
	}
	
//	@Override
//	public void addAllStocks(Collection<Stock> stocks) {
//		stocks.forEach(this::addStock);
//	}

	@Override
	public Stock getStock(String stockSymbol) {
		Stock stock = stockBySymbolMap.get(stockSymbol);
		if (stock == null) {
			throw new IllegalArgumentException(String.format("Stock [Symbol = %s] does not exist.", stockSymbol));
		}
		return stock;
	}

	@Override
	public Collection<Stock> getAllStocks() {
		return new ArrayList<>(stockBySymbolMap.values());
	}

	@Override
	public void removeStock(String stockSymbol) {
		stockBySymbolMap.remove(stockSymbol);
	}

	@Override
	public void removeAllStocks() {
		stockBySymbolMap.clear();
	}

	public static StockRefDataService getInstance() {
		return SingletonHolder.INSTANCE;
	}

}

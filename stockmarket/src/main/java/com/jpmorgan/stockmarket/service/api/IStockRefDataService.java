package com.jpmorgan.stockmarket.service.api;

import java.util.Collection;

import com.jpmorgan.stockmarket.dto.Stock;

public interface IStockRefDataService {

	Stock addCommonStock(String symbol, double lastDividendInPennies, double parValueInPennies);

	Stock addPreferredStock(String symbol, double lastDividendInPennies, double fixedDividendInPercentage,
			double parValueInPennies);

	Stock getStock(String stockSymbol);

	Collection<Stock> getAllStocks();

	void removeStock(String stockSymbol);

	void removeAllStocks();
}

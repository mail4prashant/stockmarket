package com.jpmorgan.stockmarket.service.api;

public interface IStockExchangeService {

	double getDividentYield(String stockSymbol, double price);
	
	double getPeRatio(String stockSymbol, double price);
	
	double getVolumeWeightedStockPrice(String stockSymbol);
	
	public double getAllShareIndex();
	
}

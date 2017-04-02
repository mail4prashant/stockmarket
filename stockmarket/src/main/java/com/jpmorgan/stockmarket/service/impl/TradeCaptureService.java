package com.jpmorgan.stockmarket.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.dto.Trade;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.service.api.ITradeCaptureService;
import com.jpmorgan.stockmarket.type.BuySellIndicatorType;

/**
 * InMemory based implementation of {@link ITradeCaptureService}.
 */
public class TradeCaptureService implements ITradeCaptureService {

	private static final Comparator<Trade> TRADE_CREATION_TIME_COMPARATOR = (t1, t2) -> t1.getCreatedOn().compareTo(t2.getCreatedOn());
	
	private static class SingletonHolder { 
        private static final TradeCaptureService INSTANCE = new TradeCaptureService();
    }

	private final Map<Stock, List<Trade>> tradesByStockMap;

	public TradeCaptureService() {
		this.tradesByStockMap = new ConcurrentHashMap<>();
	}

	@Override
	public Trade recordBuySideTrade(Stock stock, long noOfShares, double priceInPennies) {
		Trade trade = new Trade(stock, BuySellIndicatorType.BUY, LocalDateTime.now(), noOfShares, priceInPennies);
		return addTrade(trade);
	}

	@Override
	public Trade recordSellSideTrade(Stock stock, long noOfShares, double priceInPennies) {
		Trade trade = new Trade(stock, BuySellIndicatorType.SELL, LocalDateTime.now(), noOfShares, priceInPennies);
		return addTrade(trade);
	}

	private Trade addTrade(Trade trade) {
		tradesByStockMap.computeIfAbsent(trade.getStock(), key -> new ArrayList<>()).add(trade);
		return trade;
	}

	@Override
	public List<Trade> getTrades(Stock stock) {
		List<Trade> trades = tradesByStockMap.get(stock);
		if(trades != null && !trades.isEmpty()) {
			return new ArrayList<>(trades);
		}
		return Collections.emptyList();
	}

	@Override
	public List<Trade> getRecentTrades(Stock stock, long time, ChronoUnit unit) {
		LocalDateTime cutoffTime = LocalDateTime.now().minus(time, unit);
		List<Trade> trades = getTrades(stock);
		Collections.sort(trades, TRADE_CREATION_TIME_COMPARATOR);
		return trades.stream().filter(trade -> trade.getCreatedOn().isAfter(cutoffTime))
				.collect(Collectors.toList());
	}
	
	@Override
	public void clearAllStockTrades() {
		tradesByStockMap.clear();
	}

	public static TradeCaptureService getInstance() {
		return SingletonHolder.INSTANCE;
	}

}

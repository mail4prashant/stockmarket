package com.jpmorgan.stockmarket.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jpmorgan.stockmarket.common.DataProviderItem;
import com.jpmorgan.stockmarket.common.DataProviderItemBase;
import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.dto.Trade;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.service.api.ITradeCaptureService;
import com.jpmorgan.stockmarket.service.factory.StockExchangeServiceFactory;


/**
 * The Class tests {@link ITradeCaptureService}.
 */
public class TradeCaptureServiceTest extends AbstractStockMarketTest {
	
	private static final String RECORD_TRADE_DATA_PROVIDER = "TradeCaptureServiceTest.testRecordTrade";
	private static final String GET_RECENT_TRADE_DATA_PROVIDER = "TradeCaptureServiceTest.testRecentTrades";
	
	private final IStockRefDataService refDataService = StockExchangeServiceFactory.getStockRefDataService();
	private final ITradeCaptureService tradeCaptureService = StockExchangeServiceFactory.getTradeCaptureService();
	

	@BeforeMethod
	public void clearAllTrades() {
		tradeCaptureService.clearAllStockTrades();
	}

	/**
	 * Test {@link ITradeCaptureService#recordBuySideTrade(Stock, long, double)}.
	 *
	 * @param testData the test data
	 */
	@Test(dataProvider = RECORD_TRADE_DATA_PROVIDER)
	public void testRecordBuySideTrade(TradeTestData testData) {
		Stock stock = refDataService.addCommonStock(testData.stock.symbol, testData.stock.lastDividendInPennies, testData.stock.parValueInPennies);
		
		tradeCaptureService.recordBuySideTrade(stock, testData.trade.noOfShares, testData.trade.priceInPennies);
		assertThat("Received trade size is not equal to recorded trade size.", tradeCaptureService.getTrades(stock).size() == 1);
	}

	/**
	 * Test {@link ITradeCaptureService#recordSellSideTrade(Stock, long, double)}.
	 *
	 * @param testData the test data
	 */
	@Test(dataProvider = RECORD_TRADE_DATA_PROVIDER)
	public void testRecordSellSideTrade(TradeTestData testData) {
		Stock stock = refDataService.addCommonStock(testData.stock.symbol, testData.stock.lastDividendInPennies, testData.stock.parValueInPennies);

		tradeCaptureService.recordSellSideTrade(stock, testData.trade.noOfShares, testData.trade.priceInPennies);
		
		assertThat("Received trade size is not equal to recorded trade size.", tradeCaptureService.getTrades(stock).size() == 1);
	}
	
	/**
	 * Test {@link ITradeCaptureService#getRecentTrades(Stock, long, ChronoUnit)}.
	 *
	 * @param testData the test data
	 */
	@Test(dataProvider = GET_RECENT_TRADE_DATA_PROVIDER)
	public void testGetRecentTrades(TradeTestData testData) {
		Stock stock = recordTrades(testData);
		
		List<Trade> trades = tradeCaptureService.getRecentTrades(stock, testData.cutoffTime, testData.timeUnit);

		assertThat("Received trade size is not equal to recorded trade size.", trades.size() == testData.expectedTradeCount);
	}

	
	private Stock recordTrades(TradeTestData testData) {
		Stock stock = refDataService.addCommonStock(testData.stock.symbol, testData.stock.lastDividendInPennies, testData.stock.parValueInPennies);
		testData.trades.forEach(trade -> {
			if(trade.timeToSleep > 0) {
				try {
					Thread.sleep(trade.timeToSleep*1000);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			} else{
				tradeCaptureService.recordBuySideTrade(stock, trade.noOfShares, trade.priceInPennies);
			}
		});
		return stock;
	}

	@DataProvider(name = RECORD_TRADE_DATA_PROVIDER)
    private static Object[][] getRecordTradeTestData() {
        return DataProviderItemBase.transformData(
                new TradeTestData("General Case")
                .withTrade(10, 100)
        );
    }

	@DataProvider(name = GET_RECENT_TRADE_DATA_PROVIDER)
    private static Object[][] getRecentTradeTestData() {
        return DataProviderItemBase.transformData(
                new TradeTestData("case 1")
                .withTrade(10, 100)
                .withTrade(20, 200)
                .withSleepInSeconds(2)
                .withTrade(30, 300)
                .withTrade(40, 400)
                .withTrade(50, 500)
                .withSleepInSeconds(2)
                .withTrade(60, 600)
                .withTrade(70, 700)
                .withCutoff(1, ChronoUnit.SECONDS)
                .withExpectedTradeCount(2),
                
                new TradeTestData("case 2")
                .withTrade(10, 100)
                .withTrade(20, 200)
                .withSleepInSeconds(2)
                .withTrade(30, 300)
                .withTrade(40, 400)
                .withTrade(50, 500)
                .withSleepInSeconds(2)
                .withTrade(60, 600)
                .withTrade(70, 700)
                .withCutoff(3, ChronoUnit.SECONDS)
                .withExpectedTradeCount(5),
                
                new TradeTestData("case 3")
                .withTrade(10, 100)
                .withTrade(20, 200)
                .withSleepInSeconds(2)
                .withTrade(30, 300)
                .withTrade(40, 400)
                .withTrade(50, 500)
                .withSleepInSeconds(2)
                .withTrade(60, 600)
                .withTrade(70, 700)
                .withCutoff(5, ChronoUnit.SECONDS)
                .withExpectedTradeCount(7)
                
                
        );
    }
	
  
	static class TradeTestData extends DataProviderItem {

		private TestStock stock;
		private TestTrade trade;
		private Collection<TestTrade> trades;
		private long expectedTradeCount;
		private long cutoffTime;
		private ChronoUnit timeUnit;

		public TradeTestData(String caseDescription) {
			super(caseDescription);
			stock = new TestStock("ABC", 0, Double.NaN, 100);
			trades = new ArrayList<>();
		}

		public TradeTestData withTrade(long noOfShares, double priceInPennies) {
			trade = new TestTrade(noOfShares, priceInPennies);
			trades.add(trade);
			return this;
		}

		public TradeTestData withSleepInSeconds(long timeInSeconds) {
			trade = new TestTrade(0, 0, timeInSeconds);
			trades.add(trade);
			return this;
		}

		public TradeTestData withCutoff(long time, ChronoUnit unit) {
			this.cutoffTime = time;
			this.timeUnit = unit;
			return this;
		}

		public TradeTestData withExpectedTradeCount(long expectedTradeCount) {
			this.expectedTradeCount = expectedTradeCount;
			return this;
		}
		
	}
	
	static class TestTrade {

		private long noOfShares;
		private double priceInPennies;
		private long timeToSleep;

		public TestTrade(long noOfShares, double priceInPennies) {
			this(noOfShares, priceInPennies, 0L);
		}

		public TestTrade(long noOfShares, double priceInPennies, long timeToSleep) {
			this.noOfShares = noOfShares;
			this.priceInPennies = priceInPennies;
			this.timeToSleep = timeToSleep;
		}
	}
	
}

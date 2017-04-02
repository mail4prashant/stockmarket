package com.jpmorgan.stockmarket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jpmorgan.stockmarket.common.DataProviderItem;
import com.jpmorgan.stockmarket.common.DataProviderItemBase;
import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.service.api.IStockExchangeService;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.service.api.ITradeCaptureService;


public class StockExchangeServiceTest extends AbstractStockMarketTest {
	
	private static final String GET_DIVIDENT_YIELD_DATA_PROVIDER = "TradeCaptureServiceTest.testGetDividentYield";
	private static final String GET_PE_RATIO_DATA_PROVIDER = "TradeCaptureServiceTest.testGetPeRatio";
	private static final String GET_VOL_WGHT_STOCK_PRICE_DATA_PROVIDER = "TradeCaptureServiceTest.testVolumeWeightedStockPrice";
	private static final String GET_ALL_SHARE_INDEX_DATA_PROVIDER = "TradeCaptureServiceTest.testGetAllShareIndex";
	
	private final IStockRefDataService stockRefDataService = StockRefDataService.getInstance();
	private final ITradeCaptureService tradeCaptureService = TradeCaptureService.getInstance();
	private final IStockExchangeService stockExchangeService = new StockExchangeService(stockRefDataService, tradeCaptureService);

	@BeforeMethod
	public void clearAllTrades() {
		tradeCaptureService.clearAllStockTrades();
		stockRefDataService.addCommonStock("TEA", 0, 100);
		stockRefDataService.addCommonStock("POP", 8, 100);
		stockRefDataService.addCommonStock("ALE", 23, 60);
		stockRefDataService.addPreferredStock("GIN", 8, 2, 100);
		stockRefDataService.addCommonStock("JOE", 13, 250);
	}

	@Test(dataProvider = GET_DIVIDENT_YIELD_DATA_PROVIDER)
	public void testGetDividentYield(DividentTestData testData) {
		double yield = stockExchangeService.getDividentYield(testData.symbol, testData.price);
		assertThat("calculated yield is not same as expected yield", equal(yield, testData.expectedDividentYield));
	}

	@Test(dataProvider = GET_PE_RATIO_DATA_PROVIDER)
	public void testGetPeRatio(PeRatioTestData testData) {
		double peRatio = stockExchangeService.getPeRatio(testData.symbol, testData.price);
		assertThat("calculated PERatio is not same as expected PE Ratio", equal(peRatio, testData.expectedRatio));
	}

	@Test(dataProvider = GET_VOL_WGHT_STOCK_PRICE_DATA_PROVIDER)
	public void testVolumeWeightedStockPrice(VolumeWeightedStockPriceTestData testData) {
		testData.trades.forEach(trade -> {
			Stock stock = stockRefDataService.getStock(trade.symbol);
			tradeCaptureService.recordBuySideTrade(stock, trade.noOfShares, trade.priceInPennies);
		});

		double volumeWeightedStockPrice = stockExchangeService.getVolumeWeightedStockPrice(testData.symbol);

		assertThat("calculated VolumeWeightedStockPrice is not same as expected VolumeWeightedStockPrice",
				equal(volumeWeightedStockPrice, testData.expectedVolumeWeightedStockPrice));
	}

	@Test(dataProvider = GET_ALL_SHARE_INDEX_DATA_PROVIDER)
	public void testGetAllShareIndex(AllShareIndexTestData testData) {
		testData.trades.forEach(trade -> {
			Stock stock = stockRefDataService.getStock(trade.symbol);
			tradeCaptureService.recordBuySideTrade(stock, trade.noOfShares, trade.priceInPennies);
		});

		double volumeWeightedStockPrice = stockExchangeService.getAllShareIndex();

		assertThat("calculated AllShare Index is not same as expected AllShare Index",
				equal(volumeWeightedStockPrice, testData.expectedAllShareIndex));
	}

	@DataProvider(name = GET_DIVIDENT_YIELD_DATA_PROVIDER)
    private static Object[][] getDividentYieldTestData() {
        return DataProviderItemBase.transformData(
                new DividentTestData("case 1")
                .withStock("TEA", 100, 0),
                new DividentTestData("case 2")
                .withStock("POP", 100, 0.08),
                new DividentTestData("case 3")
                .withStock("ALE", 100, 0.23),
                new DividentTestData("case 4")
                .withStock("GIN", 100, 2),
                new DividentTestData("case 5")
                .withStock("JOE", 100, .13)
                
        );
    }

	@DataProvider(name = GET_PE_RATIO_DATA_PROVIDER)
    private static Object[][] getPeRatioTestData() {
        return DataProviderItemBase.transformData(
                new PeRatioTestData("case 1")
                .withStock("TEA", 100, Double.POSITIVE_INFINITY),
                new PeRatioTestData("case 2")
                .withStock("POP", 100, 12.5),
                new PeRatioTestData("case 3")
                .withStock("ALE", 100, 4.347826),
                new PeRatioTestData("case 4")
                .withStock("GIN", 100, 0.5),
                new PeRatioTestData("case 5")
                .withStock("JOE", 100, 7.69230)
                
        );
    }

	@DataProvider(name = GET_VOL_WGHT_STOCK_PRICE_DATA_PROVIDER)
    private static Object[][] getVolumeWeightedStockPriceTestData() {
        return DataProviderItemBase.transformData(
                new VolumeWeightedStockPriceTestData("case 1")
                .withTrade("TEA", 100, 10)
                .withTrade("TEA", 200, 20)
                .withTrade("TEA", 300, 30)
                .withTrade("TEA", 400, 40)
                .withTrade("GIN", 400, 40)
                .withTrade("GIN", 500, 50)
                .withExpectedVolumeWeightedStockPrice("TEA", 300),
                
                new VolumeWeightedStockPriceTestData("case 2")
                .withTrade("TEA", 100, 10)
                .withTrade("TEA", 200, 20)
                .withTrade("TEA", 300, 30)
                .withTrade("TEA", 400, 40)
                .withTrade("GIN", 400, 40)
                .withTrade("GIN", 500, 50)
                .withExpectedVolumeWeightedStockPrice("GIN", 455.5555)
        );
    }

	@DataProvider(name = GET_ALL_SHARE_INDEX_DATA_PROVIDER)
    private static Object[][] getAllShareTestData() {
        return DataProviderItemBase.transformData(
                new AllShareIndexTestData("case 1")
                .withTrade("TEA", 100, 10)
                .withTrade("TEA", 200, 20)
                .withTrade("TEA", 300, 30)
                .withTrade("TEA", 400, 40)
                .withTrade("POP", 500, 50)
                .withTrade("POP", 600, 60)
                .withTrade("ALE", 700, 70)
                .withTrade("ALE", 800, 80)
                .withTrade("JOE", 900, 90)
                .withTrade("JOE", 100, 10)
                .withTrade("GIN", 200, 20)
                .withTrade("GIN", 300, 30)
                .withExpectedAllShareIndex(484.5811)
        );
    }
	
	static class DividentTestData extends DataProviderItem {
		private String symbol;
		private double price;
		private double expectedDividentYield;
		
		public DividentTestData(String caseDescription) {
			super(caseDescription);
		}

		public DividentTestData withStock(String symbol, double price, double expectedDividentYield) {
			this.symbol = symbol;
			this.price = price;
			this.expectedDividentYield = expectedDividentYield;
			return this;
		}
	}

	static class PeRatioTestData extends DataProviderItem {
		private String symbol;
		private double price;
		private double expectedRatio;
		
		public PeRatioTestData(String caseDescription) {
			super(caseDescription);
		}

		public PeRatioTestData withStock(String symbol, double price, double expectedRatio) {
			this.symbol = symbol;
			this.price = price;
			this.expectedRatio = expectedRatio;
			return this;
		}
	}
	
	static class VolumeWeightedStockPriceTestData extends DataProviderItem {
		private String symbol;
		private List<TestTrade> trades;
		private double expectedVolumeWeightedStockPrice;

		public VolumeWeightedStockPriceTestData(String caseDescription) {
			super(caseDescription);
			this.trades = new ArrayList<>();
		}

		public VolumeWeightedStockPriceTestData withTrade(String symbol, double price, long quantity) {
			this.trades.add(new TestTrade(symbol, quantity, price));
			return this;
		}

		public VolumeWeightedStockPriceTestData withExpectedVolumeWeightedStockPrice(String symbol,
				double expectedVolumeWeightedStockPrice) {
			this.symbol = symbol;
			this.expectedVolumeWeightedStockPrice = expectedVolumeWeightedStockPrice;
			return this;
		}
	}

	static class AllShareIndexTestData extends DataProviderItem {
		private List<TestTrade> trades;
		private double expectedAllShareIndex;

		public AllShareIndexTestData(String caseDescription) {
			super(caseDescription);
			this.trades = new ArrayList<>();
		}

		public AllShareIndexTestData withTrade(String symbol, double price, long quantity) {
			this.trades.add(new TestTrade(symbol, quantity, price));
			return this;
		}

		public AllShareIndexTestData withExpectedAllShareIndex(double expectedAllShareIndex) {
			this.expectedAllShareIndex = expectedAllShareIndex;
			return this;
		}
	}
}

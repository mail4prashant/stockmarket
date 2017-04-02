package com.jpmorgan.stockmarket.service.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jpmorgan.stockmarket.common.DataProviderItem;
import com.jpmorgan.stockmarket.common.DataProviderItemBase;
import com.jpmorgan.stockmarket.dto.Stock;
import com.jpmorgan.stockmarket.service.api.IStockRefDataService;
import com.jpmorgan.stockmarket.type.StockType;


public class StockRefDataServiceTest extends AbstractStockMarketTest {
	
	private static final String ADD_COMMON_STOCK_DATA_PROVIDER = "StockRefDataServiceTest.testAddCommonStock";
	private static final String ADD_PREFERRED_STOCK_DATA_PROVIDER = "StockRefDataServiceTest.testAddPreferredStock";
	
	private final IStockRefDataService refDataService = StockRefDataService.getInstance();

	@BeforeMethod
	public void clearAllStocks() {
		refDataService.removeAllStocks();
	}

	@Test(dataProvider = ADD_COMMON_STOCK_DATA_PROVIDER)
	public void testAddCommonStock(StockTestData testData) {
		Stock stock = refDataService.addCommonStock(testData.stock.symbol, testData.stock.lastDividendInPennies, testData.stock.parValueInPennies);
		
		assertThat("Received stock is null.", stock != null);
		assertThat("Received stock symbol is not matching.", stock.getSymbol().equals(testData.stock.symbol));
		assertThat("Received stock type is not matching.", stock.getType() == StockType.COMMON);
		assertThat("Received stock last dividend is not matching.", equal(stock.getLastDividendInPennies(), testData.stock.lastDividendInPennies));
		assertThat("Received stock fixed dividend is not matching.", equal(stock.getFixedDividendInPercentage(), Double.NaN));
		assertThat("Received stock par value is not matching.", equal(stock.getParValueInPennies(), testData.stock.parValueInPennies));
	}

	@Test(dataProvider = ADD_PREFERRED_STOCK_DATA_PROVIDER)
	public void testAddPreferredStock(StockTestData testData) {
		Stock stock = refDataService.addPreferredStock(testData.stock.symbol, testData.stock.lastDividendInPennies, testData.stock.fixedDividendInPercentage, testData.stock.parValueInPennies);
		
		assertThat("Received stock is null.", stock != null);
		assertThat("Received stock symbol is not matching.", stock.getSymbol().equals(testData.stock.symbol));
		assertThat("Received stock type is not matching.", stock.getType() == StockType.PREFERRED);
		assertThat("Received stock last dividend is not matching.", equal(stock.getLastDividendInPennies(), testData.stock.lastDividendInPennies));
		assertThat("Received stock fixed dividend is not matching.", equal(stock.getFixedDividendInPercentage(), testData.stock.fixedDividendInPercentage));
		assertThat("Received stock par value is not matching.", equal(stock.getParValueInPennies(), testData.stock.parValueInPennies));
	}

    @DataProvider(name = ADD_COMMON_STOCK_DATA_PROVIDER)
    private static Object[][] getAddCommonStockTestData() {
        return DataProviderItemBase.transformData(
                new StockTestData("case 1")
                .withStock("TEA", 0, 2, 100)
        );
    }

    @DataProvider(name = ADD_PREFERRED_STOCK_DATA_PROVIDER)
    private static Object[][] getAddPrefferedStockTestData() {
        return DataProviderItemBase.transformData(
                new StockTestData("case 1")
                .withStock("TEA", 8, 2, 100)
        );
    }
    
	public static class StockTestData extends DataProviderItem {

		private TestStock stock;

		public StockTestData(String caseDescription) {
			super(caseDescription);
		}

		public StockTestData withStock(String symbol, double lastDividendInPennies,
				double fixedDividendInPercentage, double parValueInPennies) {
			stock = new TestStock(symbol, lastDividendInPennies, fixedDividendInPercentage, parValueInPennies);
			return this;
		}
	}
	
}

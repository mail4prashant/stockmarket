package com.jpmorgan.stockmarket.service.impl;

public abstract class AbstractStockMarketTest {

	public void assertThat(String reason, boolean assertion) {
		if (!assertion) {
			throw new AssertionError(reason);
		}
	}

	public static boolean equal(double d1, double d2) {
		return Double.valueOf(d1).equals(Double.valueOf(d2)) || Math.abs(d1 - d2) < 1e-4;
	}

	static class TestStock {

		String symbol;
		double lastDividendInPennies;
		double fixedDividendInPercentage;
		double parValueInPennies;

		public TestStock(String symbol, double lastDividendInPennies, double fixedDividendInPercentage,
				double parValueInPennies) {
			this.symbol = symbol;
			this.lastDividendInPennies = lastDividendInPennies;
			this.fixedDividendInPercentage = fixedDividendInPercentage;
			this.parValueInPennies = parValueInPennies;
		}

	}

	static class TestTrade {

		String symbol;
		long noOfShares;
		double priceInPennies;

		public TestTrade(String symbol, long noOfShares, double priceInPennies) {
			this.symbol = symbol;
			this.noOfShares = noOfShares;
			this.priceInPennies = priceInPennies;
		}

	}

}

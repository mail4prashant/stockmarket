package com.jpmorgan.stockmarket.dto;

import com.jpmorgan.stockmarket.type.StockType;

/**
 * This class represent a Stock in stock market exchange.
 */
public class Stock {

	private final String symbol;

	private final StockType type;

	private final double lastDividendInPennies;

	private final double fixedDividendInPercentage;

	private final double parValueInPennies;

	/**
	 * Instantiates a new stock.
	 *
	 * @param symbol the symbol
	 * @param type the type
	 * @param lastDividendInPennies the last dividend in pennies
	 * @param fixedDividendInPercentage the fixed dividend in percentage
	 * @param parValueInPennies the par value in pennies
	 */
	public Stock(String symbol, StockType type, double lastDividendInPennies, double fixedDividendInPercentage,
			double parValueInPennies) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividendInPennies = lastDividendInPennies;
		this.fixedDividendInPercentage = fixedDividendInPercentage;
		this.parValueInPennies = parValueInPennies;
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * Gets the last dividend in pennies.
	 *
	 * @return the last dividend in pennies
	 */
	public double getLastDividendInPennies() {
		return lastDividendInPennies;
	}

	/**
	 * Gets the fixed dividend in percentage.
	 *
	 * @return the fixed dividend in percentage
	 */
	public double getFixedDividendInPercentage() {
		return fixedDividendInPercentage;
	}

	/**
	 * Gets the par value in pennies.
	 *
	 * @return the par value in pennies
	 */
	public double getParValueInPennies() {
		return parValueInPennies;
	}

	/**
	 * Gets the dividend.
	 *
	 * @return the dividend
	 */
	public double getDividend() {
		if (type == StockType.PREFERRED) {
			return fixedDividendInPercentage * parValueInPennies;
		} else if (type == StockType.COMMON) {
			return lastDividendInPennies;
		}
		return Double.NaN;
	}

	/**
	 * Gets the dividend yield.
	 *
	 * @param price the price
	 * @return the dividend yield
	 */
	public double getDividendYield(double price) {
		return getDividend() / price;
	}

	/**
	 * Gets the pe ratio.
	 *
	 * @param price the price
	 * @return the pe ratio
	 */
	public double getPeRatio(double price) {
		return price / getDividend();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + "]";
	}
	
}

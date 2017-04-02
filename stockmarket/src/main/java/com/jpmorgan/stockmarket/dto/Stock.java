package com.jpmorgan.stockmarket.dto;

import com.jpmorgan.stockmarket.type.StockType;

public class Stock {

	private final String symbol;

	private final StockType type;

	private final double lastDividendInPennies;

	private final double fixedDividendInPercentage;

	private final double parValueInPennies;

	public Stock(String symbol, StockType type, double lastDividendInPennies, double fixedDividendInPercentage,
			double parValueInPennies) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividendInPennies = lastDividendInPennies;
		this.fixedDividendInPercentage = fixedDividendInPercentage;
		this.parValueInPennies = parValueInPennies;
	}

	public String getSymbol() {
		return symbol;
	}

	public StockType getType() {
		return type;
	}

	public double getLastDividendInPennies() {
		return lastDividendInPennies;
	}

	public double getFixedDividendInPercentage() {
		return fixedDividendInPercentage;
	}

	public double getParValueInPennies() {
		return parValueInPennies;
	}

	public double getDivident() {
		if (type == StockType.PREFERRED) {
			return fixedDividendInPercentage * parValueInPennies;
		} else if (type == StockType.COMMON) {
			return lastDividendInPennies;
		}
		return Double.NaN;
	}

	public double getDividentYield(double price) {
		return getDivident() / price;
	}

	public double getPeRatio(double price) {
		return price / getDivident();
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

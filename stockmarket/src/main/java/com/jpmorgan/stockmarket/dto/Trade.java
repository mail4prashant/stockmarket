package com.jpmorgan.stockmarket.dto;

import java.time.LocalDateTime;

import com.jpmorgan.stockmarket.type.BuySellIndicatorType;

public class Trade {
	
	private final Stock stock;
	
	private final BuySellIndicatorType buySellIndicator;
	
	private final LocalDateTime createdOn;
	
	private final long quantity;
	
	private final double priceInPennies;

	public Trade(Stock stock, BuySellIndicatorType buySellIndicator, LocalDateTime createdOn, long quantity,
			double priceInPennies) {
		this.stock = stock;
		this.buySellIndicator = buySellIndicator;
		this.createdOn = createdOn;
		this.quantity = quantity;
		this.priceInPennies = priceInPennies;
	}

	public Stock getStock() {
		return stock;
	}

	public BuySellIndicatorType getBuySellIndicator() {
		return buySellIndicator;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public long getQuantity() {
		return quantity;
	}

	public double getPriceInPennies() {
		return priceInPennies;
	}

	@Override
	public String toString() {
		return "Trade [stock=" + stock.getSymbol() + ", createdOn=" + createdOn + ", quantity=" + quantity + ", priceInPennies="
				+ priceInPennies + "]";
	}
	
	

}

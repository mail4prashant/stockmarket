package com.jpmorgan.stockmarket.dto;

import java.time.LocalDateTime;

import com.jpmorgan.stockmarket.type.BuySellIndicatorType;

/**
 * This class represent a trade in stock markets exchange.
 */
public class Trade {
	
	private final Stock stock;
	
	private final BuySellIndicatorType buySellIndicator;
	
	private final LocalDateTime createdOn;
	
	private final long quantity;
	
	private final double priceInPennies;

	/**
	 * Instantiates a new trade.
	 *
	 * @param stock the stock
	 * @param buySellIndicator the buy sell indicator
	 * @param createdOn the created on
	 * @param quantity the quantity
	 * @param priceInPennies the price in pennies
	 */
	public Trade(Stock stock, BuySellIndicatorType buySellIndicator, LocalDateTime createdOn, long quantity,
			double priceInPennies) {
		this.stock = stock;
		this.buySellIndicator = buySellIndicator;
		this.createdOn = createdOn;
		this.quantity = quantity;
		this.priceInPennies = priceInPennies;
	}

	/**
	 * Gets the stock.
	 *
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * Gets the buy sell indicator.
	 *
	 * @return the buy sell indicator
	 */
	public BuySellIndicatorType getBuySellIndicator() {
		return buySellIndicator;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 * Gets the price in pennies.
	 *
	 * @return the price in pennies
	 */
	public double getPriceInPennies() {
		return priceInPennies;
	}

	@Override
	public String toString() {
		return "Trade [stock=" + stock.getSymbol() + ", createdOn=" + createdOn + ", quantity=" + quantity + ", priceInPennies="
				+ priceInPennies + "]";
	}
	
	

}

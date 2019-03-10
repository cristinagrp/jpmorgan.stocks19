package com.cg.jp.stock;

import java.time.LocalDateTime;

public class Trade {
	enum BuySell {
		BUY,
		SELL;
	}
	private LocalDateTime timestamp;
	private long shareCount;
	private BuySell buySell;
	private double price;
	
	public Trade(long quantity, BuySell buySell, double price) {
		this.timestamp = LocalDateTime.now();
		this.shareCount = quantity;
		this.buySell = buySell;
		this.price = price;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public long getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public BuySell getBuySell() {
		return buySell;
	}

	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

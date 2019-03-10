package com.cg.jp.stock;

import java.time.LocalDateTime;

public class Trade {
	enum BuySell {
		BUY,
		SELL;
	}
	private LocalDateTime timestamp;
	private int shareCount;
	private BuySell buySell;
	private int price;
	
	public Trade(int shareCount, BuySell buySell, int price) {
		this.timestamp = LocalDateTime.now();
		this.shareCount = shareCount;
		this.buySell = buySell;
		this.price = price;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getShareCount() {
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

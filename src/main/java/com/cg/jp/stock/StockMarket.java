package com.cg.jp.stock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cg.jp.stock.exception.StockNotFoundException;

public class StockMarket {
	private static final int WINDOW_DURATION_MINS = 15;
	private Map<String, List<Trade>> tradeMap;
	private Duration windowDuration;
	
	public StockMarket() {
		this.tradeMap = new HashMap<>();
		this.windowDuration = Duration.ofMinutes(WINDOW_DURATION_MINS);
	}
	
	public void recordTrade(String stockSymbol, Trade trade) {
		if (!tradeMap.containsKey(stockSymbol)) {
			tradeMap.put(stockSymbol, new ArrayList<>());
		}
		tradeMap.get(stockSymbol).add(trade);
	}
	
	public double calculateVWSP(String stockSymbol) {
		return calculateVWSP(stockSymbol, LocalDateTime.now());
	}
	
	public double calculateVWSP(String stockSymbol, LocalDateTime now) {
		if (!tradeMap.containsKey(stockSymbol)) {
			throw new StockNotFoundException();
		}
		
		List<Trade> trades = tradeMap.get(stockSymbol);
		if (trades.isEmpty()) {
			return 0.0;
		}
		
		LocalDateTime windowStart = calculateWindowStart(now);
		int tradedValue = 0;
		int tradedQuantity = 0;
		
		for (int i = trades.size(); i>=0; i--) {
			Trade trade = trades.get(i);
			if (trade.getTimestamp().isAfter(windowStart)) {
				tradedValue += trade.getPrice() * trade.getShareCount();
				tradedQuantity += trade.getShareCount();
			} else {
				break;
			}
		}
		
		return (double) tradedValue / (double) tradedQuantity;
		
	}
	
	public double calculateGBCE() {
		double gbce = 1.0;
		LocalDateTime now = LocalDateTime.now();
		int positivePriceCount = 0;
		
		for (String stockSymbol : this.tradeMap.keySet()) {
			double stockPrice = calculateVWSP(stockSymbol, now);
			if (stockPrice > 0) {
				gbce *= stockPrice;
				positivePriceCount++;
			}
		}
		
		return Math.pow(gbce, 1.0 / positivePriceCount);
	}
	
	private LocalDateTime calculateWindowStart(LocalDateTime now) {
		return now.minus(this.windowDuration);
	}
}
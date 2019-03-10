package com.cg.jp.stock;

public class CommonStock {
	private String stockSymbol;
	private int lastDividend;
	private int parValue;
	
	public CommonStock(String stockSymbol, int lastDividend, int parValue) {
		this.stockSymbol = stockSymbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
	}
	
	public int getDividend() {
		return this.lastDividend;
	}
	
	public double calculateDividendYield(int price) {
		return (double) getDividend() / (double) price;
	}
	
	public double calculatePERatio(int price) {
		int dividend = getDividend();
		if (dividend == 0) {
			return Double.NaN;
		}
		return (double) price / (double) dividend;
	}
	
	public int getParValue() {
		return this.parValue;
	}

	public int getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(int lastDividend) {
		this.lastDividend = lastDividend;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}
}

package com.cg.jp.stock;

public class PreferredStock extends CommonStock {

	private double fixedDividend; 
	
	public PreferredStock(String stockSymbol, int lastDividend, int parValue, int fixedDividend) {
		super(stockSymbol, lastDividend, parValue);
		this.fixedDividend = (double) fixedDividend / 100;
	}
	
	@Override
	public int getDividend() {
		return (int) (this.fixedDividend * getParValue());
	}
	
	@Override
	public double calculateDividendYield(int price) {
		return (double) this.fixedDividend * getParValue() / price;
	}
	
}

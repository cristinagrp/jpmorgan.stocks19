package com.cg.jp.stock;

public class PreferredStock extends AbstractStock {

	private double fixedDividend; 
	
	public PreferredStock(String stockSymbol, double lastDividend, double parValue, double fixedDividend) {
		super(stockSymbol, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}
	
	@Override
	public Double getDividend() {
		return this.fixedDividend * getParValue();
	}
	
}

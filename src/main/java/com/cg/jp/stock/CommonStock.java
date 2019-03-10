package com.cg.jp.stock;

public class CommonStock  extends AbstractStock {
	
	public CommonStock(String stockSymbol, double lastDividend, double parValue) {
		super(stockSymbol, lastDividend, parValue);
	}
	
	@Override
	public Double getDividend() {
		return getLastDividend();
	}
}

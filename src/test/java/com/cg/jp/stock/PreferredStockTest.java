package com.cg.jp.stock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class PreferredStockTest {
	@Test
    @Parameters({
            "GIN, 0, 0.02, 100, 55, 0.03",
            "GIO, 8, 0.02, 100, 12, 0.16",
            "GIP, 23, 0.1, 60, 16, 0.37",
            "GIQ, 13, 0.08, 250, 124, 0.16"

    })
	public void testCalculateDividendYield(String stockSymbol, double lastDividend, double fixedDividend, double parValue, double price, double expectedDividendYield) {
		PreferredStock stock = new PreferredStock(stockSymbol, lastDividend, parValue, fixedDividend);
		
		Assert.assertEquals(expectedDividendYield, stock.calculateDividendYield(price), 0.009);
	}
	
	@Test
    @Parameters({
            "GIN, 0, 0.02, 100, 55, 27.5",
            "GIO, 8, 0.02, 100, 12, 6",
            "GIP, 23, 0.1, 60, 16, 2.66",
            "GIQ, 13, 0.08, 250, 124, 6.2"

    })
	public void testCalculatePERatio(String stockSymbol, double lastDividend, double fixedDividend, double parValue, double price, double expectedPERatio) {
		PreferredStock stock = new PreferredStock(stockSymbol, lastDividend, parValue, fixedDividend);
		
		Assert.assertEquals(expectedPERatio, stock.calculatePERatio(price), 0.009);
	}
	
	@Test
    @Parameters({
            "TEA, 100.0, 0.0, 55"
    })
	public void testCalculatePERatio_ZeroDividend(String stockSymbol, double parValue, double fixedDividend, double price) {
		CommonStock stock = new CommonStock(stockSymbol, 0, parValue);
		Assert.assertEquals(Double.NaN, stock.calculatePERatio(price), 0.0);
	}

}

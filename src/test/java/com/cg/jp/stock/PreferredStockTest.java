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
            "GIN, 0, 2, 100, 55, 0.03",
            "GIO, 8, 2, 100, 12, 0.16",
            "GIP, 23, 10, 60, 16, 0.37",
            "GIQ, 13, 8, 250, 124, 0.16"

    })
	public void testCalculateDividendYield(String stockSymbol, int lastDividend, int fixedDividend, int parValue, int price, double expectedDividendYield) {
		PreferredStock stock = new PreferredStock(stockSymbol, lastDividend, parValue, fixedDividend);
		
		Assert.assertEquals(expectedDividendYield, stock.calculateDividendYield(price), 0.009);
	}
	
	@Test
    @Parameters({
            "GIN, 0, 2, 100, 55, 27.5",
            "GIO, 8, 2, 100, 12, 6",
            "GIP, 23, 10, 60, 16, 2.66",
            "GIQ, 13, 8, 250, 124, 6.2"

    })
	public void testCalculatePERatio(String stockSymbol, int lastDividend, int fixedDividend, int parValue, int price, double expectedPERatio) {
		PreferredStock stock = new PreferredStock(stockSymbol, lastDividend, parValue, fixedDividend);
		
		Assert.assertEquals(expectedPERatio, stock.calculatePERatio(price), 0.009);
	}
	
	@Test
    @Parameters({
            "TEA, 100, 0, 55"
    })
	public void testCalculatePERatio_ZeroDividend(String stockSymbol, int parValue, int fixedDividend, int price) {
		CommonStock stock = new CommonStock(stockSymbol, 0, parValue);
		Assert.assertEquals(Double.NaN, stock.calculatePERatio(price), 0.0);
	}

}

package com.cg.jp.stock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cg.jp.stock.CommonStock;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CommonStockTest {
	
	@Test
    @Parameters({
            "TEA, 0, 100, 55, 0.0",
            "POP, 8, 100, 12, 0.66",
            "ALE, 23, 60, 16, 1.43",
            "JOE, 13, 250, 124, 0.1"

    })
	public void testCalculateDividendYield(String stockSymbol, int lastDividend, int parValue, int price, double expectedDividendYield) {
		CommonStock stock = new CommonStock(stockSymbol, lastDividend, parValue);
		
		Assert.assertEquals(expectedDividendYield, stock.calculateDividendYield(price), 0.009);
	}
	
	@Test
    @Parameters({
            "TEA, 123, 100, 55, 0.44",
            "POP, 8, 100, 12, 1.5",
            "ALE, 23, 60, 16, 0.69",
            "JOE, 13, 250, 124, 9.53"

    })
	public void testCalculatePERatio(String stockSymbol, int lastDividend, int parValue, int price, double expectedPERatio) {
		CommonStock stock = new CommonStock(stockSymbol, lastDividend, parValue);
		
		Assert.assertEquals(expectedPERatio, stock.calculatePERatio(price), 0.009);
	}
	
	@Test
    @Parameters({
            "TEA, 100, 55"
    })
	public void testCalculatePERatio_ZeroDividend(String stockSymbol, int parValue, int price) {
		CommonStock stock = new CommonStock(stockSymbol, 0, parValue);
		Assert.assertEquals(Double.NaN, stock.calculatePERatio(price), 0.0);
	}
}

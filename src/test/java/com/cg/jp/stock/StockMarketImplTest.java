package com.cg.jp.stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.cg.jp.stock.Trade.BuySell;

/**
 * Tests {@link StockExchangeServiceImpl}.
 *
 * @author CristinaGroapa
 */
public class StockMarketImplTest {

    /** The object to test */
    private StockMarketImpl market;

    /**
     * POJO containing expected calculation results for a stock
     *
     * @author CristinaGroapa
     */
    private class ExpectedStockResult {
    	Double givenPrice = Double.NaN;
    	
        Double dividendYield = Double.NaN;

        Double peRatio = Double.NaN;
        
        Double vwStockPrice = Double.NaN;
    }

    /** Expected calculation results for the test stocks */
    private Map<String, ExpectedStockResult> expectedResults;

    /** The expected share index */
    private Double expectedGBCE;

    /**
     * Tests {@link StockExchangeServiceImpl#calculateDividendYield(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculatePERatio(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculateStockPrice(StockSymbol)},
     * {@link StockExchangeServiceImpl#calculateGeometricMean()}.
     *
     * @throws IOException from BufferedReader
     */
    @Test
    public void testStockProperties() throws IOException {
        loadTests();

        for (String stockSymbol : this.expectedResults.keySet()) {
        	ExpectedStockResult expectedResult = this.expectedResults.get(stockSymbol);
        	double givenPrice = expectedResult.givenPrice;
            Assert.assertEquals(expectedResult.dividendYield, this.market.calculateDividendYield(stockSymbol, givenPrice));
            Assert.assertEquals(expectedResult.peRatio, this.market.calculatePERatio(stockSymbol, givenPrice));
            Assert.assertEquals(expectedResult.vwStockPrice, this.market.calculateVWSP(stockSymbol));
        }

        Assert.assertEquals(this.expectedGBCE, this.market.calculateGBCE());
    }

    /**
     * Reads the stock data from the test file and adds it to the service.
     * @throws FileNotFoundException 
     */
    private void loadTests() throws FileNotFoundException {
        this.market = new StockMarketImpl();
        this.expectedResults = new HashMap<>();
        
        try (Scanner scanner = new Scanner (new File("src/test/resources/testData"))) {

            String line;
            String stockSymbol = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
            	if (line.length() == 0) {
                    continue;
                }

                if (line.startsWith("STOCK")) {
                    // This line describes a stock
                    stockSymbol = processStock(line);
                } else if (line.startsWith("GBCE")) {
                    // This line contains the expected GBCE
                    String[] fields = line.split(" ");
                    this.expectedGBCE = Double.valueOf(fields[1]);
                } else if (line.startsWith("RESULT")) {
                    // This line contains the expected stock calculation results
                    processExpectedResults(line, stockSymbol);
                } else {
                    // This line contains a list of trades for the previous stock
                    processTrades(line, stockSymbol);
                }
            }
        } 
    }

    /**
     * Processes a line in the test file containing the details of a stock
     *
     * @param line the line to process
     * @return the symbol of the processed stock
     */
    private String processStock(final String line) {
        String[] stockDetails = line.split(" ");
        String stockSymbol = stockDetails[1];
        boolean isPreferred = "Preferred".equalsIgnoreCase(stockDetails[2]);
        double lastDividend = Double.valueOf(stockDetails[3]);
        double parValue = Double.valueOf(stockDetails[4]);
        if (isPreferred) {
            double fixedDividend = Double.valueOf(stockDetails[5]);
            this.market.createPreferredStock(stockSymbol, parValue, lastDividend, fixedDividend);
        } else {
            this.market.createCommonStock(stockSymbol, parValue, lastDividend);
        }
        this.expectedResults.put(stockSymbol, new ExpectedStockResult());

        return stockSymbol;
    }

    /**
     * Processes a line in the test file containing the trades for a stock
     *
     * @param line the line to process
     * @param stockSymbol the corresponding stock symbol
     */
    private void processTrades(final String line, final String stockSymbol) {
        String[] fields = line.split(" ");
        int txStartIndex = 0;
        while (txStartIndex < fields.length) {
            BuySell type = BuySell.valueOf(fields[txStartIndex]);
            long quantity = Long.parseLong(fields[txStartIndex + 1]);
            double price = Double.parseDouble(fields[txStartIndex + 2]);
            Trade trade = new Trade(quantity, type, price);
            this.market.addTrade(stockSymbol, trade);
            txStartIndex += 3;
        }
    }

    /**
     * Processes a line in the test file containing the expected calculation results for a stock
     *
     * @param line the line to process
     * @param stockSymbol the corresponding stock symbol
     */
    private void processExpectedResults(final String line, final String stockSymbol) {
        ExpectedStockResult expectedResult = this.expectedResults.get(stockSymbol);
        String[] fields = line.split(" ");
        expectedResult.givenPrice = Double.parseDouble(fields[1]);
        expectedResult.dividendYield = Double.parseDouble(fields[2]);
        expectedResult.peRatio = Double.parseDouble(fields[3]);
        expectedResult.vwStockPrice = Double.parseDouble(fields[4]);
    }
}

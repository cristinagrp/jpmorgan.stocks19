package com.cg.jp.stock;


public interface StockMarket {

	/**
     * Creates a common stock with the given details and adds it to the internal register.
     * 
     * @param stockSymbol the stock symbol
     * @param parValue the par value of the stock
     * @param lastDividend the last dividend of the stock
     */
    void createCommonStock(String stockSymbol, double parValue, double lastDividend);

    /**
     * Creates a preferred stock with the given details and adds it to the internal register.
     * 
     * @param stockSymbol the stock symbol
     * @param parValue the par value of the stock
     * @param lastDividend the last dividend of the stock
     * @param fixedDividend the stock's fixed dividend
     */
    void createPreferredStock(String stockSymbol, double parValue, double lastDividend, double fixedDividend);

    Double calculateGBCE();

    void addTrade(String stockSymbol, Trade trade);
    
    Double calculateVWSP(String stockSymbol);

    /**
     * Calculates the dividend yield for a given stock.
     * 
     * @param stockSymbol the identifier of the stock for which to calculate the dividend yield
     * @param price the given price
     * @return the dividend yield, or {@link Double#NaN} if not enough data is available for the stock
     */
    Double calculateDividendYield(String stockSymbol, double price);

    /**
     * Calculates the P/E Ratio for a given stock.
     * 
     * @param stockSymbol the identifier of the stock for which to calculate the P/E Ratio
     * @param price the given price
     * @return the P/E Ratio, or {@link Double#NaN} if not enough data is available for the stock
     */
    Double calculatePERatio(String stockSymbol, double price);

}

package com.cg.jp.stock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Common representation of a stock.
 *
 * @author CristinaGroapa
 */
public abstract class AbstractStock {
	
	private static final int WINDOW_DURATION_MINS = 15;
	
	private Duration windowDuration;

    private final String stockSymbol;

    /**
     * A record of all the transactions that took place for this stock. The most recent are at the head of the list.
     * <b>NB I assumed that the transactions are added in real-time, i.e. in chronological order, which is why I am not
     * sorting the queue by the timestamp. Otherwise the transaction history could be a sorted collection and Transaction could
     * implement Comparable.</b>
     */
    private final Deque<Trade> tradeHistory;

    private final Double parValue;

    private Double lastDividend;

    public AbstractStock(final String symbol, final double lastDividend, final double parValue) {
        this.stockSymbol = symbol;
        this.parValue = parValue;
        this.lastDividend = lastDividend;
        this.tradeHistory = new ArrayDeque<>();
        this.windowDuration = Duration.ofMinutes(WINDOW_DURATION_MINS);
    }

    public Double calculateDividendYield(Double price) {
    	if (price.isNaN() || price == 0.0) {
    		return Double.NaN;
    	}
		return getDividend() / price;
	}
    
    public abstract Double getDividend();

    /**
     * Calculates the P/E Ratio for this stock.
     *
     * @return the P/E Ratio of this stock, or {@link Double#NaN} if not enough data is available
     */
    public Double calculatePERatio(double price) {
        Double dividend = getDividend();
    	if (dividend.isNaN() || dividend == 0.0) {
            return Double.NaN;
        }

        return price / dividend;
    }

    /**
     * Calculates the stock price for this stock.
     *
     * @return the stock price of this stock, or {@link Double#NaN} if not enough data is available - i.e. there are no
     *         transactions
     */  
    public double calculateVWSP() {
    	if (this.tradeHistory.isEmpty()) {
            return Double.NaN;
        }
		
    	LocalDateTime now = LocalDateTime.now();
		LocalDateTime windowStart = calculateWindowStart(now);
		double tradedValueSum = 0.0;
		long tradedQuantitySum = 0;
		boolean intervalFinished = false;
		Iterator<Trade> iterator = this.tradeHistory.iterator();
		
		while (iterator.hasNext() && !intervalFinished) {
            Trade trade = iterator.next();
			if (trade.getTimestamp().isAfter(windowStart)) {
				tradedValueSum += trade.getPrice() * trade.getShareCount();
				tradedQuantitySum += trade.getShareCount();
			} else {
				intervalFinished = true;
			}
		}
		
		return tradedValueSum / tradedQuantitySum;
		
	}
    
	private LocalDateTime calculateWindowStart(LocalDateTime now) {
		return now.minus(this.windowDuration);
	}

    /**
     * Adds a trade to the trade history.
     * <b>NB I assumed that the trades are added in real-time, i.e. in chronological order, which is why I am not
     * sorting the queue by the timestamp. Otherwise the transaction history could be a sorted list and Transaction could
     * implement Comparable.</b>
     *
     * @param trade the transaction to add
     */
    public void addTrade(final Trade trade) {
        this.tradeHistory.push(trade);
    }

    /**
     * @return the lastDividend
     */
    public double getLastDividend() {
        return this.lastDividend;
    }

    /**
     * @param lastDividend the lastDividend to set
     */
    public void setLastDividend(final double lastDividend) {
        this.lastDividend = lastDividend;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return this.stockSymbol;
    }

    /**
     * @return true if the transaction history is not empty, false otherwise
     */
    public boolean hasTransactions() {
        return !this.tradeHistory.isEmpty();
    }

    /**
     * @return the parValue
     */
    public double getParValue() {
        return this.parValue;
    }
}

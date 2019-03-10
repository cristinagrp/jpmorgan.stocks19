package com.cg.jp.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.jp.stock.exception.StockNotFoundException;


public class StockMarketImpl implements StockMarket {
	
	private final Logger logger = LoggerFactory.getLogger(StockMarketImpl.class);

    private final Map<String, AbstractStock> stocks;
	
	public StockMarketImpl() {
		this.stocks = new HashMap<>();
	}
	
	@Override
    public void createCommonStock(final String stockSymbol, final double parValue, final double lastDividend) {
        this.logger.info("Creating common stock {} with parValue={}, lastDividend={}.", stockSymbol, parValue,
                         lastDividend);
        this.stocks.put(stockSymbol, new CommonStock(stockSymbol, lastDividend, parValue));
    }

    @Override
    public void createPreferredStock(final String stockSymbol, final double parValue, final double lastDividend,
            final double fixedDividend) {
        this.logger.info("Creating preferred stock {} with parValue={}, lastDividend={}, fixedDividend={}.",
                         stockSymbol, parValue, lastDividend, fixedDividend);
        this.stocks.put(stockSymbol, new PreferredStock(stockSymbol, lastDividend, parValue, fixedDividend));
    }

    @Override
    public Double calculateDividendYield(String stockSymbol, double price) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            return roundDouble(stock.calculateDividendYield(price));
        }
        this.logger.info("Asked to calculate dividend yield for non-existing stock {}. Returning NaN.", stockSymbol);
        return Double.NaN;
    }

    @Override
    public Double calculatePERatio(String stockSymbol, double price) {
        AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            return roundDouble(stock.calculatePERatio(price));
        }
        this.logger.info("Asked to calculate P/E Ratio for non-existing stock {}. Returning NaN.", stockSymbol);
        return Double.NaN;
    }
	
	@Override
	public void addTrade(String stockSymbol, Trade trade) {
		AbstractStock stock = this.stocks.get(stockSymbol);
        if (stock != null) {
            stock.addTrade(trade);
            this.logger.debug("Adding trade for stock {}", stockSymbol);
        } else {
            this.logger.info("Asked to add trade for non-existing stock {}. Nothing to do.", stockSymbol);
        }
	}
	
	@Override
	public Double calculateVWSP(String stockSymbol) {
		AbstractStock stock = this.stocks.get(stockSymbol);
		
		if (stock == null) {
			throw new StockNotFoundException();
		}
		
		return roundDouble(stock.calculateVWSP());
		
	}
	
	@Override
	public Double calculateGBCE() {
		double gbce = 1.0;
		int positivePriceCount = 0;
		
		for (AbstractStock stock : this.stocks.values()) {
			Double stockPrice = stock.calculateVWSP();
			if (!stockPrice.isNaN() && stockPrice != 0.0) {
				gbce *= stockPrice;
				positivePriceCount++;
			}
		}
		
		return roundDouble(Math.pow(gbce, 1.0 / positivePriceCount));
	}
	
    /**
     * Rounds a given double value to 2 decimals. In the current implementation the number of decimals is hardcoded, but
     * it can easily be made configurable.
     *
     * @param value the value to round
     * @return the double value rounded to 2 decimals
     */
    protected Double roundDouble(final Double value) {
        if (value.isNaN()) {
            return value;
        }
        BigDecimal bd = new BigDecimal(value.toString());
        bd = bd.setScale(2, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
}
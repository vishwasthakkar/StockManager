package stockmanager.model;

import java.util.Date;

/**
 * This interface represents an API that fetches the stock data. Every API (java class) that fetches
 * the stock data for the application should implement this interface. Hence, by using this
 * interface the application can switch between different sources for fetching the stock data.
 */

public interface GenericStockDataFetcher {


  /**
   * From the stock data generated, fetch out the price on that day for that stock.
   *
   * @param date   date on which price needs to be fetched
   * @param ticker name of stock
   * @return the price on that day for that stock
   * @throws IllegalArgumentException if the date invalid
   */
  double getPriceOnADate(String ticker, Date date) throws IllegalArgumentException;

  /**
   * From the stock data generated, fetch out the latest price of a stock. If the market is closed,
   * the closing price of last open date will be calculated.
   *
   * @param ticker name of stock
   * @return the latest price of a stock
   * @throws IllegalArgumentException if the stock not found
   */
  double getMostRecentPrice(String ticker) throws IllegalArgumentException;

  /**
   * Fetch all information about the given stock.
   *
   * @param ticker the name of the stock
   * @return all information about the given stock as a string
   * @throws IllegalArgumentException if the stock not found
   */
  String generateStockData(String ticker) throws IllegalArgumentException;
}

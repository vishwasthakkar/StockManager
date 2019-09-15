package stockmanager.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>This class represents the stock of a particular company listed on the stock market. Class
 * stores the Ticker Symbol, the date at which it was bought, the buying price, quantity
 * bought.</p>
 *
 * <p>The current price of stock is fetched every time the stock is accessed through one of the
 * methods of the model class using the getPriceOnADate() function.</p>
 *
 * <p>The user of the application should follow these points:</p>
 * <ul>
 * <li><p>The user must use the dates of upto '2014-04-15' for purchasing the stocks or examining
 * the stock data. The application will give an error (throw an exception) if valid dates are not
 * used.
 * </p></li>
 *
 * <li><p>If the ticker symbol provided by the user is not valid (not listed in the stock market).
 * The application will give an error message.</p></li>
 *
 * <li><p>Days when market is closed: The application will not allow the user to buy stocks when
 * the stock market is not open. Similarly, when a date is given on which the market was closed, the
 * application gives an error saying the same.</p></li>
 *
 * <li><p>The quantity of the stock purchased cannot be lesser than one.</p></li>
 *
 * </ul>
 */
class Stock {

  private String tickerSymbol;
  private Date date;
  private double buyingPrice;
  private double commission;
  private double quantity;
  private GenericStockDataFetcher fetcher;

  /**
   * Constructs a stock with a given name, quantity and a date (BUY).
   *
   * @param tickerSymbol the given name of a stock
   * @param amount       the given quantity of stocks the user buy
   * @param date         the given date when user buy the stock
   * @throws IllegalArgumentException if quantity or date doesn't meet requirements
   */
  Stock(String tickerSymbol, double amount, double commission, Date date,
        GenericStockDataFetcher fetcher) throws IllegalArgumentException {
    if (commission < 0) {
      throw new IllegalArgumentException("Commission fees should either be zero or greater "
              + "than zero.");
    }
    if (amount < 0) {
      throw new IllegalArgumentException("Amount should either be zero or greater "
              + "than zero.");
    }
    validateDate(date);
    this.fetcher = fetcher;
    this.tickerSymbol = tickerSymbol;
    this.buyingPrice = fetcher.getPriceOnADate(tickerSymbol, date);
    this.quantity = (amount - commission) / buyingPrice;
    this.commission = commission;
    this.date = date;
  }

  /**
   * Constructs a stock with all variables (Parse).
   *
   * @param tickerSymbol the given name of a stock
   * @param buyQuantity  the given quantity of stocks the user buy
   * @param date         the given date when user buy the stock
   * @param buyPrice     the price when buy the stock
   * @param commission   the commission fee
   * @throws IllegalArgumentException if variables are invalid
   */
  Stock(String tickerSymbol, Date date, double buyPrice, double buyQuantity, double commission)
          throws IllegalArgumentException {
    if (commission < 0) {
      throw new IllegalArgumentException("Commission fees should either be zero or greater "
              + "than zero.");
    }
    this.commission = commission;
    if (buyQuantity < 0) {
      throw new IllegalArgumentException("Quantity should be greater than 0.");
    }
    this.quantity = buyQuantity;

    fetcher = new AlphaVantageAPI();
    fetcher.generateStockData(tickerSymbol);
    this.tickerSymbol = tickerSymbol;

    if (date == null) {
      this.date = null;
      this.buyingPrice = buyPrice;
    } else {
      validateDate(date);
      double price = fetcher.getPriceOnADate(tickerSymbol, date);
      this.date = date;

      if (price != buyPrice) {
        throw new IllegalArgumentException("The given price doesn't match the fact.");
      }
      this.buyingPrice = buyPrice;
    }
  }


  /**
   * Constructor to ADD stocks to portfolio without buying. The buyingPrice and the date will not
   * initialized in this case (ADD).
   *
   * @param tickerSymbol the name of the stock
   * @param quantity     the quantity of this stock users want to add to portfolio
   * @throws IllegalArgumentException if the quantity is invalid
   */
  Stock(String tickerSymbol, double quantity, GenericStockDataFetcher fetcher)
          throws IllegalArgumentException {
    if (quantity < 1) {
      throw new IllegalArgumentException("Invalid Quantity.");
    }
    this.fetcher = fetcher;
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }


  /**
   * Returns all information about this stock as a string.
   *
   * @return all information about this stock
   */

  String getStockDetails() {
    return tickerSymbol + ", " + buyingPrice + ", " + quantity + ", "
            + commission + ", "
            + fetcher.getMostRecentPrice(tickerSymbol) + ", " + date;
  }

  /**
   * Returns the ticker symbol of this stock.
   *
   * @return the ticker symbol of this stock
   */
  String getTickerSymbol() {
    return tickerSymbol;
  }

  /**
   * Returns the buying price about this stock.
   *
   * @return the buying price about this stock
   */
  double getBuyingPrice() {
    return buyingPrice;
  }

  /**
   * Returns the quantity about this stock.
   *
   * @return the quantity about this stock
   */
  double getQuantity() {
    return quantity;
  }

  /**
   * Returns the buying date about this stock.
   *
   * @return the buying date about this stock
   */
  Date getBuyingDate() {
    return this.date;
  }

  /**
   * Returns the commission fee date about this purchase.
   *
   * @return the commission fee date about this purchase
   */
  double getCommission() {
    return commission;
  }

  /**
   * Returns price on a particular date for a particular ticker.
   *
   * @param date the particular date when user want to know the price of the stock
   * @return the particular date for a particular ticker
   */
  double getPrice(Date date) {
    return fetcher.getPriceOnADate(tickerSymbol, date);
  }

  /**
   * Validates the date is valid or not.
   *
   * @param date the date that needs to validate
   * @throws IllegalArgumentException if the date is invalid
   */
  private void validateDate(Date date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("Invalid date.");
    }
    if (date.before(new GregorianCalendar(2014, Calendar.APRIL, 15).getTime())) {
      throw new IllegalArgumentException("Invalid date.");
    }
    if (date.after(new Date())) {
      throw new IllegalArgumentException("Invalid date.");
    }
  }

}

package stockmanager.model;

import java.util.Date;
import java.util.List;

/**
 * This Interface represents the model and contains all the operations that can be performed in the
 * 'stockmanager' application by the'user'. The application can contain any number of portfolios and
 * each portfolio can contain any number of stocks in it. These operations can be used to create
 * portfolios and buy stocks inside this portfolios and also, examine them.
 *
 * <p>The user of the application should take care of the following:</p>
 * <ul>
 * <li><p>The user must use the dates of upto '2014-04-15' for purchasing the stocks or examining
 * the stock data. The application will give an error (throw an exception) if valid dates are not
 * used.
 * </p></li>
 *
 * <li><p>We are fetching the “closing price” of a stock on a particular day. If the date is today,
 * then current price is being fetched.</p></li>
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


public interface StockManagerModel {

  /**
   * Creates an empty portfolio by using just the name of it. The name of the portfolio must be
   * greater than 3 characters and also, must be alpha-numeric.
   *
   * <p>The user cannot add multiple portfolios with the same name.</p>
   *
   * @param name Name of the portfolio being created
   */
  void createPortfolio(String name) throws IllegalArgumentException;


  /**
   * Gets the name of all the portfolios added by the user in the 'stockmanager' application.
   *
   * @return String containing names of portfolios
   */
  List<String> getAllPortfolios();

  /**
   * <p>Shows the user, the composition of a particular portfolio. The user has to pass the name of
   * the portfolio to call this function.</p>
   *
   * <p>The function will get the ticker symbol, quantity, buying price, current price and
   * the date of purchasing of the stocks.</p>
   *
   * <p>When fetching the current price of a stock, if the market is closed at this moment,
   * the current price will be the same as the buying price of stocks.</p>
   *
   * <p>The ouput will be in the form of:
   * TICkER NAME BUYINGPRICE CURRENTPRICE QUANTITY DATE</p>
   *
   * @param portfolioName name of portfolio that user needs to examine
   * @return the composition of the portfolio as a string
   * @throws IllegalArgumentException when invalid portfolio name is given
   */
  List<String> getPortfolioComposition(String portfolioName) throws IllegalArgumentException;


  /**
   * Adds a stock to a portfolio without buying.
   *
   * @param portfolioName Name of the portfolio being created
   * @param ticker        the name of stock
   * @param quantity      the quantity of the stock user want to add to portfolio
   * @throws IllegalArgumentException if portfolio name or ticker symbol or quantity invalid
   */
  void addStocksToPortfolio(String portfolioName, String ticker, double quantity)
          throws IllegalArgumentException;

  /**
   * Buy a stock to a portfolio.
   *
   * @param portfolioName Name of the portfolio being created
   * @param ticker        the name of stock
   * @param amount        the money user want to use to buy this stock
   * @param commission    the commission of this purchase
   * @param date          the date user want to buy this stock on
   * @throws IllegalArgumentException if portfolio name or ticker symbol or amount or commission fee
   *                                  or date invalid
   */
  void buyStocksUsingAmount(String portfolioName, String ticker, double amount, double commission
          , Date date) throws IllegalArgumentException;

  /**
   * This method will calculate the total money spent on a particular portfolio by the user, until a
   * given date (stock bought on this date will also be included in the calculation).
   *
   * <p>Conditions:<br>Portfolio should Exist.</p>
   * <p>A valid date as per application conditions should be entered.</p>
   *
   * @param portfolioName the portfolio name
   * @param date          A valid date until which the calculation should be done
   * @return total cost incurred by the user upto the given date
   * @throws IllegalArgumentException when any invalid argument is passed by the user
   */
  double totalCostBasisOfPortfolio(String portfolioName, Date date)
          throws IllegalArgumentException;

  /**
   * This method will calculate the total value of a particular portfolio on a certain given date
   * (stock bought on this date will also be included in the calculation).
   *
   * <p>Conditions:<br>Portfolio should Exist.</p>
   * <p>A valid date as per application conditions should be entered.</p>
   * <p>The market should be open.</p>
   *
   * @param portfolioName the portfolio name
   * @param date          A valid date until which the  valuecalculation should be done
   * @return total value of the portfolio upto the given date
   * @throws IllegalArgumentException when any invalid argument is passed by the user
   */
  double totalValueOfPortfolio(String portfolioName, Date date)
          throws IllegalArgumentException;

  Portfolio getSpecificPortfolio(String portfolioName) throws IllegalArgumentException;
}

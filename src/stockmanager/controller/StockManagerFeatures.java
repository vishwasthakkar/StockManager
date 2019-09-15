package stockmanager.controller;


/**
 * This interface represents a set of features that the Stock Manager program offers. Each feature
 * is exposed as a function in this interface. This function is used suitably as control to the
 * controller. How the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a dialog box, or a set of
 * text inputs, etc.) Each function is designed to take in the necessary data to complete that
 * functionality.
 */

public interface StockManagerFeatures {

  /**
   * Create a new portfolio, by giving it a name.
   *
   * @param name name of the portfolio
   */
  void createPortfolio(String name);

  /**
   * Buy stocks in a portfolio using these functions.
   *
   * @param portfolioName name of the portfolio in which stocks will be bought
   * @param ticker        ticker for a stock
   * @param amount        ampunt the user wants to invest
   * @param commission    commission the used was charged
   * @param date          the date on which stocks were purchased (yyyy-MM-dd)
   */
  void buyStocks(String portfolioName, String ticker, String amount, String commission,
                 String date);

  /**
   * Add stocks to a portfolio without buying.
   *
   * @param portfolioName name of the portfolio in which stocks will be added
   * @param ticker        ticker for a stock
   * @param quantity      quantity of stock you want to add
   */
  void addStocks(String portfolioName, String ticker, String quantity);

  /**
   * Calculate the cost-basis for a portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param date          date until until which user wants the cost basis
   */
  void getCostBasis(String portfolioName, String date);

  /**
   * Calculate the current value for a portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param date          date until until which user wants the value
   */
  void getValue(String portfolioName, String date);

  /**
   * Save the portfolio in an xml file at a user-specified location.
   *
   * @param portfolioName name of the portfolio
   * @param filePath      location where the user want to store the xml
   */
  void savePortfolio(String portfolioName, String filePath);

  /**
   * Retrieve the portfolio from an xml file from a user-specified location.
   *
   * @param filePath location where the user want to store the xml
   */
  void retrievePortfolio(String filePath);


}


package stockmanager.controller;

import javax.swing.table.TableModel;

/**
 * This interface adds more new features to the original project, it adds features: investing in
 * existing portfolio, clickedGetPortfolioAndInvestButton, clickedStrategyExistingPortfolioButton,
 * getSpecificPortfolio, addStrategyOnExistingPortfolio, addStrategyOnNewPortfolio, save Strategy
 * and retrieve Strategy.
 */
public interface AdditionalStockManagerFeatures extends StockManagerFeatures {

  /**
   * Invests in existing portfolio.
   *
   * @param portfolioName the name of portfolio
   * @param m             the table model
   * @param amount        the amount users will use on investment
   * @param commission    commission fee
   * @param date          the date for investment
   */
  void investInExistingPortfolio(String portfolioName, TableModel m, String amount,
                                 String commission, String date);

  /**
   * Get portfolio and invest for it.
   *
   * @param portfolioName the name of portfolio
   */
  void clickedGetPortfolioAndInvestButton(String portfolioName);

  /**
   * Use strategy on an existing portfolio.
   *
   * @param portfolioName the name of portfolio
   */
  void clickedStrategyExistingPortfolioButton(String portfolioName);

  /**
   * Get a specific portfolio by its name.
   *
   * @param portfolioName the name of portfolio
   */
  void getSpecificPortfolio(String portfolioName);

  /**
   * Add a strategy on an existing portfolio.
   *
   * @param strategyName  the name of strategy
   * @param portfolioName the name of portfolio
   * @param stockWeights  the table model
   * @param amount        the amount use want to buy stocks
   * @param commission    commission fee
   * @param startDate     start date
   * @param endDate       end date
   * @param freq          frequency
   */
  void addStrategyOnExistingPortfolio(String strategyName, String portfolioName,
                                      TableModel stockWeights, String amount, String commission,
                                      String startDate, String endDate, String freq);

  /**
   * Add a strategy on a new portfolio.
   *
   * @param strategyName  the name of strategy
   * @param portfolioName the name of portfolio
   * @param stockWeights  the table model
   * @param amount        the amount use want to buy stocks
   * @param commission    commission fee
   * @param startDate     start date
   * @param endDate       end date
   * @param freq          frequency
   */
  void addStrategyOnNewPortfolio(String strategyName, String portfolioName,
                                 TableModel stockWeights, String amount, String commission,
                                 String startDate, String endDate, String freq);

  /**
   * Save a strategy in a XML file.
   *
   * @param strategyName the name of strategy
   * @param filePath     the path to store xml file
   */
  void saveStrategy(String strategyName, String filePath);

  /**
   * Retrieve a strategy in a XML file.
   *
   * @param filePath the path of the location of the xml file
   */
  void retrieveStrategy(String filePath);
}

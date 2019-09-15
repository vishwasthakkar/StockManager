package stockmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class implements the model of the application. It contains a list that keeps track of all
 * the portfolios that are created by the user. The portfolios themselves contains a 'List' of
 * stocks. Further, the model contains functions and operations to operate on these portfolios and
 * stocks.
 */
public class StockManagerModelImpl implements StockManagerModel {

  protected GenericStockDataFetcher fetcher;
  protected Set<Portfolio> allPortfolios;

  /**
   * Initialize the Set object that will store the portfolios.
   *
   * @param fetcher the GenericStockDataFetcher
   */
  public StockManagerModelImpl(GenericStockDataFetcher fetcher) {
    allPortfolios = new HashSet<>();
    this.fetcher = fetcher;
  }


  @Override
  public void createPortfolio(String name) throws IllegalArgumentException {
    if (name == null || name.length() < 3 || !name.matches("^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException("Please pass valid name for portfolio.");
    }
    for (Portfolio p : allPortfolios) {
      if (p.getPortfolioName().equals(name)) {
        throw new IllegalArgumentException("This portfolio already exists.");
      }
    }
    allPortfolios.add(new Portfolio(name));
  }

  @Override
  public List<String> getAllPortfolios() {
    List<String> list = new ArrayList<>();
    for (Portfolio p : allPortfolios) {
      list.add(p.getPortfolioName());
    }
    return list;
  }

  @Override
  public List<String> getPortfolioComposition(String portfolioName)
          throws IllegalArgumentException {
    return getSpecificPortfolio(portfolioName).showComposition();
  }

  @Override
  public void addStocksToPortfolio(String portfolioName, String ticker, double quantity)
          throws IllegalArgumentException {
    getSpecificPortfolio(portfolioName).addStock(new Stock(ticker, quantity, fetcher));
  }

  @Override
  public void buyStocksUsingAmount(String portfolioName, String ticker, double amount,
                                   double commission, Date date) throws IllegalArgumentException {
    getSpecificPortfolio(portfolioName).addStock(new Stock(ticker, amount, commission,
            date, fetcher));
  }


  @Override
  public double totalCostBasisOfPortfolio(String portfolioName, Date date)
          throws IllegalArgumentException {

    return getSpecificPortfolio(portfolioName).getCostBasis(date);

  }

  @Override
  public double totalValueOfPortfolio(String portfolioName, Date date)
          throws IllegalArgumentException {

    return getSpecificPortfolio(portfolioName).getTotalValue(date);
  }

  /**
   * Returns a specific portfolio by the given portfolioName.
   *
   * @param portfolioName Name of the portfolio wants to be found in set
   * @return a specific portfolio with the given name
   * @throws IllegalArgumentException if portfolio name is invalid or the portfolio doesn't exist
   */
  @Override //FIXME
  public Portfolio getSpecificPortfolio(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be null.");
    }
    for (Portfolio p : allPortfolios) {
      if (p.getPortfolioName().equals(portfolioName)) {
        return p;
      }
    }
    throw new IllegalArgumentException("The portfolio '" + portfolioName
            + " ' does not exist.");
  }
}

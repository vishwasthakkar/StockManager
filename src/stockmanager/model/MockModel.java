package stockmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A mock model to test the controller in isolation.
 */
public class MockModel implements PersistingStockManagerModel {

  private StringBuilder log;
  private final int uniqueCode;

  /**
   * Constructs a MockModel for testing.
   *
   * @param log        used to test input
   * @param uniqueCode used to test output
   */
  public MockModel(StringBuilder log, int uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void createPortfolio(String name) throws IllegalArgumentException {
    log.append(name);
  }

  @Override
  public void addStocksToPortfolio(String portfolioName, String ticker, double quantity)
          throws IllegalArgumentException {
    //do nothing
  }

  @Override
  public List<String> getAllPortfolios() {
    return new ArrayList<>();
  }

  @Override
  public List<String> getPortfolioComposition(String portfolioName)
          throws IllegalArgumentException {
    return new ArrayList<>();
  }

  @Override
  public void buyStocksUsingAmount(String portfolioName, String ticker, double amount,
                                   double commission, Date date) throws IllegalArgumentException {

    log.append(portfolioName);
    log.append(ticker);

  }

  @Override
  public double totalCostBasisOfPortfolio(String portfolioName, Date date) {
    return uniqueCode;
  }

  @Override
  public double totalValueOfPortfolio(String portfolioName, Date date) {
    return uniqueCode;
  }

  @Override
  public void savePortfolio(String portfolioName, String filePath) {
    //do nothing
  }

  @Override
  public void extractPortfolioFromXML(String filePath) {
    // do nothing
  }

  @Override
  public Portfolio getSpecificPortfolio(String name) {
    return new Portfolio(name);
  }
}

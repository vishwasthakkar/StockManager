package stockmanager.model;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class adds more new features to the persisting functionality, it can save and retrieve a
 * strategy.
 */
public class EnhancedStockManagerModelImpl
        extends PersistingStockManagerModelImpl implements EnhancedStockManagerModel {

  protected Map<String, Strategy> strategyMap;

  /**
   * Constructs a EnhancedStockManagerModelImpl.
   *
   * @param fetcher the GenericStockDataFetcher
   * @throws IllegalArgumentException if the information doesn't exist
   */
  public EnhancedStockManagerModelImpl(GenericStockDataFetcher fetcher) {
    super(fetcher);
    strategyMap = new HashMap<>();
    executeStrategies();
  }

  @Override
  public void addStrategy(String strategyName, String portfolioName,
                          Map<String, Double> stockWeights, double amount,
                          double commission, Date startDate, Date endDate, int freq)
          throws IllegalArgumentException {

    if (endDate == null) {
      endDate = new Date();
    }
    if (strategyName == null || strategyName.length() < 3 ||
            !strategyName.matches("^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException("Please pass valid name for strategy.");
    }
    if (strategyMap.containsKey(strategyName)) {
      throw new IllegalArgumentException("The Strategy already exists");
    }
    if (startDate.after(new Date())) {
      throw new IllegalArgumentException("The start date cannot be in the future");
    }
    if (freq <= 0) {
      throw new IllegalArgumentException("The frequency should be a positive integer");
    }
    strategyMap.put(strategyName, new Strategy(portfolioName, stockWeights, amount,
            commission, startDate, endDate, freq));
    executeStrategy(strategyMap.get(strategyName));
  }

  @Override
  public void investUsingWeightsInExistingPortfolio(
          String portfolioName, Map<String, Double> stockWeights, double amount,
          Double commission, Date date) throws IllegalArgumentException {

    for (String ticker : stockWeights.keySet()) {
      buyStocksUsingAmount(portfolioName, ticker, (stockWeights.get(ticker) / 100) * amount,
              (stockWeights.get(ticker) / 100) * commission, date);
    }
  }

  private void executeStrategies() {
    for (String strategyName : strategyMap.keySet()) {
      executeStrategy(strategyMap.get(strategyName));
    }
  }

  private void executeStrategy(Strategy strategy) {
    List<Date> dateList = strategy.getListOfBuyingDates();
    for (Date eachDate : dateList) {
      investUsingWeightsInExistingPortfolio(
              strategy.getPortfolioName(),
              strategy.getStockWeights(),
              strategy.getAmount(),
              strategy.getCommission(),
              eachDate
      );
    }
  }

  @Override
  public Strategy getSpecificStrategy(String strategyName) throws IllegalArgumentException {
    if (strategyName == null || strategyName.equals("")) {
      throw new IllegalArgumentException("Strategy name cannot be null.");
    }
    for (Map.Entry<String, Strategy> entry : this.strategyMap.entrySet()) {
      if (entry.getKey().equals(strategyName)) {
        return entry.getValue();
      }
    }
    throw new IllegalArgumentException("The strategy '" + strategyName
            + " ' does not exist.");
  }

  @Override
  public void saveStrategy(String strategyName, String filePath)
          throws IllegalArgumentException, ParserConfigurationException, FileNotFoundException,
          TransformerException {
    getSpecificStrategy(strategyName).save(filePath + ".xml", strategyName);
  }

  @Override
  public void extractStrategyFromXML(String filePath)
          throws IllegalArgumentException, ParserConfigurationException, SAXException,
          IOException, ParseException {

    XMLReader reader = new XMLReader();
    Map<String, Strategy> newStrategyMap = reader.parseStrategyXML(filePath);
    String name = newStrategyMap.keySet().toArray()[0].toString();
    Strategy strategy = newStrategyMap.get(name);
    strategyMap.put(name, strategy);
  }

}

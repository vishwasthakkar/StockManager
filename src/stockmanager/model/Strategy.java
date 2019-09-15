package stockmanager.model;

import org.w3c.dom.Element;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class represents a strategy which can construct, save and retrieve a strategy.
 */
public class Strategy {
  private String portfolioName;
  private Map<String, Double> stockWeights;
  private double amount;
  private double commission;
  private Date startDate;
  private Date endDate;
  private int freq;
  private List<Date> listOfBuyingDates;

  /**
   * Constructs a strategy with all variables.
   *
   * @param portfolioName the name of a portfolio
   * @param stockWeights  stock weights map
   * @param amount        the given quantity of stocks the user buy
   * @param commission    commission fee
   * @param startDate     start date
   * @param endDate       end date
   * @param freq          buying frequency
   */
  public Strategy(String portfolioName, Map<String, Double> stockWeights, double amount,
                  double commission, Date startDate, Date endDate, int freq) {
    this.portfolioName = portfolioName;
    this.stockWeights = stockWeights;
    this.amount = amount;
    this.commission = commission;
    this.startDate = startDate;
    this.endDate = endDate;
    this.freq = freq;

    listOfBuyingDates = new ArrayList<>();
    Date temp = startDate;
    while (temp.before(endDate) || temp.equals(endDate)) {
      listOfBuyingDates.add(temp);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(temp);
      calendar.add(Calendar.DATE, freq);
      temp = calendar.getTime();
    }
  }

  /**
   * Saves this strategy into an XML file to a given path.
   *
   * @param filePath     the path user want to store XML file
   * @param strategyName the name of the strategy
   */
  void save(String filePath, String strategyName)
          throws ParserConfigurationException, FileNotFoundException, TransformerException {
    XMLWriter writer = new XMLWriter(filePath);
    Element root = writer.createDocumentElement("Strategy");
    Element theStrategyName = writer.createElement(root, "StrategyName", strategyName);
    Element portfolioName = writer.createElement(root, "PortfolioName", this.portfolioName);
    Element amount = writer.createElement(root, "Amount", this.amount + "");
    Element commission = writer.createElement(root, "CommissionFee", this.commission + "");
    Element startDate = writer.createElement(root, "StartDate", this.startDate + "");
    Element endDate = writer.createElement(root, "EndDate", this.endDate + "");
    Element freq = writer.createElement(root, "Frequency", this.freq + "");
    for (Map.Entry<String, Double> entry : this.stockWeights.entrySet()) {
      Element stockWeights = writer.createElement(root, "StockWeights", "");
      writer.createElement(stockWeights, "StockName", entry.getKey());
      writer.createElement(stockWeights, "StockWeight", entry.getValue() + "");
    }
    writer.toSave();
  }

  /**
   * returns the list of buying dates.
   *
   * @return the list of buying dates
   */
  List<Date> getListOfBuyingDates() {
    return listOfBuyingDates;
  }

  /**
   * returns the name of portfolio.
   *
   * @return the name of portfolio
   */
  public String getPortfolioName() {
    return portfolioName;
  }

  /**
   * returns stock weights map.
   *
   * @return stock weights map
   */
  public Map<String, Double> getStockWeights() {
    return stockWeights;
  }

  /**
   * returns the amount.
   *
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * returns the commission fee.
   *
   * @return the commission fee
   */
  public double getCommission() {
    return commission;
  }

  /**
   * returns the start date.
   *
   * @return the start date
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * returns the end date.
   *
   * @return the end date
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * returns the buying frequency.
   *
   * @return the buying frequency
   */
  public int getFreq() {
    return freq;
  }
}

package stockmanager.model;

import org.w3c.dom.Element;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class represents a portfolio object containing all the stocks added by the user, along with
 * all the dates and prices of it. There are several operations defined in this class that operates
 * on portfolio objects.
 */

public class Portfolio {
  private String portfolioName;
  private List<Stock> containingStocks;

  /**
   * Constructs a Portfolio with a given name.
   *
   * @param name the name user want to give the newly created portfolio
   */
  Portfolio(String name) {
    portfolioName = name;
    containingStocks = new ArrayList<>();
  }

  /**
   * Constructs a Portfolio with a given name and list of stocks.
   *
   * @param name             the name of the portfolio
   * @param containingStocks the stocks containing in the portfolio
   */
  Portfolio(String name, List<Stock> containingStocks) {
    this.portfolioName = name;
    this.containingStocks = containingStocks;
  }

  /**
   * Returns the name of a portfolio.
   *
   * @return the name of a portfolio
   */
  public String getPortfolioName() {
    return portfolioName;
  }

  /**
   * Adds a stock into a list of stocks.
   *
   * @param stock the stock you want to add to the list of stocks
   */
  void addStock(Stock stock) throws IllegalArgumentException {
    containingStocks.add(stock);
  }

  /**
   * Saves this portfolio into an XML file to a given path.
   *
   * @param filePath the path user want to store XML file
   */
  void save(String filePath)
          throws ParserConfigurationException, FileNotFoundException, TransformerException {
    XMLWriter writer = new XMLWriter(filePath);
    Element root = writer.createDocumentElement("Portfolio");
    writer.createElement(root, "PortfolioName", this.portfolioName);
    for (Stock stock : this.containingStocks) {
      Element newStock = writer.createElement(root, "Stock", "");
      writer.createElement(newStock, "StockName", stock.getTickerSymbol());
      String text = null;
      if (stock.getBuyingDate() != null) {
        text = stock.getBuyingDate().toString();
      }
      writer.createElement(newStock, "BuyDate", text);
      writer.createElement(newStock, "BuyPrice", stock.getBuyingPrice() + "");
      writer.createElement(newStock, "BuyQuantity", stock.getQuantity() + "");
      writer.createElement(newStock, "CommissionFee", stock.getCommission() + "");
    }
    writer.toSave();
  }

  /**
   * Returns the composition of a portfolio as a string.
   *
   * @return the composition of a portfolio
   */
  List<String> showComposition() throws IllegalArgumentException {
    List<String> list = new ArrayList<>();
    for (Stock s : containingStocks) {
      list.add(s.getStockDetails());
    }
    return list;
  }

  /**
   * Returns the list of portfolio names as a string.
   *
   * @return the list of portfolio names as a string
   */
  public List<String> getContainingPortfolioNames() {
    List<String> list = new ArrayList<>();
    for (Stock s : containingStocks) {
      list.add(s.getTickerSymbol());
    }
    return list;
  }

  /**
   * Returns the cost basis of a portfolio on the given date.
   *
   * @param date the date user want to get the cost basis on
   * @return the cost basis of a portfolio on the given date
   */
  double getCostBasis(Date date) throws IllegalArgumentException {
    double totalBuyingPrice;
    totalBuyingPrice = 0;
    for (Stock eachStock : containingStocks) {
      if (eachStock.getBuyingDate() == null) {
        continue;
      }
      if (eachStock.getBuyingDate().before(date) || eachStock.getBuyingDate().equals(date)) {
        totalBuyingPrice = ((eachStock.getBuyingPrice() * eachStock.getQuantity())
                + eachStock.getCommission()) + totalBuyingPrice;
      }
    }
    return totalBuyingPrice;
  }

  /**
   * Returns the value of a portfolio on the given date.
   *
   * @param date the date user want to get the value on
   * @return the value of a portfolio on the given date
   */
  double getTotalValue(Date date) throws IllegalArgumentException {
    double totalValue;
    totalValue = 0;
    for (Stock eachStock : containingStocks) {
      if (eachStock.getBuyingDate() == null) {
        totalValue += (eachStock.getPrice(date) * eachStock.getQuantity());
        continue;
      }
      if (eachStock.getBuyingDate().before(date) || eachStock.getBuyingDate().equals(date)) {
        totalValue = (eachStock.getPrice(date) * eachStock.getQuantity()) + totalValue;
      }
    }
    return totalValue;
  }

  List<Stock> getStockList() {
    return containingStocks;
  }

}

package stockmanager.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class can read a XML file containing a portfolio data from the given path.
 */
class XMLReader {

  /**
   * Returns the portfolio object from the XML file containing a portfolio data from the given
   * path.
   *
   * @param filePath the path of XML file
   * @return the portfolio object from the XML file
   * @throws ParserConfigurationException if parse XML file fails
   * @throws SAXException                 if parse XML file fails
   * @throws IOException                  if get element fails
   * @throws ParseException               if parse date fails
   */
  Portfolio parseXML(String filePath)
          throws ParserConfigurationException, SAXException, IOException, ParseException {

    File xmlFile = new File(filePath);
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document doc = builder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    NodeList nodeList = doc.getElementsByTagName("Portfolio");

    Node nameNode = nodeList.item(0);
    Element ele = (Element) nameNode;

    String portfolioName = ele.getElementsByTagName("PortfolioName").item(0).getTextContent();
    List<Stock> containingStocks = getContainingStocks(ele.getElementsByTagName("Stock"));

    Portfolio newPortfolio = new Portfolio(portfolioName, containingStocks);
    return newPortfolio;
  }

  /**
   * Returns the strategy and its name from the XML file containing a strategy data from the given
   * path.
   *
   * @param filePath the path of XML file
   * @return the strategy and its name in a map
   * @throws ParserConfigurationException if parse XML file fails
   * @throws SAXException                 if parse XML file fails
   * @throws IOException                  if get element fails
   * @throws ParseException               if parse date fails
   */
  Map<String, Strategy> parseStrategyXML(String filePath)
          throws ParserConfigurationException, SAXException, IOException, ParseException {

    Map<String, Strategy> newStrategyMap = new HashMap<>();
    File xmlFile = new File(filePath);
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document doc = builder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    NodeList nodeList = doc.getElementsByTagName("Strategy");

    Node nameNode = nodeList.item(0);
    Element ele = (Element) nameNode;

    String theStrategyName = ele.getElementsByTagName("StrategyName")
            .item(0).getTextContent();
    String portfolioName = ele.getElementsByTagName("PortfolioName")
            .item(0).getTextContent();
    double amount = Double.valueOf(ele.getElementsByTagName("Amount")
            .item(0).getTextContent());
    double commission = Double.valueOf(ele.getElementsByTagName("CommissionFee")
            .item(0).getTextContent());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = null;
    Date endDate = null;
    String dateString1 = ele.getElementsByTagName("StartDate").item(0).getTextContent();
    String dateString2 = ele.getElementsByTagName("EndDate").item(0).getTextContent();
    if (!dateString1.equals("")) {
      dateString1 = formatDate(dateString1);
      startDate = sdf.parse(dateString1);
    }

    if (!dateString2.equals("")) {
      dateString2 = formatDate(dateString2);
      endDate = sdf.parse(dateString2);
    }

    Map<String, Double> stockWeights = getStockWeights(ele.getElementsByTagName("StockWeights"));
    int frequency = Integer
            .valueOf(ele.getElementsByTagName("Frequency").item(0).getTextContent());


    Strategy newStrategy = new Strategy(portfolioName, stockWeights, amount, commission,
            startDate, endDate, frequency);

    newStrategyMap.put(theStrategyName, newStrategy);
    return newStrategyMap;
  }

  /**
   * Returns the map of stock weights.
   *
   * @param nodeList all child nodes under portfolio node
   * @return the list of stocks in the portfolio
   * @throws ParseException if parse date fails
   */
  private Map<String, Double> getStockWeights(NodeList nodeList) throws ParseException {
    Map<String, Double> stockWeights = new HashMap<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      Element ele = (Element) node;
      if (node.getNodeType() == Element.ELEMENT_NODE) {
        String stockName = ele.getElementsByTagName("StockName").item(0).getTextContent();
        double stockWeight = Double.parseDouble(ele.getElementsByTagName("StockWeight")
                .item(0).getTextContent());
        stockWeights.put(stockName, stockWeight);
      }
    }
    return stockWeights;
  }

  /**
   * Returns the list of stocks in the portfolio.
   *
   * @param nodeList all child nodes under portfolio node
   * @return the list of stocks in the portfolio
   * @throws ParseException if parse date fails
   */
  private List<Stock> getContainingStocks(NodeList nodeList) throws ParseException {
    List<Stock> stocks = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      Element ele = (Element) node;
      if (node.getNodeType() == Element.ELEMENT_NODE) {
        String tickerSymbol = ele.getElementsByTagName("StockName").item(0).getTextContent();
        Date date = null;
        String dateString = ele.getElementsByTagName("BuyDate").item(0).getTextContent();
        if (!dateString.equals("")) {
          dateString = formatDate(dateString);
          date = sdf.parse(dateString);
        }
        double buyingPrice = Double.parseDouble(ele.getElementsByTagName("BuyPrice")
                .item(0).getTextContent());
        double quantity = Double.parseDouble(ele.getElementsByTagName("BuyQuantity")
                .item(0).getTextContent());
        double commission = Double.parseDouble(ele.getElementsByTagName("CommissionFee")
                .item(0).getTextContent());
        Stock newStock = new Stock(tickerSymbol, date, buyingPrice, quantity, commission);
        stocks.add(newStock);
      }
    }
    return stocks;
  }

  /**
   * Returns a string contains the right format of a date we need.
   *
   * @param dateString a string contains date information with wrong format
   * @return a string contains the right format of a date we need
   */
  private String formatDate(String dateString) {

    Map<String, String> months = new HashMap<>();
    months.put("Jan", "01");
    months.put("Feb", "02");
    months.put("Mar", "03");
    months.put("Apr", "04");
    months.put("May", "05");
    months.put("Jun", "06");
    months.put("Jul", "07");
    months.put("Aug", "08");
    months.put("Sep", "09");
    months.put("Oct", "10");
    months.put("Nov", "11");
    months.put("Dec", "12");

    String[] aStrings = dateString.split(" ");
    aStrings[1] = months.get(aStrings[1]);

    String date = aStrings[5] + "-" + aStrings[1] + "-" + aStrings[2];
    return date;
  }
}

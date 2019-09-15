package stockmanager.model;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This interface adds more new features to the persisting functionality, it can save and retrieve a
 * strategy.
 */
public interface EnhancedStockManagerModel extends PersistingStockManagerModel {

  /**
   * Uses weights to invest in an existing portfolio.
   *
   * @param portfolioName Name of the portfolio
   * @param stockWeights  stock weights
   * @param amount        the amoung user want ot use on invest
   * @param commission    commission fee
   * @param date          the date to invest
   * @throws IllegalArgumentException if the portfolio doesn't exist
   */
  void investUsingWeightsInExistingPortfolio(
          String portfolioName, Map<String, Double> stockWeights, double amount,
          Double commission, Date date) throws IllegalArgumentException;

  /**
   * Adds a strategy.
   *
   * @param strategyName  name of strategy
   * @param portfolioName Name of the portfolio
   * @param stockWeights  stock weights
   * @param amount        the amoung user want ot use on invest
   * @param commission    commission fee
   * @param startDate     the date to start
   * @param endDate       the date to end
   * @param freq          buying frequency
   * @throws IllegalArgumentException if the portfolio doesn't exist
   */
  void addStrategy(String strategyName, String portfolioName, Map<String, Double> stockWeights,
                   double amount, double commission, Date startDate, Date endDate, int freq)
          throws IllegalArgumentException;

  /**
   * Saves an strategy as an XML file.
   *
   * @param strategyName Name of the strategy being saved
   * @param filePath     path to save strategy
   * @throws IllegalArgumentException     if the portfolio doesn't exist
   * @throws ParserConfigurationException if parse configuration fails
   * @throws FileNotFoundException        if file not found
   * @throws TransformerException         if transform fails
   */
  void saveStrategy(String strategyName, String filePath)
          throws IllegalArgumentException, ParserConfigurationException, FileNotFoundException,
          TransformerException;

  /**
   * Returns a strategy read from an XML file.
   *
   * @param filePath path of the XML file
   * @throws IllegalArgumentException     if the XML file could not be found
   * @throws ParserConfigurationException if parse configuration fails
   * @throws SAXException                 if parse XML fails
   * @throws FileNotFoundException        if file not found
   * @throws IOException                  if any exception happens
   * @throws ParseException               if parse fails
   */
  void extractStrategyFromXML(String filePath)
          throws IllegalArgumentException, ParserConfigurationException, SAXException,
          IOException, ParseException;

  /**
   * Gets a specific strategy by its name.
   *
   * @param strategyName Name of the strategy being saved
   * @return returns the strategy
   * @throws IllegalArgumentException if the strategy doesn't exist
   */
  Strategy getSpecificStrategy(String strategyName) throws IllegalArgumentException;
}

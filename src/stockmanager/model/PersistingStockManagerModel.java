package stockmanager.model;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This interface is to save portfolio data into a XML file and retrieve a portfolio from a XMl
 * file.
 */
public interface PersistingStockManagerModel extends StockManagerModel {
  /**
   * Saves an portfolio as an XML file. *
   *
   * @param portfolioName Name of the portfolio being saved
   * @param filePath      the path user want to store this XML file
   * @throws IllegalArgumentException     if the portfolio doesn't exist
   * @throws ParserConfigurationException if parse configuration fails
   * @throws FileNotFoundException        if file not found
   * @throws TransformerException         if transform fails
   */
  void savePortfolio(String portfolioName, String filePath)
          throws IllegalArgumentException, ParserConfigurationException, FileNotFoundException,
          TransformerException;

  /**
   * Returns a portfolio read from an XML file.
   *
   * @param filePath path of the XML file
   * @throws IllegalArgumentException     if the XML file could not be found
   * @throws ParserConfigurationException if parse configuration fails
   * @throws SAXException                 if parse XML fails
   * @throws FileNotFoundException        if file not found
   * @throws IOException                  if any exception happens
   * @throws ParseException               if parse fails
   */
  void extractPortfolioFromXML(String filePath)
          throws IllegalArgumentException, ParserConfigurationException, SAXException,
          IOException, ParseException;
}

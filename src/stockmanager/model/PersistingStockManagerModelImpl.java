package stockmanager.model;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class is to save portfolio data into a XML file and retrieve a portfolio from a XMl file.
 */
public class PersistingStockManagerModelImpl extends StockManagerModelImpl
        implements PersistingStockManagerModel {

  public PersistingStockManagerModelImpl(GenericStockDataFetcher fetcher) {
    super(fetcher);
  }

  @Override
  public void savePortfolio(String portfolioName, String filePath)
          throws IllegalArgumentException, ParserConfigurationException, FileNotFoundException,
          TransformerException {
    getSpecificPortfolio(portfolioName).save(filePath + portfolioName + ".xml");
  }

  @Override
  public void extractPortfolioFromXML(String filePath)
          throws IllegalArgumentException, ParserConfigurationException, SAXException,
          IOException, ParseException {

    XMLReader reader = new XMLReader();
    Portfolio newPortfolio = reader.parseXML(filePath);
    allPortfolios.add(newPortfolio);
  }

}

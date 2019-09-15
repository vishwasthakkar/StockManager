import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import stockmanager.model.AlphaVantageAPI;
import stockmanager.model.GenericStockDataFetcher;
import stockmanager.model.PersistingStockManagerModel;
import stockmanager.model.PersistingStockManagerModelImpl;

import static org.junit.Assert.fail;

/**
 * A JUnit class to test the persisting functionality.
 */
public class PersistingStockManagerModelImplTest {
  private PersistingStockManagerModel model;

  //FIXME:
  // Test whatever data is writtern to XML such that test read the data from xml and then
  // validate it.
  @Before
  public void setUp() {
    GenericStockDataFetcher fetcher;
    fetcher = new AlphaVantageAPI();
    model = new PersistingStockManagerModelImpl(fetcher);
  }

  @Test
  public void testSave() {
    model.createPortfolio("ABC");
    model.addStocksToPortfolio("ABC", "GOOG", 100);
    model.buyStocksUsingAmount("ABC", "AAPL", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");
      // if file object does not find the specified file exception will be thrown
      File file = new File("/Users/joe/Desktop/ABC.xml");
    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {
      fail("The above line should not have thrown an exception");
      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testRetrieve() {
    model.createPortfolio("ABC");
    model.addStocksToPortfolio("ABC", "GOOG", 100);
    model.buyStocksUsingAmount("ABC", "AAPL", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");
      model.extractPortfolioFromXML("/Users/joe/Desktop/");

    } catch (Exception e) {
      fail("The above line should not have thrown an exception");
      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testSaveExceptions() {
    model.createPortfolio("XYZ");
    model.addStocksToPortfolio("XYZ", "GOOG", 100);
    model.buyStocksUsingAmount("XYZ", "AAPL", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    fail("The above line should have thrown an exception");
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");

    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {

      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testRetrieveExceptions() {
    model.createPortfolio("ABC");
    model.addStocksToPortfolio("ABC", "GOOG", 100);
    model.buyStocksUsingAmount("ABC", "AAPL", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");
      model.extractPortfolioFromXML("/Users/joe/");
      fail("The above line should have thrown an exception");
    } catch (Exception e) {

      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testMultiplePortfolioSave() {
    model.createPortfolio("ABC");
    model.createPortfolio("XYZ");
    model.addStocksToPortfolio("ABC", "GOOG", 100);
    model.buyStocksUsingAmount("XYZ", "MSFT", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");
      model.savePortfolio("XYZ", "/Users/joe/Desktop/");
      // if file object does not find the specified file exception will be thrown
      File file1 = new File("/Users/joe/Desktop/ABC.xml");
      File file2 = new File("/Users/joe/Desktop/XYZ.xml");


    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {
      fail("The above line should not have thrown an exception");
      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testMultiplePortfolioRetrieve() {
    model.createPortfolio("ABC");
    model.createPortfolio("XYZ");
    model.addStocksToPortfolio("ABC", "GOOG", 100);
    model.buyStocksUsingAmount("XYZ", "MSFT", 5000, 5,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime());
    try {
      model.savePortfolio("ABC", "/Users/joe/Desktop/");
      model.savePortfolio("XYZ", "/Users/joe/Desktop/");

      // if file object does not find the specified file exception will be thrown
      File file1 = new File("/Users/joe/Desktop/ABC.xml");
      File file2 = new File("/Users/joe/Desktop/XYZ.xml");

      model.extractPortfolioFromXML("/Users/joe/Desktop/ABC.xml");
      model.extractPortfolioFromXML("/Users/joe/Desktop/ABC.xml");

    } catch (Exception e) {
      fail("The above line should not have thrown an exception");
      //do not do anything except catch the exception and let the test continue
    }
  }

  @Test
  public void testPastModel() {
    model.createPortfolio("FANG");
//    model.buyStocksUsingAmount();
  }

}



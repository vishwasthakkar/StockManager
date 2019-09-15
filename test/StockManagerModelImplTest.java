import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import stockmanager.model.AlphaVantageAPI;
import stockmanager.model.GenericStockDataFetcher;
import stockmanager.model.StockManagerModel;
import stockmanager.model.StockManagerModelImpl;

/**
 * A JUnit class to test the model.
 */
public class StockManagerModelImplTest {
  GenericStockDataFetcher fetcher;
  private StockManagerModel model;

  @Before
  public void setUp() {
    fetcher = new AlphaVantageAPI();
    model = new StockManagerModelImpl(fetcher);
  }

  @Test
  public void testModelConstructor() {
    model.createPortfolio("Nifty");
    assertEquals("'Nifty' portfolio contains:\n" + "\n" + "EMPTY : You currently "
                    + "have no stocks in this portfolio. ",
            model.getPortfolioComposition("Nifty"));
  }

  @Test
  public void testAddPortfolio() {
    model.createPortfolio("Nifty");
    model.createPortfolio("DowJones");
    assertEquals("'Nifty' portfolio contains:\n" + "\n" + "EMPTY : You currently "
                    + "have no stocks in this portfolio. "
            , model.getPortfolioComposition("Nifty"));
    assertEquals("'DowJones' portfolio contains:\n" +
                    "\n" + "EMPTY : You currently have no stocks in this portfolio. "
            , model.getPortfolioComposition("DowJones"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddPortfolioExceptionInvalidName() {
    StockManagerModel model = new StockManagerModelImpl(fetcher);
    //space not allowed in portfolio name
    model.createPortfolio("Dow Jones");
  }

  @Test
  public void testCreateMultiplePortfoliosOfSameName() {
    try {
      model.createPortfolio("First");
      model.buyStocksUsingAmount("First", "GOOG", 1000, 100
              , new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
      model.createPortfolio("First");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("This portfolio already exists.", ex.getMessage());
    }
  }

  @Test
  public void getAllPortfolios() {
    model.createPortfolio("First");
    model.createPortfolio("Second");
    model.createPortfolio("Third");
    String expected = "List of Portfolios created:\n" +
            "Second\n" +
            "Third\n" +
            "First\n";
    assertEquals(expected, model.getAllPortfolios());
  }
  

  @Test
  public void testBuyStock() {

    model.createPortfolio("First");
    model.buyStocksUsingAmount("First", "GOOG", 1000, 100
            , new GregorianCalendar(2015, Calendar.MARCH, 11).getTime());
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String temp;
    List<String> temp1 = model.getPortfolioComposition("First");

    StringBuilder sb = new StringBuilder();
    for (String s : temp1) {
      sb.append(s);
    }
    String sb1 = sb.toString();
    //asserting that the stock composition has data of the stock bought (except for current price
    // which we cannot assert because it keeps changing)
    assertTrue(sb1.contains("GOOG"));
    assertTrue(sb1.contains("100"));
    assertTrue(sb1.contains("Wed Mar 11 00:00:00 EDT 2015"));
    assertTrue(sb1.contains("551.18"));
  }

  @Test
  public void testBuyStockInvalidTickerSymbol() {
    try {

      model.createPortfolio("First");
      model.buyStocksUsingAmount("First", "xyz", 1000, 100
              , new GregorianCalendar(2015, Calendar.MARCH, 11).getTime());
      fail();
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
    }
  }


  @Test
  public void testStockInvalidQuantity() {
    try {

      model.createPortfolio("First");
      model.buyStocksUsingAmount("First", "AAPL", -1000, 10
              , new GregorianCalendar(2015, Calendar.MARCH, 11).getTime());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Quantity.", ex.getMessage());
    }
  }

  @Test
  public void testMarketNotOpenToBuy() {
    // Used a date on which market was closed to test the situation
    try {

      model.createPortfolio("ABC");
      model.buyStocksUsingAmount("ABC", "GOOG", 6000, 100,
              new GregorianCalendar(2019, Calendar.MARCH, 17).getTime());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("\nMARKET CLOSED : The market is/was closed on this date.\n"
              , ex.getMessage());
    }
  }

  @Test
  public void testStockInvalidDateAfter() {
    // Invalid date: greater than today used
    try {

      model.createPortfolio("ABC");
      model.buyStocksUsingAmount("ABC", "GOOG", 1000, 1100,
              new GregorianCalendar(2019, Calendar.APRIL, 17).getTime());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Purchase date."
              , ex.getMessage());
    }
  }

  @Test
  public void testStockInvalidDateBefore() {
    // Invalid date: date used at which stock data is not available from the API
    try {

      model.createPortfolio("ABC");
      model.buyStocksUsingAmount("ABC", "GOOG", 600, 10,
              new GregorianCalendar(2013, Calendar.APRIL, 17).getTime());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Purchase date."
              , ex.getMessage());
    }
  }

  @Test
  public void testExamineCompositionOfEmptyPortfolio() {

    model.createPortfolio("ABC");
    assertEquals("'ABC' portfolio contains:\n" +
                    "\nEMPTY : You currently have no stocks in this portfolio. "
            , model.getPortfolioComposition("ABC"));
  }

  @Test
  public void testExamineCompositionOfPortfolioIllegalName() {
    try {

      model.createPortfolio("ABC");
      assertEquals("'ABC' portfolio contains:\n" +
                      "\nEMPTY : You currently have no stocks in this portfolio. "
              , model.getPortfolioComposition("ABCD"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("The portfolio 'ABCD' does not exist.", ex.getMessage());
    }
  }

  @Test
  public void testExamineCompositionOfPortfolio() {

    model.createPortfolio("ABC");
    model.buyStocksUsingAmount("ABC", "GOOG", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 18).getTime());

    //asserting that the stock composition has data of the stock bought (except for current price
    // which we cannot assert because it keeps changing)
    List<String> temp = model.getPortfolioComposition("ABC");
    assertTrue(temp.contains("GOOG"));
    assertTrue(temp.contains("1184.26"));
    assertTrue(temp.contains("6.0"));
    assertTrue(temp.contains("Mon Mar 18 00:00:00 EDT 2019"));
  }

  @Test
  public void testCreatingMultiplePortfolios() {
    try {

      model.createPortfolio("DowJones");
      model.buyStocksUsingAmount("DowJones", "GOOG", 6000, 900,
              new GregorianCalendar(2019, Calendar.MARCH, 18).getTime());
      model.createPortfolio("DowJones");
      model.createPortfolio("Nifty");
      model.createPortfolio("BSE");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("This portfolio already exists.", ex.getMessage());
    }
  }

  @Test
  public void testBuyingMultipleStocksInOnePortfolio() {

    model.createPortfolio("DowJones");
    model.buyStocksUsingAmount("DowJones", "GOOG", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    model.buyStocksUsingAmount("DowJones", "AAPL", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    List<String> temp = model.getPortfolioComposition("DowJones");
    //tests all the stock data from composition
    assertTrue(temp.contains("GOOG"));
    assertTrue(temp.contains("1184.46"));
    assertTrue(temp.contains("600.0"));
    assertTrue(temp.contains("Mar 15 00:00:00 EDT 2019"));
    assertTrue(temp.contains("AAPL"));
    assertTrue(temp.contains("186.12"));
    assertTrue(temp.contains("60.0"));
    assertTrue(temp.contains("Mar 15 00:00:00 EDT 2019"));
  }

  @Test
  public void testBuyingMultipleStocksInMultiplePortfolio() {
    //This test calls API more than 5 times in a minute which causes the API to does not return
    //any stock data.
    // We used Thread.sleep(10000) in the API to delay the fetches and hence get correct stock data

    model.createPortfolio("First");
    model.createPortfolio("Second");
    model.buyStocksUsingAmount("First", "GOOG", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    model.buyStocksUsingAmount("First", "AAPL", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    model.buyStocksUsingAmount("Second", "MSFT", 6000, 900,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    model.buyStocksUsingAmount("Second", "MSFT", 8000, 700,
            new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());

    System.out.println(model.getPortfolioComposition("First"));
    System.out.println(model.getPortfolioComposition("Second"));
    List<String> temp1 = model.getPortfolioComposition("First");
    List<String> temp2 = model.getPortfolioComposition("Second");
    //tests all the stock data from composition
    assertTrue(temp1.contains("GOOG"));
    assertTrue(temp1.contains("1184.46"));
    assertTrue(temp1.contains("600.0"));
    assertTrue(temp1.contains("Mar 15 00:00:00 EDT 2019"));
    assertTrue(temp1.contains("AAPL"));
    assertTrue(temp1.contains("186.12"));
    assertTrue(temp1.contains("60.0"));
    assertTrue(temp1.contains("Mar 15 00:00:00 EDT 2019"));

    assertTrue(temp2.contains("MSFT"));
    assertTrue(temp2.contains("115.91"));
    assertTrue(temp2.contains("600.0"));
    assertTrue(temp2.contains("Mar 15 00:00:00 EDT 2019"));
    assertTrue(temp2.contains("MSFT"));
    assertTrue(temp2.contains("115.91"));
    assertTrue(temp2.contains("800.0"));
    assertTrue(temp2.contains("Mar 15 00:00:00 EDT 2019"));
  }

  @Test
  public void testInvalidTickerSymbol() {
    try {
      model.createPortfolio("First");
      model.buyStocksUsingAmount("First", "XYZ", 6000, 900,
              new GregorianCalendar(2019, Calendar.MARCH, 15).getTime());
    } catch (IllegalArgumentException ex) {
      assertEquals("INVALID TICKER : The ticker symbol does not exist ", ex.getMessage());
    }
  }

  @Test
  public void testTotalCostBasisOnADate() {
    model.createPortfolio("First");
    model.buyStocksUsingAmount("First", "GOOG", 1000, 100
            , new GregorianCalendar(2015, Calendar.MARCH, 11).getTime());
    model.buyStocksUsingAmount("First", "AAPL", 2000, 900
            , new GregorianCalendar(2018, Calendar.MARCH, 26).getTime());

    assertEquals(89672.0, model.totalCostBasisOfPortfolio("First"
            , new GregorianCalendar(2018, Calendar.MARCH, 26).getTime()), 0.001);
    assertEquals(55117.99999999999, model.totalCostBasisOfPortfolio("First"
            , new GregorianCalendar(2016, Calendar.MARCH, 25).getTime()), 0.001);
  }


  @Test
  public void testMultipleBuys() {
    model.createPortfolio("abc");
    model.buyStocksUsingAmount("abc", "GOOG", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 22).getTime());
    model.buyStocksUsingAmount("abc", "GOOG", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    model.buyStocksUsingAmount("abc", "AAPL", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    model.buyStocksUsingAmount("abc", "AAPL", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    System.out.println(model.getPortfolioComposition("abc"));
  }
}
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import stockmanager.model.AlphaVantageAPI;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.model.EnhancedStockManagerModelImpl;
import stockmanager.model.GenericStockDataFetcher;

/**
 * A JUnit class to test the EnhancedStockManagerModelImpl model.
 */
public class EnhancedStockManagerModelImplTest {

  @Test
  public void testAddingInvestmentStrategyToExisting() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("abc");
    model.buyStocksUsingAmount("abc", "VZ", 10000, 1000,
            new GregorianCalendar(2018, Calendar.FEBRUARY, 18).getTime());
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("VZ", 50.0);
    model.investUsingWeightsInExistingPortfolio("abc", map, 1000,
            100.0,
            new GregorianCalendar(2018, Calendar.DECEMBER, 20).getTime());

    double d = model.totalCostBasisOfPortfolio("abc",
            new GregorianCalendar(2018, Calendar.DECEMBER, 21).getTime());
    assertEquals(10500.000, d, 0.001);
  }

  @Test
  public void testAddingStrategiesToNewPortfolio() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("abc");
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("MSFT", 50.0);
    map.put("AAPL", 50.0);
    model.addStrategy("First", "abc", map, 10000, 100,
            new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2019, Calendar.FEBRUARY, 2).getTime(),
            10);
    double costBasis = model.totalCostBasisOfPortfolio("abc", new Date());
    double value = model.totalValueOfPortfolio("abc", new GregorianCalendar(2019,
            Calendar.FEBRUARY, 2).getTime());
    assertEquals(40000, costBasis, 0.001);
    assertEquals(41789.378, value, 0.001);

  }

  @Test
  public void testInvestmentWithWeights() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("abc");
    model.buyStocksUsingAmount("abc", "GOOG", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    model.buyStocksUsingAmount("abc", "AAPL", 1000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("GOOG", 50.0);
    map.put("AAPL", 50.0);
    model.investUsingWeightsInExistingPortfolio("abc", map, 1000,
            100.0,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    double costBasis = model.totalCostBasisOfPortfolio("abc",
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());

    assertEquals(12000, costBasis, 0.001);

  }

  @Test
  public void testWeightedInvestmentWithMoreThanThreeStocks() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("First");
    model.buyStocksUsingAmount("First", "GOOG", 10000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    model.buyStocksUsingAmount("First", "AAPL", 1000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    model.buyStocksUsingAmount("First", "MSFT", 1000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    model.buyStocksUsingAmount("First", "VZ", 1000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    model.buyStocksUsingAmount("First", "T", 1000, 1000,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("GOOG", 20.0);
    map.put("AAPL", 20.0);
    map.put("MSFT", 20.0);
    map.put("VZ", 20.0);
    map.put("T", 20.0);
    model.investUsingWeightsInExistingPortfolio("First", map, 1000,
            100.0,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime());
    double costBasis = model.totalCostBasisOfPortfolio("First",
            new GregorianCalendar(2019, Calendar.FEBRUARY, 21).getTime());

    assertEquals(15000, costBasis, 0.001);
  }

  @Test
  public void testWeightedInvestmentInPortfolioExceptions() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("xyz");
    try {
      Map<String, Double> map = new HashMap<String, Double>();
      map.put("VZ", 20.0);
      map.put("T", 80.0);
      model.investUsingWeightsInExistingPortfolio("xy", map, -100, 0.0,
              new Date());
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      Map<String, Double> map = new HashMap<String, Double>();
      map.put("VZ", 20.0);
      map.put("T", 80.0);
      model.investUsingWeightsInExistingPortfolio("xy", map, 100, -1.0,
              new Date());
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      Map<String, Double> map = new HashMap<String, Double>();
      map.put("VZ", 20.0);
      map.put("T", 80.0);
      model.investUsingWeightsInExistingPortfolio("xy", map, 100, -1.0,
              new GregorianCalendar(2019, Calendar.DECEMBER, 20).getTime());
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

  }

  @Test
  public void testWeightedInvestmentInPortfolioValue() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("ABC");
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("VZ", 20.0);
    map.put("T", 60.0);
    map.put("MSFT", 20.0);
    model.investUsingWeightsInExistingPortfolio("ABC", map, 1000000,
            0.0, new Date());

    assertEquals(1000000, model.totalCostBasisOfPortfolio("ABC", new Date()),
            0.001);
  }

  @Test
  public void testStrategyCostBasis() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("ABC");
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("VZ", 20.0);
    map.put("T", 60.0);
    map.put("MSFT", 20.0);
    model.addStrategy("QWERTY", "ABC", map, 1000, 100,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime(),
            new Date(), 10);

    assertEquals(6000, model.totalCostBasisOfPortfolio("ABC", new Date()),
            0.001);
  }

  @Test
  public void testStrategyValue() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("ABC");
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("VZ", 20.0);
    map.put("T", 60.0);
    map.put("MSFT", 20.0);
    model.addStrategy("QWERTY", "ABC", map, 1000, 100,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime(),
            new Date(), 10);

    assertEquals(5596.3251, model.totalValueOfPortfolio("ABC", new Date()),
            0.001);
  }

  @Test
  public void testExtensive() {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);
    model.createPortfolio("ABC");
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("VZ", 15.0);
    map.put("T", 45.0);
    map.put("AAPL", 20.0);
    map.put("GOOG", 5.0);
    map.put("MSFT", 15.0);
    model.addStrategy("XYZ", "ABC", map, 1000, 90,
            new GregorianCalendar(2018, Calendar.FEBRUARY, 20).getTime(),
            new GregorianCalendar(2018, Calendar.JULY, 20).getTime(),
            15);

    assertEquals(2000, model.totalCostBasisOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MARCH, 15).getTime()), 0.001);
    assertEquals(8000, model.totalCostBasisOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.JUNE, 10).getTime()), 0.001);
    assertEquals(6000, model.totalCostBasisOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MAY, 10).getTime()), 0.001);

    assertEquals(1, model.totalValueOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MARCH, 15).getTime()), 0.001);
    assertEquals(1, model.totalValueOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.JUNE, 10).getTime()), 0.001);
    assertEquals(1, model.totalValueOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MAY, 10).getTime()), 0.001);

    model.addStrategy("XYZ", "ABC", map, 100, 90,
            new GregorianCalendar(2018, Calendar.JUNE, 20).getTime(),
            new GregorianCalendar(2018, Calendar.SEPTEMBER, 20).getTime(),
            15);

    assertEquals(1, model.totalCostBasisOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MARCH, 15).getTime()), 0.001);
    assertEquals(1, model.totalValueOfPortfolio("ABC",
            new GregorianCalendar(2018, Calendar.MAY, 10).getTime()), 0.001);
  }
}
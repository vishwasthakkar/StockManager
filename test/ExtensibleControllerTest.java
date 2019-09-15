import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import stockmanager.controller.ExtensibleController;
import stockmanager.controller.IController;
import stockmanager.model.AlphaVantageAPI;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.model.EnhancedStockManagerModelImpl;
import stockmanager.model.GenericStockDataFetcher;
import stockmanager.view.HomeView;
import stockmanager.view.IView;

import static org.junit.Assert.assertTrue;

/**
 * A JUnit class to test the commands of the controller.
 */
public class ExtensibleControllerTest {
  private EnhancedStockManagerModel model;
  private StringBuffer out;
  GenericStockDataFetcher fetcher;

  @Before
  public void setUp() {
    fetcher = new AlphaVantageAPI();
    model = new EnhancedStockManagerModelImpl(fetcher);
  }

  @Test
  public void testCreatePortfolio() {

    Reader in = new StringReader("create ABC q");
    out = new StringBuffer();
    IView view = new HomeView(in, out);
    IController controller = new ExtensibleController(model, view);
    controller.goController();
    assertTrue(out.toString().contains("uccessfully executed: create"));
  }

  @Test
  public void testBuyStocks() {
    Reader in = new StringReader("create ABC\nbuy ABC GOOG 100 2019-02-19 q");
    out = new StringBuffer();
    IView view = new HomeView(in, out);
    IController controller = new ExtensibleController(model, view);
    controller.goController();
    assertTrue(out.toString().contains("Successfully executed: buy"));
  }

  @Test
  public void testShowPortfolio() {
    Reader in = new StringReader("create ABC\nbuy ABC GOOG 100 2019-02-19\nshow ABC q");
    out = new StringBuffer();
    IView view = new HomeView(in, out);
    IController controller = new ExtensibleController(model, view);
    controller.goController();
    System.out.println(out.toString());
    assertTrue(out.toString().contains("Successfully executed: show"));
  }

  @Test
  public void testGetValue() {
    Reader in = new StringReader("create ABC\nbuy ABC GOOG 100 2019-02-19\n"
            + "getvalue ABC 2019-02-19 q");
    out = new StringBuffer();
    IView view = new HomeView(in, out);
    IController controller = new ExtensibleController(model, view);
    controller.goController();
    assertTrue(out.toString().contains("Successfully executed: getvalue"));
  }

  @Test
  public void testGetCost() {
    Reader in = new StringReader("create ABC\nbuy ABC GOOG 100 2019-02-19\n"
            + "getcost ABC 2019-02-19 q");
    out = new StringBuffer();
    IView view = new HomeView(in, out);
    IController controller = new ExtensibleController(model, view);
    controller.goController();
    assertTrue(out.toString().contains("Successfully executed: getcost"));
  }

}
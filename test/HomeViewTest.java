import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import stockmanager.controller.IController;
import stockmanager.controller.MockController;
import stockmanager.view.HomeView;
import stockmanager.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit class to test the HomeView.
 */
public class HomeViewTest {

  @Test
  public void testShowMethodConstructor() {
    //check if Appendable and Readable objects are passed correctly
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Hello");
    IView view = new HomeView(in, out);
    view.showMessage(view.getInput());
    assertEquals("Hello\n", out.toString());
  }


  @Test
  public void testShowMessageConstructor() {
    //check if Appendable and Readable objects are passed correctly
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("");
    IView view = new HomeView(in, out);
    view.showMessage("Hello");
    assertEquals("Hello\n", out.toString());
  }

  @Test
  public void testGetInput() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Hello");
    IView view = new HomeView(in, out);
    String str = view.getInput();
    assertEquals("Hello", str);
  }

  //MockController tests start from here
  @Test
  public void testGoUsingMockController() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("HelloThere!");
    StringBuffer log = new StringBuffer();
    IView view = new HomeView(in, out);
    IController mockController = new MockController(log, view);
    mockController.goController();
    assertEquals("HelloThere!", log.toString());
  }

  @Test
  public void testShowMessageUsingMockController() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("HelloThere!");
    StringBuffer log = new StringBuffer();
    IView view = new HomeView(in, out);
    IController mockController = new MockController(log, view);
    mockController.goController();
    assertEquals("122323343443435\n", out.toString());
  }

  @Test
  public void testShowUsingMockController() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("HelloThere!");
    StringBuffer log = new StringBuffer();
    IView view = new HomeView(in, out);
    IController mockController = new MockController(log, view);
    mockController.processCommand("efuhf");
    assertEquals("HelloThere!", log.toString());
  }
}
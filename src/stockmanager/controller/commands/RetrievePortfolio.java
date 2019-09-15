package stockmanager.controller.commands;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to retrieve a portfolio given a path.
 */
public class RetrievePortfolio implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a RetrievePortfolio with a given IView.
   *
   * @param view the given IView
   */
  public RetrievePortfolio(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String path;

    view.showMessage("To save a particular portfolio to a location:\n" +
            "NOTE:\n" +
            "1) Enter the location where you have stored the xml file along with its name.\n" +
            "2) For Windows, the location should be like:\n" +
            "\t\teg: C:\\Users\\ABC\\Desktop\\Nifty.xml\n" +
            "3) For Mac OS, the location should be like:\n" +
            "\t\teg: /Users/joe/Desktop/Nifty.xml\n" +
            "4) The portfolio can be created manually be the user.\n" +
            "5) The name of the xml file should be the same as portfolio name.\n" +
            "Enter the path:");

    path = view.getInput();
    view.showMessage(" Portfolio retrieved successfully from " + path);
    try {
      m.extractPortfolioFromXML(path);
    } catch (IllegalArgumentException | ParserConfigurationException | SAXException |
            IOException | ParseException e) {
      view.showMessage(e.getMessage());
    }
  }
}

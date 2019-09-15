package stockmanager.controller.commands;

import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to save a portfolio given a path.
 */
public class SavePortfolio implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a SavePortfolio with a given IView.
   *
   * @param view the given IView
   */
  public SavePortfolio(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    String path;


    view.showMessage("To save a particular portfolio to a location:\n" +
            "NOTE:\n" +
            "1) Enter the name of portfolio that already exists.\n" +
            "2) Enter the location where you want to save the portfolio.\n" +
            "3) For Windows, the location should be like:\n" +
            "\t\teg: C:\\Users\\ABC\\Desktop\\\n" +
            "4) For Mac OS, the location should be like:\n" +
            "\t\teg: /Users/joe/Desktop/\n" +
            "5) The portfolio will be saved as an XML file format.\n" +
            "6) The name of the xml file will be the same as portfolio name.\n");
    portfolioName = view.getInput();
    path = view.getInput();
    view.showMessage(portfolioName + " saved successfully");
    try {
      m.saveStrategy(portfolioName, path);
    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {
      view.showMessage(e.getMessage());
    }
  }
}

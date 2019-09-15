package stockmanager.controller.commands;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;


import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to retrieve a strategy from XML file.
 */
public class RetrieveStrategy implements StockManagerCommand {
  private IView view;

  /**
   * Constructs a SavePortfolio with a given IView.
   *
   * @param view the given IView
   */
  public RetrieveStrategy(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String path;


    path = view.getInput();
    view.showMessage("Retrieved successfully");
    try {
      m.extractStrategyFromXML(path);
    } catch (IllegalArgumentException | ParserConfigurationException | SAXException |
            IOException | ParseException e) {
      view.showMessage(e.getMessage());
    }
  }
}

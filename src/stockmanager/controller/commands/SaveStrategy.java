package stockmanager.controller.commands;

import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to save a strategy to a XML file.
 */
public class SaveStrategy implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a SavePortfolio with a given IView.
   *
   * @param view the given IView
   */
  public SaveStrategy(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String strategyName;
    String path;

    strategyName = view.getInput();
    path = view.getInput();
    view.showMessage(strategyName + " saved successfully");
    try {
      m.saveStrategy(strategyName, path);
    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {
      view.showMessage(e.getMessage());
    }
  }
}

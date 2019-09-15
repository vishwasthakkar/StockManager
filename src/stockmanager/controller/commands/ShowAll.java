package stockmanager.controller.commands;

import java.util.List;

import stockmanager.controller.StockManagerCommand;

import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to show all the portfolios added by the user.
 */
public class ShowAll implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a ShowAll with a given IView.
   *
   * @param view the given IView
   */
  public ShowAll(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    List<String> all = m.getAllPortfolios();
    StringBuilder sb = new StringBuilder();
    sb.append("\nThe portfolios that are added:\n\n");
    for (String p : all) {
      sb.append(p);
      sb.append("\n");
    }
    view.showMessage(sb.toString());
  }
}
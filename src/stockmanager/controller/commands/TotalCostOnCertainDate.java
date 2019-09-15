package stockmanager.controller.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to get total cost of a certain portfolio on a
 * certain date.
 */
public class TotalCostOnCertainDate implements StockManagerCommand {

  private IView view;

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Constructs a TotalCostOnCertainDate with a given IView.
   *
   * @param view the given IView
   */
  public TotalCostOnCertainDate(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    Date date;
    try {
      view.showMessage("You can get the total cost basis of a portfolio at a certain date."
              + " Please type the name of portfolio and the date formatted as YYYY-MM-DD.");
      portfolioName = view.getInput();
      try {
        date = SDF.parse(view.getInput());
        double d = m.totalCostBasisOfPortfolio(portfolioName, date);
        view.showMessage(String.valueOf(d));
      } catch (ParseException e) {
        view.showMessage("The date format is wrong.");
      }
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}

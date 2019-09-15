package stockmanager.controller.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to get total value of a certain portfolio on a
 * certain date.
 */
public class TotalValueOnCertainDate implements StockManagerCommand {

  private IView view;

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Constructs a TotalValueOnCertainDate with a given IView.
   *
   * @param view the given IView
   */
  public TotalValueOnCertainDate(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    Date date;
    try {
      view.showMessage("You can get the total value of a portfolio at a certain date."
              + " Please type the name of portfolio and the date formatted as YYYY-MM-DD.");
      portfolioName = view.getInput();
      try {
        date = SDF.parse(view.getInput());
        double d = m.totalValueOfPortfolio(portfolioName, date);
        view.showMessage(String.valueOf(d));
      } catch (ParseException e) {
        view.showMessage("The date format is wrong.");
      }
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}

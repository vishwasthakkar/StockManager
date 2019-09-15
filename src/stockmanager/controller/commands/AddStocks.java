package stockmanager.controller.commands;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to add stocks into a certain portfolio.
 */
public class AddStocks implements StockManagerCommand {
  private IView view;

  /**
   * Constructs a AddStocks with a given IView.
   *
   * @param view the given IView
   */
  public AddStocks(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    String stockName;
    double stockQuantity;

    try {
      view.showMessage(" You can add stock to a portfolio without buying: Enter the portfolio name,"
              + " ticker symbol and the quantity of the portfolio");
      portfolioName = view.getInput();
      stockName = view.getInput();
      stockQuantity = Double.parseDouble(view.getInput());
      m.addStocksToPortfolio(portfolioName, stockName, stockQuantity);
      view.showMessage("Stocks added successfully.");
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}
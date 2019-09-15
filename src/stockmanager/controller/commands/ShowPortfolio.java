package stockmanager.controller.commands;


import java.util.List;

import stockmanager.controller.StockManagerCommand;

import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to show composition of a certain portfolio.
 */
public class ShowPortfolio implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a ShowPortfolio with a given IView.
   *
   * @param view the given IView
   */
  public ShowPortfolio(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    try {
      view.showMessage("Show the composition of a portfolio. Type the name of portfolio.");
      portfolioName = view.getInput();
      List<String> portfolioComposition = m.getPortfolioComposition(portfolioName);
      StringBuilder sb = new StringBuilder();
      sb.append("'");
      sb.append(portfolioName);
      sb.append("' portfolio contains:\n\n");

      for (String stock : portfolioComposition) {
        String[] array = stock.split(", ");
        sb.append("Ticker: ");
        sb.append(array[0]);
        sb.append("\n");
        sb.append("BuyingPrice: ");
        sb.append(array[1]);
        sb.append("\n");
        sb.append("Qty: ");
        sb.append(array[2]);
        sb.append("\n");
        sb.append("Commission: ");
        sb.append(array[3]);
        sb.append("\n");
        sb.append("CurrentPrice: ");
        sb.append(array[4]);
        sb.append("\n");
        sb.append("Date: ");
        sb.append(array[5]);
        sb.append("\n");
      }
      sb.append("\n");
      sb.append("\n");
      view.showMessage(sb.toString());
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}

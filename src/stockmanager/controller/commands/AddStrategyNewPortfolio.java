package stockmanager.controller.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to add a strategy to a new portfolio.
 */
public class AddStrategyNewPortfolio implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a AddStocks with a given IView.
   *
   * @param view the given IView
   */
  public AddStrategyNewPortfolio(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String strategyName;
    String portfolioName;
    Map<String, Double> stockWeights;
    double amount;
    double commission;
    Date startDate;
    Date endDate;
    int freq;

    int numberOfStocks;

    startDate = null;
    endDate = null;
    stockWeights = new HashMap<>();

    try {
      view.showMessage(" You can create a stratey to invest by creating a new portfolio here:" +
              "Enter the Strategy Name, Enter the new portfolio name, "
              + " ticker symbols and weights seperated by space, amount, " +
              "commission, start date, endDate in (yyyy-MM-dd) format and Frequency");
      strategyName = view.getInput();
      portfolioName = view.getInput();
      m.createPortfolio(portfolioName);

      numberOfStocks = Integer.parseInt(view.getInput());
      for (int i = 0; i < numberOfStocks; i++) {
        stockWeights.put(view.getInput(), Double.parseDouble(view.getInput()));
      }
      amount = Double.parseDouble(view.getInput());
      commission = Double.parseDouble(view.getInput());
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.setLenient(false);
      freq = Integer.parseInt(view.getInput());
      try {
        startDate = sdf.parse(view.getInput());
        endDate = sdf.parse(view.getInput());
      } catch (ParseException p1) {
        view.showMessage(p1.getMessage());
      }
      m.addStrategy(strategyName, portfolioName, stockWeights, amount, commission, startDate,
              endDate, freq);
      view.showMessage("Stocks added successfully.");
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}
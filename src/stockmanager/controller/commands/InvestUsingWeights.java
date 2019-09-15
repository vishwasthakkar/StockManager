package stockmanager.controller.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.model.Portfolio;
import stockmanager.view.IView;

/**
 * This class represents a command to invest using weights.
 */
public class InvestUsingWeights implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a AddStocks with a given IView.
   *
   * @param view the given IView
   */
  public InvestUsingWeights(IView view) {
    this.view = view;
  }

  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    Map<String, Double> stockWeights;
    double amount;
    double commission;
    Date date;

    date = null;
    stockWeights = new HashMap<>();

    try {
      view.showMessage(" You can invest in a portfolio here: Enter the portfolio name,"
              + " ticker symbol and weights seperated by space, amount, " +
              "commission and date in (yyyy-MM-dd) format");
      portfolioName = view.getInput();
      Portfolio p = m.getSpecificPortfolio(portfolioName);
      for (String str : p.getContainingPortfolioNames()) {
        view.showMessage(str);
        stockWeights.put(str, Double.parseDouble(view.getInput()));
      }
      amount = Double.parseDouble(view.getInput());
      commission = Double.parseDouble(view.getInput());
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.setLenient(false);
      try {
        date = sdf.parse(view.getInput());
      } catch (ParseException p1) {
        view.showMessage(p1.getMessage());
      }
      m.investUsingWeightsInExistingPortfolio(portfolioName, stockWeights, amount,
              commission, date);
      view.showMessage("Stocks added successfully.");
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }


}

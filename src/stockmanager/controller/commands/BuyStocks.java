package stockmanager.controller.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to buy stocks into a certain portfolio.
 */
public class BuyStocks implements StockManagerCommand {

  private IView view;

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Constructs a BuyStocks with a given IView.
   *
   * @param view the given IView
   */
  public BuyStocks(IView view) {
    this.view = view;
  }


  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    String portfolioName;
    String stockName;
    double amount;
    double commission;
    Date buyDate;

    try {
      view.showMessage("You can buy a stock in a portfolio. Type the name of this portfolio, "
              + "the Ticker symbol of the stock, the amount, commission you payed "
              + "and the date (in yyyy-MM-dd format) when you want to buy this stock:");
      portfolioName = view.getInput();
      stockName = view.getInput();
      amount = Double.parseDouble(view.getInput());
      commission = Double.parseDouble(view.getInput());
      try {
        buyDate = SDF.parse(view.getInput());
        m.buyStocksUsingAmount(portfolioName, stockName, amount, commission, buyDate);
        view.showMessage("Bought stocks successfully.");
      } catch (ParseException e) {
        view.showMessage("The date format is wrong.");
      }
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}

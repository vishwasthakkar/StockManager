package stockmanager.controller.commands;

import stockmanager.controller.StockManagerCommand;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a command to allow users to create a portfolio with a given name.
 */
public class CreatePortfolio implements StockManagerCommand {

  private IView view;

  /**
   * Constructs a CreatePortfolio with a given IView.
   *
   * @param view the given IView
   */
  public CreatePortfolio(IView view) {
    this.view = view;
  }


  @Override
  public void goCommand(EnhancedStockManagerModel m) {
    try {
      view.showMessage("You can create a portfolio here. Type the name for this portfolio.");
      String portfolioName = view.getInput();
      m.createPortfolio(portfolioName);
      view.showMessage("Portfolio is Successfully Created.");
    } catch (IllegalArgumentException e) {
      view.showMessage(e.getMessage());
    }
  }
}

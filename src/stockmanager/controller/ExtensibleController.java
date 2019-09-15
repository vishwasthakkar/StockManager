package stockmanager.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import stockmanager.controller.commands.AddStocks;
import stockmanager.controller.commands.AddStrategyExistingPortfolio;
import stockmanager.controller.commands.AddStrategyNewPortfolio;
import stockmanager.controller.commands.BuyStocks;
import stockmanager.controller.commands.CreatePortfolio;
import stockmanager.controller.commands.InvestUsingWeights;
import stockmanager.controller.commands.RetrievePortfolio;
import stockmanager.controller.commands.RetrieveStrategy;
import stockmanager.controller.commands.SavePortfolio;
import stockmanager.controller.commands.SaveStrategy;
import stockmanager.controller.commands.ShowAll;
import stockmanager.controller.commands.ShowPortfolio;
import stockmanager.controller.commands.TotalCostOnCertainDate;
import stockmanager.controller.commands.TotalValueOnCertainDate;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IView;

/**
 * This class represents a ExtensibleController to process a command and give control to the
 * controller.
 */
public class ExtensibleController implements IController {

  private EnhancedStockManagerModel model;
  private IView view;
  private Stack<StockManagerCommand> commands;
  private Map<String, Function<IView, StockManagerCommand>> knownCommands;

  /**
   * Constructs a ExtensibleController with a given IView and model.
   *
   * @param model the given StockManagerModel
   * @param view  the given IView
   */
  public ExtensibleController(EnhancedStockManagerModel model, IView view) {
    this.model = model;
    this.view = view;
    this.commands = new Stack<>();
    this.knownCommands = new HashMap<>();
  }

  @Override
  public String processCommand(String command) {
    StringBuilder output = new StringBuilder();
    Scanner scan = new Scanner(command);

    while (scan.hasNext()) {
      StockManagerCommand c;
      String in = scan.next().toLowerCase();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        output.append("Program finished.");
      }

      Function<IView, StockManagerCommand> cmd = knownCommands.getOrDefault(in, null);
      if (cmd == null) {
        output.append(String.format("Unknown command %s", in));
        break;
      } else {
        c = cmd.apply(view);
        commands.add(c);
        c.goCommand(model);
        output.append("Successfully executed: " + command);
      }
    }
    return output.toString();
  }

  @Override
  public void goController() {

    knownCommands.put("create", CreatePortfolio::new);
    knownCommands.put("buy", BuyStocks::new);
    knownCommands.put("add", AddStocks::new);
    knownCommands.put("show", ShowPortfolio::new);
    knownCommands.put("showall", ShowAll::new);
    knownCommands.put("getcost", TotalCostOnCertainDate::new);
    knownCommands.put("getvalue", TotalValueOnCertainDate::new);
    knownCommands.put("saveport", SavePortfolio::new);
    knownCommands.put("retrieveport", RetrievePortfolio::new);
    knownCommands.put("invest", InvestUsingWeights::new);
    knownCommands.put("strategyexisting", AddStrategyExistingPortfolio::new);
    knownCommands.put("strategynew", AddStrategyNewPortfolio::new);
    knownCommands.put("savestrategy", SaveStrategy::new);
    knownCommands.put("retrievestrategy", RetrieveStrategy::new);

    while (true) {
      this.view.showMessage("You can choose to:\ntype 'create' to create a portfolio,\n"
              + "type 'buy' to buy a stock in a portfolio,\n"
              + "type 'add' to add a stock in a portfolio,\n"
              + "type 'showall' to see all the portfolios added,\n"
              + "type 'show' to exam composition of a portfolio,\n"
              + "type 'getcost' to get the total cost basis of a portfolio at a certain date,\n"
              + "type 'getvalue' to get total value of a portfolio at a certain date,\n"
              + "type 'save' to save a portfolio,\n"
              + "type 'retrieve' to retrieve a portfolio,\n"
              + "type 'strategyexisting' to apply strategy to an existing portfolio,\n"
              + "type 'strategynew' to apply strategy to a new portfolio,\n"
              + "type 'savestrategy' to save the strategy,\n"
              + "type 'retrievestrategy' to retrieve a strategy,\n"
              + "type 'q' or 'quit' to quit.");
      String command = this.view.getInput();
      if (command == null) {
        continue;
      }
      if (command.equalsIgnoreCase("q")
              || command.equalsIgnoreCase("quit")) {

        break;
      }
      String message = processCommand(command);
      this.view.showMessage(message);
    }
  }
}

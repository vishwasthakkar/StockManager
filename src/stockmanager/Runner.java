package stockmanager;

import java.io.InputStreamReader;
import java.util.Scanner;

import stockmanager.controller.AdditionalStockManagerFeatures;
import stockmanager.controller.EnhancedStockManagerController;
import stockmanager.controller.ExtensibleController;
import stockmanager.controller.IController;
import stockmanager.model.AlphaVantageAPI;
import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.model.EnhancedStockManagerModelImpl;
import stockmanager.model.GenericStockDataFetcher;
import stockmanager.view.HomeView;
import stockmanager.view.IStockGUIView;
import stockmanager.view.IView;
import stockmanager.view.StockGUIView;

/**
 * This runner class to start the whole stock application.
 */
public class Runner {

  /**
   * This main method starts the whole stock application.
   */
  public static void main(String[] args) {
    GenericStockDataFetcher fetcher = new AlphaVantageAPI();
    EnhancedStockManagerModel model = new EnhancedStockManagerModelImpl(fetcher);

    System.out.println("Enter: 'Text' OR 'GUI'");
    Scanner x = new Scanner(System.in);
    String input = x.next();
    if (input.equals("GUI") || input.equals("gui")) {
      // for GUI
      IStockGUIView view = new StockGUIView();
      AdditionalStockManagerFeatures controller = new EnhancedStockManagerController(model);
      ((EnhancedStockManagerController) controller).setView(view);
    } else if (input.equals("Text") || input.equals("text")) {
      // for Text-based
      IView textView = new HomeView(new InputStreamReader(System.in), System.out);
      IController controller1 = new ExtensibleController(model, textView);
      controller1.goController();
    } else {
      System.out.println("Invalid Input");
    }
  }
}

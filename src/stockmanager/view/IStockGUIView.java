package stockmanager.view;

import java.util.List;

import stockmanager.controller.AdditionalStockManagerFeatures;

/**
 * This interface represents a GUI view of our application. And it sets all features and shows
 * output.
 */
public interface IStockGUIView {

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param features the set of feature callbacks as a Features object
   */
  void setFeatures(AdditionalStockManagerFeatures features);

  /**
   * To output a message from the controller in a message box.
   *
   * @param output the output that needs to be shown to the user
   */
  void showOutput(String output);

  void setStockWeightTableInvestInExistingPortfolio(List<String> row);

  void setStockWeightTableStrategyExisting(List<String> row);
}

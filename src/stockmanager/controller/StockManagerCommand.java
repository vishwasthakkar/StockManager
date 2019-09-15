package stockmanager.controller;

import stockmanager.model.EnhancedStockManagerModel;

/**
 * This is a StockManagerCommand interface and represents a high-level command.
 */
public interface StockManagerCommand {

  /**
   * Give a set of operations that must be executed in the given model.
   *
   * @param m the given StockManagerModel
   */
  void goCommand(EnhancedStockManagerModel m);
}

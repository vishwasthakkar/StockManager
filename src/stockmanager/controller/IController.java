package stockmanager.controller;

/**
 * The controller interface for the Stock program. The functions here have been designed to give
 * control to the controller, and the primary operation for the controller to function (process a
 * stock command).
 */

public interface IController {
  /**
   * Process a given string command and return status or error as a string.
   *
   * @param command the command given, including any parameters
   * @return status or error message
   */
  String processCommand(String command);

  /**
   * Start the program, i.e. give control to the controller.
   */
  void goController();
}

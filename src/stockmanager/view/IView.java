package stockmanager.view;

/**
 * This interface represents a HomeView and it gets input and pass to controller.
 */
public interface IView {

  /**
   * Shows message after the operation informing whether the operation is successful.
   *
   * @param message the message informing whether the operation is successful
   */
  void showMessage(String message);

  /**
   * Returns the input as a string.
   *
   * @return the input as a string
   */
  String getInput();
}

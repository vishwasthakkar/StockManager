package stockmanager.controller;


import stockmanager.view.IView;

/**
 * A controller class that implements IController Interface, intending to test the 'view'.
 */
public class MockController implements IController {
  private IView view;
  private StringBuffer log;

  /**
   * Constructs a MockModel for testing.
   *
   * @param log  used to test input
   * @param view used to test view
   */
  public MockController(StringBuffer log, IView view) {
    this.log = log;
    this.view = view;
  }

  @Override
  public String processCommand(String command) {
    return "";
  }

  @Override
  public void goController() {
    log.append(view.getInput());
    view.showMessage("122323343443435");
  }

}

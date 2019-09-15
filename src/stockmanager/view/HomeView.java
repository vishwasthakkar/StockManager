package stockmanager.view;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents a HomeView get input and pass to controller.
 */
public class HomeView implements IView {

  private Readable r;
  private Appendable ap;
  private Scanner sc;

  /**
   * Constructs a HomeView with a given Readable and Appendable.
   *
   * @param is the given Readable input
   * @param ap the given Appendable output
   */
  public HomeView(Readable is, Appendable ap) {
    this.ap = ap;
    this.sc = new Scanner(is);
  }

  @Override
  public void showMessage(String message) {
    try {
      this.ap.append(message);
      this.ap.append("\n");
    } catch (IOException e) {
      //Do nothing
    }
  }

  @Override
  public String getInput() {
    return sc.next();
  }


}

package stockmanager.controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.view.IStockGUIView;

/**
 * This class is a controller for the GUI view and the persisting model. It implements the features
 * interface. This means each of the functions of the GUI view will give control to this
 * controller.
 */
public class StockManagerController implements StockManagerFeatures {
  protected EnhancedStockManagerModel model;
  protected IStockGUIView view;

  /**
   * Constructor that initializes the model.
   * @param m takes the model object
   */
  public StockManagerController(EnhancedStockManagerModel m) {
    model = m;
  }

  //FIXME
  public void setView(IStockGUIView v) {
    view = v;
    view.setFeatures(new EnhancedStockManagerController(model));
  }

  @Override
  public void createPortfolio(String portfolioName) {
    try {
      model.createPortfolio(portfolioName);
      view.showOutput("Portfolio " + portfolioName + " Created");
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void buyStocks(String portfolioName, String ticker, String amount, String commission,
                        String date) {
    Date tempDate = null;
    try {
      tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException pe) {
      view.showOutput(pe.getMessage());
    }
    try {
      double amt = Double.parseDouble(amount);
      double com = Double.parseDouble(commission);
      model.buyStocksUsingAmount(portfolioName, ticker, amt,
              com, tempDate);
      view.showOutput(ticker + " Stocks worth $" + (amt - com) + " purchased in portfolio " +
              portfolioName + ".\n(NOTE: The "
              + "commission will be deducted from the amount)");
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void addStocks(String portfolioName, String ticker, String quantity) {
    try {
      model.addStocksToPortfolio(portfolioName, ticker, Double.parseDouble(quantity));
      view.showOutput(quantity + " " + ticker + " stocks added to portfolio " + portfolioName);
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }


  @Override
  public void getCostBasis(String portfolioName, String date) {
    Date tempDate = null;
    String sb;
    try {
      tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException pe) {
      pe.getStackTrace();
    }
    try {
      sb = "The total cost-basis of " + portfolioName + ": $"
              + model.totalCostBasisOfPortfolio(portfolioName, tempDate);
      view.showOutput(sb);
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }

  }

  @Override
  public void getValue(String portfolioName, String date) {
    Date tempDate = null;
    try {
      tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException pe) {
      pe.getStackTrace();
    }
    try {
      String sb = "The total value of $" + portfolioName + ": "
              + model.totalValueOfPortfolio(portfolioName, tempDate);
      view.showOutput(sb);
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void savePortfolio(String portfolioName, String filePath) {
    try {
      model.savePortfolio(portfolioName, filePath);
      view.showOutput(portfolioName + " saved to path:\n" + filePath);
    } catch (IllegalArgumentException | ParserConfigurationException | FileNotFoundException |
            TransformerException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void retrievePortfolio(String filePath) {
    try {
      model.extractPortfolioFromXML(filePath);
      view.showOutput("Portfolio Retrieved:\n" + filePath);
    } catch (IllegalArgumentException | ParserConfigurationException | SAXException |
            IOException | ParseException e) {
      view.showOutput(e.getMessage());
    }
  }
}

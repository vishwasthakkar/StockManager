package stockmanager.controller;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import stockmanager.model.EnhancedStockManagerModel;
import stockmanager.model.Portfolio;
import stockmanager.view.IStockGUIView;

/**
 * This class adds more new features to the original project, it adds features: investing in
 * existing portfolio, clickedGetPortfolioAndInvestButton, clickedStrategyExistingPortfolioButton,
 * getSpecificPortfolio, addStrategyOnExistingPortfolio, addStrategyOnNewPortfolio, save Strategy
 * and retrieve Strategy.
 */
public class EnhancedStockManagerController
        extends StockManagerController
        implements AdditionalStockManagerFeatures {

  /**
   * Constructs a Enhanced Stock Manager Controller.
   *
   * @param m the Enhanced Stock Manager Model
   */
  public EnhancedStockManagerController(EnhancedStockManagerModel m) {
    super(m);
  }

  /**
   * Sets the view.
   *
   * @param v the IStock GUI View
   */
  public void setView(IStockGUIView v) {
    view = v;
    view.setFeatures(this);
  }


  @Override
  public void investInExistingPortfolio(String portfolioName, TableModel m, String amount,
                                        String commission, String date) {
    Map<String, Double> map = new HashMap<>();
    boolean isWeightGiven = true;
    for (int i = 0; i < m.getRowCount(); i++) {
      Object temp = m.getValueAt(i, 1);
      if (temp == null) {
        isWeightGiven = false;
      }
    }

    if (isWeightGiven) {
      double sum = 0;
      try {
        for (int i = 0; i < m.getRowCount(); i++) {
          Double eachWeight = Double.parseDouble(m.getValueAt(i, 1).toString());
          validateWeight(eachWeight);
          map.put((String) m.getValueAt(i, 0), eachWeight);
          sum += eachWeight;
        }
        validateHundredPercent(sum);
      } catch (IllegalArgumentException e) {
        view.showOutput(e.getMessage());
      }
    } else {
      for (int i = 0; i < m.getRowCount(); i++) {
        map.put((String) m.getValueAt(i, 0), (double) 100 / m.getRowCount());
      }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    try {
      model.investUsingWeightsInExistingPortfolio(portfolioName, map, Double.parseDouble(amount),
              Double.parseDouble(commission), sdf.parse(date));
    } catch (ParseException p) {
      view.showOutput(p.getMessage());
    }
  }

  private void validateWeight(Double weight) throws IllegalArgumentException {
    if (weight > 100 || weight < 0) {
      throw new IllegalArgumentException("Invalid weight entered");
    }
  }

  private void validateHundredPercent(double sum) {
    if (sum != 100) {
      throw new IllegalArgumentException("Invalid weights entered. Weights should add up to 100%.");
    }
  }


  @Override
  public void getSpecificPortfolio(String portfolioName) {
    model.getSpecificPortfolio(portfolioName);
  }


  @Override
  public void addStrategyOnExistingPortfolio(String strategyName, String portfolioName,
                                             TableModel m, String amount,
                                             String commission,
                                             String startDate, String endDate, String freq) {

    Map<String, Double> map = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    Date date1 = null;
    if (endDate.equals("End Date") || endDate == null) {
      date1 = null;
    } else {
      try {
        date1 = sdf.parse(endDate);
      } catch (ParseException p) {
        view.showOutput("Invalid Date. Date you entered cannot be used.");
      }
    }
    boolean isWeightGiven = true;
    for (int i = 0; i < m.getRowCount(); i++) {
      Object temp = m.getValueAt(i, 1);
      if (temp == null) {
        isWeightGiven = false;
      }
    }

    if (isWeightGiven) {
      double sum = 0;
      try {
        for (int i = 0; i < m.getRowCount(); i++) {
          Double eachWeight = Double.parseDouble(m.getValueAt(i, 1).toString());
          validateWeight(eachWeight);
          map.put((String) m.getValueAt(i, 0), eachWeight);
          sum += eachWeight;
        }
        validateHundredPercent(sum);
      } catch (IllegalArgumentException e) {
        view.showOutput(e.getMessage());
      }
    } else {
      for (int i = 0; i < m.getRowCount(); i++) {
        map.put((String) m.getValueAt(i, 0), (double) 100 / m.getRowCount());
      }
    }


    try {
      model.addStrategy(strategyName, portfolioName, map, Double.parseDouble(amount),
              Double.parseDouble(commission), sdf.parse(startDate), date1,
              Integer.parseInt(freq));
    } catch (ParseException p) {
      view.showOutput(p.getMessage());
    }
  }

  @Override
  public void addStrategyOnNewPortfolio(String strategyName, String portfolioName,
                                        TableModel m, String amount, String commission,
                                        String startDate, String endDate, String freq) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    Date date1 = null;
    if (endDate.equals("End Date") || endDate == null) {
      date1 = null;
    } else {
      try {
        date1 = sdf.parse(endDate);
      } catch (ParseException p) {
        view.showOutput("Invalid Date. Date you entered cannot be used.");
      }
    }
    Map<String, Double> map = new HashMap<>();
    boolean isWeightGiven = true;
    for (int i = 0; i < m.getRowCount(); i++) {
      Object temp = m.getValueAt(i, 1);
      if (temp == null) {
        isWeightGiven = false;
      }
    }
    if (isWeightGiven) {
      double sum = 0;
      try {
        for (int i = 0; i < m.getRowCount(); i++) {
          Double eachWeight = Double.parseDouble(m.getValueAt(i, 1).toString());
          validateWeight(eachWeight);
          map.put((String) m.getValueAt(i, 0), eachWeight);
          sum += eachWeight;
        }
        validateHundredPercent(sum);
      } catch (IllegalArgumentException e) {
        view.showOutput(e.getMessage());
      }
    } else {
      for (int i = 0; i < m.getRowCount(); i++) {
        map.put((String) m.getValueAt(i, 0), (double) 100 / m.getRowCount());
      }
    }


    try {
      model.createPortfolio(portfolioName);
      model.addStrategy(strategyName, portfolioName, map, Double.parseDouble(amount),
              Double.parseDouble(commission), sdf.parse(startDate), date1,
              Integer.parseInt(freq));
    } catch (ParseException p) {
      view.showOutput(p.getMessage());
    }
  }

  @Override
  public void clickedGetPortfolioAndInvestButton(String portfolioName) {
    try {
      Portfolio p = model.getSpecificPortfolio(portfolioName);
      List<String> list = p.getContainingPortfolioNames();

      view.setStockWeightTableInvestInExistingPortfolio(list);
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void clickedStrategyExistingPortfolioButton(String portfolioName) {
    try {
      Portfolio p = model.getSpecificPortfolio(portfolioName);
      List<String> list = p.getContainingPortfolioNames();
      view.setStockWeightTableStrategyExisting(list);
    } catch (IllegalArgumentException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void saveStrategy(String strategyName, String filePath) {
    try {
      model.saveStrategy(strategyName, filePath);
    } catch (ParserConfigurationException | FileNotFoundException | TransformerException e) {
      view.showOutput(e.getMessage());
    }
  }

  @Override
  public void retrieveStrategy(String filePath) {
    try {
      model.extractStrategyFromXML(filePath);

    } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
      view.showOutput(e.getMessage());
    }
  }
}


package stockmanager.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import stockmanager.controller.AdditionalStockManagerFeatures;


/**
 * A java swing class that creates a GUI to communicate with the user and pass the inputs and
 * outputs to the controller.
 */
public class StockGUIView extends JFrame implements IStockGUIView {
  private JTextField portfolioName;
  private JButton getPortfolioButton;

  private JTextField portfolioNameBuy;
  private JTextField tickerBuy;
  private JTextField amountBuy;
  private JTextField commissionBuy;
  private JTextField dateBuy;
  private JButton buttonBuyStocks;

  private JTextField portfolioNameAdd;
  private JTextField tickerAdd;
  private JTextField qtyAdd;
  private JButton buttonAddStocks;

  private JTextField getPortfolioNameCostBasis;
  private JTextField dateCostBasis;
  private JButton buttonCostBasis;

  private JTextField getPortfolioNameValue;
  private JTextField dateValue;
  private JButton buttonValue;

  private JTextField portfolioNameSave;
  private JTextField filePathSave;
  private JButton buttonSave;

  private JTextField filePathRetrieve;
  private JButton buttonRetrieve;

  // Invest Using weights components
  private JTextField textInvestInExistingPortfolioPortfolioName;
  private JTextField textInvestInExistingPortfolioAmount;
  private JTextField textInvestInExistingPortfolioCommission;
  private JTextField textInvestInExistingPortfolioDate;
  private JTable investInExistingPortfolioStockWeights;
  private JButton buttonInvestInExistingPortfolio;
  private JButton buttonInvestInExistingPortfolioFinally;
  private DefaultTableModel tableModelForInvest;

  private JLabel label1;
  private JButton button1;
  private JButton button2;

  //For Strategy to existing portfolio
  private JTextField textStrategyNameExisting;
  private JTextField textStrategyPortfolioNameExisting;
  private JTextField textStrategyExistingAmount;
  private JTextField textStrategyExistingCommission;
  private JTextField textStrategyExistingStartDate;
  private JTextField textStrategyExistingEndDate;
  private JTextField textStrategyExistingFrequency;
  private JTable stockWeightStrategyExisting;
  private JButton buttonStrategyInExistingPortfolio;
  private JButton buttonExistingStrategyExecute;
  private DefaultTableModel tableModelForStrategyExisting;
  private JScrollPane pane1;

  private JTextField textStrategyNameNew;
  private JTextField textStrategyPortfolioNameNew;
  private JTextField textStrategyNewAmount;
  private JTextField textStrategyNewCommission;
  private JTextField textStrategyNewStartDate;
  private JTextField textStrategyNewEndDate;
  private JTextField textStrategyNewFrequency;
  private JTable stockWeightStrategyNew;
  private JButton buttonStrategyInNewPortfolio;
  private DefaultTableModel modelNewStrategy;
  private JLabel label2;
  private JTextField getNumber;
  private JButton jButtonn;
  private JButton goBack;

  private JLabel fileSaveDisplay;
  private JLabel fileOpenDisplay;
  private JButton saveButton;
  private JButton retrieveButton;
  private JTextField saveStrategyName;


  //Panels that are used to contain each functionality (in tabs)
  private JPanel portfolioCreate;
  private JPanel stockAdd;
  private JPanel stockBuy;
  private JPanel costBasis;
  private JPanel totalValue;
  private JPanel savePortfolio;
  private JPanel retrievePortfolio;
  private JPanel investInExistingPortfolio;
  private JPanel strategiesPanel;
  private JPanel saveStrategy;
  private JPanel retrieveStrategy;

  /**
   * Call the constructor to set all the components that are used to communicate with the user.
   */
  public StockGUIView() {
    super("StockManager");

    //Using Tabbed Pane
    JTabbedPane tabbedPane = new JTabbedPane();
    getContentPane().add(tabbedPane);
    portfolioCreate = new JPanel();
    stockAdd = new JPanel();
    stockBuy = new JPanel();
    costBasis = new JPanel();
    totalValue = new JPanel();
    savePortfolio = new JPanel();
    retrievePortfolio = new JPanel();
    investInExistingPortfolio = new JPanel();
    strategiesPanel = new JPanel();
    saveStrategy = new JPanel();
    retrieveStrategy = new JPanel();

    //adding panels as tabs
    tabbedPane.addTab("Create Portfolio", portfolioCreate);
    tabbedPane.addTab("Add Stocks", stockAdd);
    tabbedPane.addTab("Buy Stocks", stockBuy);
    tabbedPane.addTab("Cost-Basis", costBasis);
    tabbedPane.addTab("Total-Value", totalValue);
    tabbedPane.addTab("Save Portfolio", savePortfolio);
    tabbedPane.addTab("Retrieve Portfolio", retrievePortfolio);
    tabbedPane.addTab("Invest", investInExistingPortfolio);
    tabbedPane.addTab("Use Strategies! ", strategiesPanel);
    tabbedPane.addTab("Save Strategies", saveStrategy);
    tabbedPane.addTab("Retrieve Strategy", retrieveStrategy);

    //setting up components
    setCreatePortfolioComponents();
    setBuyStockComponents();
    setAddStockComponents();
    setCostBasisComponents();
    setValueComponents();
    setSavePortfolioComponents();
    setRetrievePortfolioComponents();
    setInvestingInExistingPortfolioComponents();
    setStrategiesPanel();
    setSaveStrategyComponents();
    setRetrieveStrategyComponents();

    setVisible(true);
    setLocation(200, 200);
    setSize(800, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void setFeatures(AdditionalStockManagerFeatures f) {
    getPortfolioButton.addActionListener(l -> f.createPortfolio(portfolioName.getText()));


    buttonBuyStocks.addActionListener(l -> f.buyStocks(
            portfolioNameBuy.getText(),
            tickerBuy.getText(),
            amountBuy.getText(),
            commissionBuy.getText(),
            dateBuy.getText()
    ));

    buttonAddStocks.addActionListener(l -> f.addStocks(
            portfolioNameAdd.getText(),
            tickerAdd.getText(),
            qtyAdd.getText()
    ));

    buttonCostBasis.addActionListener(l -> f.getCostBasis(
            getPortfolioNameCostBasis.getText(),
            dateCostBasis.getText()
    ));

    buttonValue.addActionListener(l -> f.getValue(
            getPortfolioNameValue.getText(),
            dateValue.getText()
    ));

    buttonSave.addActionListener(l -> f.savePortfolio(
            portfolioNameSave.getText(),
            filePathSave.getText()
    ));

    buttonRetrieve.addActionListener(l -> f.retrievePortfolio(
            filePathRetrieve.getText()
    ));

    buttonInvestInExistingPortfolio.addActionListener(l -> f.clickedGetPortfolioAndInvestButton(
            textInvestInExistingPortfolioPortfolioName.getText()
    ));

    buttonInvestInExistingPortfolioFinally.addActionListener(l -> f.investInExistingPortfolio(
            textInvestInExistingPortfolioPortfolioName.getText(),
            investInExistingPortfolioStockWeights.getModel(),
            textInvestInExistingPortfolioAmount.getText(),
            textInvestInExistingPortfolioCommission.getText(),
            textInvestInExistingPortfolioDate.getText()
    ));

    buttonStrategyInExistingPortfolio.addActionListener(l ->
            f.clickedStrategyExistingPortfolioButton(
                    textStrategyPortfolioNameExisting.getText()
            ));

    buttonExistingStrategyExecute.addActionListener(l -> f.addStrategyOnExistingPortfolio(
            textStrategyNameExisting.getText(),
            textStrategyPortfolioNameExisting.getText(),
            stockWeightStrategyExisting.getModel(),
            textStrategyExistingAmount.getText(),
            textStrategyExistingCommission.getText(),
            textStrategyExistingStartDate.getText(),
            textStrategyExistingEndDate.getText(),
            textStrategyExistingFrequency.getText()
    ));

    buttonStrategyInNewPortfolio.addActionListener(l -> f.addStrategyOnNewPortfolio(
            textStrategyNameNew.getText(),
            textStrategyPortfolioNameNew.getText(),
            stockWeightStrategyNew.getModel(),
            textStrategyNewAmount.getText(),
            textStrategyNewCommission.getText(),
            textStrategyNewStartDate.getText(),
            textStrategyNewEndDate.getText(),
            textStrategyNewFrequency.getText()
    ));

    saveButton.addActionListener(l -> f.saveStrategy(
            saveStrategyName.getText(),
            fileSaveDisplay.getText()
    ));
    retrieveButton.addActionListener(l -> f.retrieveStrategy(
            fileOpenDisplay.getText()
    ));

  }

  @Override
  public void showOutput(String output) {
    JOptionPane.showMessageDialog(null, output);
  }


  @Override
  public void setStockWeightTableInvestInExistingPortfolio(List<String> row) {
    String[] colNames = {"Stock Tickers", "Weight (In Percentage %)"};
    tableModelForInvest.setColumnIdentifiers(colNames);
    for (String s : row) {
      String[] temp = {s};
      tableModelForInvest.addRow(temp);
    }

    textInvestInExistingPortfolioAmount.setVisible(true);
    textInvestInExistingPortfolioCommission.setVisible(true);
    textInvestInExistingPortfolioDate.setVisible(true);
    buttonInvestInExistingPortfolioFinally.setVisible(true);
    revalidate();
    repaint();
  }

  @Override
  public void setStockWeightTableStrategyExisting(List<String> row) {
    String[] colNames = {"Stock Tickers", "Weight (In Percentage %)"};
    tableModelForStrategyExisting.setColumnIdentifiers(colNames);
    for (String s : row) {
      String[] temp = {s};
      tableModelForStrategyExisting.addRow(temp);
    }

    pane1.setVisible(true);

    buttonStrategyInExistingPortfolio.setVisible(true);
    textStrategyExistingAmount.setVisible(true);
    textStrategyExistingCommission.setVisible(true);
    textStrategyExistingStartDate.setVisible(true);
    textStrategyExistingEndDate.setVisible(true);
    textStrategyExistingFrequency.setVisible(true);
    buttonExistingStrategyExecute.setVisible(true);
    revalidate();
    repaint();
  }


  private void setCreatePortfolioComponents() {

    //Components for creating portfolio
    JLabel createPortfolioLabel = new JLabel("Create a Portfolio by giving it a name!\n"
            + "The name can only be alpha-numeric with no special characters.");
    portfolioName = new JTextField("Enter portfolio name here.", 20);
    JLabel note = new JLabel("\n\nPLEASE NOTE:\t Please delete the text that is already" +
            " present in any text box before entering any new text.");

    getPortfolioButton = new JButton("Create");
    portfolioName.setActionCommand("create");

    portfolioCreate.add(createPortfolioLabel);
    portfolioCreate.add(portfolioName);
    portfolioCreate.add(getPortfolioButton);
    portfolioCreate.add(note);
  }

  /**
   * Set all the components that are used to buy stocks.
   */
  private void setBuyStockComponents() {
    //Components for buying stocks in portfolio
    JTextArea buyStocksLabel = new JTextArea("Buy Stocks in a particular portfolio here!\n"
            + "NOTE:\n 1) The ticker should be valid.\n" +
            " 2) Amount is the amount of money you want to invest in dollars "
            + "(Should be a number)\t\t\t\n" +
            "3) Commission should be a positive number and in dollars\n" +
            "4) Date should be in the Format:(yyyy-MM-dd)\t\t\t\t\t " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
    buyStocksLabel.setEditable(false);
    portfolioNameBuy = new JTextField("Portfolio Name", 15);
    tickerBuy = new JTextField("Ticker", 15);
    amountBuy = new JTextField("Amount", 15);
    commissionBuy = new JTextField("Commission", 15);
    dateBuy = new JTextField("Date", 15);
    buttonBuyStocks = new JButton("Buy this Stock");
    portfolioName.setActionCommand("buystocks");

    stockBuy.add(buyStocksLabel);
    stockBuy.add(portfolioNameBuy);
    stockBuy.add(tickerBuy);
    stockBuy.add(amountBuy);
    stockBuy.add(commissionBuy);
    stockBuy.add(dateBuy);
    stockBuy.add(buttonBuyStocks);
  }

  /**
   * Set all the components that are used to add stocks.
   */
  private void setAddStockComponents() {
    //Components for adding stocks in portfolio
    JTextArea addStocksLabel = new JTextArea("Buy Stocks in a particular portfolio here!\n"
            + "NOTE: 1) The ticker should be valid.\n"
            + "        2) Quantity should be a valid number of shares (cannot be lesser than or " +
            "less than zero)" +
            "\n3) The portfolio should exist"
            + "\t\t\t\t\t\t\t"
    );
    portfolioNameAdd = new JTextField("Portfolio Name", 15);
    tickerAdd = new JTextField("Ticker", 15);
    qtyAdd = new JTextField("Quantity", 15);
    buttonAddStocks = new JButton("Add this Stock");
    buttonAddStocks.setActionCommand("addstocks");

    stockAdd.add(addStocksLabel);
    stockAdd.add(portfolioNameAdd);
    stockAdd.add(tickerAdd);
    stockAdd.add(qtyAdd);
    stockAdd.add(buttonAddStocks);

  }

  /**
   * Set all the components that are used to get cost basis of a portfolio.
   */
  private void setCostBasisComponents() {
    //Components for getting the cost-basis of a portfolio
    JTextArea costBasisLabel = new JTextArea("To get the total cost occurred to you " +
            "(Cost Basis):\n" +
            "NOTE:\n" +
            "1) The portfolio should Exist." +
            "\n2) The date format should be (yyyy-MM-dd)" +
            "\t\t\t\t\t\t");
    getPortfolioNameCostBasis = new JTextField("Enter Portfolio Name", 15);
    dateCostBasis = new JTextField("Enter the date", 15);
    buttonCostBasis = new JButton("Get Cost-Basis");
    buttonCostBasis.setActionCommand("getcostbasis");

    costBasis.add(costBasisLabel);
    costBasis.add(getPortfolioNameCostBasis);
    costBasis.add(dateCostBasis);
    costBasis.add(buttonCostBasis);
  }

  /**
   * Set all the components that are used to get value of a portfolio.
   */
  private void setValueComponents() {
    //Components for getting the total value of a portfolio
    JTextArea totalValueLabel = new JTextArea("To get the total current value of a portfolio:\n" +
            "NOTE:\n" +
            "1) The portfolio should Exist." +
            "\n2) The date format should be (yyyy-MM-dd)" +
            "\t\t\t\t\t\t");
    getPortfolioNameValue = new JTextField("Enter Portfolio Name", 15);
    dateValue = new JTextField("Enter the date", 15);
    buttonValue = new JButton("Get total value");
    buttonValue.setActionCommand("getvalue");

    totalValue.add(totalValueLabel);
    totalValue.add(getPortfolioNameValue);
    totalValue.add(dateValue);
    totalValue.add(buttonValue);
  }

  /**
   * Set all the components that are used to save a portfolio to a XML file.
   */
  private void setSavePortfolioComponents() {
    //Components for saving portfolio
    JTextArea savePortfolioText = new JTextArea("To save a particular portfolio to a location:\n" +
            "NOTE:\n" +
            "1) Enter the name of portfolio that already exists.\n" +
            "2) Enter the location where you want to save the portfolio.\n" +
            "3) The portfolio will be saved as an XML file format.\n" +
            "4) The name of the xml file will be the same as portfolio name.\n" +
            "\t\t\t\t\t\t\t");
    portfolioNameSave = new JTextField("Enter the Portfolio Name here", 15);
    filePathSave = new JTextField("Enter the location", 15);
    buttonSave = new JButton("Save");

    savePortfolio.add(savePortfolioText);
    savePortfolio.add(portfolioNameSave);
    savePortfolio.add(filePathSave);
    savePortfolio.add(buttonSave);
  }

  /**
   * Set all the components that are used to save a portfolio to retrieve a XML file.
   */
  private void setRetrievePortfolioComponents() {
    //Components for retrieving portfolio
    JTextArea retrievePortfolioText = new JTextArea("To save a particular portfolio " +
            "to a location:\n" +
            "NOTE:\n" +
            "1) Enter the location where you have stored the xml file along with its name.\n" +
            "2) For Windows, the location should be like:\n" +
            "\t\teg: C:\\Users\\ABC\\Desktop\\Nifty.xml\n" +
            "3) For Mac OS, the location should be like:\n" +
            "\t\teg: /Users/joe/Desktop/Nifty.xml\n" +
            "4) The portfolio can be created manually be the user.\n" +
            "5) The name of the xml file should be the same as portfolio name.\n" +
            "6) After retrieving XML file, this portfolio will be added to set of portfolios.\n" +
            "\t\t\t\t\t\t\t");
    filePathRetrieve = new JTextField("Enter the location of xml file here", 15);
    buttonRetrieve = new JButton("Retrieve");

    retrievePortfolio.add(retrievePortfolioText);
    retrievePortfolio.add(filePathRetrieve);
    retrievePortfolio.add(buttonRetrieve);
  }


  private void setInvestingInExistingPortfolioComponents() {
    investInExistingPortfolio.setLayout(new BoxLayout(investInExistingPortfolio,
            BoxLayout.PAGE_AXIS));
    textInvestInExistingPortfolioPortfolioName = new JTextField(
            "Enter the Name of the portfolio here.");
    textInvestInExistingPortfolioAmount = new JTextField("Amount");
    textInvestInExistingPortfolioCommission = new JTextField("Commission");
    textInvestInExistingPortfolioDate = new JTextField("Date");
    buttonInvestInExistingPortfolio = new JButton("Select and Invest");
    buttonInvestInExistingPortfolioFinally = new JButton("INVEST");
    tableModelForInvest = new DefaultTableModel();
    investInExistingPortfolio.add(textInvestInExistingPortfolioPortfolioName);
    investInExistingPortfolio.add(buttonInvestInExistingPortfolio);
    investInExistingPortfolioStockWeights = new JTable(tableModelForInvest);
    investInExistingPortfolioStockWeights.putClientProperty("terminateEditOnFocusLost", true);
    JScrollPane pane = new JScrollPane(investInExistingPortfolioStockWeights);
    investInExistingPortfolio.add(pane);
    investInExistingPortfolio.add(textInvestInExistingPortfolioAmount);
    investInExistingPortfolio.add(textInvestInExistingPortfolioCommission);
    investInExistingPortfolio.add(textInvestInExistingPortfolioDate);
    investInExistingPortfolio.add(buttonInvestInExistingPortfolioFinally);
    pane.setVisible(true);
    textInvestInExistingPortfolioAmount.setVisible(false);
    textInvestInExistingPortfolioCommission.setVisible(false);
    textInvestInExistingPortfolioDate.setVisible(false);
    buttonInvestInExistingPortfolioFinally.setVisible(false);
  }

  private void setStrategiesPanel() {
    strategiesPanel.setLayout(new BoxLayout(strategiesPanel, BoxLayout.PAGE_AXIS));

    label1 = new JLabel("Please Select two of the following options:");
    button1 = new JButton("Implement Strategy on Existing Portfolio");
    button2 = new JButton("Create a new portfolio and "
            + "implement strategy on it.");

    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        label1.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        strategiesActual1();
      }
    });

    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        label1.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        strategiesActual2();
      }
    });


    initialise();
    add();
    visiibleFalse();

    strategiesPanel.add(label1);
    strategiesPanel.add(button1);
    strategiesPanel.add(button2);

    goBack.setVisible(false);

  }

  private void strategiesActual1() {

    textStrategyNameExisting.setVisible(true);
    textStrategyPortfolioNameExisting.setVisible(true);
    buttonStrategyInExistingPortfolio.setVisible(true);
    goBack.setVisible(true);
  }

  private void strategiesActual2() {
    textStrategyNameNew.setVisible(true);
    textStrategyPortfolioNameNew.setVisible(true);
    getNumber.setVisible(true);
    jButtonn.setVisible(true);
    goBack.setVisible(true);
    jButtonn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setTable();
        gotNumbers();
      }
    });
  }

  private void setTable() {
    for (int i = 0; i < Integer.parseInt(getNumber.getText()); i++) {
      String[] temp = {};
      modelNewStrategy.addRow(temp);
    }
  }

  private void gotNumbers() {
    label2.setVisible(true);
    stockWeightStrategyNew.setVisible(true);
    textStrategyNewAmount.setVisible(true);
    textStrategyNewCommission.setVisible(true);
    textStrategyNewStartDate.setVisible(true);
    textStrategyNewEndDate.setVisible(true);
    textStrategyNewFrequency.setVisible(true);
    buttonStrategyInNewPortfolio.setVisible(true);
  }

  private void initialise() {
    //components Strategy with existing portfolio
    textStrategyNameExisting = new JTextField("Strategy Name here");
    textStrategyPortfolioNameExisting = new JTextField("Portfolio Name ");
    buttonStrategyInExistingPortfolio = new JButton("Select this portfolio ");
    tableModelForStrategyExisting = new DefaultTableModel();
    stockWeightStrategyExisting = new JTable(tableModelForStrategyExisting);
    stockWeightStrategyExisting.putClientProperty("terminateEditOnFocusLost", true);
    pane1 = new JScrollPane(stockWeightStrategyExisting);
    textStrategyExistingAmount = new JTextField("Amount");
    textStrategyExistingCommission = new JTextField("Commission");
    textStrategyExistingStartDate = new JTextField("Start Date");
    textStrategyExistingEndDate = new JTextField("End Date");
    textStrategyExistingFrequency = new JTextField("Frequency");
    buttonExistingStrategyExecute = new JButton("Implement Stategy");

    goBack = new JButton("<< Back");
    goBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        start();
      }
    });

    //components Strategy with new portfolio
    textStrategyNameNew = new JTextField("New Strategy Name");
    textStrategyPortfolioNameNew = new JTextField("New Portfolio Name");
    textStrategyNewAmount = new JTextField("Amount");
    textStrategyNewCommission = new JTextField("Commission");
    textStrategyNewStartDate = new JTextField("Start Date");
    textStrategyNewEndDate = new JTextField("End Date");
    textStrategyNewFrequency = new JTextField("Frequency");
    String[] cols = {"Ticker Symbol", "Weight (%)"};
    modelNewStrategy = new DefaultTableModel(0, 2);
    modelNewStrategy.setColumnIdentifiers(cols);
    stockWeightStrategyNew = new JTable(modelNewStrategy);
    stockWeightStrategyNew.putClientProperty("terminateEditOnFocusLost", true);
    label2 = new JLabel("Enter the STOCK TICKER in the first Column and WEIGHT in (%) in the "
            + "second column");
    buttonStrategyInNewPortfolio = new JButton("Implement");
    getNumber = new JTextField("Number of stocks to add:");
    jButtonn = new JButton("Get");
  }

  private void add() {
    //for existing portfolio
    strategiesPanel.add(textStrategyNameExisting);
    strategiesPanel.add(textStrategyPortfolioNameExisting);
    strategiesPanel.add(buttonStrategyInExistingPortfolio);
    strategiesPanel.add(pane1);
    strategiesPanel.add(textStrategyExistingAmount);
    strategiesPanel.add(textStrategyExistingCommission);
    strategiesPanel.add(textStrategyExistingStartDate);
    strategiesPanel.add(textStrategyExistingEndDate);
    strategiesPanel.add(textStrategyExistingFrequency);
    strategiesPanel.add(buttonExistingStrategyExecute);

    //for new portfolio
    strategiesPanel.add(textStrategyNameNew);
    strategiesPanel.add(textStrategyPortfolioNameNew);
    strategiesPanel.add(getNumber);
    strategiesPanel.add(jButtonn);
    strategiesPanel.add(label2);
    strategiesPanel.add(stockWeightStrategyNew);
    strategiesPanel.add(textStrategyNewAmount);
    strategiesPanel.add(textStrategyNewCommission);
    strategiesPanel.add(textStrategyNewStartDate);
    strategiesPanel.add(textStrategyNewEndDate);
    strategiesPanel.add(textStrategyNewFrequency);
    strategiesPanel.add(buttonStrategyInNewPortfolio);
    strategiesPanel.add(goBack);
  }

  private void visiibleFalse() {
    pane1.setVisible(false);
    textStrategyNameExisting.setVisible(false);
    textStrategyPortfolioNameExisting.setVisible(false);
    buttonStrategyInExistingPortfolio.setVisible(false);
    buttonStrategyInExistingPortfolio.setVisible(false);
    textStrategyExistingAmount.setVisible(false);
    textStrategyExistingCommission.setVisible(false);
    textStrategyExistingStartDate.setVisible(false);
    textStrategyExistingEndDate.setVisible(false);
    textStrategyExistingFrequency.setVisible(false);
    buttonExistingStrategyExecute.setVisible(false);

    getNumber.setVisible(false);
    jButtonn.setVisible(false);
    textStrategyNameNew.setVisible(false);
    textStrategyPortfolioNameNew.setVisible(false);
    label2.setVisible(false);
    stockWeightStrategyNew.setVisible(false);
    textStrategyNewAmount.setVisible(false);
    textStrategyNewCommission.setVisible(false);
    textStrategyNewStartDate.setVisible(false);
    textStrategyNewEndDate.setVisible(false);
    textStrategyNewFrequency.setVisible(false);
    buttonStrategyInNewPortfolio.setVisible(false);
  }

  private void start() {
    visiibleFalse();
    label1.setVisible(true);
    button1.setVisible(true);
    button2.setVisible(true);
  }


  private void setSaveStrategyComponents() {
    JPanel filesavePanel = new JPanel();
    saveStrategyName = new JTextField("Enter the Name of the Strategy");
    saveButton = new JButton("Save this Strategy");
    filesavePanel.setLayout(new FlowLayout());
    saveStrategy.add(filesavePanel);
    JButton fileSaveButton = new JButton("Set Path to save a strategy");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(StockGUIView.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
        }
      }
    });
    filesavePanel.add(saveStrategyName);
    filesavePanel.add(fileSaveButton);
    filesavePanel.add(saveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
  }

  private void setRetrieveStrategyComponents() {
    JPanel fileopenPanel = new JPanel();
    retrieveButton = new JButton("Retrieve Strategy");
    fileopenPanel.setLayout(new FlowLayout());
    retrieveStrategy.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Set Path to Retrieve Strategy");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(StockGUIView.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
        }
      }
    });
    fileopenPanel.add(fileOpenButton);
    fileopenPanel.add(retrieveButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);
  }
}

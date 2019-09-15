##################################################################################

Design Changes:
	1) To include the several functionalities that are required, we created a new interface 
		(EnhancedStockManagerModel) and extended the previous model interface (PersistingStockManagerModel)
		hence the additional operations are added to it.
	
	2) Some Operations added to this interface are - InvestUsingWeights(), addStrategy(), saveStrategy)(),
		extractStrategyFromXML().
		
	3) EnhancedStockManagerModelImpl is the class that implements the EnhancedStockManagerModel.
	
	4) StockManagerFeatures interface included all the methods/features implemented by the 
		StockManagerController. That interface is extended by AddistionalStockManagerFeatures,
		which contains all the methods that are used to implement the new features in MVC.
	
	5) We have used the same class (ExtensibleController) and Interface (IController) to implement the
		text-based UI. Since it is already implemented it in the Command-design pattern, we need not had
		to create a new extending class.
		
	6) The commands class in command design implements the StockManagerCommand interface and all the commands 
	implement this interface and any number of command classes can be added without changing the older code.
	
	7) We have used the same class and interface for the GUIs. Since it is mostly component based and 
		all the components can be added without changing the earlier code.


##################################################################################
The Design of the 'StockManager' application follows MVC architecture. Hence it is divided
into 3 parts:

1) Model : Represents shape of the data and business logic. 
	It maintains the data of the application
2) View : View display data using model to the user and also enables 
	them to modify the data.
3) Controller : Handles the controller, model and the user requests.

Model has following classes:

1) StockManagerModel (Interface)
2) StockManagerModelImpl (Implements the StockManagerModel interface)
3) PersistingStockManagerModel (Interface - extends StockManagerModel) - to add the 
	save and retrieve portfolio functionalities. 
4) PersistingStockManagerModelImpl (Implements PersistingStockManagerModel)
5) MockModel (Implements the StockManagerModel interface)
6) Stock 
7) Portfolio
8) GenericStockDataFetcher (interface - used to make the stock data source generic
	so that model can work with any data source)
9) AlphaVantageAPI (implements the GenericStockDataFetcher so that it can act as a
	data source for the model)

Working: The model creates an object of AlphaVantageAPI class and call its
generateStockData() method to get the stock data. This data is in the form of string
with data seperated by ','. We can get this data ie. the buying price and current price 
of by calling the given methods. This prices (ie buying price) are then stored in stock
is stored in the data member of Stock class. 

When fetching the current price the model just calls the getPriceOnADate(Date) method 
in the Stock class. This is the same method used to fetch the buying price.

Model class calls the portfolio class which in turn calls the Stock class to do various functions
and operations.

Stocks are bought by specifying the amount the user wants to invest and commission fees along
with date. The model will fetch the stock data from the API and create a stock object 
that can be stored in a portfolio class object. Model contains a set of to Store all the
portfolios. While the portfolio contains an ArrayList to store all the stocks a portfolio 
object contains.

-------------------------------------------------------------------------------------------
The program has two controllers - one for GUI and other for command-line. 

The commandline Controller has following classes:

1) IController (the controller interface)
2) ExtensibleController (A command based design controller that implements the IController)
3) MockController (Implements the interface)
4) StockManagerCommand (Interface for the command of the controller)
5) Command Package contains :   a) BuyStocks
				b) CreatePortfolio
				c) ShowPortfolio
				d) TotalCostOnCertainDate
				e) TotalValueOnCertainDate
				f) ShowAll
				g) Retrieve Portfolio
				h) Save Portfolio

All of these command classes implements the StockManagerCommandInterface.
These Command are mapped in the controller to certain words, to call them.
for example: "create" command to call CreatePortfolio class object.

A map is used to bind the keywords with the commands that and hence when keyword is typed
the command object is created and command is executer.

The GUI controller has the following classes:

1) StockManagerFeatures (Interface) - Contains all the features(functionalities provided 
	by the program)
2) StockManagerController (Implements the StockManagerFeatures interface)

The controller consists of callback methods and sets these callback models as 
ActionListerners of different JButtons. Hence the control is totally in the hands of 
controller. When an event occurs in GUI view the appropriate callback function will 
be called.

----------------------------------------------------------------------------------------------

Again, There are two type of views: GUI and command-line.

The command-line View has following two classes:
1) IView (Interface)
2) HomeView (Implements the interface)

The HomeView class has methods to show user what command is used for what purpose.
Also, It is used to fetch data from the user and even output the data to the user.


The GUI view has the following two classes:
1) IStockGUIView (Interface containing all the methods a GUI view must implement)
2) StockGUIView (implements the IStockGUIView interface)

The StockGUIView contains components like JButton, JTextField and JLabel to interact 
with the user. When an event is generated the callback function from controller is called.

The view is a tabbed view which keeps a different tab for each and every single command.

OverALL:

The program contains two controllers and two views that are coexisting. The model is only
ONE and is communicating with the controller. The controller get initialized and starts talking to model and view. It calls methods in
view to input and output the data from the user. It talks with model to store and 
manipulate this data.

We need seperate controller and view for GUI and seperate controller and view for the 
command line view.

Overall it is a very efficient and readable design.
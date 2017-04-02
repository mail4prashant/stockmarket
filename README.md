# Super Simple Stock Markets

Super simple stocks is an application to simulate basic trading activities in stock market exchange. 
It expose an api which client can use to do follwoing operation in stock market exchange.
- Maintain Stock Reference data. i.e. Add Stock , Get Stock, remove stock
- Record Trades for Stocks.
- Calculate various importants results for stocks.
 
## Getting Started


### Prerequisites

You need jdk 8, maven to compile, build and run the project.


### Installing

Clone the git repo and build the project using following maven command.

mvn clean install

### API usage

Application expose following apis.
1. IStockRefDataService

This service should be used to perform reference data operation in stock market exchange. ie. adding stock, removing stock, getting stock etc.
This service can be available using following code.

IStockRefDataService refDataService = StockRefDataService.getInstance()

2. ITradeCaptureService

This service should be used to capture trades in stock markets and find out trades for given stocks. record buy/sell side trade, query trades for stock
This service can be available using following code.

ITradeCaptureService tradeCaptureService = TradeCaptureService.getInstance()

3. IStockExchangeService

This service should be used to get various different calculations in stock markets. 
This service can be available using following code.

	IStockRefDataService stockRefDataService = StockRefDataService.getInstance();
	ITradeCaptureService tradeCaptureService = TradeCaptureService.getInstance();
	IStockExchangeService stockExchangeService = new StockExchangeService(stockRefDataService, tradeCaptureService);

## Running the tests

There are unit tests written using TestNG library. You can execute the tests using following maven command.

mvn test

### Unit Tests description

There are following unit tests.

StockExchangeServiceTest : This unit test tests StockExchangeService methods.  

StockRefDataServiceTest : This unit test tests StockRefDataService methods. 

TradeCaptureServiceTest : This unit test tests TradeCaptureService methods.


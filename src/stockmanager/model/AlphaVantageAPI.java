package stockmanager.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is to fetch stock data from API.
 */
public class AlphaVantageAPI implements GenericStockDataFetcher {
  private Map<String, String> cachedData = new HashMap<>();

  private String getStockData(String ticker) {
    if (cachedData.containsKey(ticker)) {
      return cachedData.get(ticker);
    } else {
      return generateStockData(ticker);
    }
  }

  @Override
  public String generateStockData(String ticker) throws IllegalArgumentException {
    String apiKey = "ZNAMZR3BEED8GKLF";
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */

      /*try {
        Thread.sleep(10000);
      }catch (Exception ex){
        //do nothing
      }*/
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    String temp;
    temp = output.toString();
    validateTicker(temp);
    cachedData.put(ticker, temp);
    return temp;
  }

  @Override
  public double getPriceOnADate(String ticker, Date date) throws IllegalArgumentException {
    String stockData;
    stockData = getStockData(ticker);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    StringBuilder sb = new StringBuilder();
    int i;
    i = stockData.indexOf(dateFormat.format(date));
    if (i == -1) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.DATE, 1);
      date = calendar.getTime();
      return getPriceOnADate(ticker, date);
    }
    for (; stockData.charAt(i) != '\n'; i++) {
      sb.append(stockData.charAt(i));
    }
    String[] array = sb.toString().split(",");
    return Double.parseDouble(array[4]);
  }

  @Override
  public double getMostRecentPrice(String ticker) throws IllegalArgumentException {
    String stockData;
    stockData = getStockData(ticker);
    int i = stockData.substring(38, 100).indexOf("\n");
    String[] array = stockData.substring(38, 37 + i).split(",");
    return Double.parseDouble(array[4]);
  }

  /**
   * Generates an error message about ticker.
   *
   * @param str the error message from API
   * @throws IllegalArgumentException the message contains error information
   */
  private void validateTicker(String str) throws IllegalArgumentException {
    if (str.contains("Error")) {
      throw new IllegalArgumentException("INVALID TICKER : The ticker symbol does not exist ");
    }
    if (str.contains("Thank you for using Alpha Vantage")) {
      throw new IllegalArgumentException("API LIMIT EXCEEDED: 5 calls per minute only. ");
    }
  }

}

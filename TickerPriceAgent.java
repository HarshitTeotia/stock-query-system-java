package agents;

import utils.HttpClientHelper;
import com.google.gson.*;

public class TickerPriceAgent {
    public static String getCurrentPrice(String ticker, String apiKey) {
        String url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s", ticker, apiKey);
        String json = HttpClientHelper.get(url);

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        JsonObject timeSeries = obj.getAsJsonObject("Time Series (5min)");

        if (timeSeries != null && !timeSeries.entrySet().isEmpty()) {
            String latestTime = timeSeries.keySet().iterator().next();
            JsonObject latestData = timeSeries.getAsJsonObject(latestTime);
            return latestData.get("4. close").getAsString();
        }
        return "Price unavailable.";
    }
}


package agents;

import utils.HttpClientHelper;
import com.google.gson.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TickerPriceChangeAgent {
    public static String getPriceChange(String ticker, String apiKey, int daysBack) {
        String url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", ticker, apiKey);
        String json = HttpClientHelper.get(url);

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        JsonObject timeSeries = obj.getAsJsonObject("Time Series (Daily)");

        if (timeSeries == null || timeSeries.size() == 0) {
            return "Price change data unavailable.";
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Find latest available date
        String latestDate = timeSeries.keySet().iterator().next();
        JsonObject latestData = timeSeries.getAsJsonObject(latestDate);
        double latestClose = Double.parseDouble(latestData.get("4. close").getAsString());

        // Find the closing price 'daysBack' ago
        String pastDate = null;
        int count = 0;
        for (String date : timeSeries.keySet()) {
            if (count == daysBack) {
                pastDate = date;
                break;
            }
            count++;
        }

        if (pastDate == null) {
            return "Not enough historical data.";
        }

        JsonObject pastData = timeSeries.getAsJsonObject(pastDate);
        double pastClose = Double.parseDouble(pastData.get("4. close").getAsString());

        double change = latestClose - pastClose;
        double percent = (change / pastClose) * 100;

        return String.format("%.2f%% (%s → %s)", percent, pastClose, latestClose);
    }
}


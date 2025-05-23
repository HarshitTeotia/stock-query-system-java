package agents;

import utils.HttpClientHelper;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class TickerAnalysisAgent {
    public static String analyze(String ticker, String stockApiKey, String newsApiKey) {
        // Step 1: Get recent price change
        String priceChange = TickerPriceChangeAgent.getPriceChange(ticker, stockApiKey, 7);

        // Step 2: Get recent news titles
        List<String> headlines = getTopNewsHeadlines(ticker, newsApiKey);
        if (headlines.isEmpty()) {
            return "Stock changed: " + priceChange + "\nNo news articles found to explain movement.";
        }

        // Step 3: Return simple analysis summary
        StringBuilder analysis = new StringBuilder();
        analysis.append("Stock changed: ").append(priceChange).append("\n");
        analysis.append("Possible reasons based on recent news:\n");

        for (String title : headlines) {
            analysis.append("• ").append(title).append("\n");
        }

        return analysis.toString();
    }

    private static List<String> getTopNewsHeadlines(String ticker, String apiKey) {
        List<String> headlines = new ArrayList<>();

        String url = String.format("https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&apiKey=%s", ticker, apiKey);
        String json = HttpClientHelper.get(url);

        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonArray articles = obj.getAsJsonArray("articles");

            int count = Math.min(3, articles.size());
            for (int i = 0; i < count; i++) {
                JsonObject article = articles.get(i).getAsJsonObject();
                String title = article.get("title").getAsString();
                headlines.add(title);
            }
        } catch (Exception e) {
            headlines.add("Error parsing news headlines.");
        }

        return headlines;
    }
}


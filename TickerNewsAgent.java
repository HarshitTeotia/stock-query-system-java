package agents;

import utils.HttpClientHelper;
import com.google.gson.*;

public class TickerNewsAgent {
    public static String getRecentNews(String ticker, String apiKey) {
        // Using NewsAPI to get recent news
        String url = String.format("https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&apiKey=%s", ticker, apiKey);
        String json = HttpClientHelper.get(url);

        StringBuilder newsSummary = new StringBuilder();
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonArray articles = obj.getAsJsonArray("articles");

            if (articles == null || articles.size() == 0) {
                return "No recent news found.";
            }

            int count = Math.min(3, articles.size()); // Show top 3 articles
            for (int i = 0; i < count; i++) {
                JsonObject article = articles.get(i).getAsJsonObject();
                String title = article.get("title").getAsString();
                String source = article.getAsJsonObject("source").get("name").getAsString();
                newsSummary.append("• ").append(title).append(" (").append(source).append(")\n");
            }
        } catch (Exception e) {
            return "Failed to parse news data.";
        }

        return newsSummary.toString();
    }
}


package agents;
import java.util.HashMap;

public class IdentifyTickerAgent {
    private static final HashMap<String, String> companyToTicker = new HashMap<>();
    static {
        companyToTicker.put("Tesla", "TSLA");
        companyToTicker.put("Nvidia", "NVDA");
        companyToTicker.put("Palantir", "PLTR");
    }

    public static String identifyTicker(String query) {
        for (String company : companyToTicker.keySet()) {
            if (query.toLowerCase().contains(company.toLowerCase())) {
                return companyToTicker.get(company);
            }
        }
        return null;
    }
}

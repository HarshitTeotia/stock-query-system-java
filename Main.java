package org.example;

import agents.*;
import java.util.Scanner;

public class Main{
    // Replace with your actual API keys
    private static final String ALPHA_VANTAGE_API_KEY = "XOGLUOMDG2E0BBDS";
    private static final String NEWS_API_KEY = "1aaa3cb35c944360bb1bc36ac571d86a";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Multi-Agent Stock Analyzer!");
        System.out.print("Enter your stock-related question: ");
        String userQuery = scanner.nextLine();

        // Step 1: Identify the stock ticker
        String ticker = IdentifyTickerAgent.identifyTicker(userQuery);
        if (ticker == null) {
            System.out.println("Could not identify a valid stock ticker from your query.");
            return;
        }
        System.out.println("Identified Ticker: " + ticker);

        // Step 2: Get current stock price
        String price = TickerPriceAgent.getCurrentPrice(ticker, ALPHA_VANTAGE_API_KEY);
        System.out.println("Current Price of " + ticker + ": $" + price);

        // Step 3: Get price change (last 7 days)
        String priceChange = TickerPriceChangeAgent.getPriceChange(ticker, ALPHA_VANTAGE_API_KEY, 7);
        System.out.println("Price Change over 7 days: " + priceChange);

        // Step 4: Get recent news
        String newsSummary = TickerNewsAgent.getRecentNews(ticker, NEWS_API_KEY);
        System.out.println("Recent News: \n" + newsSummary);

        // Step 5: Analyze the situation
        String analysis = TickerAnalysisAgent.analyze(ticker, ALPHA_VANTAGE_API_KEY, NEWS_API_KEY);
        System.out.println("Analysis: " + analysis);
    }
}

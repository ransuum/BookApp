package org.bookmap;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        OrderBook orderBook = new OrderBook();
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                String action = array[0];
                if ("u".equals(action)) {
                    if (array.length >= 4) {
                        int price = Integer.parseInt(array[1]);
                        int size = Integer.parseInt(array[2]);
                        String orderType = array[3];
                        orderBook.LimitOrder(orderType, price, size);
                    } else {
                        writer.write("Error");
                        writer.newLine();
                    }
                } else if ("q".equals(action)) {
                    String queryType = array[1];
                    if ("best_bid".equals(queryType) || "best_ask".equals(queryType)) {
                        String result = orderBook.getBestPriceAndSize(queryType);
                        writer.write(result);
                        writer.newLine();
                    } else if ("size".equals(queryType) && array.length >= 4) {
                        int price = Integer.parseInt(array[2]);
                        int size = orderBook.getSizeAtPrice(array[3], price);
                        StringBuilder result = new StringBuilder();
                        result.append(size).append(",").append(price);
                        writer.write(result.toString());
                        writer.newLine();
                    }
                } else if ("o".equals(action)) {
                    if (array.length >= 3) {
                        String orderType = array[1];
                        int size = Integer.parseInt(array[2]);

                        if ("buy".equals(orderType)) {
                            orderBook.MarketOrder(orderType, size);
                        } else if ("sell".equals(orderType)) {
                            orderBook.MarketOrder(orderType, size);
                        }
                        writer.write(Integer.toString(size));
                        writer.newLine();
                    }
                }
            }
        }
    }
}

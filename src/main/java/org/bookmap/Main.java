package org.bookmap;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        OrderBook orderBook = new OrderBook();
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s+", " ");
                line = line.replaceAll("^\\s*u", "u");
                line = line.replaceAll("^\\s*q", "q");
                line = line.replaceAll("^\\s*o", "o");
                String[] array;
                if (line.contains(",")) {
                    array = line.trim().split(",\\s*");
                } else {
                    array = line.trim().split("\\s+");
                }
                String action = array[0];
                if (action.isEmpty()) {
                    writer.write("No action there");
                    System.exit(0);
                } else {
                    if (array[0].equals("u")) {
                        if (array.length >= 4) {
                            String orderType = array[3].trim();
                            int price = Integer.parseInt(array[1].trim());
                            int size = Integer.parseInt(array[2].trim());
                            if (price < 1 || price > 109 || size < 0 || size > 108) {
                                writer.write("Error: size > 108 or < 0, price > 109 or < 1");
                                writer.newLine();
                            } else {
                                orderBook.LimitOrder(orderType, price, size);
                            }
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
                            if (price < 1 || price > 109 || size < 0 || size > 108) {
                                writer.write("Error: size > 108 or < 0, price > 109 or < 1");
                                writer.newLine();
                            } else {
                                writer.write(price + "," + size);
                                writer.newLine();
                            }
                        }
                    } else if ("o".equals(action)) {
                        if (array.length >= 3) {
                            String orderType = array[1];
                            int size = Integer.parseInt(array[2]);
                            if (size < 0 || size > 108) {
                                writer.write("Error: size > 108 or < 0");
                                writer.newLine();
                            } else {
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
    }
}

package org.bookmap;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class OrderBook {
    private final TreeMap<Integer, Integer> bidOrders;
    private final TreeMap<Integer, Integer> askOrders;

    public OrderBook(){
        bidOrders = new TreeMap<>(Collections.reverseOrder());
        askOrders = new TreeMap<>();
    }
    public void LimitOrder(String type, int price, int size) {
        if ("bid".equals(type)) {
            if (size > 0) {
                bidOrders.put(price, size);
            } else {
                bidOrders.remove(price);
            }
        } else if ("ask".equals(type)) {
            if (size > 0) {
                askOrders.put(price, size);
            } else {
                askOrders.remove(price);
            }
        }
    }

    public void MarketOrder(String type, int size) {
        TreeMap<Integer, Integer> orders;

        if ("buy".equals(type)) {
            orders = askOrders;
        } else if ("sell".equals(type)) {
            orders = bidOrders;
        } else {
            return;
        }
        int remainingSize = size;
        for (Map.Entry<Integer, Integer> entry : orders.entrySet()) {
            int price = entry.getKey();
            int orderSize = entry.getValue();
            if (remainingSize >= orderSize) {
                remainingSize -= orderSize;
                orders.remove(price);
            } else {
                orders.put(price, orderSize - remainingSize);
                break;
            }
        }
    }
    public String getBestPriceAndSize(String type) {
        Map<Integer, Integer> orders = ("best_bid".equals(type)) ? bidOrders : askOrders;
        if (orders.isEmpty()) {
            return "0,0";
        }
        int bestPrice = type.equals("best_bid") ? orders.keySet().stream().max(Integer::compareTo).get() :
                orders.keySet().stream().min(Integer::compareTo).get();
        int bestSize = orders.get(bestPrice);
        return bestPrice + "," + bestSize;
    }

    public int getSizeAtPrice(String type, int price) {
        Map<Integer, Integer> orders = ("bid".equals(type)) ? bidOrders : askOrders;
        return orders.getOrDefault(price, 0);
    }
    public boolean isBidPrice(int price) {
        return bidOrders.containsKey(price);
    }
    public boolean isAskPrice(int price) {
        return askOrders.containsKey(price);
    }
}

package service.impl;

import model.order.Order;
import model.enums.OrderStatus;
import service.OrderService;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private final Map<Integer, Order> orderMap = new HashMap<>();
    private int orderIdCounter = 1;

    @Override
    public void placeOrder(Order order) {
        synchronized (this) { // Thread-safety for order placement
            order.setId(orderIdCounter++);
        }
        order.setOrderStatus(String.valueOf(OrderStatus.ORDERED));
        orderMap.put(order.getId(), order);

        System.out.println("Order placed successfully: " + order);
    }

    @Override
    public String getOrderStatus(int orderId) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            return "Order Status: " + order.getOrderStatus();
        }
        return "Order not found!";
    }

    @Override
    public void cancelOrder(Order order) {
        if (orderMap.containsKey(order.getId())) {
            orderMap.remove(order.getId());
            System.out.println("Order canceled: " + order);
        } else {
            System.out.println("Unable to cancel. Order not found: " + order.getId());
        }
    }

    @Override
    public void updateOrderStatus(int orderId, OrderStatus newStatus) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.setOrderStatus(String.valueOf(newStatus));
            System.out.println("Order ID " + orderId + " status updated to: " + newStatus);
        } else {
            System.out.println("Order not found: " + orderId);
        }
    }

    @Override
    public Map<Integer, Order> listAllOrders() {
        System.out.println("Listing all orders:");
        orderMap.values().forEach(System.out::println);
        return new HashMap<>(orderMap);
    }
}

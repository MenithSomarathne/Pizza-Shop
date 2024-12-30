package service;

import model.enums.OrderStatus;
import model.order.Order;

import java.util.Map;

public interface OrderService {
    void placeOrder(Order order);
    String getOrderStatus(int orderId);
    void cancelOrder(Order order);
    void updateOrderStatus(int orderId, OrderStatus newStatus);
    Map<Integer, Order> listAllOrders();
}

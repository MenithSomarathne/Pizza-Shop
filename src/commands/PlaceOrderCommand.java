package commands;

import model.order.Order;
import service.OrderService;

public class PlaceOrderCommand implements Command {
    private final OrderService orderService;
    private final Order order;

    public PlaceOrderCommand(OrderService orderService, Order order) {
        this.orderService = orderService;
        this.order = order;
    }

    @Override
    public void execute() {
        orderService.placeOrder(order);
        System.out.println("Order placed: " + order);
    }

    @Override
    public void undo() {
        orderService.cancelOrder(order);
        System.out.println("Order canceled: " + order);
    }
}

package model.order;

public class CompletedState implements OrderState {
    @Override
    public void handleOrder(Order order) {

        order.setOrderStatus("Order is completed. Thank you for your purchase!");
//        System.out.println("Order is completed. Thank you for your purchase!");
    }
}
